package com.codependentvariables.aiandme;

import atlantafx.base.theme.NordDark;
import atlantafx.base.theme.NordLight;
import com.codependentvariables.aiandme.database.SqliteConnection;
import com.codependentvariables.aiandme.modules.toast.Toast;
import com.codependentvariables.aiandme.modules.toast.ToastMessageType;
import com.codependentvariables.aiandme.modules.router.View;
import com.codependentvariables.aiandme.modules.router.ViewUtils;
import com.codependentvariables.aiandme.modules.state.AppState;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AiAndMe extends Application {
    private static final Logger logger = Logger.getLogger(AiAndMe.class.getName());
    private static final AppState appState = AppState.getInstance();

    public static final String TITLE = "AI & Me";
    public static final Double WIDTH = 800.0;
    public static final Double HEIGHT = 600.0;

    public static final String lightModeStylesheet = new NordLight().getUserAgentStylesheet();
    public static final String darkModeStylesheet = new NordDark().getUserAgentStylesheet();
    public static final String lightLogoUrlString = Objects.requireNonNull(AiAndMe.class.getResource("dark-logo.png")).toString();
    public static final String darkLogoUrlString = Objects.requireNonNull(AiAndMe.class.getResource("logo.png")).toString();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Thread.setDefaultUncaughtExceptionHandler((Thread t, Throwable e) -> {
            logger.log(Level.SEVERE, "Uncaught exception in thread: " + t.getName(), e);

            if (Toast.isSetup()) {
                // If the UI is alive, push to the FX thread to show the toast
                Platform.runLater(() -> {
                    Toast.addMessage("Error", "Oops, an unexpected error occurred. Please try again later.", ToastMessageType.ERROR);
                });
            } else {
                logger.info("Toast not ready; shutting down.");
                System.exit(1);
            }
        });

        SqliteConnection.getConnection(); // Frontload db connection load time at app startup

        stage.setTitle(TITLE);
        stage.setMaximized(true);
        // TODO: decide if we want to disable resizing or enforce max height and width on startup

        URL stylesheetResource = AiAndMe.class.getResource("stylesheet.css");
        Application.setUserAgentStylesheet((new NordLight()).getUserAgentStylesheet());

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(AiAndMe.class.getResource(ViewUtils.getResourceName(View.APP)));
            Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
            if (stylesheetResource != null) {
                scene.getStylesheets().add(stylesheetResource.toExternalForm());
            }

            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            throw new RuntimeException("Could not load AppController.", ex);
        }
    }
}