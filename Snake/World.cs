using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;
using System.Windows.Threading;

namespace SnakeGameCouseProject
{
    public class World
    {
        private MainWindow mainWindow;
        public int ElementSize { get; private set; }
        public int ColumnCount { get; private set; }
        public int RowCount { get; private set; }
        public double GameAreaWidth { get; private set; }
        public double GameAreaHeight { get; private set; }
        Random random;

        public Food Food { get; set; }
        public TheSnake Snake { get; set; }
        DispatcherTimer _timer;
        public bool IsRunning { get; set; }
        public World(MainWindow mainWindow)
        {
            this.mainWindow = mainWindow;
            ImageBrush backGround = new ImageBrush();
            backGround.ImageSource = new BitmapImage(new Uri(@"d3d_default_Bermuda-Header.jpg", UriKind.Relative));
            this.mainWindow.Background = backGround;
            random = new Random(DateTime.Now.Millisecond / DateTime.Now.Second);
        }
        public void InitializeGame(int difficulty, int elementSize)
        {
            ElementSize = elementSize*2;
            GameAreaWidth = mainWindow.World.ActualWidth;
            GameAreaHeight = mainWindow.World.ActualHeight;
            ColumnCount = (int)GameAreaWidth / ElementSize;
            RowCount = (int)GameAreaHeight / ElementSize;

            DrawGameWorld();
            InitializeSnake();
            InitializeTimer(difficulty);
            IsRunning = true;
        }

        private void InitializeTimer(int difficulty)
        {
            var interval = TimeSpan.FromSeconds(0.1 + .9 / difficulty).Ticks;
            _timer = new DispatcherTimer
            {
                Interval = TimeSpan.FromTicks(interval)
            };
            _timer.Tick += MainGameLoop;
            _timer.Start();
        }
        private void InitializeSnake()
        {
            Snake = new TheSnake(ElementSize);
            Snake.PositionFirstElement(ColumnCount, RowCount, MovementDirection.Right);
        }

        private void MainGameLoop(object sender, EventArgs e)
        {
            Snake.MoveSnake();
            CheckCollision();
            CreateFood();
            Draw();
        }
        private void Draw()
        {
            DrawSnake();
            DrawFood();
        }
        private void DrawGameWorld()
        {
            int i = 0;
            for (; i < RowCount; i++)
                mainWindow.World.Children.Add(GenerateHorizontalWorldLine(i));
            int j = 0;
            for (; j < ColumnCount; j++)
                mainWindow.World.Children.Add(GenerateVerticalWorldLine(j));
            mainWindow.World.Children.Add(GenerateVerticalWorldLine(j));
            mainWindow.World.Children.Add(GenerateHorizontalWorldLine(i));
        }

        private void DrawSnake()
        {
            foreach (var snakeElement in Snake.Elements)
            {
                if (!mainWindow.World.Children.Contains(snakeElement.Element))
                    mainWindow.World.Children.Add(snakeElement.Element);
                Canvas.SetLeft(snakeElement.Element, snakeElement.X + 2);
                Canvas.SetTop(snakeElement.Element, snakeElement.Y + 2);
            }
        }
        
        
       

			
        private void DrawFood()
        {
            if (!mainWindow.World.Children.Contains(Food.Element))
                mainWindow.World.Children.Add(Food.Element);
            Canvas.SetLeft(Food.Element, Food.X + 2);
            Canvas.SetTop(Food.Element, Food.Y + 2);
        }

        private void CheckCollision()
        {
            if (CollisionWithApple())
                ProcessCollisionWithApple();
            if (Snake.CollisionWithSelf() || CollisionWithWorldBounds())
            {
                mainWindow.GameOver();
                StopGame();
            }
        }
        private void CreateFood()
        {       
            if (Food != null)
                return;
            Food = new Food(ElementSize)
            {
                X = random.Next(0, ColumnCount) * ElementSize,
                Y = random.Next(0, RowCount) * ElementSize
            };
        }
        private bool CollisionWithWorldBounds()
        {
            if (Snake == null || Snake.TheHead == null)
                return false;
            var snakeHead = Snake.TheHead;
            return (snakeHead.X > GameAreaWidth - ElementSize ||
                snakeHead.Y > GameAreaHeight - ElementSize ||
                snakeHead.X < 0 || snakeHead.Y < 0);
        }

        private bool CollisionWithApple()
        {
            if (Food == null || Snake == null || Snake.TheHead == null)
                return false;
            SnakeParts head = Snake.TheHead;
            return head.X == Food.X && head.Y == Food.Y;
        }

        private void ProcessCollisionWithApple()
        {
            mainWindow.IncrementScore();
            mainWindow.World.Children.Remove(Food.Element);
            Food = null;
            Snake.Grow();
            IncreaseGameSpeed();
        }
        private Line GenerateVerticalWorldLine(int j)
        {
            return new Line
            {
                X1 = j * ElementSize,
                Y1 = 0,
                X2 = j * ElementSize,
                Y2 = ElementSize * RowCount
            };
        }

        private Line GenerateHorizontalWorldLine(int i)
        {
            return new Line
            {
                X1 = 0,
                Y1 = i * ElementSize,
                X2 = ElementSize * ColumnCount,
                Y2 = i * ElementSize
            };
        }

        

        public void StopGame()
        {
            _timer.Stop();
            _timer.Tick -= MainGameLoop;
            IsRunning = false;
        }

        private void IncreaseGameSpeed()
        {
            var part = _timer.Interval.Ticks / 10;
            _timer.Interval = TimeSpan.FromTicks(_timer.Interval.Ticks - part);
        }

        public void PauseGame()
        {
            _timer.Stop();
            IsRunning = false;
        }
        public void ContinueGame()
        {
            _timer.Start();
            IsRunning = true;
        }
        internal void UpdateMovementDirection(MovementDirection movementDirection)
        {
            if (Snake != null)
                Snake.UpdateMovementDirection(movementDirection);
        }
    }
}
    

