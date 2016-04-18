// configvar.ftpconfig
var configvar = {
    BASE_URL: 'http://www.centillioncosmos.com/MobileNetServiceTech/RestMobileNetService.svc/',
    token: null,
    passwordsent: "The password is sent to your mobile number.",
    noInternetString: "Unable to connect with the server.\nCheck your internet connection and try again.",
    serverUnavailable: "Server is unavailable,please try after some time.",
    noimagestring: "Please select Image",
    cancelpreview: "Are you sure you want to cancel the application?",
    deletefile: "Are you sure you want to delete?",
};
var ialert = function(message) {
    //alert(message);
    // console.log(message)
}
var calert = function(msg) {
        // navigator.notification.alert('' + msg, // message
        //     alertDismissedDoNothing, // callback
        //     'Centillion', // title
        //     'Ok' // buttonName
        // );
        alert(msg)
    }
    // Error log
var alertDismissedDoNothing = function() {
    // do something
};
// showloader($ionicLoading);


var sleep = function(milliseconds) {
        var start = new Date().getTime();
        for (var i = 0; i < 1e7; i++) {
            if ((new Date().getTime() - start) > milliseconds) {
                break;
            }
        }
    }
    //toast($cordovaToast,configvar.synmessagebackground);
var toast = function($cordovaToast, message) {
    $cordovaToast.showShortBottom(message).then(function(success) {
        // success
    }, function(error) {
        // error
    });
};