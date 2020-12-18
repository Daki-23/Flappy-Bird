package com.dakster;

import com.dakster.constants.StageConstants;
import com.dakster.gameobjects.Bird;
import com.dakster.gameobjects.MainStage;
import com.dakster.gameobjects.ScoreLabel;
import com.dakster.gameobjects.Window;
import com.dakster.gameobjects.pipes.Pipes;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Duration;

import static com.dakster.constants.BirdConstants.HIT_SOUND_FILENAME;
import static com.dakster.constants.BirdConstants.POINT_SOUND_FILENAME;
import static com.dakster.constants.GameConstants.GameState;

public class App extends Application {

    private Scene scene;
    private Window root;
    private Bird bird;
    private Pipes pipes;
    private ScoreLabel scoreLabel;
    private GameState gameState;
    private AudioClip hitSoundPlayer;
    private AudioClip pointSoundPlayer;

    @Override
    public void start(Stage defaultIgnoredStage) {
        gameState = GameState.NOT_STARTED;
        root = new Window();

        initializeGameObjects();
        initializeSoundPlayers();
        Timeline timeline = initializeTimeline();
        timeline.play();

        scene = new Scene(root);

        Stage stage = new MainStage();
        stage.setScene(scene);
        stage.show();
    }

    private void resetGame() {
        root.getChildren().clear();
        initializeGameObjects();
    }

    private void initializeGameObjects() {
        bird = new Bird(StageConstants.WIDTH/2, StageConstants.HEIGHT/2);
        pipes = new Pipes();
        scoreLabel = new ScoreLabel();

        bird.addToWindow(root);
        pipes.addToWindow(root);
        scoreLabel.addToWindow(root);
    }

    private void initializeSoundPlayers() {
        pointSoundPlayer = new AudioClip(getClass()
                .getResource(POINT_SOUND_FILENAME)
                .toExternalForm());
        hitSoundPlayer = new AudioClip(getClass()
                .getResource(HIT_SOUND_FILENAME)
                .toExternalForm());
    }

    private Timeline initializeTimeline() {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame gameNotStartedFrame = getGameNotStartedFrame();
        KeyFrame gameInProgressFrame = gameInProgressFrame();
        KeyFrame gameOverFrame = getGameOverFrame();

        timeline.getKeyFrames().add(gameNotStartedFrame);
        timeline.getKeyFrames().add(gameInProgressFrame);
        timeline.getKeyFrames().add(gameOverFrame);

        return timeline;
    }

    private void handleOnClickSpace() {
        switch (gameState) {
            case NOT_STARTED:
                gameState = GameState.IN_PROGRESS;
                break;
            case IN_PROGRESS:
                bird.jump();
                break;
            case OVER:
                gameState = GameState.NOT_STARTED;
                resetGame();
        }
    }

    private boolean isColliding(Bird bird) {
        return bird.isCollidingWithBorders()
                || pipes.isCollidingWith(bird);
    }

    private KeyFrame getGameNotStartedFrame() {
        return new KeyFrame(Duration.ONE, event -> {
            if (gameState != GameState.NOT_STARTED) {
                return;
            }

            scene.setOnKeyReleased(key -> {
                if (key.getCode() == KeyCode.SPACE) {
                    handleOnClickSpace();
                    bird.jump();
                }
            });
        });
    }

    private KeyFrame gameInProgressFrame() {
        return new KeyFrame(Duration.millis(20), event -> {
            if (gameState != GameState.IN_PROGRESS) {
                return;
            }

            scene.setOnKeyReleased(key -> {
                if (key.getCode() == KeyCode.SPACE) {
                    handleOnClickSpace();
                }
            });

            bird.updatePosition();
            pipes.updatePosition();
            pipes.addToWindow(root);

            if (isColliding(bird)) {
                hitSoundPlayer.play();
                bird.setDead();
                gameState = GameState.OVER;
            }

            if (pipes.incrementPoint(bird)) {
                pointSoundPlayer.play();
                scoreLabel.incrementScore();
            }
        });
    }

    private KeyFrame getGameOverFrame() {
        return new KeyFrame(Duration.ONE, event -> {
            if (gameState != GameState.OVER) {
                return;
            }

            boolean isCollidingWithGround = bird.isCollidingWithGround();
            // Wait until all birds are dead.
            if (!isCollidingWithGround) {
                bird.updatePosition();
            }

            scene.setOnKeyReleased(key -> {
                if (key.getCode() == KeyCode.SPACE && isCollidingWithGround) {
                    handleOnClickSpace();
                }
            });
        });
    }

    public static void main(String[] args) {
        launch();
    }

}