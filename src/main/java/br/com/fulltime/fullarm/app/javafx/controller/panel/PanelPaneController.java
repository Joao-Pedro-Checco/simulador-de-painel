package br.com.fulltime.fullarm.app.javafx.controller.panel;

import br.com.fulltime.fullarm.app.javafx.controller.Controller;

public interface PanelPaneController extends Controller {
    void updateConnectionStatus(boolean connected);
    void updateInterface();
}
