/**
 * Created by dingpengwei on 12/19/15.
 */

/**
 * 棋盘线条对象
 * @param id
 * @param startX
 * @param startY
 * @param endX
 * @param endY
 * @param direction 方向 0标示为纵线 1标示为横线
 * @constructor
 */
function CheckerboardLineObject(id,startX, startY, endX, endY, direction) {
    this.id = id;
    this.startX = startX;
    this.startY = startY;
    this.endX = endX;
    this.endY = endY;
    this.direction = direction;
    this.strokeStyle = "#FF0000";
    this.lineWidth = 2;
}

/**
 * 棋子对象
 * @param id
 * @param centerX
 * @param centerY
 * @param radius
 * @param startAngle
 * @param endAngle
 * @param fillStyle
 * @constructor
 */
function ChessmanObject(id,centerX,centerY,radius,startAngle,endAngle,fillStyle){
    this.id = id;
    this.centerX = centerX;
    this.centerY = centerY;
    this.radius = radius;
    this.startAngle = startAngle;
    this.endAngle = endAngle;
    this.fillStyle = fillStyle;
}
