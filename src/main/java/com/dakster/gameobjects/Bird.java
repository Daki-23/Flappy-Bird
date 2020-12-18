package com.dakster.gameobjects;

import com.dakster.constants.StageConstants;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Ellipse;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.dakster.constants.BirdConstants.*;

public class Bird extends Ellipse implements GameObject, ChildNode {
    private static final AtomicInteger count = new AtomicInteger(0);
    private final int id;
    private final ImagePattern birdStraightImage;
    private final ImagePattern birdUpImage;
    private final ImagePattern birdDownImage;
    private final AudioClip jumpSoundPlayer;
    private final AudioClip dieSoundPlayer;

    private double velocityY;
    private boolean isDead;

    public Bird(double initialX, double initialY) {
        super();
        velocityY = 0;
        isDead = false;
        id = count.incrementAndGet();
        setRadiusX(RADIUS);
        setRadiusY(RADIUS);
        setCenterX(initialX);
        setCenterY(initialY);
        setViewOrder(VIEW_ORDER);

        jumpSoundPlayer = new AudioClip(getClass()
                .getResource(JUMP_SOUND_FILENAME)
                .toExternalForm());
        dieSoundPlayer = new AudioClip(getClass()
                .getResource(DIE_SOUND_FILENAME)
                .toExternalForm());

        birdStraightImage = new ImagePattern(new Image(getClass()
                .getResource(BIRD_STRAIGHT_FILENAME)
                .toExternalForm()));
        birdUpImage = new ImagePattern(new Image(getClass()
                .getResource(BIRD_UP_FILENAME)
                .toExternalForm()));
        birdDownImage = new ImagePattern(new Image(getClass()
                .getResource(BIRD_DOWN_FILENAME)
                .toExternalForm()));

        setFill(birdStraightImage);
    }

    public int getBirdId() {
        return id;
    }

    public void jump() {
        if (isDead) {
            return;
        }

        jumpSoundPlayer.play();
        velocityY = -JUMP;

        if (getUpdatedY() < getTopPosition()) {
            velocityY = 0;
            setCenterY(RADIUS);
            return;
        }

        setCenterY(getUpdatedY());
    }

    public boolean isCollidingWithBorders() {
        return getCenterY() <= getTopPosition() || isCollidingWithGround();
    }

    public boolean isCollidingWithGround() {
        return getCenterY() >= getGroundPosition();
    }

    public double getXCollidingZone() {
        return getCenterX() + RADIUS - X_COLLISION_FINE_TUNING;
    }

    public List<Double> getYCollidingZones() {
        return Arrays.asList(
                getCenterY() + RADIUS - Y_COLLISION_FINE_TUNING,
                getCenterY() - RADIUS + Y_COLLISION_FINE_TUNING);
    }

    public void setDead() {
        dieSoundPlayer.play();
        isDead = true;
    }

    private double getGroundPosition() {
        return StageConstants.HEIGHT - StageConstants.WINDOW_PADDING - RADIUS ;
    }

    private double getTopPosition() {
        return StageConstants.WINDOW_PADDING + RADIUS;
    }

    private double getUpdatedY() {
        return getCenterY() + velocityY;
    }

    @Override
    public void addToWindow(Window window) {
        window.getChildren().add(this);
    }

    @Override
    public void updatePosition() {
        setBirdOrientation();

        velocityY += GRAVITY;
        if (getUpdatedY() > getGroundPosition()) {
            setCenterY(getGroundPosition());
            return;
        }
        setCenterY(getUpdatedY());
    }

    private void setBirdOrientation() {
        if (velocityY > 0) {
            setFill(birdDownImage);
        } else if (velocityY < 0) {
            setFill(birdUpImage);
        } else {
            setFill(birdStraightImage);
        }
    }
}
