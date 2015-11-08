var canvas = this.document.getElementById("canvas");
var context = canvas.getContext("2d");
var spaceOffsetLine = 24;
var multipleOfScale = spaceOffsetLine / 8;
var maxMultipleOfScale = 39;
var minMultipleOfScale = 10;

drawBoardLine(context,spaceOffsetLine);

function drawBoardLine(context,spaceOffsetLine){
    context.clearRect(0,0,this.window.screen.width,this.window.screen.height);
    for(var x = 0;x < this.window.screen.width; x = x + spaceOffsetLine){
        context.beginPath();
        context.lineWidth = "1";
        context.strokeStyle = "#FF0000";
        context.moveTo(x,0)
        context.lineTo(x,this.window.screen.height);
        context.stroke();
    }

    for(var y = 0;y < this.window.screen.height; y = y + spaceOffsetLine){
        context.beginPath();
        context.lineWidth = "1";
        context.moveTo(0,y)
        context.lineTo(this.window.screen.width,y);
        context.stroke();
    }
}




if (canvas.addEventListener) {
    // IE9, Chrome, Safari, Opera
    canvas.addEventListener("mousewheel", onMouseWheelHandler, false);
    // Firefox
    canvas.addEventListener("DOMMouseScroll", onMouseWheelHandler, false);
}else{
    // IE 6/7/8
    canvas.attachEvent("onmousewheel", onMouseWheelHandler);
}

function onMouseWheelHandler(event){
    event.preventDefault();
    var e = window.event || event; // old IE support
    var delta = Math.max(-1, Math.min(1, (e.wheelDelta || -e.detail)));
    if(delta > 0 && spaceOffsetLine < maxMultipleOfScale){
        //向上滚动
        spaceOffsetLine = spaceOffsetLine + multipleOfScale;
        drawBoardLine(context,spaceOffsetLine);
    }else if(delta < 0 && spaceOffsetLine > minMultipleOfScale){
        //向下滚动
        spaceOffsetLine = spaceOffsetLine - multipleOfScale;
        drawBoardLine(context,spaceOffsetLine);
    }

//    console.log(delta+"->"+ e.wheelDelta+'->'+ e.detail);
    }
