package TheClient;

import Server.Build.Build;
import Server.Coordinates;
import Server.GameParts;
import Server.GameState;
import Server.Storage;
import Server.addIns.Level;
import Server.addIns.UserInformation;
import TheClient.Logic.ControllLogic;
import TheClient.MakeLogic.Generator;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.HashMap;

import static javafx.scene.paint.Color.TURQUOISE;


public class UserInterface1 implements IUserInterfaceContract.View, EventHandler<KeyEvent> {
    @FXML
    private final Button btnNewGame= new Button( "New Game");
    @FXML private final Button btnShowSolution=new Button("Show");
    @FXML private final Button btnExit=new Button("Exit");
    @FXML private final RadioButton optEasy=new RadioButton("Easy");
    @FXML private final RadioButton optMedium=new RadioButton("Medium");
    @FXML private final RadioButton optHard=new RadioButton("Hard");
    @FXML private final Button btnRedo=new Button("Redo");
    @FXML private final Button btnUndo=new Button("Undo");



    private final Stage stage;
    private final Group root;
    private final UserInformation user;
    private UndoAndRedo movesManager;
    private Level currentLevel;



    private final HashMap<Coordinates, GameTextField> textCoordinates;

    private IUserInterfaceContract.EventListen listener;

    protected static final double WINDOW_X= 700;
    protected static final double WINDOW_Y= 700;
    private static final double Board_PADDING= 55;
    private static final double Board_X_Y= 570;

    private static final Color BACKGROUND=Color.rgb(224, 10, 136);
    private static final Color BACKGROUND_BOARD=Color.rgb(0, 150, 136);
    private static final String SUDOKU="Sudoku Game";

    public UserInterface1(Stage stage) {
        this.stage = stage;
        this.root=new Group();
        this.textCoordinates = new HashMap<>();
        user = new UserInformation(SudokuUser.getUsername());
        initializeInterface();

        currentLevel = Level.EASY;
    }

    private void initializeInterface() {
        drawBackground(root);
        drawTitle(root);
        drawBoarder(root);
        drawTextFields(root);
        drawGridLines(root);

        Start();
        Solution();
        Exit();
        Undo();
        Redo();
        stage.show();

    }


    private void drawBackground(Group root) {
        Rectangle boardBackground= new Rectangle();
        boardBackground.setX(Board_PADDING);
        boardBackground.setY(Board_PADDING);

        boardBackground.setWidth(Board_X_Y);
        boardBackground.setHeight(Board_X_Y);

        boardBackground.setFill(BACKGROUND_BOARD);

        root.getChildren().addAll(boardBackground);
    }
    private void drawTitle(Group root) {
        Text title= new Text(200, 50, SUDOKU);
        title.setFill(TURQUOISE);
        Font font= new Font(50);
        title.setFont(font);
        root.getChildren().add(title);

    }

    private void drawBoarder(Group root) {
        Scene scene= new Scene(root, WINDOW_X, WINDOW_Y);
        scene.setFill(BACKGROUND);
        stage.setScene(scene);

    }
    private void drawTextFields(Group root) {
        final int xOrigin=43;
        final int yOrigin=43;

        final int Delta=63;

        for(int xIndex=0; xIndex<9; ++xIndex){
            for(int yIndex=0; yIndex<9; ++yIndex){
              int x=xOrigin+xIndex*Delta;
              int y=yOrigin+yIndex*Delta;

              GameTextField title= new GameTextField(xIndex, yIndex);

              styleTitle(title, x, y);

              title.setOnKeyPressed(this);

              textCoordinates.put(new Coordinates(xIndex, yIndex), title);

              root.getChildren().add(title);
            }
        }
    }

    private void styleTitle(GameTextField title, int x, int y) {
        Font numberFont= new Font(30);
        title.setFont(numberFont);
        title.setAlignment(Pos.CENTER);

        title.setLayoutX(x);
        title.setLayoutY(y);
        title.setPrefHeight(64);
        title.setPrefWidth(64);

        title.setBackground(Background.EMPTY);

    }
    private void drawGridLines(Group root) {
        int xAndY=114;
        int index=0;
        while(index<8) {
            int thickness;
            if (index == 2 || index==5) {
                thickness = 5;
            } else {
                thickness = 2;
            }
            Rectangle vertical = getline(xAndY + 64 * index, Board_PADDING, Board_X_Y, thickness);
            Rectangle horizontal = getline(Board_PADDING,xAndY + 64 * index,   thickness, Board_X_Y);

            root.getChildren().addAll(vertical, horizontal);
            index++;
        }
    }
    private Rectangle getline(double x, double y, double height, double width){
        Rectangle line =new Rectangle();
        line.setX(x);
        line.setY(y);
        line.setHeight(height);
        line.setWidth(width);

        line.setFill(Color.CYAN);
        return line;
    }
    @Override
    public void setListener(IUserInterfaceContract.EventListen listener) {
      this.listener=listener;
    }

    @Override
    public void updateSquare(int x, int y, int input) {
        GameTextField title= textCoordinates.get(new Coordinates(x, y));

        String value= Integer.toString(input);

        if(value.equals("0")) value="";
        title.textProperty().setValue(value);

    }

    @Override
    public void updateBoard(GameParts gameParts) {
        for(int xIndex=0; xIndex<9; ++xIndex){
            for(int yIndex=0; yIndex<9; ++yIndex){
                 TextField title=textCoordinates.get(new Coordinates(xIndex, yIndex));
                 String value= Integer.toString(gameParts.getGrid()[xIndex][yIndex]);

                 if(value.equals("0")) value="";
                 title.setText(value);

                 if(gameParts.getState()== GameState.NEW){
                     if(value.equals("")){
                         title.setStyle("-fx-opacity: 1;");
                         title.setDisable(false);
                     } else{
                         title.setStyle("-fx-opacity: 0.8;");
                         title.setDisable(true);
                     }
                 }
            }
        }
    }

    @Override
    public void ShowDialog(String message) {
        Alert dialog=new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK);
        dialog.showAndWait();

        if(dialog.getResult()==ButtonType.OK) listener.onDialogClick();

    }

    @Override
    public void ShowError(String message) {
        Alert dialog=new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        dialog.showAndWait();

    }

    @Override
    public void handle(KeyEvent keyEvent) {
        if(keyEvent.getEventType()==KeyEvent.KEY_PRESSED){
            if(keyEvent.getText().matches("[0-9]")){
                int value= Integer.parseInt(keyEvent.getText());
                handleInput(value, keyEvent.getSource());
            } else if(keyEvent.getCode()== KeyCode.BACK_SPACE){
                handleInput(0, keyEvent.getSource());
            } else{
                ((TextField) keyEvent.getSource()).setText("");
            }
        }
              keyEvent.consume();
    }

    private void handleInput(int value, Object source) {
        listener.onSudoku(((GameTextField)source).getX(), ((GameTextField)source).getY(), value);
    }

    public UserInformation getUser() {
        return user;
    }

    public void startGame() throws IOException {
        IUserInterfaceContract.View uiImpl = new UserInterface1(stage);
        try {
            Build.buildUser(uiImpl);

        } catch (IOException e){
            e.printStackTrace();
            throw e;

        }

    }
    public void Start(){
        btnNewGame.setMaxSize(300, 500);
        btnNewGame.setStyle("fx-background-color:  #0000ff");
        btnNewGame.setLayoutX(50);
        btnNewGame.setLayoutY(650);
        btnNewGame.setOnAction(value-> {
            try {
                startGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        root.getChildren().add(btnNewGame);
    }
    public void Solution(){
        btnShowSolution.setMaxSize(300, 500);
        btnShowSolution.setStyle("fx-background-color:  #0000ff");
        btnShowSolution.setLayoutX(150);
        btnShowSolution.setLayoutY(650);
        btnShowSolution.setOnAction(value->{
            Generator.getSolved();
        });
        root.getChildren().add(btnShowSolution);
    }

    public void Exit(){
        btnExit.setMaxSize(300, 500);
        btnExit.setStyle("fx-background-color:  #0000ff");
        btnExit.setLayoutX(200);
        btnExit.setLayoutY(650);
        btnExit.setOnAction(value->{
            ControllLogic.quitApp();
        });
        root.getChildren().add(btnExit);
    }

    public void Undo(){
        btnUndo.setMaxSize(300, 500);
        btnUndo.setStyle("fx-background-color:  #0000ff");
        btnUndo.setLayoutX(300);
        btnUndo.setLayoutY(650);
        btnUndo.setOnAction(value->{movesManager.Undo();});
        root.getChildren().add(btnUndo);
    }
    public void Redo(){
        btnRedo.setMaxSize(300, 500);
        btnRedo.setStyle("fx-background-color:  #0000ff");
        btnRedo.setLayoutX(350);
        btnRedo.setLayoutY(650);
        btnRedo.setOnAction(value->{movesManager.Redo();});
        root.getChildren().add(btnRedo);
    }
}
