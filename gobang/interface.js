var canvas = this.document.getElementById("canvas");
canvas.draggable = true;
canvas.addEventListener("dragstart", onMouseDragStartHandler);
canvas.addEventListener("drag", onMouseDragHandler);
canvas.addEventListener("dragend", onMouseDragEndHandler);

var context = canvas.getContext("2d");

var marginOffset = 100;

var startOfX = 0 - marginOffset;
var startOfY = 0 - marginOffset;

canvas.width = this.window.screen.width + marginOffset;
canvas.height = this.window.screen.width + marginOffset;

var endOfX = this.window.screen.width + marginOffset;
var endOfY = this.window.screen.height + marginOffset;

/*初始化地图线条的间隔*/
var spaceOffsetLine = 24;
/*定义每次滚动鼠标滚轮缩放地图线条间隔的比率*/
var multipleOfScale = spaceOffsetLine / 8;
/*最大的地图线条的间隔*/
var maxMultipleOfScale = 39;
/*最小的地图线条的间隔*/
var minMultipleOfScale = 10;

/*初始化绘制地图*/
drawBoardLine(context, startOfX, startOfY, endOfX, endOfY, spaceOffsetLine);

/**
 * 绘制地图线条的函数
 * @param context
 * @param startOfX
 * @param startOfY
 * @param endOfX
 * @param endOfY
 * @param spaceOffsetLine
 */
function drawBoardLine(context, startOfX, startOfY, endOfX, endOfY, spaceOffsetLine) {
    context.clearRect(startOfX, startOfY, endOfX, endOfY);

    var counterOfX = 0;
    for (var x = startOfX; x < endOfX; x = x + spaceOffsetLine) {
        context.beginPath();
        context.lineWidth = 2;
        context.strokeStyle = "#FF0000";
        context.moveTo(x, 0)
        context.lineTo(x, endOfY);
        context.stroke();

        drawText(context,counterOfX,x - 3,20,20);

        counterOfX++;

        console.log(counterOfX);
    }

    var counterOfY = 0;
    for (var y = startOfY; y < endOfY; y = y + spaceOffsetLine) {
        context.beginPath();
        context.lineWidth = 2;
        context.moveTo(0, y)
        context.lineTo(endOfX, y);
        context.stroke();

        drawText(context,counterOfY + "", 5, y + 3, 20);
        counterOfY++;
    }
}

function drawText(context,text,x,y,maxWidth){
    context.save();
    context.font = "20px serif";
    context.fillText(text, x, y, maxWidth);
    context.restore();
}

if (canvas.addEventListener) {
    // IE9, Chrome, Safari, Opera
    canvas.addEventListener("mousewheel", onMouseWheelHandler, false);
    // Firefox
    canvas.addEventListener("DOMMouseScroll", onMouseWheelHandler, false);
} else {
    // IE 6/7/8
    canvas.attachEvent("onmousewheel", onMouseWheelHandler);
}

function onMouseWheelHandler(event) {
    event.preventDefault();
    var e = window.event || event; // old IE support
    var delta = Math.max(-1, Math.min(1, (e.wheelDelta || -e.detail)));
    if (delta > 0 && spaceOffsetLine < maxMultipleOfScale) {
        //向上滚动
        spaceOffsetLine = spaceOffsetLine + multipleOfScale;
        drawBoardLine(context, startOfX, startOfY, endOfX, endOfY, spaceOffsetLine);
    } else if (delta < 0 && spaceOffsetLine > minMultipleOfScale) {
        //向下滚动
        spaceOffsetLine = spaceOffsetLine - multipleOfScale;
        drawBoardLine(context, startOfX, startOfY, endOfX, endOfY, spaceOffsetLine);
    }
}

function onMouseDragStartHandler(event) {
    console.log("on drag start" + event.x + ":" + event.y + ":" + event.clientX + ":" + event.clientY);
}


function onMouseDragHandler(event) {
    console.log("on drag " + event.x + ":" + event.y + ":" + event.clientX + ":" + event.clientY);
}



function onMouseDragEndHandler(event) {
    console.log("on drag end" + event.x + ":" + event.y + ":" + event.clientX + ":" + event.clientY);
}
