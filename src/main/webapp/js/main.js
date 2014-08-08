/**
 * Created by R500 on 5.8.2014 г..
 */

var app = angular.module('myApp',[]);

app.config(function($routeProvider){
    $routeProvider
        .when('/' || '/login',{
            templateUrl : 'login.html'
//            controller : 'mainController'
        })
        .when('/signUp',{
            templateUrl : 'signUp.html'
        })
        .when('/admin',{
            templateUrl : 'admin.html'
        })
        .when('/changePassword',{
            templateUrl : 'changePassword.jsp'
        })
        .when('/controlPanel',{
            templateUrl : 'controlPanel.html'
        })
        .when('/normalUser',{
            templateUrl : 'normalUser.html'
        })
        .otherwise({
        redirectTo: '/'
    });
});

app.controller('mainController',function($scope){

});

app.controller('loginController', function($scope, $http, $location){
    $scope.login = function(email, password) {
        $scope.message = "";
        $scope.access = "";
        $scope.accountStatus = "";
        $scope.login = "";
        $scope.credentials =  {
                email: email,
                password: password
        };
        $http({
            method: 'POST',
            url: 'LoginServlet',
            params: $scope.credentials
        }).success(function(data){

            access = data.access;
            accountStatus = data.accountStatus;
            login = data.login;
            if(login == "Failed"){
                alertify.alert("Wrong email or password");
                $location.path('/login');
            } else {
                if (access == 1) {
                    $location.path('/admin')
                } else {
                    if (accountStatus == 0) {
                        $location.path('/changePassword')
                    } else {
                        $location.path('/normalUser')
                    }
                }
            }
        }).error(function(e){
            alert(e);
        });
    }
});

app.controller('menuController', function($scope, $http, $location){
    $scope.logout = function() {
        $http({
            method: 'POST',
            url: 'LogoutServlet'
        });
        $location.path('/login')
    };

    $scope.changePassword = function() {
        $location.path('/changePassword')
    };

    $scope.controlPanel = function() {
        $location.path('/controlPanel')
    };

    $scope.requestTable = function() {
        $location.path('/admin')
    };
});

app.controller('SignUpController', function($scope, $http, $location){
    $scope.toSignUp = function() {
        $location.path('/signUp')
    };

    $scope.signUp = function(fName, surname, lName, email, password, confirmPassword) {
        $scope.message = "";
        if(fName == null) fName = "";
        if(surname == null) surname = "";
        if(lName == null) lName = "";
        if(email == null) email = "";
        if(password == null) password = "";
        if(confirmPassword == null) confirmPassword = "";
        $scope.data = {
            fName: fName,
            surname: surname,
            lName: lName,
            email: email,
            password: password,
            confirmPassword: confirmPassword
        };
        $http({
            method: 'POST',
            url: 'SignUpServlet',
            params: $scope.data
        }).success(function(data){
            message = data.message;
            if(message == ""){
                $location.path('/login');
            } else {
                alertify.alert(message);
                $location.path('/signUp');
            }
        }).error(function(e){
            alert(e);
        });
    }
});

app.controller('changePasswordController', function($scope, $http, $location) {
    $scope.data = {};
    $scope.changePassword = function(oldPassword, newPassword, confirmNewPassword) {
        $scope.message = "";
        $scope.access = "";
        $scope.status = "";
        if (oldPassword == null) oldPassword = "";
        if (newPassword == null) newPassword = "";
        if (confirmNewPassword == null) confirmNewPassword = "";
        $scope.data = {
            oldPassword: oldPassword,
            newPassword: newPassword,
            confirmNewPassword: confirmNewPassword
        };
        alertify.confirm('Are you sure you want to change your password?').setting('onok', function () {
            $scope.status = "ok";
            $scope.changePasswordSuccess();
        });
    };
    $scope.changePasswordSuccess = function() {
        if($scope.status == "ok"){
            $http({
                method: 'POST',
                url: 'ChangePasswordServlet',
                params: $scope.data
            }).success(function(data){
                message = data.message;
                if(message == ""){
                    access = data.access;
                    alertify.success('Password changed successfully');
                    if(access == 1){
                        $location.path('/admin');
                    } else {
                        $location.path('/normalUser');
                    }
                } else {
                    alertify.alert(message);
                    $location.path('/changePassword');
                }
            }).error(function(e){
                alert(e);
            });
        }
    }
});