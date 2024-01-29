package br.com.fulltime.fullarm.app.javafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

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
            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FXMLLoader getLastLoader() {
        return lastLoader;
    }
}
