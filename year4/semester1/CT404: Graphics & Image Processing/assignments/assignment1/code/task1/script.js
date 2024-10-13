const canvas = document.getElementById('myCanvas');
const context = canvas.getContext('2d');

// make canvas the same size as the window
canvas.width = window.innerWidth;
canvas.height = window.innerHeight;

// prompt user to enter a number between 1 and 5 and not continuing until one is selected
let numberStickFigures = 0;
while (numberStickFigures < 1 || numberStickFigures > 5) {
    numberStickFigures = prompt("Enter an integer between 1 and 5");
}

// array to store position, speed etc of each stick figure
let stickFigures = [];

// Create stick figures with random positions and velocities
for (let i = 0; i < numberStickFigures; i++) {
    stickFigures.push({
        x: Math.random() * canvas.width,
        y: Math.random() * canvas.height,
        dx: (Math.random() - 0.5) * 4,
        dy: (Math.random() - 0.5) * 4,
        angle: 0, // Initial limb angle
        angleSpeed: (Math.random() * 0.05) + 0.02 // Speed of limb movement
    });
}

// Draw the stick figure
function drawStickFigure(x, y, angle) {
    context.lineWidth = 3;
    context.strokeStyle = 'black';

    // draw the head
    context.beginPath();
    context.arc(x, y - 30, 20, 0, Math.PI * 2);
    context.stroke();

    // left eye
    context.beginPath();
    context.arc(x - 7, y - 33, 1, 0, Math.PI * 2);
    context.stroke();
    context.fill();     // fill in circle

    // right eye
    context.beginPath();
    context.arc(x + 7, y - 33, 1, 0, Math.PI * 2);
    context.stroke();
    context.fill();

    // mouth
    context.beginPath();
    context.arc(x, y - 25, 6, 0, Math.PI);
    context.stroke();

    // torso
    context.beginPath();
    context.moveTo(x, y - 10);
    context.lineTo(x, y + 50);
    context.stroke();

    // Arms with 2 parts (upper and lower arm)
    const armLength = 30;
    const upperArmAngle = Math.sin(angle) * Math.PI / 4; // Swinging angle for upper arms
    const lowerArmAngle = Math.cos(angle) * Math.PI / -2; // Swinging angle for lower arms

    context.beginPath();
    // Left upper arm
    context.moveTo(x, y + 10);
    context.lineTo(x - armLength * Math.cos(upperArmAngle), y + 10 + armLength * Math.sin(upperArmAngle));
    // Left lower arm
    context.lineTo(x - armLength * Math.cos(upperArmAngle) - armLength * Math.cos(lowerArmAngle), y + 10 + armLength * Math.sin(upperArmAngle) + armLength * Math.sin(lowerArmAngle));

    // Right upper arm
    context.moveTo(x, y + 10);
    context.lineTo(x + armLength * Math.cos(upperArmAngle), y + 10 + armLength * Math.sin(upperArmAngle));
    // Right lower arm
    context.lineTo(x + armLength * Math.cos(upperArmAngle) + armLength * Math.cos(lowerArmAngle), y + 10 + armLength * Math.sin(upperArmAngle) + armLength * Math.sin(lowerArmAngle));
    context.stroke();

    // Legs with 2 parts (thigh and calf)
    const legLength = 40;
    const upperLegAngle = Math.sin(angle) * Math.PI / 5; // Swinging angle for upper legs
    const lowerLegAngle = Math.cos(angle) * Math.PI / 2; // Swinging angle for lower legs

    context.beginPath();
    // Left upper leg
    context.moveTo(x, y + 50);
    context.lineTo(x - legLength * Math.cos(upperLegAngle), y + 50 + legLength * Math.sin(upperLegAngle));
    // Left lower leg
    context.lineTo(x - legLength * Math.cos(upperLegAngle) - legLength * Math.cos(lowerLegAngle), y + 50 + legLength * Math.sin(upperLegAngle) + legLength * Math.sin(lowerLegAngle));

    // Right upper leg
    context.moveTo(x, y + 50);
    context.lineTo(x + legLength * Math.cos(upperLegAngle), y + 50 + legLength * Math.sin(upperLegAngle));
    // Right lower leg
    context.lineTo(x + legLength * Math.cos(upperLegAngle) + legLength * Math.cos(lowerLegAngle), y + 50 + legLength * Math.sin(upperLegAngle) + legLength * Math.sin(lowerLegAngle));
    context.stroke();
}

// Update the position and dance movements of each stick figure
function updateStickFigures() {
    context.clearRect(0, 0, canvas.width, canvas.height); // Clear the canvas

    stickFigures.forEach((figure) => {
        // Update position
        figure.x += figure.dx;
        figure.y += figure.dy;

        // Bounce off the boundaries
        if (figure.x + 20 > canvas.width || figure.x - 20 < 0) figure.dx = -figure.dx; // Horizontal boundary check
        if (figure.y + 100 > canvas.height || figure.y - 50 < 0) figure.dy = -figure.dy; // Vertical boundary check

        // Update limb angles for dancing
        figure.angle += figure.angleSpeed;

        // Draw the updated stick figure with animated limbs
        drawStickFigure(figure.x, figure.y, figure.angle);
    });

    requestAnimationFrame(updateStickFigures); // Recursive call for animation
}

// Start animation
updateStickFigures();
