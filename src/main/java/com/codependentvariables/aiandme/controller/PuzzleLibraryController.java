package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.model.Category;
import com.codependentvariables.aiandme.model.User;
import com.codependentvariables.aiandme.services.CategoryService;
import com.codependentvariables.aiandme.services.QuizTemplateService;
import com.codependentvariables.aiandme.state.AppState;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.Optional;

/**
 * Controller for the Puzzle Library screen.
 * Reuses quiz-library.fxml which is the same layout as the Quiz Library.
 * Puzzle is a type of quiz and will be added here, separating the logic.
 */
public class PuzzleLibraryController extends QuizController {

    @FXML
    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    protected String getPageTitle() {
        return "Puzzle Library";
    }

    @Override
    protected boolean showsTemplateImage() {
        return true;
    }

    /** Only show templates that ARE puzzles. */
    @Override
    protected Boolean isPuzzleFilter() {
        return true;
    }

    @Override
    public void initialise() {
    }


    /**
     * Overrides the base create dialog to add an "Is Puzzle?" checkbox.
     * The checkbox value is captured but not yet persisted anywhere.
     */
    @FXML
    @Override
    protected void handleCreate() {
        CategoryService categoryService = CategoryService.getInstance();
        QuizTemplateService templateService = QuizTemplateService.getInstance();
        List<Category> categories = categoryService.getVisibleCategories();

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Create Template");
        dialog.setHeaderText("Create a new puzzle template");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Label nameLabel = new Label("Template name:");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter template name");

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
        categoryCombo.setMaxWidth(Double.MAX_VALUE);

        CheckBox newCategoryCheck = new CheckBox("New Category?");
        TextField newCategoryField = new TextField();
        newCategoryField.setPromptText("Enter new category name");
        newCategoryField.setVisible(false);
        newCategoryField.setManaged(false);

        newCategoryCheck.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            newCategoryField.setVisible(isNowSelected);
            newCategoryField.setManaged(isNowSelected);
            categoryCombo.setDisable(isNowSelected);
            if (isNowSelected) categoryCombo.setValue(null);
        });


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

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isEmpty() || result.get() != ButtonType.OK) return;

        String templateName = nameField.getText().trim();
        boolean isPuzzle = true; // Always true on the Puzzle Library page
        Category targetCategory;

        if (newCategoryCheck.isSelected()) {
            String newCatName = newCategoryField.getText().trim();
            Category newCat = new Category(newCatName);
            categoryService.submitCategory(newCat);
            targetCategory = categoryService.getVisibleCategories().stream()
                    .filter(c -> c.getName().equalsIgnoreCase(newCatName))
                    .findFirst().orElse(newCat);
        } else {
            targetCategory = categoryCombo.getValue();
        }

        try {
            User currentUser = AppState.getInstance().getCurrentUser();
            int userId = (currentUser != null) ? currentUser.getId() : 0;
            templateService.createTemplate(templateName, targetCategory.getId(), userId, isPuzzle);
            showInfo("Template \"" + templateName + "\" created in category \"" + targetCategory.getName() + "\"" +
                    (isPuzzle ? " (marked as Puzzle)." : "."));
        } catch (IllegalArgumentException e) {
            showWarning(e.getMessage());
        }
    }
}
