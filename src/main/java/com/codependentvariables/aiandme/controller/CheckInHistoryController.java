package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.model.CheckIn;
import com.codependentvariables.aiandme.model.User;
import com.codependentvariables.aiandme.navigation.Router;
import com.codependentvariables.aiandme.navigation.View;
import com.codependentvariables.aiandme.services.CheckInService;
import com.codependentvariables.aiandme.state.AppState;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.util.List;

public class CheckInHistoryController {
    private final AppState appState = AppState.getInstance();
    private final CheckInService checkInService;

    public CheckInHistoryController(CheckInService checkInService) {
        this.checkInService = checkInService;
    }

    public CheckInHistoryController() {
        this(CheckInService.getInstance());
    }

    // Table displaying CheckIn records
    @FXML
    public TableView<CheckIn> checkInsTable;

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

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAiUse.setCellValueFactory(new PropertyValueFactory<>("aiUse"));
        colAiHappiness.setCellValueFactory(new PropertyValueFactory<>("aiHappiness"));
        colAiDependence.setCellValueFactory(new PropertyValueFactory<>("aiDependence"));
        colComment.setCellValueFactory(new PropertyValueFactory<>("comment"));
        colCompletedAt.setCellValueFactory(new PropertyValueFactory<>("completedAt"));

        loadCheckIns();
    }

    /**
     * Loads CheckIns for current user into the table
     */
    public void loadCheckIns() {
        User currentUser = appState.getCurrentUser();

        if (currentUser == null) {
            Router.navigateLayout(View.HOME);
            return;
        }

        List<CheckIn> checkIns = checkInService.getAllByUserId(currentUser.getId());
        checkInsTable.setItems(FXCollections.observableArrayList(checkIns));
    }
}
