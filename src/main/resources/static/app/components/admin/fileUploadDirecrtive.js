
'use strict';

angular
    .module('app')
    .directive('fileUpload', fileUpload);

fileUpload.$inject = ['$parse'];

function fileUpload($parse) {
    var directive = {
        restrict: 'A',
        link: function (scope, element, attrs) {
            var model = $parse(attrs.fileUpload);
            var modelSetter = model.assign;

            element.bind('change', function () {
                scope.$apply(function () {
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
    return directive;

}

