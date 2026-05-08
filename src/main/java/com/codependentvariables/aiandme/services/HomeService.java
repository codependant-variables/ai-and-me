package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.model.CheckIn;
import com.codependentvariables.aiandme.model.dao.ICheckInDAO;
import com.codependentvariables.aiandme.model.dao.SqliteCheckInDAO;
import com.codependentvariables.aiandme.state.AppState;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HomeService {
    private static HomeService instance;
    private final ICheckInDAO checkInDAO;

    private static final AppState appState = AppState.getInstance();

    private HomeService(ICheckInDAO checkInDAO) {
        this.checkInDAO = checkInDAO;
    }

    public static HomeService getInstance() {
        if (instance == null) {
            instance = new HomeService(new SqliteCheckInDAO());
        }
        return instance;
    }

    public String getLastCheckInDate() {
        if (checkInDAO.getAllByUserId(appState.getCurrentUser().getId()).isEmpty()){
            return "Complete your first check-in!";
        }
        LocalDateTime latestCheckInDateTime = checkInDAO.getAllByUserId(appState.getCurrentUser().getId()).getFirst().getCompletedAt();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return latestCheckInDateTime.format(formatter);
    }

    public String getCheckInStreak() {
        List<CheckIn> userCheckIns = checkInDAO.getAllByUserId(appState.getCurrentUser().getId());

        if (userCheckIns.size() >= 2 && Duration.between(userCheckIns.getFirst().getCompletedAt(), LocalDateTime.now()).toHours() < 24) {

            int streakStart = -1;

            for (int i = 0; i < userCheckIns.size() - 1; i++) {
                Duration duration = Duration.between(userCheckIns.get(i + 1).getCompletedAt(), userCheckIns.get(i).getCompletedAt());
                if (duration.toHours() >= 24) {
                    streakStart = i;
                    break;
                }

                if (i == userCheckIns.size() - 2) {
                    streakStart = userCheckIns.size() - 1;
                }
            }

            if (streakStart != -1) {
                return Integer.toString(streakStart + 1);
            }
            return "X";
        }

        if (userCheckIns.size() == 1 && Duration.between(userCheckIns.getFirst().getCompletedAt(), LocalDateTime.now()).toHours() < 24) {
            return "1";
        }

        return "0";
    }
}
