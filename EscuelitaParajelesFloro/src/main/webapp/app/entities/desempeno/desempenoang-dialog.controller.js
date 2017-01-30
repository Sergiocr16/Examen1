(function() {
    'use strict';

    angular
        .module('escuelitaParajelesFloroApp')
        .controller('DesempenoAngDialogController', DesempenoAngDialogController);

    DesempenoAngDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Desempeno', 'Jugador', 'Entrenamiento'];

    function DesempenoAngDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Desempeno, Jugador, Entrenamiento) {
        var vm = this;

        vm.desempeno = entity;
        vm.clear = clear;
        vm.save = save;
        vm.jugadors = Jugador.query();
        vm.entrenamientos = Entrenamiento.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.desempeno.id !== null) {
                Desempeno.update(vm.desempeno, onSaveSuccess, onSaveError);
            } else {
                Desempeno.save(vm.desempeno, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('escuelitaParajelesFloroApp:desempenoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
