package com.codependentvariables.aiandme.modules.toast;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Static class for toast messages.
 */
public class Toast {
    private static final int MAX_MESSAGES = 5;
    private static final int STAY_SECONDS = 6;
    private static final int FADE_SECONDS = 1;
    private static final double MAX_WIDTH = 500.0;
    private static final VBox toastContainer = new VBox(MAX_MESSAGES);

    public record ToastMessage(String title, String description, ToastMessageType type) {}

    /**
     * Determines if the toast module is set up.
     * @return True if the toast module is set up.
     */
    public static boolean isSetUp() {
        return toastContainer.getMaxWidth() == MAX_WIDTH;
    }

    /**
     * Initialises the toast module with the pane used to place messages.
     * @param toastRef Pane for toast messages.
     */
    public static void setRef(StackPane toastRef) {
        toastContainer.setPickOnBounds(false);
        toastContainer.setAlignment(Pos.BOTTOM_RIGHT);
        toastContainer.setMaxWidth(MAX_WIDTH);
        toastRef.setPickOnBounds(false);
        toastRef.getChildren().add(toastContainer);
    }

    /**
     * Adds a toast message to the queue.
     * @param title Title for the toast message.
     * @param description Description for the toast message.
     * @param type Type for the toast message.
     */
    public static void addMessage(String title, String description, ToastMessageType type) {
        addMessage(new ToastMessage(title, description, type));
    }

    /**
     * Adds a toast message to the queue.
     * @param message Toast message to add.
     */
    public static void addMessage(ToastMessage message) {
        Platform.runLater(() -> {
            if (toastContainer.getChildren().size() >= MAX_MESSAGES) {
                toastContainer.getChildren().removeLast();
            }

            HBox toastMessageUI = createToastMessageUI(message);
            toastContainer.getChildren().addFirst(toastMessageUI);

            PauseTransition stay = new PauseTransition(Duration.seconds(STAY_SECONDS));

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(FADE_SECONDS), toastMessageUI);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);

            fadeOut.setOnFinished(e -> toastContainer.getChildren().remove(toastMessageUI));

            SequentialTransition sequence = new SequentialTransition(stay, fadeOut);

            toastMessageUI.setOnMouseClicked(e -> {
                sequence.stop();
                toastContainer.getChildren().remove(toastMessageUI);
            });

            sequence.play();
        });
    }

    /**
     * Constructs a HBox of a toast message.
     * @param message Toast message to use.
     * @return HBox containing toast message information.
     */
    private static HBox createToastMessageUI(ToastMessage message) {
        HBox box = new HBox();

        String backgroundColour = switch (message.type) {
            case INFORMATION -> "limegreen";
            case WARNING -> "sandybrown";
            case ERROR -> "orangered";
            default -> throw new RuntimeException("Toast message type not implemented.");
        };

        box.setStyle(String.format("-fx-background-color: %s; -fx-spacing: 1em; -fx-padding: 10; -fx-background-radius: 5; -fx-cursor: hand;", backgroundColour));

        Label title = new Label(message.title());
        title.setMinWidth(50.0);
        title.setWrapText(true);
        title.setStyle("-fx-font-weight: bold;");

        Label description = new Label(message.description());
        HBox.setHgrow(description, Priority.ALWAYS);
        description.setWrapText(true);

        // Makes clicks ignore text for HBox onclick to self-delete
        title.setMouseTransparent(true);
        description.setMouseTransparent(true);

        box.getChildren().addAll(title, description);
        return box;
    }
}
