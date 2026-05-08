package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.model.CheckIn;
import com.codependentvariables.aiandme.model.dao.ICheckInDAO;
import com.codependentvariables.aiandme.model.dao.SqliteCheckInDAO;
import com.codependentvariables.aiandme.state.AppState;

import java.time.Duration;
import java.time.LocalDateTime;
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

    public String getCheckInStreak() {
        List<CheckIn> userCheckIns = checkInDAO.getAllByUserId(appState.getCurrentUser().getId());

        if (userCheckIns.size() >= 2 && Duration.between(userCheckIns.getFirst().getCompletedAt(), LocalDateTime.now()).toHours() < 24) {
            System.out.println("2 or more checkins by user with last completed <24hrs ago");

            int streakStart = -1;

            for (int i = 0; i < userCheckIns.size() - 1; i++) {
                Duration duration = Duration.between(userCheckIns.get(i + 1).getCompletedAt(), userCheckIns.get(i).getCompletedAt());
                System.out.println(duration.toHours());
                if (duration.toHours() >= 24) {
                    streakStart = i;
                    System.out.println("found most recent streak start");
                    System.out.println(streakStart);
                    break;
                }

                if (i == userCheckIns.size() - 2) {
                    streakStart = userCheckIns.size() - 1;
                    System.out.println("user never broke streak");
                    System.out.println(streakStart);
                }
            }

            if (streakStart != -1) {
                System.out.println("returning streak count:");
                return Integer.toString(streakStart + 1);
            }

            System.out.println("streakStart is null. something is broken.");
            return "X";
        }

        System.out.println(Duration.between(userCheckIns.getFirst().getCompletedAt(), LocalDateTime.now()).toHours());
        if (userCheckIns.size() == 1 && Duration.between(userCheckIns.getFirst().getCompletedAt(), LocalDateTime.now()).toHours() < 24) {
            System.out.println("only 1 user checkin, but made in last 24hrs");
            return "1";
        }

        System.out.println("final case (no checkins or no streak)");
        return "0";
    }
}
