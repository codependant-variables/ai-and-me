package com.codependentvariables.cab302groupproject;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

public class Application extends javafx.application.Application {
    private ConfigurableApplicationContext springContext;

    @Override
    public void init() {
        springContext = new SpringApplicationBuilder(SpringBootApp.class).run();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("home.fxml"));
        fxmlLoader.setControllerFactory(springContext::getBean);

        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        java.net.URL stylesheetResource = Application.class.getResource("stylesheet.css");
        if (stylesheetResource != null) {
            String stylesheet = stylesheetResource.toExternalForm();
            scene.getStylesheets().add(stylesheet);
        }

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        springContext.close();
        Platform.exit();
    }
}
