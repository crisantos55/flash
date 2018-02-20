'use strict';

app.config(['$routeProvider',
  function($routeProvider){
    $routeProvider.
    when('/', {
        templateUrl : 'principal.html'
      }).
    when('/registro', {
      templateUrl : 'views/registro.html'
    }).
    when('/login', {
      templateUrl: 'views/login.html'
    }).

     when('/admin', {
      templateUrl: 'views/admin.html'
    }).

       when('/recupera', {
      templateUrl: 'views/recupera.html'
    }).

     when('/404', {
      templateUrl: '404.html'
    }).
    otherwise({
        redirectTo: '/404'
    });
  }]);


