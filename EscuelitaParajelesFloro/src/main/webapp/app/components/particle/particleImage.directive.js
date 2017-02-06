/**
 * Created by melvin on 2/4/2017.
 */

    //cambiar a una version mas verstatil con data binding
var module = angular
        .module('escuelitaParajelesFloroApp')
        .directive("particleImage", ['$window', '$document', 'Particle', 'ImageUtil', function ($window, $document, Particle, ImageUtil) {

            return {
                restrict: 'E',
                template: '<canvas/>',
                scope: {
                    image:"="
                },
                link: function(scope, element, attrs) {
                    var canvas      = element.find('canvas')[0];
                    var context     = canvas.getContext('2d');
                    var spacing     = parseInt(attrs.spacing, 10) || 5;
                    var r           = parseInt(attrs.radius, 10) || 20;
                    var mouse       = { radius: r * r, x: 0, y: 0 };
                    canvas.width    = parseInt(attrs.width, 10) || canvas.width;
                    canvas.height   = parseInt(attrs.height, 10) || canvas.height;

                    var particleArr = [];

                    var builder = Particle.builder()
                        .setFriction(parseFloat(attrs.friction))
                        .setEase(parseFloat(attrs.ease))
                        .setSize(parseInt(attrs.size, 10))
                        .setMotionColor(attrs.motionColor);


                    function newParticle(x, y, color) {
                        return builder
                            .setLocation(x, y)
                            .setColor(color)
                            .build();
                    }

                    function rgbaToString(RGBA){
                        return'rgba('
                            + RGBA.R + ','
                            + RGBA.G + ','
                            + RGBA.B + ','
                            + RGBA.A+ ')';
                    }

                    function init() {
                        var index = [0];
                        function processPixel(x, y, RGBA) {
                            var color = rgbaToString(RGBA);
                            if(index[0]++ < particleArr.length) {
                                particleArr[index[0] - 1].reset(x, y, color);
                            } else {
                                particleArr.push(newParticle(x, y, color));
                            }
                        }
                        var height = canvas.height;
                        var width  = canvas.width;

                        context.clearRect(0, 0, width, height);

                        context.drawImage(scope.image, 0, 0,
                            width, height);

                        ImageUtil.forEachAlphaPixel(context,
                            spacing, processPixel);

                        particleArr.length = index[0];
                    }



                    function update() {
                        for(var i = 0; i < particleArr.length; i++) {
                            particleArr[i].update(mouse);
                        }
                    }

                    function render() {
                        context.clearRect(0, 0, canvas.width,  canvas.height);
                        for(var i = 0; i < particleArr.length; i++) {
                            p = particleArr[i].draw(context);
                        }
                    };

                    function animate() {
                        update();
                        render();
                        $window.requestAnimationFrame(animate);
                    }

                    function setMouse(x, y){
                        var rect = canvas.getBoundingClientRect();
                        mouse.x = x - rect.left;
                        mouse.y = y - rect.top;
                    }
                    $document.bind("mousemove", function(event) {
                        setMouse(event.clientX,  event.clientY)
                    });

                    $document.bind("touchstart", function(event) {
                        setMouse(event.changedTouches[0].clientX,
                            event.changedTouches[0].clientY);
                    }, false);

                    $document.bind("touchmove", function(event) {
                        event.preventDefault();
                        setMouse( event.targetTouches[0].clientX,
                            event.targetTouches[0].clientY);
                    }, false);

                    $document.bind("touchend", function(event) {
                        event.preventDefault();
                        setMouse(0, 0);
                    }, false);



                    scope.$watch('image', function(newVal, oldVal) {
                        if(newVal === oldVal) { return; }
                        init();
                    })
                    animate();
                }
            };
        }]);
