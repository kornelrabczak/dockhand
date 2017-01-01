var LogsPrinter = function(element, clusterId, nodeId, containerId) {
    this.clusterId = clusterId;
    this.nodeId = nodeId;
    this.containerId = containerId;
    this.myCodeMirror = CodeMirror.fromTextArea(document.getElementById("code"), {
       lineNumbers: true,
       lineWrapping: true,
       readOnly:true,
       mode:  "javascript",
       theme: "monokai"
    });

    this.myCodeMirror.setValue("");
    this.myCodeMirror.clearHistory();
    this.refresh("");
}

LogsPrinter.prototype = {
    handleEvent: function(event) {
        var string = event.data.replace(/\*/, '');
        this.logsPrinter.refresh(string);
    },
    refresh: function(data) {
        var pos = {
            line: this.myCodeMirror.lastLine(),
            ch: 0
        };
        this.myCodeMirror.replaceRange(data + "\n", pos);
        this.myCodeMirror.scrollIntoView(this.myCodeMirror.lastLine());
    },
    start: function() {
        this.source = new EventSource("/cluster/" + this.clusterId + "/node/" + this.nodeId + "/container/" + this.containerId + "/logs");
        this.source.logsPrinter = this;
        this.source.onmessage = this.handleEvent;
        this.source.onopen = function(e) {
            console.log("LogsPrinter: Connection was opened.");
            this.logsPrinter.myCodeMirror.setValue("");
            this.logsPrinter.myCodeMirror.clearHistory();
        };
    },
    stop: function() {
        if (typeof this.source != "undefined") {
            this.source.close();
        }
    }
}