(function() {
    'use strict';

    angular
        .module('escuelitaParajelesFloroApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'minToTimeFilter', 'Horario', 'AlertService', '$q'];

    function HomeController ($scope, Principal, LoginService, $state, minToTimeFilter, Horario, AlertService, $q) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            $q.all([Horario.mostRecent().$promise, getAccount()])
                .then(alertMostRecentHorario)
        });

        getAccount();

        function getAccount() {
           return Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
                return account.firstName;
            });
        }
        function register () {
            $state.go('register');
        }

        function alertMostRecentHorario(result){
            var r = result[0], firstName = result[1];
            AlertService.info(
                "Hola "
                + (firstName || "")
                + ", su entrenamiento mas cercano es el "
                + r.horario.dia.toLowerCase()
                +" de "
                + minToTimeFilter(r.horario.horaInicio)
                + " a "
                + minToTimeFilter(r.horario.horaFin)
                + " con "
                + r.cantidadJugadores
                +" jugadores");
        }
    }
})();
