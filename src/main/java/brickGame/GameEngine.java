package brickGame;

import javafx.application.Platform;

/**
 * The GameEngine class manages the main game loop and timing for the game.
 * It handles the updating of game logic, physics, and time-based actions.
 */
public class GameEngine {

    private OnAction onAction;
    private int fps = 100;
    private volatile boolean isRunning = true;

    /**
     * Sets the action handler for game events.
     *
     * @param onAction The OnAction interface implementation to handle game events.
     */
    public void setOnAction(OnAction onAction) {
        this.onAction = onAction;
    }

    /**
     * Sets the frames per second (fps) rate for the game loop.
     *
     * @param fps The desired frame rate in frames per second.
     */
    public void setFps(int fps) {
        this.fps = fps;
    }

    /**
     * Starts the game engine, initializing the game loop and time tracking.
     * This method initializes the game and starts separate threads for the game loop and time tracking.
     */
    public void start() {
        isRunning = true;
        initialize();

        new Thread(this::gameLoop).start();
        new Thread(this::timeStart).start();
    }

    public void stop() {
        isRunning = false;
    }

    private void initialize() {
        runOnUIThread(() -> onAction.onInit());
    }

    /**
     * The main game loop that handles updating game state and physics.
     * This loop runs continuously during the game and manages the timing for updates.
     */
    private void gameLoop() {
        while (isRunning) {
            long startTime = System.currentTimeMillis();

            runOnUIThread(() -> {
                onAction.onUpdate();
                onAction.onPhysicsUpdate();
            });

            long sleepTime = 1000 / fps - (System.currentTimeMillis() - startTime);
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private long time = 0;

    /**
     * Tracks the passage of time and triggers time-based actions.
     * This method keeps track of the elapsed time and invokes the onTime method periodically.
     */
    private void timeStart() {
        while (isRunning) {
            try {
                Thread.sleep(1);
                time++;

                runOnUIThread(() -> onAction.onTime(time));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Executes a given Runnable on the JavaFX Application Thread.
     *
     * @param runnable The Runnable to be executed on the UI thread.
     */
    private void runOnUIThread(Runnable runnable) {
        Platform.runLater(runnable);
    }

    /**
     * The OnAction interface defines methods for handling various game events.
     * Implementations of this interface should define actions for game update, initialization,
     * physics update, and time-based events.
     */
    public interface OnAction {
        void onUpdate();

        void onInit();

        void onPhysicsUpdate();

        void onTime(long time);
    }
}