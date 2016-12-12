// Moon
var moon = {};
moon.mtop = 50;
moon.g = 0.04; // Gravity

// Ship
var ship = {};
ship.y = 0; // Position
ship.v = 0; // Speed
ship.thrust = -moon.g; // Thrust

ship.maxSpeed = 20;
ship.safeSpeed = 2; // Maximum speed 'til it explodes

ship.maxFuel = 175; // Fuel/FPS
ship.fuel = ship.maxFuel; // Restore fuel

var pause = false;
var spacePressed = 0;

// Date (time)
var d = new Date();

// Fecha de inicio del juego
var dInicio = d.getDate() + '/' + (d.getMonth() + 1) + '/' + d.getFullYear() + ' ' + d.getHours() + ':' + d.getMinutes() + ':' + d.getSeconds();

// Fecha final
var dFinal;

// Sound effects
var audio = {};
audio.puh = document.createElement("AUDIO");
audio.puh.src = './sound/puh.mp3';
$(audio.puh).prop('volume', 0);



function map(x, in_min, in_max, out_min, out_max) {
    return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
}

function reset() {
// Lo dejamos vacío para no reiniciar el juego por error.
}

function getSpeed() {
    return parseFloat(Math.round(-ship.v * 100) / 100).toFixed(2);
}

function resize() {
    moon.base = $('#landing-pad').height() + -($('#landing-pad').height() - 150) * 0.4 + 30;
}

function doEvent(kind, ms) {
    var d2 = new Date();
    pause = true;
    $('#state h1').addClass(kind);
    $('#state h1').html((kind === 'win') ? 'YOU WIN!' : 'YOU LOSE');
    $('#state h2').html((kind === 'win') ? 'Speed: ' + ms + ' m/s' : 'Maybe next time...');
    $('#time').html(d.getDate() + '/' + (d2.getMonth() + 1) + '/' + d2.getFullYear() + ' ' + d2.getHours() + ':' + d2.getMinutes() + ':' + d2.getSeconds());

    // Guardamos la fecha final de la partida.
    dFinal = d2.getDate() + '/' + (d2.getMonth() + 1) + '/' + d2.getFullYear() + ' ' + d2.getHours() + ':' + d2.getMinutes() + ':' + d2.getSeconds();
    $('#ms').css('color', (kind === 'win') ? '#0a0' : '#f00');
    if (kind === 'lose')
    {
        $(audio.puh).prop('volume', 1);
        $('#state h2').html((kind === 'lose') ? 'Speed: ' + ms + ' m/s' : 'Maybe next time...');
        $('#time').html(d.getDate() + '/' + (d.getMonth() + 1) + '/' + d.getFullYear() + ' ' + d.getHours() + ':' + d.getMinutes() + ':' + d.getSeconds());
        audio.puh.play();
        $('#explode').addClass('exploded');
        $('#explode').css('background', 'url(\'./img/explode.gif?p=' + new Date().getTime() + '\')');
    }
    $('#state').delay((kind === 'win') ? 0 : 1000).show(0);

    // Función que guarda la fecha final de la partida.
    $(document).ready(function () {
        $.ajax({
            url: '../indexServlet',
            type: 'post',

            // Guardamos la fecha final de la partida.
            data: {
                dInicio: dInicio,
                dFinal: dFinal,
                speed: ms
            }
        });
    });
}

$(document).ready(function () {
    $('body').keydown(function (e) {
        e.preventDefault();
        if (e.keyCode === 32)
            if (pause && !spacePressed && $('#state').css('display') === 'block')
                reset(); // Space while alert is shown to restart game
            else
                spacePressed = 1;
    });
    $('body').keyup(function (e) {
        e.preventDefault();
        if (e.keyCode === 32)
            spacePressed = 0;
    });

    $('#game').bind('touchstart', function (e) {
        e.preventDefault();
        audio.puh.play();
        spacePressed = 1;
    }).bind('touchend', function (e) {
        e.preventDefault();
        spacePressed = 0;
    });

    // Update
    window.setInterval(function () {
        if (!pause) {
            $(audio.puh).delay(1000).prop('volume', 0);
            ship.v += (spacePressed) ? ((ship.fuel > 0) ? ship.thrust : moon.g) : moon.g; // Aceleración
            ship.v = (ship.v > ship.maxSpeed) ? ship.maxSpeed : ((ship.v < -ship.maxSpeed) ? -ship.maxSpeed : ship.v); // Velocidad

            if ((ship.v > 0 && ship.y < 500) || (ship.v < 0 && ship.y > 0))
                ship.y += ship.v;
            else
            {
                if (ship.y >= 500)
                {
                    ship.y = 500;
                    $('#ms').html(getSpeed() + ' m/s');
                    doEvent((ship.v > ship.safeSpeed) ? 'lose' : 'win', getSpeed());
                }
                if (ship.y < 0)
                    ship.y = 0;
                if (!pause)
                    ship.v = 0;
            }

            // Quitamos fuel
            if (ship.fuel > 0 && spacePressed)
                ship.fuel--;

            // Dibujamos el juego
            resize();
            $('#gauge div').css('width', ship.fuel / ship.maxFuel * 100 + '%');
            $('#ship').css('top', map(ship.y, 0, 500, moon.mtop, $('body').height() - moon.base));
            $('#explode').css('top', map(ship.y, 0, 500, moon.mtop, $('body').height() - moon.base) - 100);
            $('#ship').css('background', (spacePressed & ship.fuel > 0) ? 'url(\'./img/ship.png\')' : 'url(\'./img/shipOff.png\')');
            $('#ms').html(getSpeed() + ' m/s');
        }
    }, 16.6666667);
});