/**
 * Created by R500 on 5.8.2014 г..
 */

var app = angular.module('myApp',[]);

app.config(function($routeProvider){
    $routeProvider
        .when('/' || '/login' || '/login.html',{
            templateUrl : 'login.html'
//            controller : 'mainController'
        })
        .when('/signUp',{
            templateUrl : 'signUp.html'
        })
        .when('/requestManager',{
            templateUrl : 'requestManager.jsp'
        })
        .when('/changePassword',{
            templateUrl : 'changePassword.jsp'
        })
        .when('/controlPanel',{
            templateUrl : 'controlPanel.jsp'
        })
        .when('/myRequests',{
            templateUrl : 'myRequests.jsp'
        })
        .when('/vacationCalendar',{
            templateUrl : 'vacationCalendar.jsp'
        })
        .when('/holidaysManager',{
            templateUrl : 'holidaysManager.jsp'
        })
        .when('/forgottenPassword',{
            templateUrl : 'forgottenPassword.jsp'
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
        $scope.accountStatus = "";
        $scope.access = "";
        $scope.login = "";
        $scope.credentials =  {
                email: email,
                password: password
        };
        $http({
            method: 'POST',
            url: 'LoginServlet',
            params: $scope.credentials
        }).success(function(data) {
            accountStatus = data.accountStatus;
            access = data.access;
            login = data.login;
            if (login == "Failed") {
                alertify.alert("Wrong email or password!");
                $location.path('/login');
            } else {
                if (access == 0){
                    alertify.alert("In order to login an admin must approve your registration first!");
                    $location.path('/login');
                } else {
                    if (accountStatus == 0) {
                        $location.path('/changePassword');
                        requestNotification($scope, $http);
                    } else {
                        $location.path('/myRequests');
                        requestNotification($scope, $http);
                    }
                }
            }
        }).error(function(e){
            alertify.alert(e);
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

    $scope.requestManager = function() {
        $location.path('/requestManager')
    };

    $scope.myRequests = function() {
        $location.path('/myRequests')
    };

    $scope.vacationCalendar = function() {
        $location.path('/vacationCalendar')
    };

    $scope.holidaysManager = function() {
        $location.path('/holidaysManager')
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
            alertify.alert(e);
        });
    }
});

app.controller('changePasswordController', function($scope, $http, $location) {
    $scope.data = {};
    $scope.changePassword = function(oldPassword, newPassword, confirmNewPassword) {
        $scope.message = "";
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
                    alertify.success('Password changed successfully');
                    $location.path('/myRequests');
                } else {
                    alertify.alert(message);
                    $location.path('/changePassword');
                }
            }).error(function(e){
                alertify.alert(e);
            });
        }
    }
});

app.controller('forgottenPasswordController', function($scope, $http, $location) {
    $scope.data = {};
    $scope.resetPassword = function(newPassword, confirmNewPassword) {
        $scope.message = "";
        $scope.status = "";
        if (newPassword == null) newPassword = "";
        if (confirmNewPassword == null) confirmNewPassword = "";
        $scope.data = {
            newPassword: newPassword,
            confirmNewPassword: confirmNewPassword
        };
        alertify.confirm('Are you sure you want to reset your password?').setting('onok', function () {
            $scope.status = "ok";
            $scope.resetPasswordSuccess();
        });
    };
    $scope.resetPasswordSuccess = function() {
        if($scope.status == "ok"){
            $http({
                method: 'POST',
                url: 'ResetForgottenPassword',
                params: $scope.data
            }).success(function(data){
                message = data.message;
                if(message == ""){
                    alertify.success('Password changed successfully');
                    $location.path('/myRequests');
                    requestNotification($scope, $http);
                } else {
                    alertify.alert(message);
                    $location.path('/forgottenPassword');
                }
            }).error(function(e){
                alertify.alert(e);
            });
        }
    }
});

function requestNotification($scope, $http){
    $scope.access;
    $http({
        method: 'POST',
        url: 'RequestNotificationController',
        params: {
            action: "requestAccess"
        }
    }).success(function(data){
        $scope.access = data.access;
        if ($scope.access == 1 || $scope.access == 2){
            $scope.requestNotifications();
        }
    }).error(function(e){
        alertify.alert(e);
    });
    $scope.requestNotifications = function() {
        $http({
            method: 'POST',
            url: 'RequestNotificationController',
            params: {
                action: "requestNotifications"
            }
        }).success(function(data){
            var endIndex = data.endIndex;
            if(endIndex > 0){
                var notifications = data.notifications;
                var requestPlural = 'requests';
                if(endIndex == 1){
                    requestPlural = 'request'
                }
                var employeeNames = '';
                for(var i = 0; i < endIndex; i++){
                    if(i == endIndex - 1){
                        employeeNames += notifications[i];
                    } else {
                        employeeNames += notifications[i] + ", ";
                    }
                }
                alertify.success('You have ' + endIndex + ' vacation ' + requestPlural + ' waiting for approval from: ' + employeeNames, 20)
            }
        }).error(function(e){
            alertify.alert(e);
        });
    }
}