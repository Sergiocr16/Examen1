(function() {
    'use strict';

    angular
        .module('escuelitaParajelesFloroApp')
        .controller('JugadorAngDialogController', JugadorAngDialogController);

    JugadorAngDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Jugador', 'Responsable', 'Categoria'];

    function JugadorAngDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Jugador, Responsable, Categoria) {
        var vm = this;

        vm.jugador = entity;
        vm.clear = clear;
        vm.save = save;
        vm.responsables = Responsable.query();
        vm.categorias = Categoria.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.jugador.id !== null) {
                Jugador.update(vm.jugador, onSaveSuccess, onSaveError);
            } else {
                Jugador.save(vm.jugador, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('escuelitaParajelesFloroApp:jugadorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
