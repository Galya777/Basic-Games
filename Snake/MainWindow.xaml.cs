using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Windows.Threading;

namespace SnakeGameCouseProject
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }
        private World _gameWorld;
        int food, score, level;

        protected override void OnContentRendered(EventArgs e)
        {
            _gameWorld = new World(this);
            InitializeScore();
            base.OnContentRendered(e);
        }

        private void InitializeScore()
        {
            food = 0;
            score = level = 1;
        }
        private void Window_KeyUp(object sender, System.Windows.Input.KeyEventArgs e)
        {
            if (_gameWorld != null)
                switch (e.Key)
                {
                    case Key.Up:
                        _gameWorld.UpdateMovementDirection(MovementDirection.Up);
                        break;
                    case Key.Left:
                        _gameWorld.UpdateMovementDirection(MovementDirection.Left);
                        break;
                    case Key.Down:
                        _gameWorld.UpdateMovementDirection(MovementDirection.Down);
                        break;
                    case Key.Right:
                        _gameWorld.UpdateMovementDirection(MovementDirection.Right);
                        break;
                   
                }
        }
        internal void GameOver()
        {
            _gameWorld.StopGame();
            MessageBox.Show($"Up to  level {level}. Score {score}.", "Game Over!");
        }
        private void StartClick(object sender, RoutedEventArgs e)
        {
            if (!_gameWorld.IsRunning)
            {
                _gameWorld.InitializeGame((int)DifficultySlider.Value, (int)ElementSizeSlider.Value);
                StartBtn.IsEnabled = false;
            }
        }

        private void RestartClick(object sender, RoutedEventArgs e)
        {
            _gameWorld.StopGame();
            _gameWorld = new World(this);
            World.Children.Clear();
            if (!_gameWorld.IsRunning)
            {
                _gameWorld.InitializeGame((int)DifficultySlider.Value, (int)ElementSizeSlider.Value);
                StartBtn.IsEnabled = false;
            }
        }

        private void HelpClick(object sender, RoutedEventArgs e)
        {
            MessageBox.Show($"In the game of Snake, the player uses the arrow keys to move a snake around the board. As the snake finds food, it eats the food, and thereby grows larger. " +
                "The game ends when the snake either moves off the screen or moves into itself. The goal is to make the snake as large as possible before that happens.");
        }
        //To Do
        private void Window_QueryCursor(object sender, QueryCursorEventArgs e)
        {

        }

        internal void IncrementScore()
        {
            food += 1;
            if (food % 3 == 0)
                level += 1;
            score += (int)DifficultySlider.Value * level;
            UpdateScore();
        }

        internal void UpdateScore()
        {
            ApplesLbl.Content = $"Food: {food}";
            ScoreLbl.Content = $"Score: {score}";
            LevelLbl.Content = $"Level: {level }";
        }
    }
}
