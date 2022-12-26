import Pacman from "./Pacman.js";
import Enemy from "./Enemy.js";
import MovingDirection from "./MovingDirection.js";

export default class TheMap{
    constructor(titleSize){
        this.titleSize=titleSize;

        this.tqrcuaseDot= new Image();
        this.tqrcuaseDot.src="images/480px-Location_dot_cyan.svg.png";

        this.pinkDot = new Image();
        this.pinkDot.src = "images/pinkDot.png";

        this.wall= new Image();
        this.wall.src="images/217579569005202.png";

        this.powerDot = this.pinkDot;
        this.powerDotAnmationTimerDefault = 30;
        this.powerDotAnmationTimer = this.powerDotAnmationTimerDefault;

    }
  
  mapSize = Math.floor((Math.random() * (18-5)+5) + 1);
  map=[];

  countNeighbours(map, i, j){
    let n=0; 
    if(i>2 && i< this.mapSize-3 && j>2 && j<this.mapSize-3){
    if(map[i][j+1]===0) n++
    if(map[i+1][j+1]===0) n++
    if(map[i-1][j+1]===0) n++
    if(map[i-1][j]===0) n++
    if(map[i+1][j]===0) n++
    if(map[i][j-1]===0) n++
    if(map[i-1][j-1]===0) n++
    if(map[i+1][j-1]===0) n++
    }   
    return n;
  }

 fillMap(s){
  for (let i = 0; i < s; i++) {
     this.map[i] =[];
   for(let j=0; j<s; j++){
    if(i===0 || j===0 || i===this.mapSize-1 || j===this.mapSize-1)
    this.map[i][j]=1;
    else
    this.map[i][j]=(Math.random() * 7 | 0);
   }
 }
 }
 changeMap(map){
 for(let i=0; i<this.mapSize-1; ++i){
    for(let j=0; j<this.mapSize-1; ++j){
        if(i===0 || j===0 || i===this.mapSize-1 || j===this.mapSize-1){
            let neighbour=this.countNeighbours(map, i, j)
             while(map[i][j]===1 && neighbour<7){
                if(i>=1 && i<=this.mapSize-4 && j>=1 && j<=this.mapSize-1){          
                let l=Math.floor((Math.random() * ((i+1)-(i-1))+(i-1)))
                let r=Math.floor((Math.random() * ((j+1)-(j-1))+(j-1)))
                while(map[l][r]!==1) {
                l=Math.floor((Math.random() * ((i+1)-(i-1))+(i-1)))
                r=Math.floor((Math.random() * ((j+1)-(j-1))+(j-1)))
                }
                map[l][r]=0  
                          
            }
            if(this.countNeighbours(map, i, j)>neighbour) neighbour++;  
            else break;
        }
    }
   }
 }

}

    draw(ctx){
        for(let row=0; row<this.map.length;++row){
            for(let column=0; column< this.map[row].length;++column){
                let tile = this.map[row][column];
                if(tile===1){
                    this.#drawWall(ctx, column, row, this.titleSize)
                } else if (tile === 0) {
                    this.#drawDot(ctx, column, row, this.titleSize);
                } else if (tile == 4) {
                    this.#drawPowerDot(ctx, column, row, this.titleSize);
                  } else {
                    this.#drawBlank(ctx, column, row, this.titleSize);
                  }
            }
        }
    }

    #drawWall(ctx, column, row, size){
        ctx.drawImage(this.wall, column*this.titleSize, row*this.titleSize,size, size);

    }
    #drawDot(ctx, column, row, size){
        ctx.drawImage(this.tqrcuaseDot, column*this.titleSize, row*this.titleSize,size, size);

    }

    #drawPowerDot(ctx, column, row, size) {
        this.powerDotAnmationTimer--;
        if (this.powerDotAnmationTimer === 0) {
          this.powerDotAnmationTimer = this.powerDotAnmationTimerDefault;
          if (this.powerDot == this.pinkDot) {
            this.powerDot = this.tqrcuaseDot;
          } else {
            this.powerDot = this.pinkDot;
          }
        }
        ctx.drawImage(this.powerDot, column * size, row * size, size, size);
      }

      #drawBlank(ctx, column, row, size) {
        ctx.fillStyle = "black";
        ctx.fillRect(column * this.titleSize, row * this.titleSize, size, size);
      }
    
      getPacman(velocity) {
        for (let row = 0; row < this.map.length; row++) {
          for (let column = 0; column < this.map[i].length; column++) {
            let tile = this.map[row][column];
            if (tile === 3) {
              this.map[row][column] = 0;
              return new Pacman(
                column * this.titleSize,
                row * this.titleSize,
                this.titleSize,
                velocity,
                this
              );
            }
          }
        }
      }
    
      getEnemies(velocity) {
        const enemies = [];
    
        for (let row = 0; row < this.map.length; row++) {
          for (let column = 0; column < this.map[i].length; column++) {
            const tile = this.map[row][column];
            if (tile == 6) {
              this.map[row][column] = 0;
              enemies.push(
                new Enemy(
                  column * this.titleSize,
                  row * this.titleSize,
                  this.titleSize,
                  velocity,
                  this
                )
              );
            }
          }
        }
        return enemies;
      }
    


    setCanvasSize(canvas){
        this.fillMap(this.mapSize)
        this.changeMap(this.map)
        canvas.width= this.map[0].length * this.titleSize;
        canvas.height = this.map.length * this.titleSize;
    }

    didCollideWithEnvironment(x, y, direction) {
        if (direction == null) {
          return;
        }
    
        if (
          Number.isInteger(x / this.titleSize) &&
          Number.isInteger(y / this.titleSize)
        ) {
          let column = 0;
          let row = 0;
          let nextColumn = 0;
          let nextRow = 0;
    
          switch (direction) {
            case MovingDirection.right:
              nextColumn = x + this.titleSize;
              column = nextColumn / this.titleSize;
              row = y / this.titleSize;
              break;
            case MovingDirection.left:
              nextColumn = x - this.titleSize;
              column = nextColumn / this.titleSize;
              row = y / this.titleSize;
              break;
            case MovingDirection.up:
              nextRow = y - this.titleSize;
              row = nextRow / this.titleSize;
              column = x / this.titleSize;
              break;
            case MovingDirection.down:
              nextRow = y + this.titleSize;
              row = nextRow / this.titleSize;
              column = x / this.titleSize;
              break;
          }
          const tile = this.map[row][column];
          if (tile === 1) {
            return true;
          }
        }
        return false;
      }
    
      didWin() {
        return this.#dotsLeft() === 0;
      }
    
      #dotsLeft() {
        return this.map.flat().filter((tile) => tile === 0).length;
      }
    
      eatDot(x, y) {
        const row = y / this.titleSize;
        const column = x / this.titleSize;
        if (Number.isInteger(row) && Number.isInteger(column)) {
          if (this.map[row][column] === 0) {
            this.map[row][column] = 5;
            return true;
          }
        }
        return false;
      }
    
      eatPowerDot(x, y) {
        const row = y / this.titleSize;
        const column = x / this.titleSize;
        if (Number.isInteger(row) && Number.isInteger(column)) {
          const tile = this.map[row][column];
          if (tile === 7) {
            this.map[row][column] = 5;
            return true;
          }
        }
        return false;
      }
}