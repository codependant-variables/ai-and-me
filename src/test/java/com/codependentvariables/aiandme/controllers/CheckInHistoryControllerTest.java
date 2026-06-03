package com.codependentvariables.aiandme.controllers;

import com.codependentvariables.aiandme.JavaFXControllerLoader;
import com.codependentvariables.aiandme.JavaFXTest;
import com.codependentvariables.aiandme.controller.CheckInHistoryController;
import com.codependentvariables.aiandme.model.CheckIn;
import com.codependentvariables.aiandme.model.User;
import com.codependentvariables.aiandme.model.mock.*;
import com.codependentvariables.aiandme.modules.router.Router;
import com.codependentvariables.aiandme.modules.router.View;
import com.codependentvariables.aiandme.services.CheckInService;
import com.codependentvariables.aiandme.services.UserService;
import com.codependentvariables.aiandme.modules.state.AppState;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckInHistoryControllerTest extends JavaFXTest {
    private static AppState appState;
    private static UserService userService;
    private static CheckInService checkInService;
    private static CheckInHistoryController controller;

    @BeforeAll
    public static void setup() {
        appState = AppState.getInstance();
        userService = UserService.createForTest(new MockCheckInDAO(), new MockQuizAttemptDAO(), new MockQuizAttemptQuestionDAO(), new MockQuizAttemptAnswerDAO(), new MockQuizTemplateDAO(), new MockQuizTemplateQuestionDAO(), new MockQuizTemplateAnswerDAO(), new MockUserDAO(), new MockUserPreferredCategoryDAO());
        checkInService = CheckInService.createForTest(new MockCheckInDAO());
    }

    @BeforeEach
    public void setupEach() {
        appState.setCurrentUser(null);
        controller = JavaFXControllerLoader.load(View.CHECK_IN_HISTORY, type -> new CheckInHistoryController(checkInService));
    }

    @Test
    public void load_check_in_history_as_guest() {
        controller.loadCheckIns();
        assertEquals(0, controller.checkInsTable.getItems().size());
        assertEquals(View.HOME, Router.getLayoutView());
    }

    @Test
    public void load_check_in_history_as_user() {
        User user = new User("Test", "test@test.test", "mypassword", "mysalt");
        userService.addUser(user);
        appState.setCurrentUser(user);

        CheckIn checkin = new CheckIn(user.getId(), 1.0f, 2.0f, 3.0f, "my comment", LocalDateTime.now());
        checkInService.submitCheckIn(checkin);

        controller.loadCheckIns();
        assertEquals(1, controller.checkInsTable.getItems().size());
    }
}