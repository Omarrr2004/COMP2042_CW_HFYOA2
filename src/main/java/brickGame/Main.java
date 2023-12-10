package brickGame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import java.util.Arrays;
import javafx.scene.image.ImageView;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Main extends Application implements EventHandler<KeyEvent>, GameEngine.OnAction {

    private int level = 1;
    private double xBreak = 0.0f;
    private double centerBreakX;
    private double yBreak = 680.0f;
    private int breakWidth     = 160;
    private int breakHeight    = 13;
    private int halfBreakWidth = breakWidth / 2;
    private int sceneWidth = 500;
    private int sceneHeigt = 700;
    private static int LEFT  = 1;
    private static int RIGHT = 2;
    private Circle ball;
    private double xBall;
    private double yBall;
    private boolean isGoldStatus = false;
    private boolean isExistHeartBlock = false;
    private Rectangle rect;
    private int  ballRadius = 13;
    private int destroyedBlockCount = 0;
    private int  heart    = 3;
    private int  score    = 0;
    private long time     = 0;
    private long hitTime  = 0;
    private long goldTime = 0;

    private GameEngine engine;
    private long lastWidthChangeTime = 0;

    private PaddleState paddleState = PaddleState.NORMAL;
    public static String savePath    = "D:/save/save.mdds";
    public static String savePathDir = "D:/save/";

    private ArrayList<Block> blocks = new ArrayList<Block>();
    private ArrayList<Bonus> chocos = new ArrayList<Bonus>();

    public  Pane             root;
    private Label            scoreLabel;
    private Label            heartLabel;
    private Label            levelLabel;

    private boolean loadFromSave = false;

    Stage  primaryStage;
    Button load    = null;
    Button newGame = null;


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        // Menu Pane
        Pane menuPane = new Pane();

        ImageView newGameIcon = new ImageView(new Image("startnewgame.jpg"));
        newGameIcon.setFitHeight(40); // Set the height of the icon
        newGameIcon.setFitWidth(160);  // Set the width of the icon

        Button startNewGameButton = new Button();
        startNewGameButton.setGraphic(newGameIcon); // Set the icon as graphic
        startNewGameButton.setStyle("-fx-background-color: transparent;"); // Make button background transparent

        // Create the load game button with custom icon
        ImageView loadGameIcon = new ImageView(new Image("loadgame.png"));
        loadGameIcon.setFitHeight(40); // Set the height of the icon
        loadGameIcon.setFitWidth(160);  // Set the width of the icon

        Button loadGameButton = new Button();
        loadGameButton.setGraphic(loadGameIcon); // Set the icon as graphic
        loadGameButton.setStyle("-fx-background-color: transparent;"); // Make button background transparent

        Image backgroundImage = new Image("bg.jpeg");
        BackgroundImage background = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));

        // Set the background on the pane
        menuPane.setBackground(new Background(background));


        double startX = 25;
        double startY =275;

        // Position the buttons
        startNewGameButton.setLayoutX(startX);
        startNewGameButton.setLayoutY(startY);
        loadGameButton.setLayoutX(startX);
        loadGameButton.setLayoutY(startY + 50);

        // Add buttons to menu
        menuPane.getChildren().addAll(startNewGameButton, loadGameButton);

        // Create menu scene
        Scene menuScene = new Scene(menuPane, sceneWidth, sceneHeigt);

        // Actions for buttons
        startNewGameButton.setOnAction(e -> startNewGame());
        loadGameButton.setOnAction(e -> loadGame());

        // Show menu
        primaryStage.setScene(menuScene);
        primaryStage.setTitle("Game Menu");
        primaryStage.show();
    }

    private void startNewGame() {
        // Resetting game state for a new game
        level = 1;
        score = 0;
        heart = 3;
        destroyedBlockCount = 0;
        isGoldStatus = false;
        isExistHeartBlock = false;
        loadFromSave = false;

        // Initialize game components
        initBall();
        initBreak();
        initBoard();

        // Set up the game scene
        setupGameScene();
    }

    private void loadGame() {
        LoadSave loadSave = new LoadSave();
        loadSave.read();

        // Set game state based on loaded data
        isExistHeartBlock = loadSave.isExistHeartBlock;
        isGoldStatus = loadSave.isGoldStauts;
        goDownBall = loadSave.goDownBall;
        goRightBall = loadSave.goRightBall;
        colideToBreak = loadSave.colideToBreak;
        colideToBreakAndMoveToRight = loadSave.colideToBreakAndMoveToRight;
        colideToRightWall = loadSave.colideToRightWall;
        colideToLeftWall = loadSave.colideToLeftWall;
        colideToRightBlock = loadSave.colideToRightBlock;
        colideToBottomBlock = loadSave.colideToBottomBlock;
        colideToLeftBlock = loadSave.colideToLeftBlock;
        colideToTopBlock = loadSave.colideToTopBlock;
        level = loadSave.level;
        score = loadSave.score;
        heart = loadSave.heart;
        destroyedBlockCount = loadSave.destroyedBlockCount;
        xBall = loadSave.xBall;
        yBall = loadSave.yBall;
        xBreak = loadSave.xBreak;
        yBreak = loadSave.yBreak;
        centerBreakX = loadSave.centerBreakX;
        time = loadSave.time;
        goldTime = loadSave.goldTime;
        vX = loadSave.vX;

        // Clear and reload blocks
        blocks.clear();
        chocos.clear();
        for (BlockSerializable ser : loadSave.blocks) {
            blocks.add(new Block(ser.row, ser.column, ser.type));
        }

        // Set up the game scene with loaded data
        setupGameScene();
    }


    private void setupGameScene() {
        root = new Pane();
        scoreLabel = new Label("Score: " + score);
        heartLabel = new Label("Heart: " + heart);
        levelLabel = new Label("Level: " + level);

        // Position labels
        heartLabel.setTranslateX(sceneWidth - 70);
        levelLabel.setTranslateY(20);

        root.getChildren().addAll(rect, ball, scoreLabel, heartLabel, levelLabel);
        for (Block block : blocks) {
            root.getChildren().add(block.rect);
        }

        Scene scene = new Scene(root, sceneWidth, sceneHeigt);
        scene.getStylesheets().add("style.css");
        scene.setOnKeyPressed(this);

        // Configure and show the primary stage
        primaryStage.setTitle("Brick Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Start the game engine
        engine = new GameEngine();
        engine.setOnAction(this);
        engine.setFps(120);
        engine.start();
    }



    private void initBoard() {
        Random random = new Random();

        ArrayList<Integer> blockTypes = new ArrayList<>(Arrays.asList(
                Block.BLOCK_BLOCK3, Block.BLOCK_BLOCK4, Block.BLOCK_BLOCK5, Block.BLOCK_BLOCK6,
                Block.BLOCK_BLOCK7, Block.BLOCK_BLOCK8, Block.BLOCK_BLOCK9, Block.BLOCK_BLOCK10,
                Block.BLOCK_BLOCK11, Block.BLOCK_BLOCK12, Block.BLOCK_BLOCK13, Block.BLOCK_BLOCK14,
                Block.BLOCK_BLOCK15, Block.BLOCK_BLOCK16, Block.BLOCK_CHOCO, Block.BLOCK_UNBREAKABLE
        ));

        final int MAX_ROWS = 11;
        final int BLOCKS_PER_ROW = 4;

        // Calculate the total number of blocks based on the level, with a maximum of MAX_ROWS rows.
        int rows = Math.min(level + 1, MAX_ROWS);
        int totalBlocks = BLOCKS_PER_ROW * rows;

        // Creating a list to keep track of all positions
        ArrayList<Integer> positions = new ArrayList<>();
        for (int i = 0; i < totalBlocks; i++) {
            positions.add(i);
        }

        // Define the number of star blocks based on the level
        int numberOfStarBlocks = level <= 6 ? 1 : 2;

        // Store the positions for star blocks
        ArrayList<Integer> starPositions = new ArrayList<>();
        for (int i = 0; i < numberOfStarBlocks; i++) {
            starPositions.add(positions.remove(random.nextInt(positions.size())));
        }

        // Randomly select a position for the heart block if level is greater than 5
        Integer heartPos = level > 5 ? positions.remove(random.nextInt(positions.size())) : null;

        // Block 1 and Block 2 positions
        Integer block1Pos = level >= 9 ? positions.remove(random.nextInt(positions.size())) : null;
        Integer block2Pos = level >= 4 ? positions.remove(random.nextInt(positions.size())) : null;

        for (int i = 0; i < totalBlocks; i++) {
            int type;
            if (starPositions.contains(i)) {
                type = Block.BLOCK_STAR;
            } else if (heartPos != null && i == heartPos) {
                type = Block.BLOCK_HEART;
            } else if (block1Pos != null && i == block1Pos) {
                type = Block.BLOCK_BLOCK1; // Block 1 for level 9 and above
            } else if (block2Pos != null && i == block2Pos) {
                type = Block.BLOCK_BLOCK2; // Block 2 for level 4 and above
            } else {
                int typeIndex = random.nextInt(blockTypes.size());
                type = blockTypes.get(typeIndex);
            }

            int row = i / BLOCKS_PER_ROW;
            int column = i % BLOCKS_PER_ROW;
            blocks.add(new Block(row, column, type));
        }
    }






    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                move(LEFT);
                break;
            case RIGHT:

                move(RIGHT);
                break;
            case DOWN:
                //setPhysicsToBall();
                break;
            case P:
                pauseGame();
                break;
            case S:
                saveGame();
                break;
        }
    }

    private boolean isPaused;

    private void pauseGame(){
        isPaused = !isPaused;
        if(isPaused){
            new Score().showMessage("Paused",this);
            engine.stop();
        } else {
            engine.start();
        }
    }

    private void move(final int direction) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int sleepTime = 4;
                for (int i = 0; i < 30; i++) {
                    if (direction == RIGHT) {
                        if (xBreak + rect.getWidth() < sceneWidth) { // Use rect.getWidth()
                            xBreak++;
                        }
                    } else if (direction == LEFT) {
                        if (xBreak > 0) {
                            xBreak--;
                        }
                    }
                    centerBreakX = xBreak + rect.getWidth() / 2; // Use rect.getWidth()
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (i >= 20) {
                        sleepTime = i;
                    }
                }
            }
        }).start();
    }




    private void initBall() {
        Random random = new Random();
        xBall = sceneWidth / 2.0;  // Start in the middle of the scene

        // Calculate the last row for the current level
        int lastRow = (level - 1) * 1 + 4;  // Increase the multiplier for each level

        // Find the bottom position of the last block
        double lastBlockBottom = lastRow * Block.getHeight() + Block.getPaddingTop() + Block.getHeight();

        // Set yBall a little below the last block
        yBall = lastBlockBottom + 15;

        ball = new Circle();
        ball.setRadius(ballRadius);
        ball.setFill(new ImagePattern(new Image("ball.png")));

        vX = 0.0; // No horizontal movement at the start
        vY = 2.0;
        goDownBall = true;
        goRightBall = false;
    }




    private void initBreak() {
        rect = new Rectangle();
        rect.setWidth(breakWidth);
        rect.setHeight(breakHeight);
        rect.setArcWidth(10);
        rect.setArcHeight(10);

        // Center the playing block at the beginning of each level
        xBreak = (sceneWidth - breakWidth) / 2.0;

        rect.setX(xBreak);
        rect.setY(yBreak);

        ImagePattern pattern = new ImagePattern(new Image("block.jpg"));
        rect.setFill(pattern);
    }



    private boolean goDownBall                  = true;
    private boolean goRightBall                 = true;
    private boolean colideToBreak               = false;
    private boolean colideToBreakAndMoveToRight = true;
    private boolean colideToRightWall           = false;
    private boolean colideToLeftWall            = false;
    private boolean colideToRightBlock          = false;
    private boolean colideToBottomBlock         = false;
    private boolean colideToLeftBlock           = false;
    private boolean colideToTopBlock            = false;

    private double vX = 2.000;
    private double vY = 2.000;


    private void resetColideFlags() {

        colideToBreak = false;
        colideToBreakAndMoveToRight = false;
        colideToRightWall = false;
        colideToLeftWall = false;

        colideToRightBlock = false;
        colideToBottomBlock = false;
        colideToLeftBlock = false;
        colideToTopBlock = false;
    }

    private void resetBallAndBreakPosition() {
        // Reset ball position
        xBall = sceneWidth / 2.0;
        yBall = yBreak - ballRadius - 10; // Place the ball just above the break

        // Reset break position
        xBreak = (sceneWidth - breakWidth) / 2.0;

        // Update the position of the break and ball on the screen
        Platform.runLater(() -> {
            rect.setX(xBreak);
            ball.setCenterX(xBall);
            ball.setCenterY(yBall);
        });
    }


    private void setPhysicsToBall() {
        //v = ((time - hitTime) / 1000.000) + 1.000;

        if (goDownBall) {
            yBall += vY;
        } else {
            yBall -= vY;
        }

        if (goRightBall) {
            xBall += vX;
        } else {
            xBall -= vX;
        }

        if (yBall <= 0) {
            //vX = 1.000;
            resetColideFlags();
            goDownBall = true;
            return;
        }
        if (yBall >= sceneHeigt) {
            goDownBall = false;
            if (!isGoldStatus) {
                heart--; // Decrement heart count
                new Score().show(sceneWidth / 2, sceneHeigt / 2, -1, this);
                if (heart == 0) {
                    new Score().showGameOver(this); // Show game over only if hearts are zero
                    engine.stop();
                } else {
                    resetBallAndBreakPosition(); // Reset ball and break positions if hearts are still available
                }
            }
        }


        if (yBall >= yBreak - ballRadius) {
            //System.out.println("Colide1");
            if (xBall >= xBreak && xBall <= xBreak + rect.getWidth()) {
                hitTime = time;
                resetColideFlags();
                colideToBreak = true;
                goDownBall = false;

                double relation = (xBall - centerBreakX) / (breakWidth / 2);

                if (Math.abs(relation) <= 0.3) {
                    //vX = 0;
                    vX = Math.abs(relation);
                } else if (Math.abs(relation) > 0.3 && Math.abs(relation) <= 0.7) {
                    vX = (Math.abs(relation) * 1.5) + (level / 3.500);
                    //System.out.println("vX " + vX);
                } else {
                    vX = (Math.abs(relation) * 2) + (level / 3.500);
                    //System.out.println("vX " + vX);
                }

                if (xBall - centerBreakX > 0) {
                    colideToBreakAndMoveToRight = true;
                } else {
                    colideToBreakAndMoveToRight = false;
                }
                //System.out.println("Colide2");
            }
        }

        if (xBall >= sceneWidth) {
            resetColideFlags();
            //vX = 1.000;
            colideToRightWall = true;
        }

        if (xBall <= 0) {
            resetColideFlags();
            //vX = 1.000;
            colideToLeftWall = true;
        }

        if (colideToBreak) {
            if (colideToBreakAndMoveToRight) {
                goRightBall = true;
            } else {
                goRightBall = false;
            }
        }

        //Wall Colide

        if (colideToRightWall) {
            goRightBall = false;
        }

        if (colideToLeftWall) {
            goRightBall = true;
        }

        //Block Colide

        if (colideToRightBlock) {
            goRightBall = true;
        }

        if (colideToLeftBlock) {
            goRightBall = true;
        }

        if (colideToTopBlock) {
            goDownBall = false;
        }

        if (colideToBottomBlock) {
            goDownBall = true;
        }


    }
    public void showWin(final Main main) {
        Platform.runLater(() -> {

            Image image = new Image("youwin.png");
            ImageView imageView = new ImageView(image);

            imageView.setTranslateX(100);
            imageView.setTranslateY(250);
            imageView.setFitWidth(300);
            imageView.setFitHeight(300);
            imageView.setPreserveRatio(true);

            main.root.getChildren().addAll(imageView);

            engine.stop();
        });
    }


    private void checkDestroyedCount() {
        if (destroyedBlockCount == blocks.size()) {
            if (level == 18) {
                showWin(this);
                engine.stop();
            } else {
                isLevelCompleted = true;
                nextLevel();
            }
        }
    }


    private void saveGame() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new File(savePathDir).mkdirs();
                File file = new File(savePath);
                ObjectOutputStream outputStream = null;
                try {
                    outputStream = new ObjectOutputStream(new FileOutputStream(file));

                    outputStream.writeInt(level);
                    outputStream.writeInt(score);
                    outputStream.writeInt(heart);
                    outputStream.writeInt(destroyedBlockCount);


                    outputStream.writeDouble(xBall);
                    outputStream.writeDouble(yBall);
                    outputStream.writeDouble(xBreak);
                    outputStream.writeDouble(yBreak);
                    outputStream.writeDouble(centerBreakX);
                    outputStream.writeLong(time);
                    outputStream.writeLong(goldTime);
                    outputStream.writeDouble(vX);


                    outputStream.writeBoolean(isExistHeartBlock);
                    outputStream.writeBoolean(isGoldStatus);
                    outputStream.writeBoolean(goDownBall);
                    outputStream.writeBoolean(goRightBall);
                    outputStream.writeBoolean(colideToBreak);
                    outputStream.writeBoolean(colideToBreakAndMoveToRight);
                    outputStream.writeBoolean(colideToRightWall);
                    outputStream.writeBoolean(colideToLeftWall);
                    outputStream.writeBoolean(colideToRightBlock);
                    outputStream.writeBoolean(colideToBottomBlock);
                    outputStream.writeBoolean(colideToLeftBlock);
                    outputStream.writeBoolean(colideToTopBlock);

                    ArrayList<BlockSerializable> blockSerializables = new ArrayList<BlockSerializable>();
                    for (Block block : blocks) {
                        if (block.isDestroyed) {
                            continue;
                        }
                        blockSerializables.add(new BlockSerializable(block.row, block.column, block.type));
                    }

                    outputStream.writeObject(blockSerializables);

                    new Score().showMessage("Game Saved", Main.this);


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        outputStream.flush();
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
    private boolean isLevelCompleted = false;

    private void nextLevel() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    if (isLevelCompleted) {
                        level++;  // Increment level here only if the level is completed
                        System.out.println("Level incremented to: " + level);
                        isLevelCompleted = false; // Reset the flag for the next level

                        // Reset game state for the new level
                        vX = 1.000;
                        resetColideFlags();
                        goDownBall = true;

                        isGoldStatus = false;
                        isExistHeartBlock = false;

                        hitTime = 0;
                        time = 0;
                        goldTime = 0;

                        // Clear old blocks, and initialize new ones
                        blocks.clear();
                        chocos.clear();
                        destroyedBlockCount = 0;
                        initBall();
                        initBreak();
                        initBoard();

                        // Update the game scene for the next level
                        updateGameScene();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void updateGameScene() {

        root.getChildren().clear();
        root.getChildren().addAll(rect, ball, scoreLabel, heartLabel, levelLabel);
        for (Block block : blocks) {
            root.getChildren().add(block.rect);
        }

        // Update labels
        scoreLabel.setText("Score: " + score);
        heartLabel.setText("Heart: " + heart);
        levelLabel.setText("Level: " + level);
    }


    public void restartGame() {

        try {
            level = 1;
            heart = 3;
            score = 0;
            vX = 1.000;
            destroyedBlockCount = 0;
            resetColideFlags();
            goDownBall = true;

            isGoldStatus = false;
            isExistHeartBlock = false;
            hitTime = 0;
            time = 0;
            goldTime = 0;

            blocks.clear();
            chocos.clear();

            start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private enum PaddleState {
        NORMAL, INCREASED, DECREASED
    }
    private void changePaddleWidth(boolean increase) {
        Platform.runLater(() -> {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastWidthChangeTime >= 9000) { // 9 seconds
                if ((increase && paddleState != PaddleState.INCREASED) || (!increase && paddleState != PaddleState.DECREASED)) {
                    double originalWidth = rect.getWidth();
                    double newWidth = increase ? originalWidth * 1.25 : originalWidth * 0.75;
                    double newX = xBreak;
                    if (newX + newWidth > sceneWidth) {
                        newX = sceneWidth - newWidth;
                    } else if (newX < 0) {
                        newX = 0;
                    }

                    xBreak = newX;
                    rect.setWidth(newWidth);
                    paddleState = increase ? PaddleState.INCREASED : PaddleState.DECREASED;

                    lastWidthChangeTime = currentTime; // Update the timestamp
                    resetPaddleWidthAfterDelay();
                }
            }
        });
    }


    private void resetPaddleWidthAfterDelay() {
        new Thread(() -> {
            try {
                Thread.sleep(9000); // Wait for 9 seconds
                Platform.runLater(this::resetPaddleWidthToDefault);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }


    private void resetPaddleWidthToDefault() {
        Platform.runLater(() -> {
            if (paddleState != PaddleState.NORMAL) {
                rect.setWidth(breakWidth);
                if (xBreak + breakWidth > sceneWidth) {
                    xBreak = sceneWidth - breakWidth;
                }
                paddleState = PaddleState.NORMAL;
            }
        });
    }



    @Override
    public void onUpdate() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                scoreLabel.setText("Score: " + score);
                heartLabel.setText("Heart : " + heart);

                rect.setX(xBreak);
                rect.setY(yBreak);
                ball.setCenterX(xBall);
                ball.setCenterY(yBall);

                for (Bonus choco : chocos) {
                    choco.choco.setY(choco.y);
                }
            }
        });
        for (Block block : blocks) {
            int hitCode = block.checkHitToBlock(xBall, yBall, ballRadius);
            if (hitCode != Block.NO_HIT && !block.isDestroyed) {
                // Check if the block hit is BLOCK_BLOCK1
                if (block.type == Block.BLOCK_BLOCK1) {
                    changePaddleWidth(true); // for increasing paddle width
                }
                // Check if the block hit is BLOCK_BLOCK2
                else if (block.type == Block.BLOCK_BLOCK2) {
                    changePaddleWidth(false); // for decreasing paddle width
                }
            }
        }



        if (yBall >= Block.getPaddingTop() && yBall <= (Block.getHeight() * (level + 1)) + Block.getPaddingTop()) {
            for (final Block block : blocks) {
                int hitCode = block.checkHitToBlock(xBall, yBall, ballRadius);
                if (hitCode != Block.NO_HIT) {
                    score += 1;

                    new Score().show(block.x, block.y, 1, this);

                    block.rect.setVisible(false);
                    block.isDestroyed = true;
                    destroyedBlockCount++;
                    //System.out.println("size is " + blocks.size());
                    resetColideFlags();

                    if (block.type == Block.BLOCK_CHOCO) {
                        final Bonus choco = new Bonus(block.row, block.column);
                        choco.timeCreated = time;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                root.getChildren().add(choco.choco);
                            }
                        });
                        chocos.add(choco);
                    }

                    if (block.type == Block.BLOCK_STAR) {
                        goldTime = time;
                        ball.setFill(new ImagePattern(new Image("goldball.png")));
                        System.out.println("gold ball");
                        root.getStyleClass().add("goldRoot");
                        isGoldStatus = true;
                    }

                    if (block.type == Block.BLOCK_HEART) {
                        heart++;
                    }
                    if(isGoldStatus){

                    }
                    else{
                    if (hitCode == Block.HIT_RIGHT) {
                        colideToRightBlock = true;
                    } else if (hitCode == Block.HIT_BOTTOM) {
                        colideToBottomBlock = true;
                    } else if (hitCode == Block.HIT_LEFT) {
                        colideToLeftBlock = true;
                    } else if (hitCode == Block.HIT_TOP) {
                        colideToTopBlock = true;
                    }

                }

                //TODO hit to break and some work here....
                //System.out.println("Break in row:" + block.row + " and column:" + block.column + " hit");
            }}
        }
    }


    @Override
    public void onInit() {

    }

    @Override
    public void onPhysicsUpdate() {
        checkDestroyedCount();
        setPhysicsToBall();

        if (time - goldTime > 2500) {
            ball.setFill(new ImagePattern(new Image("ball.png")));
            root.getStyleClass().remove("goldRoot");
            isGoldStatus = false;
        }

        for (Bonus choco : chocos) {
            if (!choco.taken) {
                choco.y += ((time - choco.timeCreated) / 1000.0) + 1.0;
                choco.choco.setY(choco.y);

                // Check for collision with the player
                if (choco.y + choco.choco.getHeight() >= yBreak &&
                        choco.y <= yBreak + breakHeight &&
                        choco.x + choco.choco.getWidth() >= xBreak &&
                        choco.x <= xBreak + breakWidth) {

                    choco.taken = true;
                    choco.choco.setVisible(false);
                    score += 3;
                    new Score().show(choco.x, choco.y, 3, this);
                }
            }
        }
    }



    @Override
    public void onTime(long time) {
        this.time = time;
    }
}