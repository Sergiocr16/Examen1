(function() {
    'use strict';

    angular
        .module('escuelitaParajelesFloroApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('horarioang', {
            parent: 'entity',
            url: '/horarioang?page&sort&search',
            data: {
                authorities: ['ROLE_USER', 'ROLE_COUCH'],
                pageTitle: 'escuelitaParajelesFloroApp.horario.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/horario/horariosang.html',
                    controller: 'HorarioAngController',
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
                    $translatePartialLoader.addPart('horario');
                    $translatePartialLoader.addPart('dia');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('horarioang-detail', {
            parent: 'entity',
            url: '/horarioang/{id}',
            data: {
                authorities: ['ROLE_USER', 'ROLE_COUCH'],
                pageTitle: 'escuelitaParajelesFloroApp.horario.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/horario/horarioang-detail.html',
                    controller: 'HorarioAngDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('horario');
                    $translatePartialLoader.addPart('dia');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Horario', function($stateParams, Horario) {
                    return Horario.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'horarioang',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('horarioang-detail.edit', {
            parent: 'horarioang-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER', 'ROLE_COUCH']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/horario/horarioang-dialog.html',
                    controller: 'HorarioAngDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Horario', function(Horario) {
                            return Horario.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('horarioang.new', {
            parent: 'horarioang',
            url: '/new',
            data: {
                authorities: ['ROLE_USER', 'ROLE_COUCH']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/horario/horarioang-dialog.html',
                    controller: 'HorarioAngDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                horaInicio: null,
                                horaFin: null,
                                dia: null,
                                categoria: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('horarioang', null, { reload: 'horarioang' });
                }, function() {
                    $state.go('horarioang');
                });
            }]
        })
        .state('horarioang.edit', {
            parent: 'horarioang',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER', 'ROLE_COUCH']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/horario/horarioang-dialog.html',
                    controller: 'HorarioAngDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Horario', function(Horario) {
                            return Horario.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('horarioang', null, { reload: 'horarioang' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('horarioang.delete', {
            parent: 'horarioang',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER', 'ROLE_COUCH']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/horario/horarioang-delete-dialog.html',
                    controller: 'HorarioAngDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Horario', function(Horario) {
                            return Horario.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('horarioang', null, { reload: 'horarioang' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
