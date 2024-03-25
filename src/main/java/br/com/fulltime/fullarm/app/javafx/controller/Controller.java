package br.com.fulltime.fullarm.app.javafx.controller;

import javafx.scene.layout.Pane;

public interface Controller {
    void onLoad();
    Pane getRoot();
}
