(function() {
    'use strict';

    angular
        .module('escuelitaParajelesFloroApp')
        .controller('EntrenamientoAngDialogController', EntrenamientoAngDialogController);

    EntrenamientoAngDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Entrenamiento', 'Desempeno', 'User', 'Horario'];

    function EntrenamientoAngDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Entrenamiento, Desempeno, User, Horario) {
        var vm = this;
        let map = {
            "DOMINGO": 0,
            "LUNES": 1,
            "MARTES": 2,
            "MIERCOLES": 3,
            "JUEVES": 4,
            "VIERNES": 5,
            "SABADO": 6,
        };
        vm.datePickerOpenStatus = {};

        vm.entrenamiento   = entity;
        vm.clear           = clear;
        vm.openCalendar    = openCalendar;
        vm.save            = save;
        vm.onHorarioChange = onHorarioChange;
        vm.desempenos      = Desempeno.query();
        vm.horarios        = Horario.query();
        vm.dateOptions     = { dateDisabled: disabled };

        User.byRole({role: 'ROLE_COUCH'}, result => vm.users = result);
        $timeout(() => angular.element('.form-group:eq(1)>input').focus());

        function disabled(data) {
            let {date, mode} = data;
            let dia = vm.horario ? map[vm.horario.dia] : -1;
            return mode === 'day' && (date.getDay() !== dia);
        }

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function onHorarioChange() {
            vm.entrenamiento.fecha = '';
        }

        function setHorario() {
            vm.entrenamiento.categoria = vm.horario.categoria;
            vm.entrenamiento.horaFin = vm.horario.horaFin;
            vm.entrenamiento.horaInicio = vm.horario.horaInicio;
        }

        function save () {
            vm.isSaving = true;
            setHorario();
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
