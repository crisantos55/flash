'use strict';

app.controller('loginController', ['$scope','$http', 'flashService', '$rootScope', '$window',  function(s,$http, FlashS, rootS, $window){


  s.errorLogin = false;
    s.succesRecupera = false;
    s.errorRecupera = false;

	s.loginValidar = function (){
		 $.blockUI({
  	         css: {border: 'none',padding: '15px',backgroundColor: '#000','-webkit-border-radius': '10px','-moz-border-radius': '10px',opacity: .5,color: '#fff'},
  	         message: '<i class="fa fa-spinner fa-pulse fa-3x fa-fw"></i>Validando creedenciales...'
  	     });
		s.userL = [];
		s.userL.cod = s.cod;
		s.userL.password = s.passwordL;
		 FlashS.peticion('POST','/loginUser', s.userL,'').then(function (r) {
			 	$.unblockUI();  
			 	rootS.token = r.data.tok;
                rootS.tokenR= r.data.tokR;
                rootS.codigoLogin = r.data.codVer;
                rootS.showOut = true;
                rootS.sesion = false;
                rootS.sesionAdmin = true;
                 $window.location.href = '#!/admin';
                },
                function (error) {
                	 $.unblockUI();  
                  s.errorLogin = true;
                  
                }); 
  	};	


    s.recuperarContrasena = function (){
    	 $.blockUI({
  	         css: {border: 'none',padding: '15px',backgroundColor: '#000','-webkit-border-radius': '10px','-moz-border-radius': '10px',opacity: .5,color: '#fff'},
  	         message: '<i class="fa fa-spinner fa-pulse fa-3x fa-fw"></i>Validando creedenciales...'
  	     });
      s.dataLogin = [];
    s.dataLogin.codigoRecuperacion = s.codigoRecuperacion;
 FlashS.peticion('POST','/recuperaC', s.dataLogin,'').then(function (r) {
	 			$.unblockUI();  
                s.succesRecupera = true;
                },
                function (error) {
                	 $.unblockUI();  
                  s.errorRecupera = true;
                }); 
    };

}]);

