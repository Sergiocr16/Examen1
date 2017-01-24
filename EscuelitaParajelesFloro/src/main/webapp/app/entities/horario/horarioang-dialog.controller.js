(function() {
    'use strict';

    angular
        .module('escuelitaParajelesFloroApp')
        .controller('HorarioAngDialogController', HorarioAngDialogController);

    HorarioAngDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Horario', 'Entrenamiento', 'Categoria'];

    function HorarioAngDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Horario, Entrenamiento, Categoria) {
        var vm = this;

        vm.horario = entity;
        vm.clear = clear;
        vm.save = save;
        vm.showTime = showTime
        vm.entrenamientos = Entrenamiento.query();
        vm.categorias = Categoria.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function showTime(h, m) {
            function addZero(x) {
                return( x < 10 ? "0" : "").concat(x);
            }
            if(isNaN(h) || isNaN(m)){
                return '';
            }
            return addZero(h) + ":" + addZero(m);
        }

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            vm.horario.horaInicio = vm.IniH * 60 + vm.IniM;;
            vm.horario.horaFin = vm.FinH * 60 + vm.FinM;
            if (vm.horario.id !== null) {
                Horario.update(vm.horario, onSaveSuccess, onSaveError);
            } else {
                Horario.save(vm.horario, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('escuelitaParajelesFloroApp:horarioUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
