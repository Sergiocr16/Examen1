(function() {
    'use strict';

    angular
        .module('escuelitaParajelesFloroApp')
        .controller('EntrenamientoAngDialogController', EntrenamientoAngDialogController);

    EntrenamientoAngDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Entrenamiento', 'Desempeno', 'User'];

    function EntrenamientoAngDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Entrenamiento, Desempeno, User) {
        var vm = this;

        vm.entrenamiento = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.desempenos = Desempeno.query();
        User.byRole({role: 'ROLE_COUCH'}, function(result) {
            vm.users = result;
        });
        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.entrenamiento.id !== null) {
                Entrenamiento.update(vm.entrenamiento, onSaveSuccess, onSaveError);
            } else {
                Entrenamiento.save(vm.entrenamiento, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('escuelitaParajelesFloroApp:entrenamientoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fecha = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
