package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.model.Checkin;
import com.codependentvariables.aiandme.model.User;
import com.codependentvariables.aiandme.model.dao.ICheckinDAO;
import com.codependentvariables.aiandme.model.dao.SqliteCheckinDAO;
import com.codependentvariables.aiandme.navigation.Router;
import com.codependentvariables.aiandme.navigation.View;
import com.codependentvariables.aiandme.state.AppState;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.util.List;

public class CheckInLibraryController {
    // Table displaying checkin records
    @FXML
    private TableView<Checkin> checkInsTable;

    // Table columns mapped to Checkin fields
    @FXML
    private TableColumn<Checkin, Integer> colId;

    @FXML
    private TableColumn<Checkin, Float> colAiUse;

    @FXML
    private TableColumn<Checkin, Float> colAiHappiness;

    @FXML
    private TableColumn<Checkin, Float> colAiDependence;

    @FXML
    private TableColumn<Checkin, String> colComment;

    @FXML
    private TableColumn<Checkin, LocalDateTime> colCompletedAt;

    // DAO for retrieving checkin data from database
    private final ICheckinDAO checkinDAO = new SqliteCheckinDAO();

    @FXML
    public void initialize() {
        // Binding columns to checkin model fields
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAiUse.setCellValueFactory(new PropertyValueFactory<>("aiUse"));
        colAiHappiness.setCellValueFactory(new PropertyValueFactory<>("aiHappiness"));
        colAiDependence.setCellValueFactory(new PropertyValueFactory<>("aiDependence"));
        colComment.setCellValueFactory(new PropertyValueFactory<>("comment"));
        colCompletedAt.setCellValueFactory(new PropertyValueFactory<>("completedAt"));

        // loads initial data
        loadCheckIns();
    }

    /**
     * Loads checkins for current user into the table
     */
    private void loadCheckIns() {
        User currentUser = AppState.getInstance().getCurrentUser();

        // Clears table if no user is logged in
        if (currentUser == null) {
            checkInsTable.setItems(FXCollections.observableArrayList());
            return;
        }

        // Retrieves checkins for current user
        List<Checkin> myCheckins = checkinDAO.getAllByUserId(currentUser.getId());

        // Clears table if no data found
        if (myCheckins == null) {
            checkInsTable.setItems(FXCollections.observableArrayList());
            return;
        }

        checkInsTable.setItems(FXCollections.observableArrayList(myCheckins));
    }

    // Handler for refresh button
    @FXML
    private void handleRefresh() {
        loadCheckIns();
    }
}
