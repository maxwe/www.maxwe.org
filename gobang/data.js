/**
 * 棋盘左偏移量
 * @type {number|jQuery}
 */
//var offsetLeft = $("#checkerboardCanvas").offset().left;
var offsetLeft = 0;
/**
 * 棋盘上偏移量
 * @type {number|jQuery}
 */
//var offsetTop = $("#checkerboardCanvas").offset().top;
var offsetTop = 0;
/**
 * 棋盘的宽度
 * @type {*|jQuery}
 */
var checkerboardCanvasWidth = $("#checkerboardCanvas").width();
/**
 * 棋盘的高度
 * @type {*|jQuery}
 */
var checkerboardCanvasHeight = $("#checkerboardCanvas").height();
/**
 * 棋盘线间距
 * @type {number}
 */
var checkerboardLineSpace = 50;

/**
 * 棋盘线集合
 * @type {Array}
 */
var checkerboardLineArray = new Array();

/**
 * 纵向棋盘线条
 */
var lineIndex = 0;
for (var startX = offsetLeft; startX <= offsetLeft + checkerboardCanvasWidth; startX = startX + checkerboardLineSpace) {
    var checkerboardLineObjectY = new CheckerboardLineObject(lineIndex ++ ,startX, offsetTop, startX, offsetTop + checkerboardCanvasHeight, 0);
    checkerboardLineArray.push(checkerboardLineObjectY);
}

/**
 * 横向棋盘线条
 */
for (var startY = offsetTop; startY <= offsetTop + checkerboardCanvasHeight; startY = startY + checkerboardLineSpace) {
    var checkerboardLineObjectX = new CheckerboardLineObject(lineIndex ++ ,offsetLeft, startY, offsetLeft + checkerboardCanvasWidth, startY, 1);
    checkerboardLineArray.push(checkerboardLineObjectX);
}

