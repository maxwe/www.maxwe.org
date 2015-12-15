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
    trans.executeSql("CREATE TABLE IF NOT EXISTS go_bang(xLineNumber Number NOT NULL ,yLineNumber Number NOT NULL ,status Number NOT NULL )", [], function (trans, result) {
        //alert("ok");
    }, function (trans, errorMessage) {
        alert(errorMessage);
    });
});
