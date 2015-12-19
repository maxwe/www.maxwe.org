/**
 * Created by dingpengwei on 12/19/15.
 */
var currentChessmanCanvas = this.document.getElementById("currentChessmanCanvas");
var currentChessmanCanvasContext = currentChessmanCanvas.getContext("2d");

var offsetLeft = currentChessmanCanvas.offsetLeft;
var offsetTop = currentChessmanCanvas.offsetTop;
var currentChessmanCanvasWidth = $("#currentChessmanCanvas").width();
var currentChessmanCanvasHeight = $("#currentChessmanCanvas").height();
var centerXOfChessmanCanvas = offsetLeft + currentChessmanCanvasWidth / 2;
var centerYOfChessmanCanvas = offsetTop + currentChessmanCanvasHeight / 2;

console.log(offsetLeft + " | " + offsetTop + " | " + currentChessmanCanvasWidth + " | " + currentChessmanCanvasHeight );

function onCurrentChessmanCheck(flag){
    currentChessmanCanvasContext.save();
    currentChessmanCanvasContext.beginPath();
    currentChessmanCanvasContext.arc(centerXOfChessmanCanvas, centerYOfChessmanCanvas, currentChessmanCanvasHeight, 0, 360, false);
    if (flag) {
        currentChessmanCanvasContext.fillStyle = "black";
    } else {
        currentChessmanCanvasContext.fillStyle = "white";
    }
    currentChessmanCanvasContext.fill();
    currentChessmanCanvasContext.closePath();
    currentChessmanCanvasContext.restore();
}
