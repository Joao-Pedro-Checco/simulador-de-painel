package br.com.fulltime.fullarm.app.javafx;

import br.com.fulltime.fullarm.app.javafx.controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static br.com.fulltime.fullarm.app.javafx.PaneMap.paneMap;

@Component
public class SceneLoader {
    @Autowired
    private ApplicationContext applicationContext;
    private FXMLLoader lastLoader;

    public Parent loadScene(String path) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            lastLoader = loader;
            loader.setControllerFactory(applicationContext::getBean);
            Parent load = loader.load();

            Object controller = loader.getController();
            Panes key = Panes.getByValue(path);

            paneMap.put(key, (Controller) controller);
            return load;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FXMLLoader getLastLoader() {
        return lastLoader;
    }
}
