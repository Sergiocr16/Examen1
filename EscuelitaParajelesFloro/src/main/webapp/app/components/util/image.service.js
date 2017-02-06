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
                    let img = new Image();
                    img.onload = function() {
                        resolve(img);
                    };
                    img.onerror = img.onabort = function() {
                        reject(src);
                    };
                    img.src = src;
                });
            }
            return Promise.all(srcs.map(loadImage));
        }

        function* forEachPixel(context,  spacing, predicate) {
            let width  = context.canvas.width;
            let height = context.canvas.height;
            let pixels = context.getImageData(0, 0, width, height).data;

            for(let y = 0; y < height; y += spacing) {
                for (let x = 0; x < width; x += spacing) {
                    let i = (y * width + x) * 4;
                    let RGBA = {
                        R: pixels[i], G: pixels[i + 1],
                        B: pixels[i + 2], A: pixels[i + 3]
                    };
                    if (predicate(RGBA)) {
                        yield [x, y, RGBA];
                    }
                }
            }
        }

        function forEachAlphaPixel(context,  spacing) {
            return forEachPixel(context,  spacing, RGBA => RGBA.A > 0);
        }

        return {
            forEachPixel: forEachPixel,
            forEachAlphaPixel: forEachAlphaPixel,
            preloadImages: preloadImages
        };
    }
})();
