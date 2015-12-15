/**
 * Created by dingpengwei on 12/15/15.
 */
var canvas = this.document.getElementById("canvas");
var screenWidth = this.window.screen.width;
var screenHeight = this.window.screen.height;
canvas.width = screenWidth;
canvas.height = screenHeight;
canvas.draggable = true;

canvas.addEventListener("click", onClickHandler);
var context = canvas.getContext("2d");

var xAxis = new Array();
var yAxis = new Array();

drawMapGrid(context);

var spaceOffsetLine = 50;

var offset = 20;


var flag;

function onClickHandler(event) {
    var x = event.x;
    var y = event.y;


    var centerX = -1;
    var centerY = -1;

    var found = false;

    for (var xAxi in xAxis) {
        if (((x > xAxis[xAxi] && x < xAxis[xAxi] + offset) || (x < xAxis[xAxi] && x > xAxis[xAxi] - offset)) && !found) {
            centerX = xAxis[xAxi];
            found = true;
        }
    }

    found = false;


    for (var yAxi in yAxis) {
        if (((y > yAxis[yAxi] && y < (yAxis[yAxi] + offset)) || ( y < yAxis[yAxi] && y > (yAxis[yAxi] - offset))) && !found) {
            centerY = yAxis[yAxi];
            found = true;
        }
    }

    console.log(x + " " + y + " " + centerX + " " + centerY);

    if (centerX > 0 && centerY > 0) {
        context.save();
        context.beginPath();
        context.arc(centerX, centerY, 23, 0, 360, false);
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


}


function drawMapGrid(context) {
    for (var startX = 0; startX < screenWidth; startX = startX + 50) {
        xAxis.push(startX);
        drawSingleLine(context, startX, 0, startX, screenHeight);
    }

    for (var startY = 0; startY < screenHeight; startY = startY + 50) {
        yAxis.push(startY);
        drawSingleLine(context, 0, startY, screenWidth, startY);
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




