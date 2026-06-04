package com.codependentvariables.aiandme.modules.dialogue;

import com.codependentvariables.aiandme.JavaFXTest;
import javafx.scene.layout.StackPane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DialogueTest extends JavaFXTest {
    @BeforeEach
    public void setupEach() {
        Dialogue.setRef(new StackPane());
    }

    @Test
    public void message_loads_dialogue_view() {
        final String message = "Here is my message";

        Dialogue.message(message);

        StackPane dialogueRef = Dialogue.getDialogueRef();
        assertNotNull(dialogueRef);
    }
}