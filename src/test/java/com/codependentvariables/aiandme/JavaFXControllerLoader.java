package com.codependentvariables.aiandme;

import com.codependentvariables.aiandme.navigation.View;
import com.codependentvariables.aiandme.navigation.ViewUtils;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class JavaFXControllerLoader {
    public static <T> T load(View view) {
        try {
            FXMLLoader loader = new FXMLLoader(JavaFXControllerLoader.class.getResource(ViewUtils.getResourceName(view)));
            loader.load();
            return loader.getController();
        } catch (IOException e) {
            throw new RuntimeException("FXML load failed", e);
        }
    }
}