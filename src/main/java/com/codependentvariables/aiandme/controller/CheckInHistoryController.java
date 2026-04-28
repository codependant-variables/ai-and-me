package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.model.CheckIn;
import com.codependentvariables.aiandme.model.User;
import com.codependentvariables.aiandme.model.dao.ICheckInDAO;
import com.codependentvariables.aiandme.model.dao.SqliteCheckInDAO;
import com.codependentvariables.aiandme.state.AppState;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.util.List;

public class CheckInHistoryController {
    // Table displaying CheckIn records
    @FXML
    private TableView<CheckIn> checkInsTable;

    // Table columns mapped to CheckIn fields
    @FXML
    private TableColumn<CheckIn, Integer> colId;

    @FXML
    private TableColumn<CheckIn, Float> colAiUse;

    @FXML
    private TableColumn<CheckIn, Float> colAiHappiness;

    @FXML
    private TableColumn<CheckIn, Float> colAiDependence;

    @FXML
    private TableColumn<CheckIn, String> colComment;

    @FXML
    private TableColumn<CheckIn, LocalDateTime> colCompletedAt;

    // DAO for retrieving CheckIn data from database
    private final ICheckInDAO checkInDAO = new SqliteCheckInDAO();

    @FXML
    public void initialize() {
        // Binding columns to CheckIn model fields
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
     * Loads CheckIns for current user into the table
     */
    private void loadCheckIns() {
        User currentUser = AppState.getInstance().getCurrentUser();

        // Clears table if no user is logged in
        if (currentUser == null) {
            checkInsTable.setItems(FXCollections.observableArrayList());
            return;
        }

        // Retrieves CheckIns for current user
        List<CheckIn> myCheckIns = checkInDAO.getAllByUserId(currentUser.getId());

        // Clears table if no data found
        if (myCheckIns == null) {
            checkInsTable.setItems(FXCollections.observableArrayList());
            return;
        }

        checkInsTable.setItems(FXCollections.observableArrayList(myCheckIns));
    }
}
