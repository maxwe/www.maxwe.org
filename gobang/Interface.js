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

var boundingX = checkerboardCanvas.getBoundingClientRect().left;
var boundingY = checkerboardCanvas.getBoundingClientRect().top;


/**
 * 绘制棋盘
 */
drawCheckerboard(checkerboardCanvasContext, checkerboardLineArray);


function toast(message) {
    $("#toast").show().html(message).fadeOut(2000);
}

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
    var offset = 20;

    var x = event.x - boundingX;
    var y = event.y - boundingY;

    var centerX = -1;
    var centerY = -1;

    for (var xAxi in checkerboardLineArray) {
        var checkerboardLine = checkerboardLineArray[xAxi];
        if (((x > checkerboardLine.startX && x < (checkerboardLine.startX + offset))
            || (x < checkerboardLine.startX && x > (checkerboardLine.startX - offset))
            || x == checkerboardLine.startX)) {
            centerX = checkerboardLine.startX;
            break;
        }
    }
    for (var yAxi in checkerboardLineArray) {
        var checkerboardLine = checkerboardLineArray[yAxi];
        if (((y > checkerboardLine.startY && y < (checkerboardLine.startY + offset))
            || ( y < checkerboardLine.startY && y > (checkerboardLine.startY - offset))
            || y == checkerboardLine.startY)) {
            centerY = checkerboardLine.startY;
            break;
        }
    }

    $("#developLog").html("developer log <br>"+ "x=" + x + ", y=" + y + ",<br> centerX=" + centerX + ",<br> centerY=" + centerY);

    if (centerX > 0 && centerY > 0) {
        var chessmanObject = (new ChessmanObject(clickerTimer, centerX, centerY, 20, 0, 360));
        /**
         * TODO 子线程调用UI线程问题
         */
        existsChessmanInDB(chessmanObject, function (existsChessmanResult) {
            if (!existsChessmanResult) {
                addChessman(chessmanObject, function (addChessmanResult) {
                    if (addChessmanResult) {
                        if (clickerTimer % 2 == 0) {
                            chessmanObject.fillStyle = "black";
                            drawChessman(checkerboardCanvasContext, chessmanObject);
                            currentChessmanCanvasContext.clearRect(0, 0, 200, 100);
                            drawChessman(currentChessmanCanvasContext, new ChessmanObject(0, 150, 80, 50, 0, 360, "black"));
                        } else {
                            chessmanObject.fillStyle = "white";
                            drawChessman(checkerboardCanvasContext, chessmanObject);
                            currentChessmanCanvasContext.clearRect(0, 0, 200, 100);
                            drawChessman(currentChessmanCanvasContext, new ChessmanObject(0, 150, 80, 50, 0, 360, "white"));
                        }
                        clickerTimer++;
                    } else {
                        toast("<h3 style='color: #ffffff;'>保存棋子失败。</h3>");
                    }
                });
            } else {
                toast("<h3 style='color: #ffffff;'>哎呦，坑已经被占用了。</h3>");
            }
        });
    }
}
