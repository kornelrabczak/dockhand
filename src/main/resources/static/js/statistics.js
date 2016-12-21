(function() {
    var container = document.getElementById('visualization');

    var dataset = new vis.DataSet();

    var options = {
        drawPoints: false,
        moveable: false,
        showCurrentTime: false,
        start: vis.moment().add(-30, 'seconds'),
        end: vis.moment(),
        shaded: {
          orientation: 'bottom'
        }
    };
    var graph2d = new vis.Graph2d(container, dataset, options);

    function renderStep() {
        var now = vis.moment();
        var range = graph2d.getWindow();
        var interval = range.end - range.start;

        graph2d.setWindow(now - interval, now, {animation: false});
        requestAnimationFrame(renderStep);
    }
    requestAnimationFrame(renderStep);

    var source = new EventSource('/statistics/subscribe/-6284378544578729124/-939424790988208844/24614648d3003d75424e38eee76cfa3f7ea303162fcd9727a640d5a8b2e2df1f');

    source.onmessage = function(event) {
        console.log(event);
        console.log(dataset);
        var now = vis.moment();
        dataset.add({
          x: now,
          y: event.data
        });

        var range = graph2d.getWindow();
        var interval = range.end - range.start;
        var oldIds = dataset.getIds({
          filter: function (item) {
            return item.x < range.start - interval;
          }
        });
        dataset.remove(oldIds);
    };

    source.onopen = function(e) {
       console.log("Connection was opened.");
    };
})();
