(function() {
    'use strict';

    angular
        .module('escuelitaParajelesFloroApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('responsableang', {
            parent: 'entity',
            url: '/responsableang?page&sort&search',
            data: {
                authorities: ['ROLE_USER', 'ROLE_COUCH'],
                pageTitle: 'escuelitaParajelesFloroApp.responsable.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/responsable/responsablesang.html',
                    controller: 'ResponsableAngController',
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
                    $translatePartialLoader.addPart('responsable');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('responsableang-detail', {
            parent: 'entity',
            url: '/responsableang/{id}',
            data: {
                authorities: ['ROLE_USER', 'ROLE_COUCH'],
                pageTitle: 'escuelitaParajelesFloroApp.responsable.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/responsable/responsableang-detail.html',
                    controller: 'ResponsableAngDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('responsable');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Responsable', function($stateParams, Responsable) {
                    return Responsable.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'responsableang',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('responsableang-detail.edit', {
            parent: 'responsableang-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER', 'ROLE_COUCH']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/responsable/responsableang-dialog.html',
                    controller: 'ResponsableAngDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Responsable', function(Responsable) {
                            return Responsable.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('responsableang.new', {
            parent: 'responsableang',
            url: '/new',
            data: {
                authorities: ['ROLE_USER', 'ROLE_COUCH']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/responsable/responsableang-dialog.html',
                    controller: 'ResponsableAngDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                apellido: null,
                                email: null,
                                telefono: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('responsableang', null, { reload: 'responsableang' });
                }, function() {
                    $state.go('responsableang');
                });
            }]
        })
        .state('responsableang.edit', {
            parent: 'responsableang',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER', 'ROLE_COUCH']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/responsable/responsableang-dialog.html',
                    controller: 'ResponsableAngDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Responsable', function(Responsable) {
                            return Responsable.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('responsableang', null, { reload: 'responsableang' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('responsableang.delete', {
            parent: 'responsableang',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER', 'ROLE_COUCH']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/responsable/responsableang-delete-dialog.html',
                    controller: 'ResponsableAngDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Responsable', function(Responsable) {
                            return Responsable.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('responsableang', null, { reload: 'responsableang' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
