(function() {
   var graphData = [];

    // initialize graph data
    for(var i = 0; i < 10; i++) {
        var graphPoint = {};
        graphPoint.timestamp = new Date().getTime();
        graphPoint.memory = 0;
        graphData.push(graphPoint);
    }

    var mainGraph = Morris.Line({
        element: 'myfirstchart',
        data: graphData,
        xkey: 'timestamp',
        ykeys: ['memory'],

        postUnits: ' °c',
        lineColors: ['#199cef'],
        goals: [6.0],
        goalLineColors: ['#FF0000'],
        labels: ['Temperature'],
        lineWidth: 3,
        pointSize: 2,
        resize: true
    });

    var source = new EventSource('/statistics/subscribe/cc8fa66f9da7');

    source.addEventListener('message', function(e) {
       var graphPoint = {};
       graphPoint.timestamp = new Date().getTime();
       graphPoint.memory = e.data;

       graphData.splice(0, 1);
       graphData.push(graphPoint);

       mainGraph.setData(graphData);
    });

    source.addEventListener('open', function(e) {
       console.log("Connection was opened.");
    }, false);
})();
