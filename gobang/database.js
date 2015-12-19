/**
 * Created by dingpengwei on 12/15/15.
 */
function getDatabase() {
    return openDatabase("goBang", "1.0", "GoBang", 1024 * 1024, function () {
    });
}
var dataBase = getDatabase();

if (!dataBase) {
    alert("您的浏览器不支持HTML5本地数据库");
}

dataBase.transaction(function (trans) {
    /**
     *
     */
    trans.executeSql("DROP TABLE IF EXISTS CHECKERBOARD");
    trans.executeSql("DROP TABLE IF EXISTS CHESSMAN");
    /**
     * 创建棋盘表
     */
    trans.executeSql("CREATE TABLE IF NOT EXISTS CHECKERBOARD(startX Number NOT NULL ,startY Number NOT NULL ,endX Number NOT NULL,endY Number NOT NULL,direction Number NOT NULL )", [],
        function (trans, result) {

        },
        function (trans, errorMessage) {
            alert(errorMessage);
        });
    /**
     * 创建棋子表
     */
    trans.executeSql("CREATE TABLE IF NOT EXISTS CHESSMAN(xLocation Number NOT NULL ,yLocation Number NOT NULL)", [],
        function (trans, result) {

        },
        function (trans, errorMessage) {
            alert(errorMessage);
        });
});


function insertCheckerboardLines(checkerboardLineArray) {
    for (var checkerboardLine in checkerboardLineArray) {
        dataBase.transaction(function (trans) {
            trans.executeSql("INSERT INTO CHECKERBOARD(startX,startY,endX,endY,direction) VALUES(?,?,?,?,?) ",
                [checkerboardLineArray[checkerboardLine].startX, checkerboardLineArray[checkerboardLine].startY, checkerboardLineArray[checkerboardLine].endX, checkerboardLineArray[checkerboardLine].endY, checkerboardLineArray[checkerboardLine].direction],
                function (trans, result) {
                    //console.log("插入棋盘线条数据" + result);
                }, function (trans, errorMessage) {
                    console.log("插入数据处错误了" + errorMessage);
                });
        });
    }
}

/**
 * 检测指定的坐标上是否已经存在棋子
 * @param chessmanObject
 * @param chessmanCheckCallBack
 */
function existsChessmanInDB(chessmanObject,chessmanCheckCallBack){
    dataBase.transaction(function (trans) {
        trans.executeSql("SELECT * FROM CHESSMAN WHERE xLocation = ? AND yLocation = ?", [chessmanObject.centerX,chessmanObject.centerY],
            function (trans, result) {
                if(result.rows.length == 1){
                    chessmanCheckCallBack(true);
                }else{
                    chessmanCheckCallBack(false);
                }
            },
            function () {
                alert("error");
            });
    });
}

/**
 * 添加棋子
 * @param chessmanObject
 * @param chessmanCheckCallBack
 */
function addChessman(chessmanObject,chessmanCheckCallBack){
    dataBase.transaction(function (trans) {
        trans.executeSql("INSERT INTO CHESSMAN(xLocation,yLocation) VALUES (?,?)", [chessmanObject.centerX,chessmanObject.centerY],
            function (trans, result) {
                chessmanCheckCallBack(true);
            },
            function () {
                chessmanCheckCallBack(false);
            });
    });
}
