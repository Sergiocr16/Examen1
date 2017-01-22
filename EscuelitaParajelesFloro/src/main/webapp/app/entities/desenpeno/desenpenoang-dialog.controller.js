(function() {
    'use strict';

    angular
        .module('escuelitaParajelesFloroApp')
        .controller('DesenpenoAngDialogController', DesenpenoAngDialogController);

    DesenpenoAngDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Desenpeno', 'Jugador', 'Entrenamiento'];

    function DesenpenoAngDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Desenpeno, Jugador, Entrenamiento) {
        var vm = this;

        vm.desenpeno = entity;
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
            if (vm.desenpeno.id !== null) {
                Desenpeno.update(vm.desenpeno, onSaveSuccess, onSaveError);
            } else {
                Desenpeno.save(vm.desenpeno, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('escuelitaParajelesFloroApp:desenpenoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
