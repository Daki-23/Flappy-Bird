package com.dakster.constants;

public class PipeConstants {
    public enum PipeType {
        TOP, BOTTOM;
    }

    public static final double WIDTH = 100;
    public static final double PIPE_GAP = 150;
    public static final double MIN_HEIGHT = 100;
    public static final double MAX_HEIGHT = StageConstants.HEIGHT - PIPE_GAP - MIN_HEIGHT;
    public static final double DISTANCE_BETWEEN_PIPES = 300;
    public static final double VIEW_ORDER = 3;
    public static final int PIPE_COUNT = 10;

    public static final String PIPE_BOTTOM_FILENAME = "/bottom-pipe.png";
}