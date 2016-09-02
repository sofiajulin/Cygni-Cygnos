var renderer = PIXI.autoDetectRenderer(800, 600);

document.getElementById("gameArea").appendChild(renderer.view);

// create the root of the scene graph
var stage = new PIXI.Container();

var global = {};


// load spine data


var position = 0,
    background,
    background2,
    foreground,
    foreground2,
    block,
    pixie,
    endScreen,
    jumping = false;

stage.interactive = true;

global.startGame = function () {
    PIXI.loader
        .add('pixie', 'images/Pixie.json')
        .load(onAssetsLoaded);
};

global.stopGame = function () {
    endScreen.visible = true;

    $.ajax({
        type: "POST",
        url: "api/player/stop",
        contentType: "application/json"
    });
}


function onAssetsLoaded(loader, res) {
    background = PIXI.Sprite.fromImage('images/iP4_BGtile.jpg');
    background2 = PIXI.Sprite.fromImage('images/iP4_BGtile.jpg');
    stage.addChild(background);
    stage.addChild(background2);

    foreground = PIXI.Sprite.fromImage('images/iP4_ground.png');
    foreground2 = PIXI.Sprite.fromImage('images/iP4_ground.png');
    stage.addChild(foreground);
    stage.addChild(foreground2);

    foreground.position.y = foreground2.position.y = 600 - 130;

    block = new PIXI.Graphics();
    block.beginFill(0xFFFF00);
    block.drawRect(0, 0, 20, 50);
    block.position = {
        x: -100,
        y: foreground.position.y
    };

    stage.addChild(block);

    pixie = new PIXI.spine.Spine(res.pixie.spineData);
    // global = pixie;
    var scale = 0.3;

    pixie.position.x = 1024 / 5;
    pixie.position.y = 500;

    pixie.scale.x = pixie.scale.y = scale;

    stage.addChild(pixie);

    endScreen = new PIXI.Text('You DIED', {fontFamily: 'Arial', fontSize: 24, fill: 0xff1010, align: 'center'});
    endScreen.visible = false;
    stage.addChild(endScreen);

    pixie.stateData.setMixByName('running', 'jump', 0.2);
    pixie.stateData.setMixByName('jump', 'running', 0.4);

    pixie.state.setAnimationByName(0, 'running', true);


    global.onTouchStart = function () {
        pixie.state.setAnimationByName(0, 'jump', false);
        pixie.state.addAnimationByName(0, 'running', true, 0);
        jumping = true;
        setTimeout(function () {
            jumping = false;
        }, 600);
    }

    stage.on('mousedown', global.onTouchStart);
    stage.on('touchstart', global.onTouchStart);

    animate();
}

function animate() {
    delta = 10;
    position += delta;

    background.position.x = -(position * 0.6);
    background.position.x %= 1286 * 2;
    if (background.position.x < 0) {
        background.position.x += 1286 * 2;
    }
    background.position.x -= 1286;

    background2.position.x = -(position * 0.6) + 1286;
    background2.position.x %= 1286 * 2;
    if (background2.position.x < 0) {
        background2.position.x += 1286 * 2;
    }
    background2.position.x -= 1286;

    foreground.position.x = -position;
    foreground.position.x %= 1286 * 2;
    if (foreground.position.x < 0) {
        foreground.position.x += 1286 * 2;
    }
    foreground.position.x -= 1286;

    foreground2.position.x = -position + 1286;
    foreground2.position.x %= 1286 * 2;
    if (foreground2.position.x < 0) {
        foreground2.position.x += 1286 * 2;
    }
    foreground2.position.x -= 1286;

    animateBlock(delta);

    if (collision(pixie, block)) {
        global.stopGame();
    } else {
        requestAnimationFrame(animate);
    }

    renderer.render(stage);
}

function animateBlock(delta) {
    block.position.x -= delta;
    if (block.position.x + block.width <= 0) {
        block.position.x = stage.width;
    }
}

/**
 * If objA is inside objB
 *
 * @param objA - Must be pixie-dude that has animation on it
 * @param objB
 * @returns {boolean}
 */
function collision(objA, objB) {
    if (jumping) {
        return false;
    }
    if (objA.position.x >= objB.position.x &&
        objA.position.x <= objB.position.x + objB.width) {
        console.log(objA.position)
        console.log(objA)
        return true;
    }
    return false;
}


