using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace SnakeGameCouseProject
{
    public class TheSnake
    {
        private readonly int size;

        public TheSnake(int elementSize)
        {
            Elements = new List<SnakeParts>();
            size = elementSize;
        }

        public SnakeParts TailBackup { get; set; }
        public List<SnakeParts> Elements { get; set; }
        public MovementDirection Movement { get; set; }
        public SnakeParts TheHead => Elements.Any() ? Elements[0] : null;

        internal void UpdateMovementDirection(MovementDirection up)
        {
            switch (up)
            {
                case MovementDirection.Up:
                    if (Movement != MovementDirection.Down)
                        Movement = MovementDirection.Up;
                    break;
                case MovementDirection.Left:
                    if (Movement != MovementDirection.Right)
                        Movement = MovementDirection.Left;
                    break;
                case MovementDirection.Down:
                    if (Movement != MovementDirection.Up)
                        Movement = MovementDirection.Down;
                    break;
                case MovementDirection.Right:
                    if (Movement != MovementDirection.Left)
                        Movement = MovementDirection.Right;
                    break;
            }
        }

        internal void Grow()
        {

            Elements.Add(new SnakeParts(size, false) { X = TailBackup.X, Y = TailBackup.Y});
            
        }

        public bool CollisionWithSelf()
        {
            SnakeParts snakeHead = TheHead;
            if (snakeHead != null)
            {
                foreach (var snakeElement in Elements)
                {
                    if (!snakeElement.Head)
                    {
                        if (snakeElement.X == snakeHead.X && snakeElement.Y == snakeHead.Y)
                        {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        internal void PositionFirstElement(int cols, int rows, MovementDirection initialDirection)
        {
            Elements.Add(new SnakeParts(size, true)
            {
                X = (cols / 2) * size,
                Y = (rows / 2) * size,
                Head = true
            }) ;
            Movement = initialDirection;
        }

        internal void MoveSnake()
        {
            SnakeParts head = Elements[0];
            SnakeParts tail = Elements[Elements.Count - 1];

            TailBackup = new SnakeParts(size, true)
            {
                X = tail.X,
                Y = tail.Y
            };

            head.Head = false;
            tail.Head = true;
            tail.X = head.X;
            tail.Y = head.Y;
            switch (Movement)
            {
                case MovementDirection.Right:
                    tail.X += size;
                    break;
                case MovementDirection.Left:
                    tail.X -= size;
                    break;
                case MovementDirection.Up:
                    tail.Y -= size;
                    break;
                case MovementDirection.Down:
                    tail.Y += size;
                    break;
                default:
                    break;
            }
            Elements.RemoveAt(Elements.Count - 1);
            Elements.Insert(0, tail);
        }
    }

    public enum MovementDirection
    {
        Right,
        Left,
        Up,
        Down
    }
}
  
