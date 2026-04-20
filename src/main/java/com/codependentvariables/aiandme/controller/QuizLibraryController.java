package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.model.*;
import com.codependentvariables.aiandme.model.dao.*;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.List;
import java.util.Optional;

public class QuizLibraryController {

    private final ICategoryDAO categoryDAO = new SqliteCategoryDAO();
    private final IQuizTemplateDAO templateDAO = new SqliteQuizTemplateDAO();

    @FXML
    private FlowPane categoryContainer;

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnModify;

    @FXML
    private Button btnDelete;

    /** Currently selected category card, null when nothing is selected. */
    private Category selectedCategory;

    /** The VBox card node that is currently highlighted. */
    private VBox selectedCard;

    @FXML
    public void initialize() {
        // Create is always available; Modify/Delete need a category card selected
        btnCreate.setDisable(false);
        btnModify.setDisable(true);
        btnDelete.setDisable(true);

        refreshCategories();
    }


    // Render related methods

    /**
     * Clears and re-renders every category card inside the FlowPane.
     */
    private void refreshCategories() {
        categoryContainer.getChildren().clear();
        selectedCategory = null;
        selectedCard = null;
        updateActionButtons();

        List<Category> categories = categoryDAO.getAll();
        for (Category category : categories) {
            categoryContainer.getChildren().add(buildCategoryCard(category));
        }
    }

    /**
     * Builds a styled VBox card for a single category.
     * Shows the category name and how many templates it contains.
     */
    private VBox buildCategoryCard(Category category) {
        int templateCount = templateDAO.getByCategoryId(category.getId()).size();

        Label nameLabel = new Label(category.getName());
        nameLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        nameLabel.setTextFill(Color.web("#333333"));

        Label countLabel = new Label(templateCount + " template" + (templateCount == 1 ? "" : "s"));
        countLabel.setFont(Font.font("System", 11));
        countLabel.setTextFill(Color.web("#777777"));

        VBox card = new VBox(6, nameLabel, countLabel);
        card.setPrefSize(160, 80);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(12));
        card.setStyle(cardStyle(false));

        card.setOnMouseClicked(e -> selectCard(card, category));

        return card;
    }

    private void selectCard(VBox card, Category category) {
        // Deselect previous card
        if (selectedCard != null) {
            selectedCard.setStyle(cardStyle(false));
        }

        selectedCategory = category;
        selectedCard = card;
        card.setStyle(cardStyle(true));
        updateActionButtons();
    }

    private void updateActionButtons() {
        boolean hasSelection = selectedCategory != null;
        btnCreate.setDisable(false); // always enabled
        btnModify.setDisable(!hasSelection);
        btnDelete.setDisable(!hasSelection);
    }

    private String cardStyle(boolean selected) {
        String border = selected
                ? "-fx-border-color: #1976d2; -fx-border-width: 2;"
                : "-fx-border-color: #cccccc; -fx-border-width: 1;";
        String bg = selected ? "-fx-background-color: #e3f2fd;" : "-fx-background-color: #ffffff;";
        return bg + border + "-fx-background-radius: 8; -fx-border-radius: 8; -fx-cursor: hand;";
    }

    // Action Handlers

    /**
     * Opens a pop-up dialog to create a new template.
     * The user can select an existing category from a drop-down or tick
     * "New Category?" to type a new category name which is persisted
     * and immediately reflected in the library.
     */
    @FXML
    private void handleCreate() {
        List<Category> categories = categoryDAO.getAll();

        // ---- Build dialog content ----
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Create Template");
        dialog.setHeaderText("Create a new quiz template");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Template name
        Label nameLabel = new Label("Template name:");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter template name");

        // Category drop-down
        Label categoryLabel = new Label("Category:");
        ComboBox<Category> categoryCombo = new ComboBox<>();
        categoryCombo.getItems().setAll(categories);
        categoryCombo.setPromptText("Select a category");
        categoryCombo.setCellFactory(lv -> new ListCell<>() {
            @Override protected void updateItem(Category c, boolean empty) {
                super.updateItem(c, empty);
                setText(empty || c == null ? null : c.getName());
            }
        });
        categoryCombo.setButtonCell(new ListCell<>() {
            @Override protected void updateItem(Category c, boolean empty) {
                super.updateItem(c, empty);
                setText(empty || c == null ? null : c.getName());
            }
        });
        // Pre-select the currently highlighted category if any
        if (selectedCategory != null) categoryCombo.setValue(selectedCategory);
        categoryCombo.setMaxWidth(Double.MAX_VALUE);

        // New Category checkbox + text field
        CheckBox newCategoryCheck = new CheckBox("New Category?");
        TextField newCategoryField = new TextField();
        newCategoryField.setPromptText("Enter new category name");
        newCategoryField.setVisible(false);
        newCategoryField.setManaged(false);

        newCategoryCheck.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            newCategoryField.setVisible(isNowSelected);
            newCategoryField.setManaged(isNowSelected);
            categoryCombo.setDisable(isNowSelected);
            if (isNowSelected) {
                categoryCombo.setValue(null);
            }
        });

        // Validate OK button
        Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDisable(true);
        Runnable validate = () -> {
            boolean nameOk = !nameField.getText().trim().isEmpty();
            boolean categoryOk = newCategoryCheck.isSelected()
                    ? !newCategoryField.getText().trim().isEmpty()
                    : categoryCombo.getValue() != null;
            okButton.setDisable(!(nameOk && categoryOk));
        };
        nameField.textProperty().addListener((o, ov, nv) -> validate.run());
        categoryCombo.valueProperty().addListener((o, ov, nv) -> validate.run());
        newCategoryField.textProperty().addListener((o, ov, nv) -> validate.run());
        newCategoryCheck.selectedProperty().addListener((o, ov, nv) -> validate.run());

        // Layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15));
        ColumnConstraints col1 = new ColumnConstraints(120);
        ColumnConstraints col2 = new ColumnConstraints(220);
        grid.getColumnConstraints().addAll(col1, col2);

        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(categoryLabel, 0, 1);
        grid.add(categoryCombo, 1, 1);
        grid.add(newCategoryCheck, 1, 2);
        grid.add(newCategoryField, 1, 3);

        dialog.getDialogPane().setContent(grid);
        nameField.requestFocus();

        // Handle result
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isEmpty() || result.get() != ButtonType.OK) return;

        String templateName = nameField.getText().trim();
        Category targetCategory;

        if (newCategoryCheck.isSelected()) {
            String newCatName = newCategoryField.getText().trim();
            Category newCat = new Category(newCatName);
            categoryDAO.add(newCat);
            // Fetch the just-added category so we have its DB-assigned id
            targetCategory = categoryDAO.getAll().stream()
                    .filter(c -> c.getName().equalsIgnoreCase(newCatName))
                    .findFirst().orElse(newCat);
        } else {
            targetCategory = categoryCombo.getValue();
        }

        QuizTemplate template = new QuizTemplate(templateName, targetCategory.getId(), "draft");
        templateDAO.add(template);
        refreshCategories();
        showInfo("Template \"" + templateName + "\" created in category \"" + targetCategory.getName() + "\".");
    }

    /**
     * Renames an existing template that belongs to the selected category.
     */
    @FXML
    private void handleModify() {
        if (selectedCategory == null) return;

        List<QuizTemplate> templates = templateDAO.getByCategoryId(selectedCategory.getId());
        if (templates.isEmpty()) {
            showWarning("No templates in " + selectedCategory.getName() + " to modify.");
            return;
        }

        // Let the user pick which template to modify
        ChoiceDialog<QuizTemplate> picker = new ChoiceDialog<>(templates.getFirst(), templates);
        picker.setTitle("Modify Template");
        picker.setHeaderText("Select a template to modify in " + selectedCategory.getName());
        picker.setContentText("Template:");
        // Display template names in the picker
        picker.getItems().setAll(templates);
        picker.getDialogPane().lookupButton(ButtonType.OK);

        // Override toString for display inside ChoiceDialog
        Optional<QuizTemplate> pickerResult = picker.showAndWait();
        if (pickerResult.isEmpty()) return;

        QuizTemplate chosen = pickerResult.get();

        TextInputDialog nameDialog = new TextInputDialog(chosen.getName());
        nameDialog.setTitle("Rename Template");
        nameDialog.setHeaderText("Rename " + chosen.getName());
        nameDialog.setContentText("New name:");

        Optional<String> nameResult = nameDialog.showAndWait();
        nameResult.map(String::trim).filter(s -> !s.isEmpty()).ifPresent(newName -> {
            chosen.setName(newName);
            templateDAO.update(chosen);
            refreshCategories();
            showInfo("Template renamed to " + newName + ".");
        });
    }

    /**
     * Deletes a template that belongs to the selected category.
     */
    @FXML
    private void handleDelete() {
        if (selectedCategory == null) return;

        List<QuizTemplate> templates = templateDAO.getByCategoryId(selectedCategory.getId());
        if (templates.isEmpty()) {
            showWarning("No templates in " + selectedCategory.getName() + " to delete.");
            return;
        }

        ChoiceDialog<QuizTemplate> picker = new ChoiceDialog<>(templates.getFirst(), templates);
        picker.setTitle("Delete Template");
        picker.setHeaderText("Select a template to delete from " + selectedCategory.getName());
        picker.setContentText("Template:");

        Optional<QuizTemplate> pickerResult = picker.showAndWait();
        if (pickerResult.isEmpty()) return;

        QuizTemplate chosen = pickerResult.get();

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Delete " + chosen.getName() + "? This cannot be undone.",
                ButtonType.YES, ButtonType.CANCEL);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText(null);

        confirm.showAndWait().filter(b -> b == ButtonType.YES).ifPresent(b -> {
            templateDAO.delete(chosen);
            refreshCategories();
            showInfo("Template " + chosen.getName() + " deleted.");
        });
    }

    // Helpers methods

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
