(function() {
    'use strict';

    angular
        .module('escuelitaParajelesFloroApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('entrenamientoang', {
            parent: 'entity',
            url: '/entrenamientoang?page&sort&search',
            data: {
                authorities: ['ROLE_USER', 'ROLE_COUCH'],
                pageTitle: 'escuelitaParajelesFloroApp.entrenamiento.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/entrenamiento/entrenamientosang.html',
                    controller: 'EntrenamientoAngController',
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
                    $translatePartialLoader.addPart('entrenamiento');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('entrenamientoang-detail', {
            parent: 'entity',
            url: '/entrenamientoang/{id}',
            data: {
                authorities: ['ROLE_USER', 'ROLE_COUCH'],
                pageTitle: 'escuelitaParajelesFloroApp.entrenamiento.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/entrenamiento/entrenamientoang-detail.html',
                    controller: 'EntrenamientoAngDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('entrenamiento');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Entrenamiento', function($stateParams, Entrenamiento) {
                    return Entrenamiento.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'entrenamientoang',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('entrenamientoang-detail.edit', {
            parent: 'entrenamientoang-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER', 'ROLE_COUCH']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entrenamiento/entrenamientoang-dialog.html',
                    controller: 'EntrenamientoAngDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Entrenamiento', function(Entrenamiento) {
                            return Entrenamiento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('entrenamientoang.new', {
            parent: 'entrenamientoang',
            url: '/new',
            data: {
                authorities: ['ROLE_USER', 'ROLE_COUCH']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entrenamiento/entrenamientoang-dialog.html',
                    controller: 'EntrenamientoAngDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                descripcion: null,
                                fecha: null,
                                horaInicio: null,
                                horaFin: null,
                                categoria: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('entrenamientoang', null, { reload: 'entrenamientoang' });
                }, function() {
                    $state.go('entrenamientoang');
                });
            }]
        })
        .state('entrenamientoang.edit', {
            parent: 'entrenamientoang',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER', 'ROLE_COUCH']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entrenamiento/entrenamientoang-dialog.html',
                    controller: 'EntrenamientoAngDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Entrenamiento', function(Entrenamiento) {
                            return Entrenamiento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('entrenamientoang', null, { reload: 'entrenamientoang' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('entrenamientoang.delete', {
            parent: 'entrenamientoang',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER', 'ROLE_COUCH']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entrenamiento/entrenamientoang-delete-dialog.html',
                    controller: 'EntrenamientoAngDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Entrenamiento', function(Entrenamiento) {
                            return Entrenamiento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('entrenamientoang', null, { reload: 'entrenamientoang' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
