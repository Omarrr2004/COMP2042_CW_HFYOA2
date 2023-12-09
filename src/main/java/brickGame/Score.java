package brickGame;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Score {

    public void show(final double x, final double y, int score, final Main main) {
        String sign = (score >= 0) ? "+" : "";
        final Label label = new Label(sign + score);
        label.setTranslateX(x);
        label.setTranslateY(y);

        Platform.runLater(() -> main.root.getChildren().add(label));

        new Thread(() -> {
            try {
                for (int i = 0; i < 21; i++) {
                    final int scale = i;
                    Platform.runLater(() -> {
                        label.setScaleX(scale);
                        label.setScaleY(scale);
                        label.setOpacity((20 - scale) / 20.0);
                    });
                    Thread.sleep(15);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void showMessage(String message, final Main main) {
        final Label label = new Label(message);
        label.setTranslateX(220);
        label.setTranslateY(340);

        Platform.runLater(() -> main.root.getChildren().add(label));

        new Thread(() -> {
            try {
                for (int i = 0; i < 21; i++) {
                    final int scale = Math.abs(i - 10);
                    final double opacity = (20 - scale) / 20.0;
                    Platform.runLater(() -> {
                        label.setScaleX(scale / 2.0);
                        label.setScaleY(scale / 2.0);
                        label.setOpacity(opacity);
                    });
                    Thread.sleep(15);
                }

                // Wait for a certain period (adjust the sleep time accordingly)
                Thread.sleep(2000);

                // Remove the label from the UI after the wait period
                Platform.runLater(() -> main.root.getChildren().remove(label));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }


    public void showGameOver(final Main main) {
        Platform.runLater(() -> {
            ImageView gameOverImage = new ImageView(new Image("youlose.png"));
            gameOverImage.setFitHeight(200); // Set the height of the image
            gameOverImage.setFitWidth(400);  // Set the width of the image
            gameOverImage.setTranslateX(53);
            gameOverImage.setTranslateY(80);

            ImageView restartIcon = new ImageView(new Image("restart.png"));
            restartIcon.setFitHeight(40); // Set the height of the icon
            restartIcon.setFitWidth(40);  // Set the width of the icon
            restartIcon.setTranslateX(230); // Set X position
            restartIcon.setTranslateY(385); // Set Y position

            restartIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> main.restartGame());

            main.root.getChildren().addAll(gameOverImage, restartIcon);
        });
    }

    public void showWin(final Main main) {
        Platform.runLater(() -> {
            Label label = new Label("You Win :)");
            label.setTranslateX(200);
            label.setTranslateY(250);
            label.setScaleX(2);
            label.setScaleY(2);

            main.root.getChildren().addAll(label);
        });
    }
}
