package brickGame;

import javafx.scene.media.MediaPlayer;

/**
 * The GameAudio class handles audio functionalities in the game.
 * It includes methods to manage audio settings like muting, playing, and stopping game sounds.
 */
public class GameAudio {

    /**
     * Toggles the game's audio state between playing and paused.
     * It also updates the audio button's icon to reflect the current audio state.
     *
     * @param main The main game object containing audio settings and elements.
     */
    public static void audioFunctionality(Main main) {
        double audioOnIconWidth = 40;
        double audioOnIconHeight = 40;
        main.audioOnIcon.setFitWidth(audioOnIconWidth);
        main.audioOnIcon.setFitHeight(audioOnIconHeight);

        double audioOnButtonX = 85;
        double audioOnButtonY = 320;
        main.audioButton.setLayoutX(audioOnButtonX);
        main.audioButton.setLayoutY(audioOnButtonY);

        double audioOffIconWidth = 40;
        double audioOffIconHeight = 40;
        main.audioOffIcon.setFitWidth(audioOffIconWidth);
        main.audioOffIcon.setFitHeight(audioOffIconHeight);

        double audioOffButtonX = 85;
        double audioOffButtonY = 320;
        main.audioButton.setLayoutX(audioOffButtonX);
        main.audioButton.setLayoutY(audioOffButtonY);

        // Toggle audio state and update button graphic
        if (main.mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            main.mediaPlayer.pause();
            main.audioButton.setGraphic(main.audioOffIcon);
            if(main.isGameStarted){
                new Score().showMessage("Muted", main);
            }
        } else {
            main.mediaPlayer.play();
            main.audioButton.setGraphic(main.audioOnIcon);
        }
    }

    /**
     * Stops the game's audio.
     * This method is used to completely stop the audio playback.
     *
     * @param main The main game object containing the MediaPlayer instance.
     */
    public static void StopAudio(Main main) {
        if (main.mediaPlayer != null) {
            main.mediaPlayer.stop();
        }
    }
}
