myapp.controller('HomeCtrl', function($scope, $location, $http, $ionicLoading) {
    $scope.$on('$ionicView.enter', function(viewInfo, state) {
        alert("$ionicView.enter");
        console.log('CTRL - $ionicView.enter', viewInfo, state);
    });
});