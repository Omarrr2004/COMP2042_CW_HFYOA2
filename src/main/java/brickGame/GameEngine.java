package brickGame;

import javafx.application.Platform;

public class GameEngine {

    private OnAction onAction;
    private int fps = 60;
    private volatile boolean isRunning = true;

    public void setOnAction(OnAction onAction) {
        this.onAction = onAction;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

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

    private void runOnUIThread(Runnable runnable) {
        Platform.runLater(runnable);
    }

    public interface OnAction {
        void onUpdate();

        void onInit();

        void onPhysicsUpdate();

        void onTime(long time);
    }
}
