/**
 * Created by melvin on 2/4/2017.
 */

    //cambiar a una version mas verstatil con data binding
angular.module('escuelitaParajelesFloroApp')
       .directive("particleImage", ParticleImage);

ParticleImage.$inject = ['$window', '$document', 'Particle', 'ImageUtil', 'ParticleImageAnimator', '$timeout'];

function ParticleImage($window, $document, Particle, ImageUtil, ParticleImageAnimator, $timeout) {
    return {
        restrict: 'E',
        template: '<canvas/>',
        scope: {
            src: "="
        },
        link: function(scope, element, attrs) {
            let canvas      = element.find('canvas')[0];
            let context     = canvas.getContext('2d');
            let r           = parseInt(attrs.radius, 10) || 20;
            let mouse       = { radius: r * r, x: -r, y: -r };
            let timeout     = parseInt(attrs.timeout, 10) || 5000;
            let piaService  = ParticleImageAnimator;
            canvas.width    = parseInt(attrs.width, 10) || canvas.width;
            canvas.height   = parseInt(attrs.height, 10) || canvas.height;
            context.globalAlpha = 0.7;

            if (!angular.isArray(scope.src)
                && scope.src.length !== 0) {
                console.log("Fuyck");
                return;

            }
            console.log(attrs.motionColor);
            let builder = Particle.builder()
                .setFriction(parseFloat(attrs.friction))
                .setEase(parseFloat(attrs.ease))
                .setSize(parseInt(attrs.size, 10))
                .setMotionColor(attrs.motionColor);

            let pia = piaService.create(builder, context)
                .setSpacing(parseInt(attrs.spacing, 10));

            function setMouse(x, y) {
                var rect = canvas.getBoundingClientRect();
                mouse.x = x - rect.left;
                mouse.y = y - rect.top;
            }
            $document.bind("mousemove", event => {
                setMouse(event.clientX, event.clientY);
            });

            $document.bind("touchstart", event => {
                setMouse(event.changedTouches[0].clientX,
                    event.changedTouches[0].clientY);
            }, false);

            $document.bind("touchmove", event => {
                event.preventDefault();
                setMouse( event.targetTouches[0].clientX,
                    event.targetTouches[0].clientY);
            }, false);

            $document.bind("touchend", event => {
                event.preventDefault();
                setMouse(0, 0);
            }, false);

            function slideShow(images) {
                if(images.length  === 1) {
                    pia.setImage(images[0]);
                }
                else {
                    (function p(i) {
                        pia.setImage(images[i]);
                        $timeout(()=> p((i + 1) % images.length), timeout);
                    })(0);
                }
                pia.start(mouse);
            }

            ImageUtil.preloadImages(scope.src)
                .then(slideShow);
        }
    };
}
