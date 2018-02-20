
app.factory('flashService',
    // IMPORTS
    ['$http', '$filter', '$q',
    function (h, f, q) {
        var service = {};
        var defaultServer = 'http://estamosunidoslatam.com/Flash';
        var moduleName = 'Pagos Anticipados Call center';

        service.peticion = function (typePeticion, urlPeticion, objeto, token, tokenR) {
            var deferred = q.defer();
              var   headers =  { 'Content-Type': false,
                 'Accept':'application/json',
                 'token': token,
                 'tokenR': tokenR}             
             h({
                 url: defaultServer + urlPeticion,
                 method: typePeticion,
                 params: objeto,
                 headers: headers
             }).then(function (response){
                 deferred.resolve(response);
             },function (error){
                      deferred.reject(error);
             });
             return deferred.promise;      
        };
         


        return service;
    }]
);