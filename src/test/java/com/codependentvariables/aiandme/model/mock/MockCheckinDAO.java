package com.codependentvariables.aiandme.model.mock;

import com.codependentvariables.aiandme.model.Checkin;
import com.codependentvariables.aiandme.model.dao.ICheckinDAO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MockCheckinDAO implements ICheckinDAO {
    private final List<Checkin> checkins = new ArrayList<>();

    public MockCheckinDAO() {
    }
    @Override
    public void add(Checkin checkin) {
        checkins.add(checkin);
    }

    @Override
    public List<Checkin> getAll() {
        return checkins;
    }

    @Override
    public Checkin get(int id) {
        return checkins.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void delete(Checkin checkin) {
        checkins.remove(checkin);
    }

    @Override
    public List<Checkin> getByUserId(int userId) {
        return checkins.stream()
                .filter(c -> c.getUserId() == userId)
                .toList();
    }
}