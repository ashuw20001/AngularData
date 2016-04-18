//http://115.112.131.44:37763/sites/IndicomGlobal/_vti_bin/IndecommApi/IndecommServices.svc/PublishedArticleList/8d0b47ec0df012e18ff5f07d1a43a403/Latest/0
//http://115.112.131.44:37763/sites/IndicomGlobal/_vti_bin/IndecommApi/IndecommServices.svc/PublishedArticleList/8d0b47ec0df012e18ff5f07d1a43a403/Latest/0
var constantvar = {
    BASE_URL: 'http://115.112.131.44:37763/sites/Intarcia/_vti_bin/interciaapi/intarciaservice.svc/',
    token: null,
    limitmsg: "Sorry, attachment limit exceed",
    article_images: [],
    noInternetString: "Unable to connect with the server.\nCheck your internet connection and try again.",
    serverUnavailable: "Server is unavailable,please try after some time.",
    noimagestring: "Please select Image",
    slider: null,
    usergrouplist: null,
    deviceid: null,
    appName: "Intarcia",
    logoutMsg: "User Logged out Successfully",
    noVisitSrc: "No Source for this Newsletter",
    passwordChangedMsg: "Password changed successfully",
    changepasstextval: "Please enter a new password with length more than 4 characters, having atleast one digit, one capital letter and one special character.",
    noNewsLetterStr: "No Newsletter Found",
    removeFavMsg: "Are you sure you want to remove this newsletter from your favorites?",
    invalidUser: 'Please enter valid user email id',
    enterUsername: 'Please enter user email id',
    enterPassword: 'Please enter user password',
};


// // showloader($ionicLoading);
// var showloader = function($ionicLoading) {
//     $ionicLoading.show();
// };
// // hide($ionicLoading);
// var hide = function($ionicLoading) {
//     $ionicLoading.hide();
// };

// showloader($ionicLoading);
var showloader = function($ionicLoading) {
    $ionicLoading.show({
        template: '<ion-spinner icon="spiral"></ion-spinner>',
        animation: 'fade-in',
        showBackdrop: true,
        maxWidth: 200,
        showDelay: 0
    });
};

// hide($ionicLoading);
var hide = function($ionicLoading) {
    $ionicLoading.hide();
};


// returnJSON(response)
var returnJSON = function(xmlresponse) {
        var x2js = new X2JS();
        return x2js.xml_str2json(xmlresponse)
    };
    //Alert Function
var appAlert = function(msg) {
        alert(msg);
    }
    //Logged Function
var appLog = function(msg, val) {
    console.log(msg + ' : ' + val);
}
var errorMsg = function(error, status) {
    ialert("2 parametrized")
    switch (status) {
        case 0:
           calert(configvar.noInternetString );
            break;
        case 404:
            calert(configvar.serverUnavailable);
            break;
        default:
            calert("Please try after sometime..");
    }
};