var canvas = this.document.getElementById("canvas");
canvas.draggable = true;
//
//if (checkerboardCanvas.addEventListener) {
//    // IE9, Chrome, Safari, Opera
//    checkerboardCanvas.addEventListener("mousewheel", onMouseWheelHandler, false);
//    // Firefox
//    checkerboardCanvas.addEventListener("DOMMouseScroll", onMouseWheelHandler, false);
//} else {
//    // IE 6/7/8
//    checkerboardCanvas.attachEvent("onmousewheel", onMouseWheelHandler);
//}

canvas.addEventListener("dragstart", onMouseDragStartHandler);
canvas.addEventListener("drag", onMouseDragHandler);
canvas.addEventListener("dragend", onMouseDragEndHandler);
canvas.addEventListener("click", onClickHandler);

if (window.localStorage) {
} else {
    alert("浏览暂不支持localStorage")
}
 //或者 if(typeof window.localStorage == 'undefined'){ 	alert("浏览暂不支持localStorage") }

var context = canvas.getContext("2d");

var marginOffset = 100;

var startOfX = 0 - marginOffset;
var startOfY = 0 - marginOffset;

canvas.width = this.window.screen.width + marginOffset;
canvas.height = this.window.screen.height + marginOffset;

var endOfX = this.window.screen.width + marginOffset;
var endOfY = this.window.screen.height + marginOffset;

/*初始化地图线条的间隔*/
var spaceOffsetLine = 48;
/*定义每次滚动鼠标滚轮缩放地图线条间隔的比率*/
var multipleOfScale = spaceOffsetLine / 3;
/*最大的地图线条的间隔*/
var maxMultipleOfScale = 76;
/*最小的地图线条的间隔*/
var minMultipleOfScale = 10;

/*初始化绘制地图*/
//drawBoardLine(checkerboardCanvasContext, startOfX, startOfY, endOfX, endOfY, spaceOffsetLine);
drawMapGrid(context, this.window.screen.width / 2, this.window.screen.height / 2, spaceOffsetLine, 0, 0);

/**
 * 滚轮滚动事件
 * @param event
 */
function onMouseWheelHandler(event) {
    event.preventDefault();
    var e = window.event || event; // old IE support
    var delta = Math.max(-1, Math.min(1, (e.wheelDelta || -e.detail)));
    if (delta > 0 && spaceOffsetLine < maxMultipleOfScale) {
        //向上滚动
        spaceOffsetLine = spaceOffsetLine + multipleOfScale;
        drawMapGrid(context, event.x, event.y, spaceOffsetLine, 0, 0);
    } else if (delta < 0 && spaceOffsetLine > minMultipleOfScale) {
        //向下滚动
        spaceOffsetLine = spaceOffsetLine - multipleOfScale;
        drawMapGrid(context, event.x, event.y, spaceOffsetLine, 0, 0);
    }
}

var onStartX, onStartY, onMoveX, onMoveY, onEndX, onEndY;
function onMouseDragStartHandler(event) {
    //console.log("on drag start" + event.x + ":" + event.y + ":" + event.clientX + ":" + event.clientY);
    onStartX = onMoveX = event.x;
    onStartY = onMoveY = event.y;
}


function onMouseDragHandler(event) {
    if (event.x == 0 && event.y == 0 && event.clientX == 0 && event.clientY == 0) {
    } else {
        //console.log("on drag " + event.x + ":" + event.y + ":" + event.clientX + ":" + event.clientY);
        onEndX = onMoveX = event.x;
        onEndY = onMoveY = event.y;

        drawMapGrid(context, onMoveX, onMoveY, spaceOffsetLine, 0, 0);
    }
}

function onMouseDragEndHandler(event) {
    //console.log("on drag end" + event.x + ":" + event.y + ":" + event.clientX + ":" + event.clientY);
}
var flag;
function onClickHandler(event) {
    context.save();
    context.beginPath();
    context.arc(event.x - 8, event.y - 8, 23, 0, 360, false);
    if (flag) {
        context.fillStyle = "green";
        flag = false;
    } else {
        context.fillStyle = "blue";
        flag = true;
    }
    context.fill();
    context.closePath();
    context.restore();
}

function drawMapGrid(context, startX, startY, spaceOffsetLine, currentXLineNumber, currentYLineNumber) {
    if (spaceOffsetLine <= 0) {
        throw "spaceOffsetLine <= 0";
    }
    var screenWidth = this.window.screen.width;
    var screenHeight = this.window.screen.height;

    var forwardCurrentXLineNumber = currentXLineNumber;
    var backwardCurrentXLineNumber = currentXLineNumber;
    var upCurrentYLineNumber = currentYLineNumber;
    var downCurrentYLineNumber = currentYLineNumber;

    context.clearRect(0, 0, screenWidth, screenHeight);

    /**
     * 向前绘制纵线
     */
    for (var x = startX; x > 0; x = x - spaceOffsetLine) {
        drawSingleLine(context, x, 0, x, screenHeight);
        drawLineNumber(context, forwardCurrentXLineNumber--, x - 5, 20);
    }

    /**
     * 向后绘制纵线
     */
    for (var x = startX; x < screenWidth; x = x + spaceOffsetLine) {
        drawSingleLine(context, x, 0, x, screenHeight);
        drawLineNumber(context, backwardCurrentXLineNumber++, x - 5, 20);
    }

    /**
     * 向上绘制横线
     */
    for (var y = startY; y > 0; y = y - spaceOffsetLine) {
        drawSingleLine(context, 0, y, screenWidth, y);
        drawLineNumber(context, upCurrentYLineNumber--, 0, y);
    }

    /**
     * 向下绘制横线
     */
    for (var y = startY; y < screenHeight; y = y + spaceOffsetLine) {
        drawSingleLine(context, 0, y, screenWidth, y);
        drawLineNumber(context, downCurrentYLineNumber++, 0, y)
    }
}

function drawSingleLine(context, startX, startY, endX, endY) {
    context.beginPath();
    context.lineWidth = 2;
    context.strokeStyle = "#FF0000";
    context.moveTo(startX, startY);
    context.lineTo(endX, endY);
    context.stroke();
}

function drawLineNumber(context, text, x, y) {
    context.save();
    context.font = "20px serif";
    context.fillText(text, x, y, 20);
    context.restore();
}
