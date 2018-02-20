'use strict';

app.controller('registroController', ['$scope','$http', 'flashService', '$filter',function(s,$http, FlashS,$filter){  

    s.vistaExitoRegistro = false;   
    s.vistaRegistro = true; 
    s.errorRegistroDB = false;
    s.noCuentaCodigo = false;

  	s.savePerson = function (){
  		 $.blockUI({
  	         css: {border: 'none',padding: '15px',backgroundColor: '#000','-webkit-border-radius': '10px','-moz-border-radius': '10px',opacity: .5,color: '#fff'},
  	         message: '<i class="fa fa-spinner fa-pulse fa-3x fa-fw"></i>Guardando Informacion...'
  	     });
  		s.Prospecto = [];
  		s.Prospecto.nombre = $filter('uppercase')(s.nombre);
  		s.Prospecto.nombre_s = $filter('uppercase')(s.nombre_s);
  		s.Prospecto.nombre_p = $filter('uppercase')(s.nombre_p);
  		s.Prospecto.nombre_m =$filter('uppercase')( s.nombre_m);
  		s.Prospecto.pais = $filter('uppercase')(s.pais);
  		s.Prospecto.provincia = $filter('uppercase')(s.provincia);
  		s.Prospecto.telefono = s.telefono;
  		s.Prospecto.email = s.email;
  		s.Prospecto.password = s.password;
  		s.Prospecto.liderCodigoV=s.codVer;
  		s.Prospecto.noCuentaCodigo = s.noCuentaCodigo;
  		s.Prospecto.fechaNacimiento = $filter('date')(s.fechaNacimiento, "yyyy/MM/dd");
 FlashS.peticion('POST','/save', s.Prospecto).then(function (r) {
                 if(r.data.data == 1){
                	  s.errorRegistroDB = false;
                  s.vistaExitoRegistro = true;
                  s.vistaRegistro = false; 
                  s.codDistLogin = r.data.cod;
                }else{
                 s.errorRegistroDB = true;
                 s.labelErrorRegistro = "El codigo verificador no existe, favor de internarlo de nuevo.";
                }
                 $.unblockUI();
                },
                function (error) {
                  s.errorRegistroDB = true;
                  s.labelErrorRegistro = "Error al guardar la informacion, intente mas tarde.";
                  $.unblockUI();
                }); 
  	};



}]);


app.directive('pwCheck', [function () {
    return {
      require: 'ngModel',
      link: function (scope, elem, attrs, ctrl) {
        var firstPassword = '#' + attrs.pwCheck;
        elem.add(firstPassword).on('keyup', function () {
          scope.$apply(function () {
            var v = elem.val()===$(firstPassword).val();
            ctrl.$setValidity('pwmatch', v);
          });
        });
      }
    }
  }]);