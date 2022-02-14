package client;

import helpers.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import server.IServer;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static javafx.scene.paint.Color.TURQUOISE;


public class Client extends Application {
    private Stage stage;
    private int selectedRow;
    private int selectedColumn;
    private IServer sudokuServer;
    private GameParts initialBoard;
    private GameParts userBoard;
    private User user;
    private UndoAndRedo movesManager;
    private GameLevels level;
    private boolean isSolved;
    private Timeline timeline;
    private final TextArea timeArea = new TextArea();

    protected static final double WINDOW_X = 700;
    protected static final double WINDOW_Y = 700;
    private static final double Board_PADDING = 55;
    private static final double Board_X_Y = 570;

    private static final Color BACKGROUND = Color.rgb(224, 10, 136);
    private static final Color BACKGROUND_BOARD = Color.rgb(0, 150, 136);
    private static final String SUDOKU = "Sudoku Game";

    private final Group root = new Group();
    @FXML
    private final Button btnNewGame = new Button("New Game");
    @FXML
    private final Button btnShowSolution = new Button("Show");
    private final Button btnCheck = new Button("Check");
    @FXML
    private final Button btnExit = new Button("Exit");
    @FXML
    private final Button btnUndo = new Button("Exit");
    @FXML
    private final Button btnRedo = new Button("Exit");
    @FXML private Canvas canvas=new Canvas();

    GraphicsContext context = canvas.getGraphicsContext2D();

    @Override
    public void start(Stage primaryStage) throws IOException {
        TextInputDialog inputDialog=new TextInputDialog();
        inputDialog.setContentText("Enter your username: ");
        Optional<String> enteredUsername = inputDialog.showAndWait();

        if(enteredUsername.isPresent()) {
            user=new User(enteredUsername.get(), GameLevels.EASY, Result.LOOSE);
        }else{
            Platform.exit();
            System.exit(0);
        }
        this.stage = primaryStage;
        initialize();

        //Adding Buttons
        Start();
        SoledItOrNot();
        Solution();
        Exit();
        Undo();
        Redo();

        //Drawing the Graphics
        drawBackground(root);
        drawTitle(root);
        drawBoarder(root);
        drawTextFields(root);
        drawGridLines(root);
        getDifficultyOptions(sudokuServer);
        VBox lev=getDifficultyOptions(sudokuServer);
        root.getChildren().add(lev);
        requestSudoku(sudokuServer);
        drawValues(context);
        drawUserValues(context);

        startTime();
        checkIsSolved();
        stage.show();
    }

    public void initialize() throws IOException {
        connectToServer();
        user = new User("", GameLevels.EASY, Result.LOOSE);
        level = GameLevels.EASY;
        startTheGame();
    }

    public void connectToServer() throws IOException {
        String host = "localhost";
        try {
            LocateRegistry.createRegistry(2020);
            Registry registry = LocateRegistry.getRegistry(host, 1099);
            sudokuServer = (IServer) registry.lookup("sudoku");
            System.out.println("Found " + sudokuServer + " the server.");
        } catch (Exception e) {
            System.out.println("ERROR " + e);
            Platform.exit();
            System.exit(0);
        }
        Runtime.getRuntime().exec("rmiregistry 2020");
        System.setProperty("java.rmi.server.hostname","1.2.3.4");
    }

    public void Start() {
        btnNewGame.setMaxSize(300, 500);
        btnNewGame.setStyle("fx-background-color:  #0000ff");
        btnNewGame.setLayoutX(50);
        btnNewGame.setLayoutY(650);
        btnNewGame.setOnAction(value -> startTheGame());
        root.getChildren().add(btnNewGame);
    }

    private void drawBackground(Group root) {
        Rectangle boardBackground = new Rectangle();
        boardBackground.setX(Board_PADDING);
        boardBackground.setY(Board_PADDING);

        boardBackground.setWidth(Board_X_Y);
        boardBackground.setHeight(Board_X_Y);

        boardBackground.setFill(BACKGROUND_BOARD);

        root.getChildren().addAll(boardBackground);
    }

    private void drawTitle(Group root) {
        Text title = new Text(200, 50, SUDOKU);
        title.setFill(TURQUOISE);
        Font font = new Font(50);
        title.setFont(font);
        root.getChildren().add(title);

    }

    private void drawBoarder(Group root) {
        Scene scene = new Scene(root, WINDOW_X, WINDOW_Y);
        scene.setFill(BACKGROUND);
        stage.setScene(scene);

    }

    private void drawTextFields(Group root) {
        final int xOrigin = 43;
        final int yOrigin = 43;

        final int Delta = 63;

        for (int xIndex = 0; xIndex < 9; ++xIndex) {
            for (int yIndex = 0; yIndex < 9; ++yIndex) {
                int x = xOrigin + xIndex * Delta;
                int y = yOrigin + yIndex * Delta;

                GameTextField title = new GameTextField(xIndex, yIndex);

                styleTitle(title, x, y);

                root.getChildren().add(title);
            }
        }
    }

    private void styleTitle(GameTextField title, int x, int y) {
        Font numberFont = new Font(30);
        title.setFont(numberFont);
        title.setAlignment(Pos.CENTER);

        title.setLayoutX(x);
        title.setLayoutY(y);
        title.setPrefHeight(64);
        title.setPrefWidth(64);

        title.setBackground(Background.EMPTY);

    }

    private void drawGridLines(Group root) {
        int xAndY = 114;
        int index = 0;
        while (index < 8) {
            int thickness;
            if (index == 2 || index == 5) {
                thickness = 5;
            } else {
                thickness = 2;
            }
            Rectangle vertical = getline(xAndY + 64 * index, Board_PADDING, Board_X_Y, thickness);
            Rectangle horizontal = getline(Board_PADDING, xAndY + 64 * index, thickness, Board_X_Y);

            root.getChildren().addAll(vertical, horizontal);
            index++;
        }
    }

    private Rectangle getline(double x, double y, double height, double width) {
        Rectangle line = new Rectangle();
        line.setX(x);
        line.setY(y);
        line.setHeight(height);
        line.setWidth(width);

        line.setFill(Color.CYAN);
        return line;
    }

    private void startTheGame() {
        try {
            initialBoard = sudokuServer.startTheGame(level.getLevel());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        user.setLevel(level);
        userBoard = new GameParts(level.getLevel());
        userBoard.makeBoard();

        movesManager = new UndoAndRedo();
        isSolved = false;

        selectedRow = 0;
        selectedColumn = 0;

        drawValues(context);
        drawUserValues(context);

        startTime();
    }
    private void userAddIns(int value, int row, int col) {
        if (initialBoard.getCell(row, col).isBlank()) {
            if(value >=0 && value <= 9)
                userBoard.setNumber(row, col, value);
            else
                System.out.println("The passed value is not valid.");
        }
        else
            System.out.println("This cell should not be changed!");
    }
    private void drawUserValues(GraphicsContext context) {
        List<List<Integer>> initialCells = initialBoard.getUserBoard();
        for(int row = 0; row < 9; row++) {
            for(int col = 0; col < 9; col++) {
                int positionY = row * 50 + 32;
                int positionX = col * 50 + 20;

                context.setFill(Color.GREEN);
                context.setFont(new Font(24));

                if(initialCells.get(row).get(col)!=0)
                    context.fillText(initialCells.get(row).get(col).toString(), positionX, positionY);
            }
        }

    }

    private void drawValues(GraphicsContext context) {
        List<List<Integer>> userCells = userBoard.getUserBoard();
        for(int row = 0; row < 9; row++) {
            for(int col = 0; col < 9; col++) {
                int positionY = row * 50 + 30;
                int positionX = col * 50 + 20;

                context.setFill(Color.GREENYELLOW);
                context.setFont(new Font(20));
                if(userCells.get(row).get(col)!=0)
                    context.fillText(userCells.get(row).get(col).toString(), positionX, positionY);
            }
        }

    }

    private VBox getDifficultyOptions(IServer server) {
        Button easy = new Button("Easy");
        Button medium = new Button("Medium");
        Button hard = new Button("Hard");
        easy.setOnAction(actionEvent -> {
            level = GameLevels.EASY;
            requestSudoku(server);
        });
        medium.setOnAction(actionEvent -> {
            level = GameLevels.MEDIUM;
            requestSudoku(server);
        });
        hard.setOnAction(actionEvent -> {
            level = GameLevels.HARD;
            requestSudoku(server);
        });

        VBox difficultyOptions = new VBox();
        difficultyOptions.setSpacing(10);
        difficultyOptions.setPadding(new Insets(16, 0, 0, 0));
        difficultyOptions.setAlignment(Pos.CENTER);
        difficultyOptions.getChildren().add(easy);
        difficultyOptions.getChildren().add(medium);
        difficultyOptions.getChildren().add(hard);

        return difficultyOptions;
    }
    private void requestSudoku(IServer server) {
        try {
            userBoard = server.startTheGame(level.getLevel());
        } catch (RemoteException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("End of game");
            alert.setHeaderText("Something went wrong. Please, try again later.");
            alert.showAndWait();

            e.printStackTrace();
            return;
        }
        resetTimer();
    }
    public void Solution(){
        btnShowSolution.setMaxSize(300, 500);
        btnShowSolution.setStyle("fx-background-color:  #0000ff");
        btnShowSolution.setLayoutX(150);
        btnShowSolution.setLayoutY(650);
        btnShowSolution.setOnAction(value->{
            userBoard.print(userBoard.getSecretBoard());
        });
        root.getChildren().add(btnShowSolution);
    }
    public void SoledItOrNot(){
        btnCheck.setMaxSize(300, 500);
        btnCheck.setStyle("fx-background-color:  #0000ff");
        btnCheck.setLayoutX(200);
        btnCheck.setLayoutY(650);
        btnCheck.setOnAction(value->{
            checkIsSolved();
        });
        root.getChildren().add(btnCheck);
    }
    public void Exit(){
        btnExit.setMaxSize(300, 500);
        btnExit.setStyle("fx-background-color:  #0000ff");
        btnExit.setLayoutX(300);
        btnExit.setLayoutY(650);
        btnExit.setOnAction(value->{
            Platform.exit();
            System.exit(0);
        });
        root.getChildren().add(btnExit);
    }
    public void Undo(){
        btnUndo.setMaxSize(300, 500);
        btnUndo.setStyle("fx-background-color:  #0000ff");
        btnUndo.setLayoutX(400);
        btnUndo.setLayoutY(650);
        btnUndo.setOnAction(value->{
            Coordinates undoneCell = new Coordinates(movesManager.Undo());
            if(undoneCell.getPreviousValue()==0) {
                System.out.println("No move to undone");
            }
            else {
                userAddIns(undoneCell.getPreviousValue(), undoneCell.getX(), undoneCell.getY());
            }
        });
        root.getChildren().add(btnUndo);
    }
    public void Redo(){
        btnRedo.setMaxSize(300, 500);
        btnRedo.setStyle("fx-background-color:  #0000ff");
        btnRedo.setLayoutX(500);
        btnRedo.setLayoutY(650);
        btnRedo.setOnAction(value->{
            Coordinates redoneCell = movesManager.Redo();
            if(redoneCell == null) {
                System.out.println("No move to redone");
                return;
            }
            userAddIns(redoneCell.getValue(), redoneCell.getX(), redoneCell.getY());
        });
        root.getChildren().add(btnRedo);
    }


    private void resetTimer() {
        timeArea.clear();
        timeline.stop();
        startTime();
    }

    private void startTime() {
        timeArea.setWrapText(true);
        timeArea.setPrefRowCount(1);
        timeArea.setVisible(false);

        Date start = Calendar.getInstance().getTime();
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            long countUp = Calendar.getInstance().getTime().getTime() - start.getTime();
            timeArea.setText("Time Playing: " + TimeUnit.SECONDS.convert(countUp, TimeUnit.MILLISECONDS));
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    private GameParts boardsUnion() {
        GameParts unionBoard = new GameParts(level.getLevel());
        unionBoard.makeBoard();

        for(int row = 0; row < 9; row++) {
            for(int col = 0; col < 9; col++) {
                int value = Integer.parseInt(initialBoard.getCell(row, col));
                if(value!=0)
                    unionBoard.setNumber(row, col, value);
                else {
                    value = Integer.parseInt(userBoard.getCell(row, col));
                    unionBoard.setNumber(row, col, value);
                }
            }
        }

        return unionBoard;
    }
void checkIsSolved(){
    isSolved = sudokuServer.isSolution(boardsUnion());

    if(isSolved) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sudoku Solved");
        alert.setHeaderText("Congratulations!");
        alert.setContentText(String.format("You successfully solved the puzzle in %s!",timeArea));
        alert.showAndWait();
    }
    else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Sudoku Unsolved");
        alert.setHeaderText("The puzzle is not correct!");
        alert.setContentText("Try again or start a new game.");
        alert.showAndWait();
    }

}



    public static void main(String[] args) {
        launch(args);
    }


}
