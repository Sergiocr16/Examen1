/**
 * Created by melvin on 2/4/2017.
 */
/**
 * Created by melvin on 2/4/2017.
 */
(function() {
    'use strict';
    angular
        .module('escuelitaParajelesFloroApp')
        .factory('Particle', ParticleService);

    ParticleService.$inject = ['$resource'];

    function ParticleService ($resource) {
        function ParticleBuilder (particleAttributes) {

            var b = this;

            b.x = b.originX = 0;
            b.y = b.originY = 0 ;
            b.color = '#000000';

            b.commonAttr = {
                friction:  0.95,
                ease: 0.1,
                size:  3,
                motionColor: undefined,
            };

            function Particle(x, y, originX, originY, color) {
                this.originalColor = this.color = color;
                this.x = this.originX = x;
                this.y = this.originY = y;
                this.vx = 0;
                this.vy = 0;
            };

            Particle.prototype.inOrigin = function() {
                return  Math.abs(this.originY - this.y) < 1
                    && Math.abs(this.originX - this.x) < 1;
            };

            Particle.prototype.update = function(mouse) {
                var rx = mouse.x - this.x;
                var ry = mouse.y - this.y;
                var distance = rx * rx + ry * ry;

                if(distance < mouse.radius) {
                    var force = -mouse.radius / distance;
                    var angle = Math.atan2(ry, rx);
                    this.vx += force * Math.cos(angle);
                    this.vy += force * Math.sin(angle);
                }
                this.x += (this.vx *=  b.commonAttr.friction)
                    + (this.originX - this.x) * b.commonAttr.ease;

                this.y += (this.vy *= b.commonAttr.friction)
                    + (this.originY - this.y) * b.commonAttr.ease;

                this.color = this.inOrigin()
                    ? this.originalColor
                    : b.commonAttr.motionColor;

                this.color = this.color || this.originalColor;
            };

            Particle.prototype.reset = function(x, y, color) {
                this.originalColor = color;
                this.originX = x;
                this.originY = y;
            };

            Particle.prototype.draw = function(context) {
                context.fillStyle = this.color;
                context.fillRect(this.x, this.y,
                    b.commonAttr.size,
                    b.commonAttr.size)
            };

            this.build = function() {
                return new Particle(b.x, b.y,
                    b.originX, b.originY, b.color);
            };

            this.setColor = function(color) {
                b.color = color;
                return b;
            };

            this.setOriginX = function(x) {
                this.originX = x;
                return b;
            };
            this.setOriginY = function(y) {
                this.originY = y;
                return b;
            };
            this.setX = function(x) {
                this.x = x;
                return b;
            };

            this.setY = function(y) {
                this.y = y;
                return b;
            };
            this.setLocation = function(x, y) {
                return b.setX(x).setY(y).setOriginX(x).setOriginY(y);
            };


            this.setMotionColor = function(color) {
                b.commonAttr.motionColor = color
                    ||  b.commonAttr.motionColor;
                return b;
            };

            this.setEase = function(ease) {
                b.commonAttr.ease = ease
                    ||  b.commonAttr.ease;
                return b;
            };

            this.setFriction = function(friction) {
                b.commonAttr.friction = friction
                    || b.commonAttr.friction;
                return b;
            };

            this.setSize = function(size) {
                b.commonAttr.size = size
                    || b.commonAttr.size;
                return b;
            };
        }

        return {
            builder : function() {
                return new ParticleBuilder();
            }
        }
    }
})();
