(function() {
    'use strict';

    angular
        .module('escuelitaParajelesFloroApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('categoriaang', {
            parent: 'entity',
            url: '/categoriaang?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'escuelitaParajelesFloroApp.categoria.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/categoria/categoriasang.html',
                    controller: 'CategoriaAngController',
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
                    $translatePartialLoader.addPart('categoria');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('categoriaang-detail', {
            parent: 'entity',
            url: '/categoriaang/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'escuelitaParajelesFloroApp.categoria.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/categoria/categoriaang-detail.html',
                    controller: 'CategoriaAngDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('categoria');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Categoria', function($stateParams, Categoria) {
                    return Categoria.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'categoriaang',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('categoriaang-detail.edit', {
            parent: 'categoriaang-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/categoria/categoriaang-dialog.html',
                    controller: 'CategoriaAngDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Categoria', function(Categoria) {
                            return Categoria.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('categoriaang.new', {
            parent: 'categoriaang',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/categoria/categoriaang-dialog.html',
                    controller: 'CategoriaAngDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                edad: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('categoriaang', null, { reload: 'categoriaang' });
                }, function() {
                    $state.go('categoriaang');
                });
            }]
        })
        .state('categoriaang.edit', {
            parent: 'categoriaang',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/categoria/categoriaang-dialog.html',
                    controller: 'CategoriaAngDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Categoria', function(Categoria) {
                            return Categoria.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('categoriaang', null, { reload: 'categoriaang' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('categoriaang.delete', {
            parent: 'categoriaang',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/categoria/categoriaang-delete-dialog.html',
                    controller: 'CategoriaAngDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Categoria', function(Categoria) {
                            return Categoria.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('categoriaang', null, { reload: 'categoriaang' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
