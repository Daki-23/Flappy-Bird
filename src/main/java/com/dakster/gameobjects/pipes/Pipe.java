package com.dakster.gameobjects.pipes;

import com.dakster.constants.StageConstants;
import com.dakster.gameobjects.Bird;
import com.dakster.gameobjects.ChildNode;
import com.dakster.gameobjects.Window;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import static com.dakster.constants.BirdConstants.BIRD_STRAIGHT_FILENAME;
import static com.dakster.constants.PipeConstants.*;

public class Pipe extends Rectangle implements ChildNode, BirdCollider {
    private final PipeType pipeType;

    public Pipe(double height, double initialX, PipeType pipeType) {
        this.pipeType = pipeType;

        ImagePattern pipeImage = new ImagePattern(new Image(getClass()
                .getResource(PIPE_BOTTOM_FILENAME)
                .toExternalForm()));

        double yPosition = 0;
        if (pipeType == PipeType.BOTTOM) {
            pipeImage = new ImagePattern(new Image(getClass()
                    .getResource(PIPE_BOTTOM_FILENAME)
                    .toExternalForm()));
            yPosition = StageConstants.HEIGHT - height;
        }

        setX(initialX);
        setY(yPosition);
        setWidth(WIDTH);
        setHeight(height);
        setFill(pipeImage);
        setViewOrder(VIEW_ORDER);
    }

    /**
     * Checks whether the pipe is colliding with the given bird.
     * @param bird - bird whose position is to be checked.
     * @return - true if the bird is colliding with the pipe else false.
     */
    public boolean isCollidingWith(Bird bird) {
        return isInWidth(bird) && isInHeight(bird);
    }

    @Override
    public void addToWindow(Window window) {
        window.getChildren().add(this);
    }

    public void updatePosition(double newXPosition) {
        setX(newXPosition);
    }

    /**
     * Checks if the provided bird is within the width of the pipe.
     * @param bird - bird whose position is to be checked.
     * @return - true if the provided bird is within the width of the pipe else false.
     */
    private boolean isInWidth(Bird bird) {
        return bird.getXCollidingZone() >= getX() && bird.getXCollidingZone() <= getX() + WIDTH;
    }

    /**
     * Checks if the provided bird is within the height of the pipe.
     * @param bird - bird whose position is to be checked.
     * @return - true if the provided bird is within the height of the pipe else false.
     */
    private boolean isInHeight(Bird bird) {
        double bottomBoundPosition = 0;
        double topBoundPosition = getHeight();

        if (pipeType == PipeType.BOTTOM) {
            bottomBoundPosition = getY();
            topBoundPosition = StageConstants.HEIGHT;
        }
        double finalBottomBoundPosition = bottomBoundPosition;
        double finalTopBoundPosition = topBoundPosition;

        return bird.getYCollidingZones()
                .stream()
                .anyMatch(collidingZone -> collidingZone >= finalBottomBoundPosition
                                && collidingZone <= finalTopBoundPosition);
    }
}
