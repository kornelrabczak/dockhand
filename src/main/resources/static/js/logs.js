var LogsPrinter = function(element, clusterId, nodeId, containerId) {
    this.myCodeMirror = CodeMirror.fromTextArea(document.getElementById("code"), {
       lineNumbers: true,
       lineWrapping: true,
       readOnly:true,
       mode:  "javascript",
       theme: "monokai"
    });

    this.source = new EventSource("/cluster/" + clusterId + "/node/" + nodeId + "/container/" + containerId + "/logs");
    this.source.logsPrinter = this;
    this.source.onmessage = this.handleEvent;
    this.source.onopen = function(e) {
        console.log("Connection was opened.");
    };
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
    }
}