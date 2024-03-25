package br.com.fulltime.fullarm.app.javafx.controller;

import br.com.fulltime.fullarm.app.javafx.Panes;
import br.com.fulltime.fullarm.app.javafx.SceneLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static br.com.fulltime.fullarm.app.javafx.PaneMap.paneMap;

@Component
public class MainController implements Controller {
    @FXML
    private StackPane contentPane;
    @FXML
    private Button panelPaneButton;
    @FXML
    private Button configPaneButton;
    @Autowired
    private SceneLoader sceneLoader;

    @FXML
    private void initialize() {
        setPanelPane();
    }

    public void setContent(String path) {
        contentPane.getChildren().clear();

        Panes key = Panes.getByValue(path);
        Controller controller = paneMap.get(key);
        if (controller == null) {
            Parent root = sceneLoader.loadScene(path);
            contentPane.getChildren().add(root);
            return;
        }

        Pane root = controller.getRoot();
        contentPane.getChildren().add(root);
        controller.onLoad();
    }

    public void setPanelPane() {
        setContent("/view/panelPane.fxml");
    }

    public void setConfigPane() {
        setContent("/view/configPane.fxml");
    }

    @Override
    public void onLoad() {

    }

    @Override
    public Pane getRoot() {
        return null;
    }
}
