(function() {
    'use strict';

    angular
        .module('escuelitaParajelesFloroApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('jugadorang', {
            parent: 'entity',
            url: '/jugadorang?page&sort&search',
            data: {
                authorities: ['ROLE_USER', 'ROLE_COUCH'],
                pageTitle: 'escuelitaParajelesFloroApp.jugador.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/jugador/jugadorsang.html',
                    controller: 'JugadorAngController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('jugador');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('jugadorang-detail', {
            parent: 'entity',
            url: '/jugadorang/{id}',
            data: {
                authorities: ['ROLE_USER', 'ROLE_COUCH'],
                pageTitle: 'escuelitaParajelesFloroApp.jugador.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/jugador/jugadorang-detail.html',
                    controller: 'JugadorAngDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('jugador');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Jugador', function($stateParams, Jugador) {
                    return Jugador.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'jugadorang',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('jugadorang-detail.edit', {
            parent: 'jugadorang-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER', 'ROLE_COUCH']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/jugador/jugadorang-dialog.html',
                    controller: 'JugadorAngDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Jugador', function(Jugador) {
                            return Jugador.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('jugadorang.new', {
            parent: 'jugadorang',
            url: '/new',
            data: {
                authorities: ['ROLE_USER', 'ROLE_COUCH']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/jugador/jugadorang-dialog.html',
                    controller: 'JugadorAngDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                apellido: null,
                                edad: null,
                                cedula: null,
                                posicion: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('jugadorang', null, { reload: 'jugadorang' });
                }, function() {
                    $state.go('jugadorang');
                });
            }]
        })
        .state('jugadorang.edit', {
            parent: 'jugadorang',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER', 'ROLE_COUCH']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/jugador/jugadorang-dialog.html',
                    controller: 'JugadorAngDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Jugador', function(Jugador) {
                            return Jugador.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('jugadorang', null, { reload: 'jugadorang' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('jugadorang.delete', {
            parent: 'jugadorang',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER', 'ROLE_COUCH']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/jugador/jugadorang-delete-dialog.html',
                    controller: 'JugadorAngDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Jugador', function(Jugador) {
                            return Jugador.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('jugadorang', null, { reload: 'jugadorang' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
