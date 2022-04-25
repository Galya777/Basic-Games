Write a RMI JFX client application that plays the popular Japanese game SUDOKU. Sudoku puzzles are 9 x 9 grids, and each square in the grid consists of a 3 x 3 subgrid called a minigrid. Your goal is to fill in the squares so that each column, row, and minigrid contains the numbers 1 through 9 exactly once.
Implement the Sudoku game using JavaFX components (GridPane, Menu etc) with the following features:
a) The client (JavaFX application)
• On connecting the RMI server each client loads a Sudoku puzzle from the server and the game starts • Create and reusable user defined visual component of JavaFX packaged in a JAR file • Ensures that only valid numbers are allowed to be placed in the currently selected cell • Checks whether a Sudoku puzzle has been solved • Keeps track of the time needed to solve a Sudoku puzzle • Undo and redo previous moves • Can request and start another game from the server
b) The RMI server (JavaFX application)
• Generates Sudoku puzzles games with 3 levels of difficulty (consult the text sources for this project), where each puzzle should be different than the rest
• Serves each client by generating and passing to the client a Sudoku puzzle of the selected level of difficulty
• Presents a solution to the given puzzle at the end of the game, if it is terminated without success by the players.
• Records statistics in a file about the client username, the level of difficulty and the outcome of the game (total time played and game solved/ unsolved result).

To do:
1. Improve
