var StatisticsEventBus = StatisticsEventBus || {};

var StatisticsEventBus = function(clusterId, nodeId, containerId) {
    this.source = new EventSource("/cluster/" + clusterId + "/node/" + nodeId + "/container/" + containerId + "/statistics");
    this.source.eventBus = this;
    this.source.onmessage = this.process;
    this.source.onopen = function(e) {
       console.log("StatisticsEventBus: Connection was opened.");
    };

    this.subscriptions = [];
};

StatisticsEventBus.prototype = {
    addListener: function(type, listener) {
        this.subscriptions[type] = listener;
    },
    process: function(event) {
        var data = JSON.parse(event.data);

        for (var key in this.eventBus.subscriptions) {
            var listener = this.eventBus.subscriptions[key];
            listener.update(data[key]);
        }
    }
};

var StatisticChart = function (element) {
    this.element = document.getElementById(element);
    this.dataSet = new vis.DataSet();
    this.options = {
        drawPoints: false,
        moveable: false,
        showCurrentTime: false,
        start: vis.moment().add(-30, 'seconds'),
        end: vis.moment(),
        shaded: {
          orientation: 'bottom'
        },
        graphHeight: 250
    };
    this.graph2d = new vis.Graph2d(this.element, this.dataSet, this.options);
    this.refresh();
};

StatisticChart.prototype = {
    refresh: function() {
        var now = vis.moment();
        var range = this.graph2d.getWindow();
        var interval = range.end - range.start;

        this.graph2d.setWindow(now - interval, now, {animation: false});
        requestAnimationFrame(this.refresh.bind(this));
    },
    update: function(value) {
        var now = vis.moment();

        this.dataSet.add({
          x: now,
          y: value
        });

        var range = this.graph2d.getWindow();
        var interval = range.end - range.start;
        var oldIds = this.dataSet.getIds({
            filter: function (item) {
                return item.x < range.start - interval;
            }
        });
        this.dataSet.remove(oldIds);
    }
}