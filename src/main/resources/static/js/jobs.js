$(function() {

    const emptyTask = {
        name: '',
        image: '',
        imageVersion: '',
        ports: [],
        envs: [],
        volumes: []
    };

    Vue.use(VueRouter)

    var bus = new Vue();

    Vue.component('alerts', {
        template: "#jobs-alert",
        data: function(){
            return({
                messages: []
            });
        },
        created: function() {
            bus.$on('alert', function(message){
              this.messages.push(message);
            }.bind(this));
        },
        methods: {
            remove(item) {
                this.messages.remove(item);
            }
        }
    });

    Vue.component('jobsList', {
        template: '#jobs-template',
        data: function() {
            return {
                jobs: []
            }
        },
        created() {
            this.jobsResource = this.$resource('/api/jobs{/id}');
            this.fetchJobs();
        },
        methods: {
           fetchJobs() {
                this.jobsResource.get().then((response) => {
                    this.jobs = response.body;
                });
           },
           showForm(id) {
               router.push({ name: 'job-form', params: { jobId: id }})
           },
           remove(id) {
                this.jobsResource.delete({id: id}).then((response) => {
                    this.fetchJobs();
                });
           }
        }
    });

    Vue.component('jobForm', {
        template: '#job-form',
        data: function() {
            return {
                job: {
                    metadata: [],
                    tasks: []
                }
            }
        },
        created() {
            this.jobsResource = this.$resource('/api/jobs/{id}');
            this.fetchSingleJob();
        },
        methods: {
            fetchSingleJob() {
                this.jobsResource.get({id: this.$route.params.jobId}).then((response) => {
                   if (response.status === 200) {
                        this.job = response.body;
                   }
                });
            },
            save() {
                this.jobsResource.save({}, this.job).then((response) => {
                   router.push("/");
                   bus.$emit('alert', {title: "Success", level: "success", text: "Job saved."});
               });
            },
            cancel() {
                router.push("/")
            },
            addMetadata: function() {
                this.job.metadata.push({key:"", value:""});
            },
            addTask() {
                this.job.tasks.push(Vue.util.extend({}, emptyTask));
            },
            addPort(taskIndex) {
                this.job.tasks[taskIndex].ports.push({});
            },
            addEnv(taskIndex) {
                this.job.tasks[taskIndex].envs.push({});
            },
            addVolume(taskIndex) {
                this.job.tasks[taskIndex].volumes.push({});
            }
        }
    });

    const router = new VueRouter({
      routes: [
        { path: '/', name: 'jobs-list', component: Vue.component('jobsList'), canReuse:false },
        { path: '/job/:jobId', name: 'job-form', component: Vue.component('jobForm') }
      ]
    });

    new Vue({
      router
    }).$mount('#app-jobs');
});