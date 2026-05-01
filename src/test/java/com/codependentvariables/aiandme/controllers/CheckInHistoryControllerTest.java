package com.codependentvariables.aiandme.controllers;

import com.codependentvariables.aiandme.controller.CheckInHistoryController;
import com.codependentvariables.aiandme.model.CheckIn;
import com.codependentvariables.aiandme.model.dao.ICheckInDAO;
import com.codependentvariables.aiandme.state.AppState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Goal: ensure the controller can be instantiated with a mock DAO
public class CheckInHistoryControllerTest {

    @BeforeEach
    public void resetAppState() {
        AppState.getInstance().setCurrentUser(null);
    }

    private ICheckInDAO createMockDao() {
        return new ICheckInDAO() {
            @Override public void add(CheckIn checkIn) {}
            @Override public void delete(CheckIn checkIn) {}
            @Override public List<CheckIn> getAll() { return List.of(); }
            @Override public CheckIn get(int id) { return null; }
            @Override public List<CheckIn> getAllByUserId(int userId) { return List.of(); }
        };
    }

    // Checks that the controller can be created with the mock DAO
    @Test
    public void controllerCreationTest() {
        CheckInHistoryController controller = new CheckInHistoryController(createMockDao());

        assertNotNull(controller);
    }
}