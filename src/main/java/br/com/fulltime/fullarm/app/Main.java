package br.com.fulltime.fullarm.app;

import br.com.fulltime.fullarm.di.SpringConfiguration;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        ApplicationService applicationService = applicationContext.getBean(ApplicationService.class);
        applicationService.start(primaryStage);
    }
}
