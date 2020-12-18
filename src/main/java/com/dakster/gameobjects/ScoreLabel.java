package com.dakster.gameobjects;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

import static com.dakster.constants.ScoreLabelConstants.*;

public class ScoreLabel extends Label implements ChildNode {
    private int score;

    public ScoreLabel() {
        super();
        score = 0;
        setText(String.valueOf(score));
        setLayoutX(X_POSITION);
        setLayoutY(Y_POSITION);
        setFont(new Font(FONT_TYPE, FONT_SIZE));
        setViewOrder(VIEW_ORDER);
    }

    @Override
    public void addToWindow(Window window) {
        window.getChildren().add(this);
    }

    public void incrementScore() {
        score += 1;
        setText(String.valueOf(score));
    }
}
