(function() {
    'use strict';

    angular
        .module('escuelitaParajelesFloroApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('desenpenoang', {
            parent: 'entity',
            url: '/desenpenoang?page&sort&search',
            data: {
                authorities: ['ROLE_USER', 'ROLE_COUCH'],
                pageTitle: 'escuelitaParajelesFloroApp.desenpeno.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/desenpeno/desenpenosang.html',
                    controller: 'DesenpenoAngController',
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
                    $translatePartialLoader.addPart('desenpeno');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('desenpenoang-detail', {
            parent: 'entity',
            url: '/desenpenoang/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'escuelitaParajelesFloroApp.desenpeno.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/desenpeno/desenpenoang-detail.html',
                    controller: 'DesenpenoAngDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('desenpeno');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Desenpeno', function($stateParams, Desenpeno) {
                    return Desenpeno.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'desenpenoang',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('desenpenoang-detail.edit', {
            parent: 'desenpenoang-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER', 'ROLE_COUCH']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/desenpeno/desenpenoang-dialog.html',
                    controller: 'DesenpenoAngDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Desenpeno', function(Desenpeno) {
                            return Desenpeno.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('desenpenoang.new', {
            parent: 'desenpenoang',
            url: '/new',
            data: {
                authorities: ['ROLE_USER', 'ROLE_COUCH']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/desenpeno/desenpenoang-dialog.html',
                    controller: 'DesenpenoAngDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                calificacion: null,
                                notas: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('desenpenoang', null, { reload: 'desenpenoang' });
                }, function() {
                    $state.go('desenpenoang');
                });
            }]
        })
        .state('desenpenoang.edit', {
            parent: 'desenpenoang',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER', 'ROLE_COUCH']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/desenpeno/desenpenoang-dialog.html',
                    controller: 'DesenpenoAngDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Desenpeno', function(Desenpeno) {
                            return Desenpeno.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('desenpenoang', null, { reload: 'desenpenoang' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('desenpenoang.delete', {
            parent: 'desenpenoang',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER', 'ROLE_COUCH']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/desenpeno/desenpenoang-delete-dialog.html',
                    controller: 'DesenpenoAngDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Desenpeno', function(Desenpeno) {
                            return Desenpeno.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('desenpenoang', null, { reload: 'desenpenoang' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
