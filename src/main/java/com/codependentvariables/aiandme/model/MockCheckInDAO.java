package com.codependentvariables.aiandme.model;

import java.util.ArrayList;
import java.util.List;

public class MockCheckInDAO implements ICheckInDAO {
    /**
     * Static list of check-ins acting as mock DB.
     */
    public static final ArrayList<CheckIn> checkIns = new ArrayList<>();
    private static int autoIncrementId = 1;

    public MockCheckInDAO() {
        addCheckIn(new CheckIn(autoIncrementId+10, 64, 32, 50));
        addCheckIn(new CheckIn(autoIncrementId+10, 30, 75, 10));
        addCheckIn(new CheckIn(autoIncrementId+11, 96, 50, 75));
    }

    @Override
    public void addCheckIn(CheckIn checkIn) {
        checkIn.setId(autoIncrementId++);
        checkIns.add(checkIn);
    }

    @Override
    public void deleteCheckIn(CheckIn checkIn) { checkIns.remove(checkIn); }

    @Override
    public CheckIn get(int id) {
        for (CheckIn checkIn : checkIns) {
            if (checkIn.getId() == id) {
                return checkIn;
            }
        }
        return null;
    }

    @Override
    public List<CheckIn> getAllCheckIns() { return new ArrayList<>(checkIns); }

    @Override
    public List<CheckIn> getAllCheckInsByUserId(int userId) {
        ArrayList<CheckIn> checkInsByUser = new ArrayList<>();
        for (CheckIn checkIn : checkIns) {
            if (checkIn.getUserId() == userId) {
                checkInsByUser.add(checkIn);
            }
        }
        if (checkInsByUser.isEmpty()) { return null; }
        return checkInsByUser;
    }
}
