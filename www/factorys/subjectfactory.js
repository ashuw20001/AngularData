myapp.factory('SubjectList', function($http) {
    return {
        getSubjectList: function() {
            var url = constantvar.BASE_URL + '/PublishedNewsLetterSubject/List/' + window.localStorage.getItem("login_token") // Intarcia
            return $http.get(url);
        },
        sayGoodbye: function(text) {
            return "Factory says \"Goodbye " + text + "\"";
        }
    }
});
// myapp.factory('SubjectList', function(){
//     return {
//         getSubjectList: function(text){
//             return "Factory says \"Hello " + text + "\"";
//         },
//         sayGoodbye: function(text){
//             return "Factory says \"Goodbye " + text + "\"";
//         }  
//     }               
// });