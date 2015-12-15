/**
 * Created by dingpengwei on 12/15/15.
 */
function getDatabase() {
    return openDatabase("goBang", "1.0", "GoBang", 1024 * 1024, function () {
    });
}
var dataBase = getDatabase();
if(!dataBase) {alert("您的浏览器不支持HTML5本地数据库");}

dataBase.transaction(function (trans) {
    //trans.executeSql("create table if not exists Demo(uName text null,title text null,words text null)", [], function (trans, result) {
    //}, function (trans, message) {//消息的回调函数alert(message);});
    //}, function (trans, result) {
    //}, function (trans, message) {
    });

