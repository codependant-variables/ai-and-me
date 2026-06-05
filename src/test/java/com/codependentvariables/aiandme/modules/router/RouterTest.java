package com.codependentvariables.aiandme.modules.router;

import com.codependentvariables.aiandme.JavaFXTest;
import com.codependentvariables.aiandme.controller.CheckInController;
import com.codependentvariables.aiandme.controller.LoginController;
import com.codependentvariables.aiandme.controller.QuizLibraryController;
import com.codependentvariables.aiandme.controller.SignupController;
import javafx.scene.layout.StackPane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RouterTest extends JavaFXTest {
    @BeforeEach
    public void setupEach() {
        Router.setApp(new StackPane());
        Router.setLayout(new StackPane());
    }

    @Test
    public void navigate_app_1() {
        Object controller = Router.navigateApp(View.LOGIN);
        boolean isCorrectController = controller instanceof LoginController;

        assertEquals(View.LOGIN, Router.getAppView());
        assertNull(Router.getLayoutView());
        assertTrue(isCorrectController);
    }

    @Test
    public void navigate_app_2() {
        Object controller = Router.navigateApp(View.CHECK_IN);
        boolean isCorrectController = controller instanceof CheckInController;

        assertEquals(View.CHECK_IN, Router.getAppView());
        assertNull(Router.getLayoutView());
        assertTrue(isCorrectController);
    }

    @Test
    public void navigate_layout_1() {
        Object controller = Router.navigateLayout(View.SIGNUP);
        boolean isCorrectController = controller instanceof SignupController;

        assertEquals(View.LAYOUT, Router.getAppView());
        assertEquals(View.SIGNUP, Router.getLayoutView());
        assertTrue(isCorrectController);
    }

    @Test
    public void navigate_layout_2() {
        Object controller = Router.navigateLayout(View.QUIZ_LIBRARY);
        boolean isCorrectController = controller instanceof QuizLibraryController;

        assertEquals(View.LAYOUT, Router.getAppView());
        assertEquals(View.QUIZ_LIBRARY, Router.getLayoutView());
        assertTrue(isCorrectController);
    }

    @Test
    public void navigate_back_1() {
        Router.navigateLayout(View.HOME);
        Router.navigateApp(View.SETTINGS);

        assertEquals(View.SETTINGS, Router.getAppView());
        assertNull(Router.getLayoutView());

        Router.navigateBack();

        assertEquals(View.LAYOUT, Router.getAppView());
        assertEquals(View.HOME, Router.getLayoutView());
    }

    @Test
    public void navigate_back_2() {
        Router.navigateApp(View.LOGIN);
        Router.navigateApp(View.SIGNUP);

        assertEquals(View.SIGNUP, Router.getAppView());
        assertNull(Router.getLayoutView());

        Router.navigateBack();

        assertEquals(View.LOGIN, Router.getAppView());
        assertNull(Router.getLayoutView());
    }
}