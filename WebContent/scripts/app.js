'use strict';



var app = angular.module('SuperManageApp', ['ngRoute','ngResource','angularUtils.directives.dirPagination']);

app.run(['$rootScope', '$window', function($rootScope, $window) {

$rootScope.showOut = false;
$rootScope.sesion = true;
$rootScope.sesionAdmin = false;

$window.location.href = '#!/login';
$rootScope.exitSesion = function (){

  	$rootScope.token = "";
	$rootScope.tokenR= "";
    $rootScope.codigoLogin = "";
    $rootScope.showOut = false;
	$rootScope.sesion = true;
	$rootScope.sesionAdmin = false;
    $window.location.href = '#!/login';
}

}]);





