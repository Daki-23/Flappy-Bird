package com.dakster.gameobjects.pipes;

import com.dakster.constants.StageConstants;
import com.dakster.gameobjects.Bird;
import com.dakster.gameobjects.GameObject;
import com.dakster.gameobjects.ChildNode;
import com.dakster.gameobjects.Window;

import java.util.LinkedList;

import static com.dakster.constants.PipeConstants.*;

public class Pipes implements GameObject, ChildNode, BirdCollider {
    private final LinkedList<PipePair> pipes;

    public Pipes() {
        pipes = new LinkedList<>();

        double initialX = StageConstants.WIDTH;
        for (int i = 0; i < PIPE_COUNT; i++) {
            pipes.addLast(new PipePair(initialX));
            initialX += DISTANCE_BETWEEN_PIPES;
        }
    }

    @Override
    public void addToWindow(Window window) {
        pipes.forEach(pipePair -> pipePair.addToWindow(window));
    }

    @Override
    public void updatePosition() {
        pipes.forEach(PipePair::updatePosition);

        PipePair firstPipePair = pipes.getFirst();
        PipePair lastPipePair = pipes.getLast();

        if (firstPipePair.shouldRemoveFromWindow()) {
            pipes.removeFirst();
            pipes.addLast(new PipePair(lastPipePair.getX() + DISTANCE_BETWEEN_PIPES));
        }
    }

    public boolean isCollidingWith(Bird bird) {
        return pipes.stream().anyMatch(pipePair -> pipePair.isCollidingWith(bird));
    }

    // Checks if the bird has passed any new pipe pair and returns true if the points
    // have been updated.
    public boolean incrementPoint(Bird bird) {
        return pipes.stream().anyMatch(pipePair -> pipePair.incrementPoint(bird));
    }
}
