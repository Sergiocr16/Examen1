/**
 * Created by melvin on 2/4/2017.
 */
(function () {
    'use strict';

    angular
        .module('escuelitaParajelesFloroApp')
        .factory('ImageUtil', ImageUtil);

    function ImageUtil () {

        function preloadImages(srcs) {
            function loadImage(src) {
                return new Promise(function(resolve, reject) {
                    var img = new Image();
                    img.onload = function() {
                        resolve(img);
                    };
                    img.onerror = img.onabort = function() {
                        reject(src);
                    };
                    img.src = src;
                });
            }
            var promises = [];
            for (var i = 0; i < srcs.length; i++) {
                promises.push(loadImage(srcs[i]));
            }
            return Promise.all(promises);
        }



        function forEachPixel(context,  spacing, callback, predicate) {
            var width  = context.canvas.width;
            var height = context.canvas.height;
            var pixels = context.getImageData(0, 0, width, height).data;

            for(var y = 0; y < height; y += spacing) {
                for (var x = 0; x < width; x += spacing) {
                    var i = (y * width + x) * 4;
                    var RGBA = {
                        R: pixels[i], G: pixels[i + 1],
                        B: pixels[i + 2], A: pixels[i + 3]
                    }
                    if (predicate(RGBA)) {
                        callback(x, y, RGBA);
                    }
                }
            }
        }

        function forEachAlphaPixel(context,  spacing, callback) {
            function isAlpha(RGBA) {
                return RGBA.A > 0;
            }
            forEachPixel(context,  spacing, callback, isAlpha)
        }

      return {
          forEachPixel: forEachPixel,
          forEachAlphaPixel: forEachAlphaPixel,
          preloadImages: preloadImages
      }
    }
})();
