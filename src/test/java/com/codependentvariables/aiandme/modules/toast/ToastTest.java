package com.codependentvariables.aiandme.modules.toast;

import com.codependentvariables.aiandme.JavaFXTest;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;

public class ToastTest extends JavaFXTest {
    public StackPane toastPane;

    public List<HBox> getMessages() {
        VBox toastContainer = (VBox) toastPane.getChildren().getFirst();
        return toastContainer.getChildren().stream().map(x -> (HBox) x).toList();
    }

    public void assertMessageContent(HBox message, String title, String description, ToastMessageType type) {
        assertTrue(message.getStyle().contains(Toast.getToastMessageTypeColour(type)));

        List<Label> labels = message.getChildren().stream().map(x -> (Label) x).toList();
        assertEquals(2, labels.size());
        assertEquals(title, labels.getFirst().getText());
        assertEquals(description, labels.get(1).getText());
    }

    @BeforeEach
    public void setupEach() {
        toastPane = new StackPane();
        Toast.setRef(toastPane);
    }

    @Test
    public void add_information_toast_message() {
        final String title = "Information";
        final String description = "Some information";
        final ToastMessageType type = ToastMessageType.INFORMATION;

        Toast.addMessage(title, description, type);

        List<HBox> messages = getMessages();
        assertEquals(1, messages.size());

        assertMessageContent(messages.getFirst(), title, description, type);
    }

    @Test
    public void add_warning_toast_message() {
        final String title = "Warning";
        final String description = "Some warning";
        final ToastMessageType type = ToastMessageType.WARNING;

        Toast.addMessage(title, description, type);

        List<HBox> messages = getMessages();
        assertEquals(1, messages.size());

        assertMessageContent(messages.getFirst(), title, description, type);
    }

    @Test
    public void add_error_toast_message() {
        final String title = "Error";
        final String description = "Some error";
        final ToastMessageType type = ToastMessageType.ERROR;

        Toast.addMessage(title, description, type);

        List<HBox> messages = getMessages();
        assertEquals(1, messages.size());

        assertMessageContent(messages.getFirst(), title, description, type);
    }

    @Test
    public void toast_message_disappears_after_timer() throws InterruptedException {
        final String title = "Information";
        final String description = "Some information";
        final ToastMessageType type = ToastMessageType.INFORMATION;
        final int staySeconds = Toast.getStaySeconds();
        final int fadeSeconds = Toast.getFadeSeconds();

        Toast.addMessage(title, description, type);

        TimeUnit.SECONDS.sleep(staySeconds + fadeSeconds + 1);

        List<HBox> messages = getMessages();
        assertEquals(0, messages.size());
    }

    @Test
    public void toast_caps_at_max_messages() {
        final String firstTitle = "First";
        final String firstDescription = "I'm first!";
        final ToastMessageType firstType = ToastMessageType.ERROR;
        final String title = "Information";
        final String description = "Some information";
        final ToastMessageType type = ToastMessageType.INFORMATION;
        final int maxMessages = Toast.getMaxMessages();

        Toast.addMessage(firstTitle, firstDescription, firstType);

        for (int i = 0; i < maxMessages; i++) {
            Toast.addMessage(title, description, type);
        }

        List<HBox> messages = getMessages();
        assertEquals(maxMessages, messages.size());

        // First message should be pushed out by execeeding maxMessages
        for (int i = 0; i < maxMessages; i++) {
            assertMessageContent(messages.get(i), title, description, type);
        }
    }
}