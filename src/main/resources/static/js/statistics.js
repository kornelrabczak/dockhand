(function() {
   /*var graphData = [];

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
*/

    console.log('test 222');

    var source = new EventSource('/statistics/subscribe/-6284378544578729124/-939424790988208844/24614648d3003d75424e38eee76cfa3f7ea303162fcd9727a640d5a8b2e2df1f');

    source.onmessage = function(event) {
        console.log(event);
//       var graphPoint = {};
//       graphPoint.timestamp = new Date().getTime();
//       graphPoint.memory = e.data;
//
//       graphData.splice(0, 1);
//       graphData.push(graphPoint);
//
//       mainGraph.setData(graphData);
    };

    source.onopen = function(e) {
       console.log("Connection was opened.");
    };
})();
