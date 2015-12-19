/**
 * Created by dingpengwei on 12/19/15.
 */

/**
 * 绘制棋盘
 * @param context
 * @param checkerboardLineArray
 */
function drawCheckerboard(context,checkerboardLineArray){
    for(var checkerboardLine in checkerboardLineArray){
        drawCheckerboardLine(context,checkerboardLineArray[checkerboardLine]);
    }
}

/**
 * 绘制棋盘线条
 * @param context
 * @param checkerboardLineObject
 */
function drawCheckerboardLine(context, checkerboardLineObject) {
    context.beginPath();
    context.lineWidth = checkerboardLineObject.lineWidth;
    context.strokeStyle = checkerboardLineObject.strokeStyle;
    context.moveTo(checkerboardLineObject.startX, checkerboardLineObject.startY);
    context.lineTo(checkerboardLineObject.endX, checkerboardLineObject.endY);
    context.stroke();
}

/**
 * 绘制棋子
 * @param context
 * @param chessmanObject
 */
function drawChessman(context, chessmanObject){
    context.save();
    context.beginPath();
    context.arc(chessmanObject.centerX, chessmanObject.centerY, chessmanObject.radius, chessmanObject.startAngle, chessmanObject.endAngle, false);
    context.fillStyle = chessmanObject.fillStyle;
    context.fill();
    context.closePath();
    context.restore();
}
