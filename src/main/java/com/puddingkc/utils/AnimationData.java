package com.puddingkc.utils;

import java.util.List;

public class AnimationData {

    public List<String> texts;
    int speed;
    String type;
    int currentIndex = 0;
    long lastUpdate = 0;
    int pingPongDirection = 1;

    public AnimationData(List<String> texts, int speed, String type) {
        this.texts = texts;
        this.speed = speed;
        this.type = type;
    }

}
