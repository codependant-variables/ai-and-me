package com.codependentvariables.aiandme.model.mock;

import com.codependentvariables.aiandme.model.*;
import com.codependentvariables.aiandme.model.dao.ICheckinDAO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MockCheckinDAO implements ICheckinDAO {
    public static final ArrayList<Checkin> checkins = new ArrayList<>();
    private static int autoIncrementId = 1;

    public MockCheckinDAO() {
        add(new Checkin(1, 6.4f, 3.2f, 5.0f, LocalDateTime.now().minusDays(3)));
        add(new Checkin(1, 3.0f, 7.5f, 1.0f, LocalDateTime.now().minusDays(2)));
        add(new Checkin(2, 9.6f, 5.0f, 7.5f, LocalDateTime.now().minusDays(1)));
    }

    @Override
    public void add(Checkin checkin) {
        checkin.setId(autoIncrementId++);
        checkins.add(checkin);
    }

    @Override
    public void delete(Checkin checkin) {
        checkins.remove(checkin);
    }

    @Override
    public Checkin get(int id) {
        for (Checkin checkin : checkins) {
            if (checkin.getId() == id) {
                return checkin;
            }
        }
        return null;
    }

    @Override
    public List<Checkin> getAll() {
        return new ArrayList<>(checkins);
    }

    @Override
    public List<Checkin> getByUserId(int userId) {
        return checkins.stream().filter(x -> x.getUserId() == userId).toList();
    }
}
