/**
 * Created by dingpengwei on 12/15/15.
 */
var checkerboardCanvas = this.document.getElementById("checkerboardCanvas");
checkerboardCanvas.width = checkerboardCanvasWidth;
checkerboardCanvas.height = checkerboardCanvasHeight;
checkerboardCanvas.draggable = true;
checkerboardCanvas.addEventListener("click", onClickListener);
var checkerboardCanvasContext = checkerboardCanvas.getContext("2d");

var currentChessmanCanvas = this.document.getElementById("currentChessmanCanvas");
var currentChessmanCanvasContext = currentChessmanCanvas.getContext("2d");



/**
 * 绘制棋盘
 */
drawCheckerboard(checkerboardCanvasContext,checkerboardLineArray);

/**
 * 下棋点击事件
 * @param event
 */
var clickerTimer = 0;
function onClickListener(event) {
    /**
     * 设置实际落点到期望落点之间最大的偏差
     * @type {number}
     */
    var offset = 10;

    var x = event.x;
    var y = event.y;

    //alert(x + " | " + y);

    var centerX = -1;
    var centerY = -1;

    var found = false;

    for (var xAxi in checkerboardLineArray) {
        var checkerboardLine = checkerboardLineArray[xAxi];
        if (((x > checkerboardLine.startX && x < (checkerboardLine.startX + offset)) || (x < checkerboardLine.startX && x > (checkerboardLine.startX - offset))) && !found) {
            centerX = checkerboardLine.startX;
            found = true;
        }
    }

    found = false;


    for (var yAxi in checkerboardLineArray) {
        var checkerboardLine = checkerboardLineArray[yAxi];
        if (((y > checkerboardLine.startY && y < (checkerboardLine.startY + offset)) || ( y < checkerboardLine.startY && y > (checkerboardLine.startY - offset))) && !found) {
            centerY = checkerboardLine.startY;
            found = true;
        }
    }

    console.log(x + " " + y + " " + centerX + " " + centerY);

    if (centerX > 0 && centerY > 0) {
        //drawChessman(checkerboardCanvasContext,new ChessmanObject());
    }
    if(clickerTimer % 2 == 0){
        drawChessman(checkerboardCanvasContext,new ChessmanObject(clickerTimer,event.x,event.y,20,0,360,"black"));
        currentChessmanCanvasContext.clearRect(0,0,200,100);
        drawChessman(currentChessmanCanvasContext,new ChessmanObject(0,150,80,50,0,360,"black"));
    }else{
        drawChessman(checkerboardCanvasContext,new ChessmanObject(clickerTimer,event.x,event.y,20,0,360,"white"));
        currentChessmanCanvasContext.clearRect(0,0,200,100);
        drawChessman(currentChessmanCanvasContext,new ChessmanObject(0,150,80,50,0,360,"white"));
    }

    clickerTimer ++;
}
