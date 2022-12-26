import TheMap from "./TheMap.js";

const titleSize=32;
const velocity=2;

const canvas= document.getElementById("gameCanvas");
const ctx = canvas.getContext("2d");
const titleMap= new TheMap(titleSize);
const pacman = titleMap.getPacman(velocity);
const enemies = titleMap.getEnemies(velocity);

let gameOver = false;
let gameWin = false;
const gameOverSound = new Audio("sounds/gameOver.wav");
const gameWinSound = new Audio("sounds/gameWin.wav");

function pause() {
    return !pacman.madeFirstMove || gameOver || gameWin;
  }

function gameLoop(){
    titleMap.draw(ctx);
    drawGameEnd();
    pacman.draw(ctx, pause(), enemies);
    enemies.forEach((enemy) => enemy.draw(ctx, pause(), pacman));
    checkGameOver();
    checkGameWin();
    
}

function checkGameWin() {
    if (!gameWin) {
      gameWin = titleMap.didWin();
      if (gameWin) {
        gameWinSound.play();
      }
    }
  }
  
  function checkGameOver() {
    if (!gameOver) {
      gameOver = isGameOver();
      if (gameOver) {
        gameOverSound.play();
      }
    }
  }
  
  function isGameOver() {
    return enemies.some(
      (enemy) => !pacman.powerDotActive && enemy.collideWith(pacman)
    );
  }
  
 
  
  function drawGameEnd() {
    if (gameOver || gameWin) {
      let text = " You Win!";
      if (gameOver) {
        text = "Game Over";
      }
  
      ctx.fillStyle = "black";
      ctx.fillRect(0, canvas.height / 3.2, canvas.width, 80);
  
      ctx.font = "75px comic sans";
      const gradient = ctx.createLinearGradient(0, 0, canvas.width, 0);
      gradient.addColorStop("0", "magenta");
      gradient.addColorStop("0.5", "blue");
      gradient.addColorStop("1.0", "red");
  
      ctx.fillStyle = gradient;
      ctx.fillText(text, 10, canvas.height / 2);
    }
  }
  
  titleMap.setCanvasSize(canvas);
  setInterval(gameLoop, 1000/75);
  