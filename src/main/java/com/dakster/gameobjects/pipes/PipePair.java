package com.dakster.gameobjects.pipes;

import com.dakster.constants.StageConstants;
import com.dakster.gameobjects.Bird;
import com.dakster.gameobjects.GameObject;
import com.dakster.gameobjects.ChildNode;
import com.dakster.gameobjects.Window;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static com.dakster.constants.GameConstants.VELOCITY_X;
import static com.dakster.constants.PipeConstants.*;

public class PipePair implements GameObject, ChildNode, BirdCollider {
    private final Pipe topPipe;
    private final Pipe bottomPipe;
    // A set of all bird ids that have been awarded points by passing this pair of pipes
    private final Set<Integer> birdIdsAwardedPoints;

    private double xPosition;

    public PipePair(double initialX) {
        xPosition = initialX;
        birdIdsAwardedPoints = new HashSet<>();

        double topPipeHeight = ThreadLocalRandom.current().nextDouble(MIN_HEIGHT, MAX_HEIGHT);
        double bottomPipeHeight = StageConstants.HEIGHT - topPipeHeight - PIPE_GAP;

        topPipe = new Pipe(topPipeHeight, initialX, PipeType.TOP);
        bottomPipe = new Pipe(bottomPipeHeight, initialX, PipeType.BOTTOM);
    }

    public boolean isCollidingWith(Bird bird) {
        return topPipe.isCollidingWith(bird) || bottomPipe.isCollidingWith(bird);
    }

    public boolean shouldRemoveFromWindow() {
        return xPosition + WIDTH <= 0;
    }

    public double getX() {
        return xPosition;
    }

    public boolean incrementPoint(Bird bird) {
        if (birdHasPassed(bird) && !birdIdsAwardedPoints.contains(bird.getBirdId())) {
            birdIdsAwardedPoints.add(bird.getBirdId());
            return true;
        }

        return false;
    }

    @Override
    public void addToWindow(Window window) {
        if (window.getChildren().contains(topPipe) || window.getChildren().contains(bottomPipe)) {
            return;
        }
        topPipe.addToWindow(window);
        bottomPipe.addToWindow(window);
    }

    @Override
    public void updatePosition() {
        xPosition -= VELOCITY_X;
        topPipe.updatePosition(xPosition);
        bottomPipe.updatePosition(xPosition);
    }

    private boolean birdHasPassed(Bird bird) {
        return bird.getCenterX() > topPipe.getX() + WIDTH/2;
    }
}
