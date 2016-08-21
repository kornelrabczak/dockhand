//var source = new EventSource('/statistics/subscribe//cc8fa66f9da7');
//
//source.addEventListener('message', function(e) {
//   console.log(e.data);
//});
//
//source.addEventListener('open', function(e) {
//   console.log("Connection was opened.");
//}, false);

(function() {
    var canvas = document.getElementById('lineChart'),
        ctx = canvas.getContext('2d'),
        startingData = {
          labels: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
          datasets: [
              {
                  fillColor: "rgba(220,220,220,0.2)",
                  strokeColor: "rgba(220,220,220,1)",
                  pointColor: "rgba(220,220,220,1)",
                  pointStrokeColor: "#fff",
                  data: [65, 59, 80, 81, 56, 55, 40, 12, 34, 55]
              }
          ]
        },
        latestLabel = startingData.labels[9];

    var myLiveChart = new Chart(ctx, {
        type: "line",
        data: startingData,
        animationSteps: 30
    });

    setInterval(function(){
      myLiveChart.addData([Math.random() * 100], ++latestLabel);
      myLiveChart.removeData();
    }, 1000);

})();
