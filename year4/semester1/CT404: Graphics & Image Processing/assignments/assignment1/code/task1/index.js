const canvas = document.getElementById('myCanvas');
const context = canvas.getContext('2d');

// make canvas the same size as the window
canvas.width = window.innerWidth;
canvas.height = window.innerHeight;

// prompt user to enter a number between 1 and 10 and not continuing until one is selected
let numberStickFigures = 0;
while (numberStickFigures < 1 || numberStickFigures > 10) {
    numberStickFigures = prompt("Enter an integer between 1 and 10");
}

// the colours that a stick figure may be (colours of the rainbow)
const colours = ["red", "orange", "yellow", "green", "blue", "indigo", "violet"];

// array to store position, speed etc of each stick figure
let stickFigures = [];

// create stick figure objects with random colour, facial expression, co-ordinates, and speed
for (let i = 0; i < numberStickFigures; i++) {
    stickFigures.push({
        colour: colours[Math.floor(Math.random() * colours.length)],
        isSmiling: Math.random() > 0.5,

        x: Math.random() * (canvas.width - 10),
        y: Math.random() * (canvas.height - 10),

        // movement speed
        dx: (Math.random() - 0.5) * 4,
        dy: (Math.random() - 0.5) * 4,

        limbAngle: 0,
        dancingSpeed: (Math.random() * 0.5) + 0.02  // speed of limb movement
    });
}

// function to draw a stick figure object
// note that i am not using context.save() etc for drawing the stick figures repeatedly and am instead drawing them from scratch
// i made the decision to do this to facilitate the stick figures dancing at different speeds, which would not be possible with context.save() etc
function drawStickFigure(figure) {
    context.lineWidth = 3;
    context.strokeStyle = figure.colour;
    context.fillStyle = figure.colour;

    // draw the head
    context.beginPath();
    context.arc(figure.x, figure.y - 30, 20, 0, Math.PI * 2);
    context.stroke();

    // left eye
    context.beginPath();
    context.arc(figure.x - 7, figure.y - 33, 1, 0, Math.PI * 2);
    context.stroke();
    context.fill();

    // right eye
    context.beginPath();
    context.arc(figure.x + 7, figure.y - 33, 1, 0, Math.PI * 2);
    context.stroke();
    context.fill();

    // draw a smile or frown depending on the value of figure.isSmiling
    context.beginPath();
    if (figure.isSmiling) {
        context.arc(figure.x, figure.y - 25, 6, 0, Math.PI);
    }
    else {
        context.arc(figure.x, figure.y - 20, 6, Math.PI, 2 * Math.PI);
    }
    context.stroke();

    // torso
    context.beginPath();
    context.moveTo(figure.x, figure.y - 10);
    context.lineTo(figure.x, figure.y + 50);
    context.stroke();

    // 2-part arms
    const armLength = 30;
    const upperArmAngle = Math.sin(figure.limbAngle) * Math.PI / 4;
    const lowerArmAngle = Math.cos(figure.limbAngle) * Math.PI / -2;

    // left arm
    context.beginPath();

    // upper left arm
    context.moveTo(figure.x, figure.y + 10);
    context.lineTo(figure.x - armLength * Math.cos(upperArmAngle), figure.y + 10 + armLength * Math.sin(upperArmAngle));

    // lower left arm
    context.lineTo(figure.x - armLength * Math.cos(upperArmAngle) - armLength * Math.cos(lowerArmAngle), figure.y + 10 + armLength * Math.sin(upperArmAngle) + armLength * Math.sin(lowerArmAngle));
    context.stroke();

    // right arm
    context.beginPath();

    // upper right arm
    context.moveTo(figure.x, figure.y + 10);
    context.lineTo(figure.x + armLength * Math.cos(-upperArmAngle), figure.y + 10 + armLength * Math.sin(-upperArmAngle));

    // lower right arm
    context.lineTo(figure.x + armLength * Math.cos(-upperArmAngle) + armLength * Math.cos(lowerArmAngle), figure.y + 10 + armLength * Math.sin(-upperArmAngle) + armLength * Math.sin(lowerArmAngle));
    context.stroke();

    // 2-part legs
    const legLength = 40;
    const upperLegAngle = (Math.PI / 3) + Math.sin(figure.limbAngle) * Math.PI / 10;
    const lowerLegAngle = (Math.PI / 3) + Math.cos(figure.limbAngle) * Math.PI / 5;

    // left leg
    context.beginPath();

    // left upper leg
    context.moveTo(figure.x, figure.y + 50);
    context.lineTo(figure.x - legLength * Math.cos(upperLegAngle), figure.y + 50 + legLength * Math.sin(upperLegAngle));

    // left lower leg
    context.lineTo(figure.x - legLength * Math.cos(upperLegAngle) - legLength * Math.cos(lowerLegAngle), figure.y + 50 + legLength * Math.sin(upperLegAngle) + legLength * Math.sin(lowerLegAngle));
    context.stroke();

    // right leg
    context.beginPath();

    // right upper leg
    context.moveTo(figure.x, figure.y + 50);
    context.lineTo(figure.x + legLength * Math.cos(upperLegAngle), figure.y + 50 + legLength * Math.sin(upperLegAngle));

    // right lower leg
    context.lineTo(figure.x + legLength * Math.cos(upperLegAngle) + legLength * Math.cos(lowerLegAngle), figure.y + 50 + legLength * Math.sin(upperLegAngle) + legLength * Math.sin(lowerLegAngle));
    context.stroke();
}

// function to update the stick figures (make them move)
function updateStickFigures() {
    context.clearRect(0, 0, canvas.width, canvas.height);

    stickFigures.forEach((figure) => {
        // update position
        figure.x += figure.dx;
        figure.y += figure.dy;

        // make figure bounce off screen boundaries
        if (figure.x + 20 >= canvas.width || figure.x - 20 <= 0) {
            figure.dx = -figure.dx;
        }
        if (figure.y + 100 >= canvas.height || figure.y - 50 <= 0) {
            figure.dy = -figure.dy;
        }

        // update limb angles for dancing
        figure.limbAngle += figure.dancingSpeed;

        drawStickFigure(figure);
    });
    requestAnimationFrame(updateStickFigures);
}

updateStickFigures();