(function() {
    'use strict';

    angular
        .module('escuelitaParajelesFloroApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'minToTimeFilter', 'Horario', '$q'];

    function HomeController ($scope, Principal, LoginService, $state, minToTimeFilter, Horario, $q) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.saludo = null;

        vm.login = LoginService.open;
        vm.register = register;
        vm.showHorarioMasCercano = showHorarioMasCercano;

        $scope.$on('authenticationSuccess', function() {
            getHorarioMasCercano();
        });

        getHorarioMasCercano();

        function getAccount() {
           return Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
                return account;
            });
        }
        function register () {
            $state.go('register');
        }

        function showHorarioMasCercano() {
            return vm.saludo != null;
        }

        function getHorarioMasCercano() {
            $q.all([Horario.mostRecent().$promise, getAccount()])
                .then(function (result) {
                    var r = result[0], u = result[1];
                    vm.saludo = {
                        saludoNombre: {
                            firstName: u.firstName
                        },
                        rangoTiempo: {
                            horaInicio: minToTimeFilter(r.horario.horaInicio),
                            horaFin: minToTimeFilter(r.horario.horaFin),
                        },
                        cantidadJugadores: {
                            cantidad: r.cantidadJugadores
                        },
                        dia: r.horario.dia,
                    };
                });
        }
    }
})();
