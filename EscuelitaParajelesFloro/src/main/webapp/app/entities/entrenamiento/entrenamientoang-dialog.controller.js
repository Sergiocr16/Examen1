(function() {
    'use strict';

    angular
        .module('escuelitaParajelesFloroApp')
        .controller('EntrenamientoAngDialogController', EntrenamientoAngDialogController);

    EntrenamientoAngDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Entrenamiento', 'Desenpeno', 'User', 'Horario', 'minToTimeFilter'];

    function EntrenamientoAngDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Entrenamiento, Desenpeno, User, Horario, minToTimeFilter) {
        var vm = this;

        vm.entrenamiento = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.desenpenos = Desenpeno.query();
        vm.users = User.query();
        vm.horarios = Horario.query();
        vm.formatHorario = formatHorario;


        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function formatHorario(h) {
            return 'El '
                + h.dia.toLowerCase()
                +' de '
                + minToTimeFilter(h.horaInicio)
                +' a '
                + minToTimeFilter(h.horaFin);
        };

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

        vm.datePickerOpenStatus.fechaHora = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
