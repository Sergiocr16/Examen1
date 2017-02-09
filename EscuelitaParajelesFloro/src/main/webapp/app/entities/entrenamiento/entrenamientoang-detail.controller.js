(function() {
    'use strict';

    angular
        .module('escuelitaParajelesFloroApp')
        .controller('EntrenamientoAngDetailController', EntrenamientoAngDetailController);

    EntrenamientoAngDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Entrenamiento', 'Desempeno','Jugador', 'User'];

    function EntrenamientoAngDetailController($scope, $rootScope, $stateParams, previousState, entity, Entrenamiento, Desempeno,Jugador, User) {
        var vm = this;

        vm.entrenamiento = entity;
        vm.previousState = previousState.name;

        Jugador.getByCategoria({id : vm.entrenamiento.categoria}).$promise.then(function(result){
         vm.jugadors = result;
         vm.jugadors.forEach(function(jugador) {
         jugador.desempeno = Desempeno.fingByEntrenamientoIdAndjugadorId({entrenamientoId: vm.entrenamiento.id, jugadorId: jugador.id});
        if(jugador.desempeno.calificacion==undefined){
        jugador.desempeno.calificacion = 0;
        }
         });
        });


       vm.saveDesempenno = function(jugador) {
            vm.isSaving = true;
            jugador.desempeno.jugadorId = jugador.id;
            jugador.desempeno.entrenamientoId = vm.entrenamiento.id;
            jugador.desempeno.entrenamientoDescripcion = vm.entrenamiento.descripcion;
            if (jugador.desempeno.id !== null) {

                Desempeno.update(jugador.desempeno, onSaveSuccess, onSaveError);
            } else {
                Desempeno.save(jugador.desempeno, onSaveSuccess, onSaveError);
            }
            function onSaveSuccess (result) {
                          console.log(result);
                                 vm.isSaving = false;
                             }

             function onSaveError () {
                 vm.isSaving = false;
             }
        }
        var unsubscribe = $rootScope.$on('escuelitaParajelesFloroApp:entrenamientoUpdate', function(event, result) {
            vm.entrenamiento = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
