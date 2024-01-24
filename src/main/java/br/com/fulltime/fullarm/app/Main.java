package br.com.fulltime.fullarm.app;

import br.com.fulltime.fullarm.di.SpringConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        ApplicationService applicationService =applicationContext.getBean(ApplicationService.class);
        applicationService.start();
    }
}
