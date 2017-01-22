(function() {
    'use strict';

    angular
        .module('escuelitaParajelesFloroApp')
        .controller('CategoriaAngDeleteController',CategoriaAngDeleteController);

    CategoriaAngDeleteController.$inject = ['$uibModalInstance', 'entity', 'Categoria'];

    function CategoriaAngDeleteController($uibModalInstance, entity, Categoria) {
        var vm = this;

        vm.categoria = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Categoria.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
