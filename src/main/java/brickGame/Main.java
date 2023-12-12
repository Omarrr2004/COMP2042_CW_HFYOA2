package brickGame;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import javafx.scene.media.MediaPlayer;

/**
 * The Main class serves as the entry point for the brick game application.
 * It extends the JavaFX Application class and implements various handlers and interfaces for game functionality.
 */
public class Main extends Application implements EventHandler<KeyEvent>, GameEngine.OnAction {

    public int level = 1;
    public boolean isGameStarted = false;
    public MediaPlayer mediaPlayer;
    public Button audioButton;
    public double xBreak = 0.0f;
    public double centerBreakX;
    public double yBreak = 680.0f;
    public int breakWidth     = 160;
    public int breakHeight    = 13;
    public int sceneWidth = 500;
    public int sceneHeigt = 700;
    public static int LEFT  = 1;
    public static int RIGHT = 2;
    public Circle ball;
    public double xBall;
    public double yBall;
    public boolean isGoldStatus = false;
    public boolean isExistHeartBlock = false;
    public Rectangle rect;
    public int  ballRadius = 13;
    public int destroyedBlockCount = 0;
    public int  heart    = 3;
    public int  score    = 0;
    public long time     = 0;
    public long hitTime  = 0;
    public long goldTime = 0;
    public GameEngine engine;
    public long lastWidthChangeTime = 0;
    public PaddleState paddleState = PaddleState.NORMAL;
    public ArrayList<Block> blocks = new ArrayList<Block>();
    public ArrayList<Bonus> chocos = new ArrayList<Bonus>();
    public  Pane             root;
    public Label            scoreLabel;
    public Label            heartLabel;
    public Label            levelLabel;
    public ImageView audioOnIcon = new ImageView(new Image("audioOn.png"));
    public ImageView audioOffIcon = new ImageView(new Image("audioOff.png"));
    public boolean isPaused;
    public boolean goDownBall                  = true;
    public boolean goRightBall                 = true;
    public boolean collideToBreak = false;
    public boolean collideToBreakAndMoveToRight = true;
    public boolean collideToRightWall = false;
    public boolean collideToLeftWall = false;
    public boolean collideToRightBlock = false;
    public boolean collideToBottomBlock = false;
    public boolean collideToLeftBlock = false;
    public boolean collideToTopBlock = false;
    public boolean isLevelCompleted = false;
    public double vX = 2.000;
    public double vY = 2.000;

    Stage  primaryStage;

    /**
     * Starts the JavaFX application, setting up the initial game scene.
     *
     * @param primaryStage The primary stage for this JavaFX application.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        GameVisuals.GameScenery(primaryStage,this);
    }
    /**
     * Initiates a new game.
     */
    public void startNewGame() {
        StartGame.newGame(this);
    }
    /**
     * Sets up the game scene, including game elements and UI components.
     */
    public void setupGameScene() {
        StartGame.setUpGame(this);
    }
    public void toggleAudio() {
        GameAudio.audioFunctionality(this);
    }
    @Override
    public void stop() {
        GameAudio.StopAudio(this);
    }
    public void initBall() {
        Initialize.initializeBall(this);
    }
    public void initBreak() {
        Initialize.initializeBreak(this,rect);
    }
    public void initBoard() {
        Initialize.initializeBoard(this);
    }
    /**
     * Main entry point of the application.
     *
     * @param args Command line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch(args);
    }
    /**
     * Handles key events during the game, such as player inputs.
     *
     * @param event The KeyEvent triggered by the player's actions.
     */
    @Override
    public void handle(KeyEvent event) {
        GameHandling.keyHandling(event, this);
    }
    public void pauseGame(){
        GameHandling.Pause(this);
    }
    public void move(final int direction) {
        GameHandling.movementOfPaddle(direction, this);
    }
    public void resetCollideFlags() {

        collideToBreak = false;
        collideToBreakAndMoveToRight = false;
        collideToRightWall = false;
        collideToLeftWall = false;

        collideToRightBlock = false;
        collideToBottomBlock = false;
        collideToLeftBlock = false;
        collideToTopBlock = false;
    }
    public void resetBallAndBreakPosition() {
        BallPhysics.positionReset(this);
    }
    public void setPhysicsToBall() {
        BallPhysics.physicsSetToBall(this);
    }
    public void showWin(final Main main) {
        GameWon.showWinMessage(this);
    }
    public void checkDestroyedCount() {
        GameWon.finalLevelCount(this);
    }
    public void nextLevel() {
        LevelManagement.theNextLevel(this);
    }
    public void restartGame() {
        LevelManagement.gameRestart(this);
    }
    public void updateGameScene() {
        LevelManagement.restartGameScene(this);
    }
    public enum PaddleState {
        NORMAL, INCREASED, DECREASED
    }
    public void changePaddleWidth(boolean increase) {
        Paddle.paddleChange(increase,this, rect);
    }
    public void resetPaddleWidthAfterDelay() {
        Paddle.resetPaddleWidth(this);
    }
    public void resetPaddleWidthToDefault() {
        Paddle.resetPaddleToDefault(this, rect);
    }
    @Override
    public void onUpdate() {
        OnUpdates.gameUpdate(this);
    }
    @Override
    public void onInit() {
    }
    @Override
    public void onPhysicsUpdate() {
        OnUpdates.physicsProcess(this);
    }
    @Override
    public void onTime(long time) {
        this.time = time;
    }
}