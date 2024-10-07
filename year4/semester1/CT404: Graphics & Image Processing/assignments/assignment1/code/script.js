const canvas = document.getElementById('myCanvas');
const ctx = canvas.getContext('2d');

// set canvas dimensions
canvas.width = window.innerWidth;
canvas.height = window.innerHeight;

// draw the stick figures
draw();

// Draw stick figure function
function drawStickFigure(x, y) {
    ctx.lineWidth = 3;
    ctx.strokeStyle = 'black';

    // draw the stick figure's head
    ctx.beginPath();
    ctx.arc(x, y - 30, 20, 0, Math.PI * 2);
    ctx.stroke();

    // draw the stick figure's left eye
    ctx.beginPath();
    ctx.arc(x - 7, y - 33, 1, 0, Math.PI * 2);
    ctx.stroke();

    // draw the stick figure's right eye
    ctx.beginPath();
    ctx.arc(x + 7, y - 33, 1, 0, Math.PI * 2);
    ctx.stroke();

    // draw the stick figure's mouth
    ctx.beginPath();
    ctx.arc(x, y - 25, 6, 0, Math.PI);
    ctx.stroke();

    // Body
    ctx.beginPath();
    ctx.moveTo(x, y - 10);
    ctx.lineTo(x, y + 50); // Length of body
    ctx.stroke();

    // Arms
    ctx.beginPath();
    ctx.moveTo(x - 30, y + 10); // Left arm
    ctx.lineTo(x + 30, y + 10); // Right arm
    ctx.stroke();

    // Legs
    ctx.beginPath();
    ctx.moveTo(x, y + 50); // Start at bottom of body
    ctx.lineTo(x - 20, y + 100); // Left leg
    ctx.moveTo(x, y + 50);
    ctx.lineTo(x + 20, y + 100); // Right leg
    ctx.stroke();
}

// Animation function
// function animate() {
//     ctx.clearRect(0, 0, canvas.width, canvas.height); // Clear the canvas
//
//     drawStickFigure(x, y); // Draw stick figure at current position
//
//     // Update position
//     x += dx;
//     y += dy;
//
//     // Check boundaries and reverse direction if necessary
//     if (x + 20 > canvas.width || x - 20 < 0) dx = -dx; // Horizontal boundary check
//     if (y + 100 > canvas.height || y - 50 < 0) dy = -dy; // Vertical boundary check
//
//     requestAnimationFrame(animate); // Recursive call to keep animation going
// }

// Start animation
// animate();

function draw() {
    // prompt the user to enter a number in a range and not continuing under a number in that range is selected
    let numberStickFigures = 0;
    while (numberStickFigures < 1 || numberStickFigures > 5) {
        numberStickFigures = prompt("Enter an integer between 1 and 5");
    }

    for (let i = 1; i <= numberStickFigures; i++) {
        // place the stick figure randomly on the canvas
        const x = Math.floor(Math.random() * canvas.width);
        const y = Math.floor(Math.random() * canvas.height);

        drawStickFigure(x, y);
    }
}