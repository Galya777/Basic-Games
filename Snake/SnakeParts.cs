using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;


namespace SnakeGameCouseProject
{
    public class SnakeParts
    {
        public SnakeParts(int size, bool head)
        {
            ImageBrush imgBrush = new ImageBrush();
            if (head==true)
            {
                imgBrush.ImageSource = new BitmapImage(new Uri(@"head.jpg", UriKind.Relative));
                head = false;
            }
            else
            {
                imgBrush.ImageSource = new BitmapImage(new Uri(@"snake.jpg", UriKind.Relative));
            }
            Rectangle snake = new Rectangle();
            snake.Width = size-4;
            snake.Height = size-4;
            snake.Fill = imgBrush;
   
            Element = snake;
            
        }
        public UIElement Element { get; set; }

        public bool Head { get; set; }

        public int X { get; set; }

        public int Y { get; set; }

        public override bool Equals(object obj)
        {
            if (obj is Food food)
                return X == food.X && Y == food.Y;
            else
                return false;
        }

        public override int GetHashCode() => base.GetHashCode();
        public override string ToString() => base.ToString();

    }
}
