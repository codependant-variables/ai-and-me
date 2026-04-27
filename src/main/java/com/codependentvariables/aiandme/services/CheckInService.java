package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.model.Checkin;
import com.codependentvariables.aiandme.model.User;
import com.codependentvariables.aiandme.model.dao.ICheckinDAO;
import com.codependentvariables.aiandme.model.dao.SqliteCheckinDAO;
import com.codependentvariables.aiandme.navigation.Toast;
import com.codependentvariables.aiandme.navigation.ToastMessageType;
import com.codependentvariables.aiandme.state.AppState;

public class CheckInService {
    private static CheckInService instance;
    private final ICheckinDAO checkInDAO;

    private static final AppState appState = AppState.getInstance();

    private CheckInService() {
        this(new SqliteCheckinDAO());
    }

    // Package-private constructor for unit tests
    CheckInService(ICheckinDAO checkInDAO) {
        this.checkInDAO = checkInDAO;
    }

    public static CheckInService getInstance() {
        if (instance == null) {
            instance = new CheckInService();
        }
        return instance;
    }

    public void submitCheckIn(Checkin checkin) {
        User currentUser = appState.getCurrentUser();

        if (currentUser == null) {
            safeToast("Check-in Unable to be Submitted","User must be logged in to submit a check-in.", ToastMessageType.ERROR);
            throw new IllegalStateException("User must be logged in to submit a check-in.");
        }

        if (!isValid(checkin)) {
            safeToast(
                    "Check-in Incomplete",
                    "Please fill out all fields before submitting check in",
                    ToastMessageType.ERROR
            );
            throw new IllegalArgumentException("Check-in is incomplete.");
        }

        checkin.setUserId(currentUser.getId());

        checkInDAO.add(checkin);

        safeToast(
                "Check-in submitted",
                "Thanks for completing your check-in!",
                ToastMessageType.INFORMATION
        );
    }

    private boolean isValid(Checkin checkin) {
        return checkin != null
                && checkin.getAiDependence() >= 0
                && checkin.getAiDependence() <= 10
                && checkin.getAiHappiness() >= 0
                && checkin.getAiHappiness() <= 10
                && checkin.getAiUse() >= 0
                && checkin.getAiUse() <= 10;
    }

    private void safeToast(String title, String msg, ToastMessageType type) {
        try {
            Toast.addMessage(title, msg, type);
        } catch (Throwable ignored) {
            // Ignore in unit tests as JavaFX is not running
        }
    }
}