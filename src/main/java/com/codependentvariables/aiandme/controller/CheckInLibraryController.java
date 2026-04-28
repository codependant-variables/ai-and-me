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
    @FXML
    private TableView<Checkin> checkInsTable;

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

    private final ICheckinDAO checkinDAO = new SqliteCheckinDAO();

    @FXML
    public void initialize() {
        // Binding columns to check in model fields
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAiUse.setCellValueFactory(new PropertyValueFactory<>("aiUse"));
        colAiHappiness.setCellValueFactory(new PropertyValueFactory<>("aiHappiness"));
        colAiDependence.setCellValueFactory(new PropertyValueFactory<>("aiDependence"));
        colComment.setCellValueFactory(new PropertyValueFactory<>("comment"));
        colCompletedAt.setCellValueFactory(new PropertyValueFactory<>("completedAt"));

        loadCheckIns();
    }

    private void loadCheckIns() {
        User currentUser = AppState.getInstance().getCurrentUser();

        if (currentUser == null) {
            checkInsTable.setItems(FXCollections.observableArrayList());
            return;
        }

        List<Checkin> myCheckins = checkinDAO.getAllByUserId(currentUser.getId());

        if (myCheckins == null) {
            checkInsTable.setItems(FXCollections.observableArrayList());
            return;
        }

        checkInsTable.setItems(FXCollections.observableArrayList(myCheckins));
    }

    @FXML
    private void handleRefresh() {
        loadCheckIns();
    }

    @FXML
    private void navigateProfile() {
        Router.navigateLayout(View.PROFILE);
    }
}
