(function() {
    'use strict';

    angular
        .module('escuelitaParajelesFloroApp')
        .controller('EntrenamientoAngDetailController', EntrenamientoAngDetailController);

    EntrenamientoAngDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Entrenamiento', 'Desenpeno','Jugador', 'User', 'Horario'];

    function EntrenamientoAngDetailController($scope, $rootScope, $stateParams, previousState, entity, Entrenamiento, Desenpeno,Jugador, User, Horario) {
        var vm = this;

        vm.entrenamiento = entity;
        vm.previousState = previousState.name;
        vm.getHorarioByEntrenamiento = function() {
         return Horario.get({id : vm.entrenamiento.horarioId}).$promise;
         }

        vm.getHorarioByEntrenamiento().then(function(result){
        Jugador.getByCategoria({id : result.categoriaId}).$promise.then(function(result){
         vm.jugadors = result;
         vm.jugadors.forEach(function(jugador) {
         jugador.desempeno = Desenpeno.fingByEntrenamientoIdAndjugadorId({entrenamientoId: vm.entrenamiento.id, jugadorId: jugador.id});
         });
        });
       })


        vm.saveDesempenno = function(jugador) {
             vm.isSaving = true;
             jugador.desempeno.jugadorId = jugador.id;
             jugador.desempeno.entrenamientoId = vm.entrenamiento.id;
             jugador.desempeno.entrenamientoDescripcion = vm.entrenamiento.descripcion;
             if (jugador.desempeno.id !== null) {

                 Desenpeno.update(jugador.desempeno, onSaveSuccess, onSaveError);
             } else {
                 Desenpeno.save(jugador.desempeno, onSaveSuccess, onSaveError);
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
