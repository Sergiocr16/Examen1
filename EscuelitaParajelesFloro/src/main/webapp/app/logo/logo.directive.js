/**
 * Created by melvin on 1/31/2017.
 *//**
 * Created by melvin on 1/31/2017.
 */
var module = angular
        .module('escuelitaParajelesFloroApp')
        .directive("logo", ['$window', '$document', function ($window, $document) {

            return {
                restrict: 'E',
                template: '<canvas/>',
                link: function(scope, element, attrs) {
                    var canvas      = element.find('canvas')[0];
                    var context     = canvas.getContext('2d');
                    var width       = canvas.width  = parseInt(attrs.width, 10) || canvas.width;
                    var height      = canvas.height = parseInt(attrs.height, 10) || canvas.height;
                    var particleArr = [];
                    var image       =  new Image;

                    image.src = attrs.src;
                    context.globalAlpha = 1;
                    var mouse = {
                        radius: Math.pow(parseInt(attrs.radius, 10) || 20, 2),
                        x: 0,
                        y: 0
                    };

                    var particleAttributes = {
                        friction: parseFloat(attrs.friction) || 0.95,
                        ease:  parseFloat(attrs.ease) || 0.19,
                        spacing: parseInt(attrs.spacing, 10) || 5,
                        size: parseInt(attrs.size, 10) || 3,
                        color: attrs.color || "#000000"
                    };

                    function Particle(x, y) {
                        this.x = this.originX = x;
                        this.y = this.originY = y;
                        this.vx = 0;
                        this.vy = 0;

                        this.update = function() {
                            var rx = mouse.x - this.x;
                            var ry = mouse.y - this.y;
                            var distance = rx * rx + ry * ry;

                            if(distance < mouse.radius) {
                                var force = -mouse.radius / distance;
                                var angle = Math.atan2(ry, rx);
                                this.vx += force * Math.cos(angle);
                                this.vy += force * Math.sin(angle);
                            }
                            this.x += (this.vx *= particleAttributes.friction)
                                + (this.originX - this.x) * particleAttributes.ease;

                            this.y += (this.vy *= particleAttributes.friction)
                                + (this.originY - this.y) * particleAttributes.ease;
                        }
                    }

                    function init() {
                        context.drawImage(image, 0, 0, width, height);
                        var pixels = context.getImageData(0, 0, width, height).data;
                        console.log(pixels.length);
                        for(var y = 0; y < height; y += particleAttributes.spacing) {
                            for(var x = 0; x < width; x += particleAttributes.spacing) {
                                var i = (y * width + x) * 4;
                                if(pixels[i + 3] > 0) {
                                    particleArr.push(new Particle(x, y));
                                }
                            }
                        }
                    };

                    function update() {
                        for(var i = 0; i < particleArr.length; i++) {
                            particleArr[i].update();
                        }
                    };

                    function render() {
                        context.clearRect(0, 0, width, height);
                        context.fillStyle = particleAttributes.color;
                        for(var i = 0; i < particleArr.length; i++) {
                            var p = particleArr[i];
                            context.fillRect(p.x, p.y,
                                particleAttributes.size,
                                particleAttributes.size);
                        }

                    };
                    function animate() {
                        update();
                        render();
                        $window.requestAnimationFrame(animate);
                    }

                    image.onload = init;
                    animate();

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
                }
            };
        }]);
