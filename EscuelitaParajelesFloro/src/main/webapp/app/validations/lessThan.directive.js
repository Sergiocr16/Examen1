/**
 * Created by melvin on 1/31/2017.
 */
(function() {
    angular
        .module('escuelitaParajelesFloroApp')
        .directive('lowerThan', LowerThan);

    function LowerThan() {
        return {
            require: 'ngModel',
            link: ($scope, $element, $attrs, ctrl) => {
                ctrl.$validators.lowerThan = function (modelValue, viewValue) {
                    var comparisonModel = $attrs.lowerThan;
                    if (!viewValue || !modelValue) {
                        return true;
                    }
                    var vV = parseInt(viewValue, 10);
                    var cM = parseInt(comparisonModel, 10);
                    return  isNaN(vV) || isNaN(cM) || vV < cM ;
                };
                $attrs.$observe('lowerThan', function (comparisonModel) {
                    ctrl.$validate();
                });
            }
        };
    }
})();
