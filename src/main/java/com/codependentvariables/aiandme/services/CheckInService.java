package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.model.CheckIn;
import com.codependentvariables.aiandme.model.User;
import com.codependentvariables.aiandme.model.dao.ICheckInDAO;
import com.codependentvariables.aiandme.model.dao.SqliteCheckInDAO;
import com.codependentvariables.aiandme.navigation.Toast;
import com.codependentvariables.aiandme.navigation.ToastMessageType;
import com.codependentvariables.aiandme.state.AppState;

public class CheckInService {
    private static CheckInService instance;
    private final ICheckInDAO checkInDAO;

    private static final AppState appState = AppState.getInstance();

    private CheckInService() {
        this(new SqliteCheckInDAO());
    }

    // Package-private constructor for unit tests
    CheckInService(ICheckInDAO checkInDAO) {
        this.checkInDAO = checkInDAO;
    }

    public static CheckInService getInstance() {
        if (instance == null) {
            instance = new CheckInService();
        }
        return instance;
    }

    public void submitCheckIn(CheckIn checkIn) {
        User currentUser = appState.getCurrentUser();

        if (currentUser == null) {
            safeToast("Check-in Unable to be Submitted","User must be logged in to submit a check-in.", ToastMessageType.ERROR);
            throw new IllegalStateException("User must be logged in to submit a check-in.");
        }

        if (!isValid(checkIn)) {
            safeToast(
                    "Check-in Incomplete",
                    "Please fill out all fields before submitting check in",
                    ToastMessageType.ERROR
            );
            throw new IllegalArgumentException("Check-in is incomplete.");
        }

        checkIn.setUserId(currentUser.getId());

        checkInDAO.add(checkIn);

        safeToast(
                "Check-in submitted",
                "Thanks for completing your check-in!",
                ToastMessageType.INFORMATION
        );
    }

    private boolean isValid(CheckIn checkIn) {
        return checkIn != null
                && checkIn.getAiDependence() >= 0
                && checkIn.getAiDependence() <= 100
                && checkIn.getAiHappiness() >= 0
                && checkIn.getAiHappiness() <= 100
                && checkIn.getAiUse() >= 0
                && checkIn.getAiUse() <= 100;
    }

    private void safeToast(String title, String msg, ToastMessageType type) {
        try {
            Toast.addMessage(title, msg, type);
        } catch (Throwable ignored) {
            // Ignore in unit tests as JavaFX is not running
        }
    }
}