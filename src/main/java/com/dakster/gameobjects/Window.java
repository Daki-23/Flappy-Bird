package com.dakster.gameobjects;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;

import static com.dakster.constants.WindowConstants.BACKGROUND_COLOUR;

public class Window extends Pane {

    public Window() {
        setupWindow();
    }

    private void setupWindow() {
        setBackground(
            new Background(
                new BackgroundFill(
                    BACKGROUND_COLOUR,
                    CornerRadii.EMPTY,
                    Insets.EMPTY))
        );
    }
}
