(function() {
    'use strict';

    angular
        .module('escuelitaParajelesFloroApp')
        .controller('CategoriaAngDialogController', CategoriaAngDialogController);

    CategoriaAngDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Categoria', 'Horario', 'Jugador'];

    function CategoriaAngDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Categoria, Horario, Jugador) {
        var vm = this;

        vm.categoria = entity;
        vm.clear = clear;
        vm.save = save;
        vm.horarios = Horario.query();
        vm.jugadors = Jugador.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.categoria.id !== null) {
                Categoria.update(vm.categoria, onSaveSuccess, onSaveError);
            } else {
                Categoria.save(vm.categoria, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('escuelitaParajelesFloroApp:categoriaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
