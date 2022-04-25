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

namespace SnakeGameCouseProject
{
    public class Food
    {
        public UIElement Element { get; internal set; }
        public int X { get; internal set; }
        public int Y { get; internal set; }

        
        public Food(int size)
        {
            ImageBrush imgBrush = new ImageBrush();
            imgBrush.ImageSource = new BitmapImage(new Uri(@"foods.jpg", UriKind.Relative));


            
            Ellipse ellipse = new Ellipse
            {
                Width = size - 4,
                Height = size - 4,
                Fill = imgBrush,
               
            };
     
        
           Element = ellipse;
        }

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
