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
    //trans.executeSql("DROP TABLE IF EXISTS CHECKERBOARD");
    /**
     *
     */
    trans.executeSql("CREATE TABLE IF NOT EXISTS CHECKERBOARD(startX Number NOT NULL ,startY Number NOT NULL ,endX Number NOT NULL,endY Number NOT NULL,direction Number NOT NULL )", [],
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

function get(){
    dataBase.transaction(function (trans) {
        trans.executeSql("SELECT * FROM CHECKERBOARD", [],
            function (trans, result) {
                console.log("查找到数据条数：" + result.rows.length);
                for (var row in result.rows) {
                    console.log("查找到" + result.rows[row]);
                }
            },
            function () {
                alert("error");
            });
    });
}
