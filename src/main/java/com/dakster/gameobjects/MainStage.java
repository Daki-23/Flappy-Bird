package com.dakster.gameobjects;

import javafx.stage.Stage;

import static com.dakster.constants.StageConstants.*;

public class MainStage extends Stage {
    public MainStage() {
        setupStage();
    }

    private void setupStage() {
        setTitle(TITLE);
        setHeight(HEIGHT);
        setWidth(WIDTH);
        setResizable(IS_RESIZABLE);
    }
}
