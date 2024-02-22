package br.com.fulltime.fullarm.app.javafx.controller.connection;

import br.com.fulltime.fullarm.app.javafx.controller.Controller;

public interface ConnectionTabController extends Controller {
    void updateConnectionStatus(boolean connected);
}
