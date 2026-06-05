package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.model.CheckIn;
import com.codependentvariables.aiandme.model.User;
import com.codependentvariables.aiandme.model.dao.ICheckInDAO;
import com.codependentvariables.aiandme.database.dao.SqliteCheckInDAO;
import com.codependentvariables.aiandme.modules.toast.Toast;
import com.codependentvariables.aiandme.modules.toast.ToastMessageType;
import com.codependentvariables.aiandme.modules.state.AppState;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Provides services for creating and retrieving user check-ins.
 */
public class CheckInService {
    private static CheckInService instance;
    private final ICheckInDAO checkInDAO;

    private static final AppState appState = AppState.getInstance();

    private CheckInService(ICheckInDAO checkInDAO) {
        this.checkInDAO = checkInDAO;
    }

    /**
     * Returns the CheckInService instance.
     *
     * @return the singleton CheckInService instance
     */
    public static CheckInService getInstance() {
        if (instance == null) {
            instance = new CheckInService(new SqliteCheckInDAO());
        }
        return instance;
    }

    /**
     * Creates a CheckInService instance for unit testing.
     *
     * @param dao DAO implementation to use
     * @return configured CheckInService instance
     */
    public static CheckInService createForTest(ICheckInDAO dao) {
        instance = new CheckInService(dao);
        return instance;
    }

    /**
     * Submits a check-in for the current user.
     *
     * @param checkIn the check-in to submit
     * @throws IllegalStateException if no user is logged in
     * @throws IllegalArgumentException if the check-in is invalid
     */
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

    /**
     * Validates the values of a check-in.
     *
     * @param checkIn the check-in to validate
     * @return true if the check-in is valid
     */
    private boolean isValid(CheckIn checkIn) {
        return checkIn != null
                && checkIn.getAiDependence() >= 0
                && checkIn.getAiDependence() <= 10
                && checkIn.getAiHappiness() >= 0
                && checkIn.getAiHappiness() <= 10
                && checkIn.getAiUse() >= 0
                && checkIn.getAiUse() <= 10;
    }

    /**
     * Retrieves a check-in by its identifier.
     *
     * @param id the check-in identifier
     * @return the matching check-in
     */
    public CheckIn getById(int id) {
        return checkInDAO.get(id);
    }

    /**
     * Retrieves all check-ins for a user.
     *
     * @param userId the user identifier
     * @return the user's check-ins
     */
    public List<CheckIn> getAllByUserId(int userId) {
        return checkInDAO.getAllByUserId(userId);
    }

    /**
     * Checks whether the current user has already completed a check-in today.
     *
     * @return true if a check-in exists for today
     */
    public boolean isExistingCheckInToday() {
        List<CheckIn> userCheckIns = checkInDAO.getAllByUserId(appState.getCurrentUser().getId());
        if (appState.getCurrentUser() != null && !userCheckIns.isEmpty()) {
            return ChronoUnit.DAYS.between(userCheckIns.getFirst().getCompletedAt().toLocalDate(), LocalDate.now()) < 1;
        }
        return false;
    }

    /**
     * Gets the 5 most recent check-ins for a user.
     *
     * @param userId user ID
     * @param limit maximum number of check-ins to return
     * @return list of recent check-ins
     */
    public List<CheckIn> getRecentCheckInsByUserId(int userId, int limit) {
        List<CheckIn> allCheckIns = getAllByUserId(userId);
        int startIndex = Math.max(0, allCheckIns.size() - limit);

        return allCheckIns.subList(startIndex, allCheckIns.size());
    }
}
