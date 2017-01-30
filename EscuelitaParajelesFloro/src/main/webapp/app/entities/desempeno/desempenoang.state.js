(function() {
    'use strict';

    angular
        .module('escuelitaParajelesFloroApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('desempenoang', {
            parent: 'entity',
            url: '/desempenoang?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'escuelitaParajelesFloroApp.desempeno.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/desempeno/desempenosang.html',
                    controller: 'DesempenoAngController',
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
                    $translatePartialLoader.addPart('desempeno');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('desempenoang-detail', {
            parent: 'entity',
            url: '/desempenoang/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'escuelitaParajelesFloroApp.desempeno.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/desempeno/desempenoang-detail.html',
                    controller: 'DesempenoAngDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('desempeno');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Desempeno', function($stateParams, Desempeno) {
                    return Desempeno.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'desempenoang',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('desempenoang-detail.edit', {
            parent: 'desempenoang-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/desempeno/desempenoang-dialog.html',
                    controller: 'DesempenoAngDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Desempeno', function(Desempeno) {
                            return Desempeno.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('desempenoang.new', {
            parent: 'desempenoang',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/desempeno/desempenoang-dialog.html',
                    controller: 'DesempenoAngDialogController',
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
                    $state.go('desempenoang', null, { reload: 'desempenoang' });
                }, function() {
                    $state.go('desempenoang');
                });
            }]
        })
        .state('desempenoang.edit', {
            parent: 'desempenoang',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/desempeno/desempenoang-dialog.html',
                    controller: 'DesempenoAngDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Desempeno', function(Desempeno) {
                            return Desempeno.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('desempenoang', null, { reload: 'desempenoang' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('desempenoang.delete', {
            parent: 'desempenoang',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/desempeno/desempenoang-delete-dialog.html',
                    controller: 'DesempenoAngDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Desempeno', function(Desempeno) {
                            return Desempeno.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('desempenoang', null, { reload: 'desempenoang' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
