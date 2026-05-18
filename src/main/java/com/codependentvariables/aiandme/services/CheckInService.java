package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.model.CheckIn;
import com.codependentvariables.aiandme.model.User;
import com.codependentvariables.aiandme.model.dao.ICheckInDAO;
import com.codependentvariables.aiandme.model.dao.SqliteCheckInDAO;
import com.codependentvariables.aiandme.modules.Toast;
import com.codependentvariables.aiandme.modules.ToastMessageType;
import com.codependentvariables.aiandme.state.AppState;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CheckInService {
    private static CheckInService instance;
    private final ICheckInDAO checkInDAO;

    private static final AppState appState = AppState.getInstance();

    private CheckInService(ICheckInDAO checkInDAO) {
        this.checkInDAO = checkInDAO;
    }

    public static CheckInService getInstance() {
        if (instance == null) {
            instance = new CheckInService(new SqliteCheckInDAO());
        }
        return instance;
    }

    public static CheckInService createForTest(ICheckInDAO dao) {
        instance = new CheckInService(dao);
        return instance;
    }

    public void submitCheckIn(CheckIn checkIn) {
        User currentUser = appState.getCurrentUser();

        if (currentUser == null) {
            Toast.addMessage("Check-in Unable to be Submitted","User must be logged in to submit a check-in.", ToastMessageType.ERROR);
            throw new IllegalStateException("User must be logged in to submit a check-in.");
        }

        if (!isValid(checkIn)) {
            Toast.addMessage(
                    "Check-in Incomplete",
                    "Please fill out all fields before submitting check in",
                    ToastMessageType.ERROR
            );
            throw new IllegalArgumentException("Check-in is incomplete.");
        }

        checkIn.setUserId(currentUser.getId());
        checkInDAO.add(checkIn);

        Toast.addMessage(
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

    public CheckIn getById(int id) {
        return checkInDAO.get(id);
    }

    public List<CheckIn> getAllByUserId(int userId) {
        return checkInDAO.getAllByUserId(userId);
    }

    public boolean isExistingCheckInToday() {
        List<CheckIn> userCheckIns = checkInDAO.getAllByUserId(appState.getCurrentUser().getId());
        if (appState.getCurrentUser() != null && !userCheckIns.isEmpty()) {
            return ChronoUnit.DAYS.between(userCheckIns.getFirst().getCompletedAt().toLocalDate(), LocalDate.now()) < 1;
        }
        return false;
    }
}
