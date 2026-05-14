package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.model.*;
import com.codependentvariables.aiandme.model.dao.IUserDAO;
import com.codependentvariables.aiandme.model.dao.SqliteUserDAO;
import com.codependentvariables.aiandme.navigation.Router;
import com.codependentvariables.aiandme.navigation.View;
import com.codependentvariables.aiandme.services.CategoryService;
import com.codependentvariables.aiandme.services.QuizAttemptService;
import com.codependentvariables.aiandme.services.QuizTemplateService;
import com.codependentvariables.aiandme.state.AppState;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class QuizController {

    private final CategoryService categoryService = CategoryService.getInstance();
    private final IUserDAO userDAO = new SqliteUserDAO();
    private final QuizTemplateService templateService = QuizTemplateService.getInstance();
    private final QuizAttemptService attemptService = QuizAttemptService.getInstance();

    @FXML private Label pageTitle;
    @FXML private FlowPane categoryContainer;
    @FXML private Button btnCreate;
    @FXML private Button btnModify;
    @FXML private Button btnDelete;
    @FXML private Button btnEditQuestions;
    @FXML private Button btnAttemptQuiz;

    // Currently selected template and its UI card
    private QuizTemplate selectedTemplate;
    private HBox selectedCard;

    /**
     * Initializes UI state and loads templates.
     */
    @FXML
    public void initialize() {
        pageTitle.setText(getPageTitle());
        btnCreate.setDisable(false);
        btnModify.setDisable(true);
        btnDelete.setDisable(true);
        btnEditQuestions.setDisable(true);
        btnAttemptQuiz.setDisable(true);
        refreshCategories();
    }

    protected abstract String getPageTitle();

    /**
     * Reloads all quiz templates and rebuilds UI cards.
     */
    private void refreshCategories() {
        categoryContainer.getChildren().clear();
        selectedTemplate = null;
        selectedCard = null;
        updateActionButtons();

        List<Category> categories = categoryService.getVisibleCategories();

        List<QuizTemplate> templates = templateService.getAllTemplates();
        for (QuizTemplate template : templates) {
            String categoryName = "Unknown";
            for (Category c : categories) {
                if (c.getId() == template.getCategoryId()) {
                    categoryName = c.getName();
                    break;
                }
            }
            categoryContainer.getChildren().add(buildTemplateCard(template, categoryName));
        }
    }

    /**
     * Builds a clickable UI card representing a quiz template.
     * Shows the template name and which category it belongs to.
     */
    private HBox buildTemplateCard(QuizTemplate template, String categoryName) {
        Label nameLabel = new Label(template.getName());
        nameLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        nameLabel.setWrapText(true);

        Label categoryLabel = new Label("Category: " + categoryName);
        categoryLabel.setFont(Font.font("System", 13));
        categoryLabel.setWrapText(true);

        String creatorName;
        if (template.getUserId() == 0) {
            creatorName = "Guest";
        } else {
            User creator = userDAO.get(template.getUserId());
            creatorName = (creator != null) ? creator.getName() : "Unknown";
        }

        String getCatagorieIcon;
        if ("Pattern Recognition".equalsIgnoreCase(categoryName)) {
            getCatagorieIcon = "/com/codependentvariables/aiandme/Images/CatagoryPatternRecognition.png"; // pastal green
        } else if ("Critical Thinking".equalsIgnoreCase(categoryName)) {
            getCatagorieIcon = "/com/codependentvariables/aiandme/Images/CatagoryCriticalThinking.png"; // pastal green
        } else if ("Mental Maths".equalsIgnoreCase(categoryName)) {
            getCatagorieIcon = "/com/codependentvariables/aiandme/Images/CatagoryComprehension.png"; //red
        } else if ("Arithmetic".equalsIgnoreCase(categoryName)) {
            getCatagorieIcon = "/com/codependentvariables/aiandme/Images/CatagoryArithmetic.png"; // primary purple
        } else {
            getCatagorieIcon = "/com/codependentvariables/aiandme/Images/CatagoryOther.png"; //secondary pink
        }

        String cardColour = getCategory(categoryName);

        Label creatorLabel = new Label("Created by: " + creatorName);
        creatorLabel.setFont(Font.font("System", 13));
        creatorLabel.setWrapText(true);

        Image catagorieIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream(getCatagorieIcon)));
        ImageView catagorieIconView = new ImageView(catagorieIcon);
        catagorieIconView.setFitWidth(120);
        catagorieIconView.setPreserveRatio(true);

        Separator seperator = new Separator();
        seperator.prefWidth(100);

        VBox quizInfoVBox = new VBox( nameLabel, seperator, categoryLabel, creatorLabel);
        quizInfoVBox.setPrefSize(180, 190);
        quizInfoVBox.setAlignment(Pos.CENTER_LEFT);
        quizInfoVBox.setPadding(new Insets(20));

        VBox catagorieIconVBox = new VBox(catagorieIconView);
        catagorieIconVBox.setPrefSize(180, 190);
        catagorieIconVBox.setAlignment(Pos.CENTER);
        catagorieIconVBox.setPadding(new Insets(20));

        HBox card = new HBox(quizInfoVBox, catagorieIconVBox);
        card.setPrefSize(360, 190);
        card.setAlignment(Pos.CENTER);

        card.setStyle("-fx-border-color: " + cardColour + "; -fx-background-radius: 20px; -fx-border-width: 5; -fx-border-radius: 20px; -fx-effect: dropshadow(three-pass-box, #00000033, 15, 0.1, 5, 5); ");

        card.setOnMouseClicked(e -> selectCard(card, template, cardColour));

        return card;
    }

    private static String getCategory(String categoryName) {
        String cardColour;
        if ("Pattern Recognition".equalsIgnoreCase(categoryName)) {
            cardColour = "#7ad1ec"; //primary blue
        } else if ("Critical Thinking".equalsIgnoreCase(categoryName)) {
            cardColour = "#8cc978"; // pastal green
        } else if ("Mental Maths".equalsIgnoreCase(categoryName)) {
            cardColour = "#ef4136"; //red
        } else if ("Arithmetic".equalsIgnoreCase(categoryName)) {
            cardColour = "#9c72b2"; // primary purple
        } else {
            cardColour = "#ce78b1"; //secondary pink
        }
        return cardColour;
    }

    private void selectCard(HBox card, QuizTemplate template, String colour) {
        // Deselect previous card
        if (selectedCard != null) {
            String prevColour = (String) selectedCard.getUserData();
            selectedCard.setStyle(cardStyle(false, prevColour));
        }

        selectedTemplate = template;
        selectedCard = card;

        card.setUserData(colour);

        card.setStyle(cardStyle(true, colour));
        updateActionButtons();
    }

    private void updateActionButtons() {
        boolean hasSelection = selectedTemplate != null;
        btnCreate.setDisable(false); // always enabled
        btnModify.setDisable(!hasSelection);
        btnDelete.setDisable(!hasSelection);
        btnEditQuestions.setDisable(!hasSelection);
        btnAttemptQuiz.setDisable(!hasSelection);
    }

    private String cardStyle(boolean selected, String colour) {
        String bg = selected
                ? "-fx-background-color: #e3f2fd;" /* probably need to change for dark mode to work */
                : "-fx-background-color: #ffffff;"; /* need to change -fx-background-color: #ffffff for dark mode to work */
        return bg + "-fx-border-color: " + colour + "; -fx-border-width: 4; -fx-background-radius: 20px; -fx-border-radius: 15px; -fx-cursor: hand; -fx-effect: dropshadow(three-pass-box, #00000033, 15, 0.1, 5, 5);";
    }

//    private String cardStyle(boolean selected, String colour) {
//        String border = selected
//                //? "-fx-border-color: " + colour + "; -fx-border-width: 8;"
//                ? "-fx-border-color: #000000; -fx-border-width: 4;"
//                : "-fx-border-color: " + colour + "; -fx-border-width: 4;";
//        String bg = selected
//                ? "-fx-background-color: #e3f2fd;"
//                : "-fx-background-color: #ffffff;";
//        return bg + border + "-fx-background-radius: 20px; -fx-border-radius: 15px; -fx-cursor: hand; -fx-effect: dropshadow(three-pass-box, #00000033, 15, 0.1, 5, 5);";
//    }

    // Action Handlers

    /**
     * Opens a pop-up dialog to create a new template.
     * The user can select an existing category from a drop-down or tick
     * "New Category?" to type a new category name which is persisted
     * and immediately reflected in the library.
     */
    @FXML
    protected void handleCreate() {
        List<Category> categories = categoryService.getVisibleCategories();

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Create Template");
        dialog.setHeaderText("Create a new quiz template");
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
            if (isNowSelected) {
                categoryCombo.setValue(null);
            }
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
            templateService.createTemplate(templateName, targetCategory.getId(), userId);
            refreshCategories();
            showInfo("Template \"" + templateName + "\" created in category \"" + targetCategory.getName() + "marked as quiz" + "\".");
        } catch (IllegalArgumentException e) {
            showWarning(e.getMessage());
        }
    }

    @FXML
    private void handleModify() {
        if (selectedTemplate == null) return;

        TextInputDialog nameDialog = new TextInputDialog(selectedTemplate.getName());
        nameDialog.setTitle("Rename Template");
        nameDialog.setHeaderText("Rename \"" + selectedTemplate.getName() + "\"");
        nameDialog.setContentText("New name:");

        Optional<String> nameResult = nameDialog.showAndWait();
        nameResult.map(String::trim).filter(s -> !s.isEmpty()).ifPresent(newName -> {
            try {
                templateService.renameTemplate(selectedTemplate, newName);
                refreshCategories();
                showInfo("Template renamed to \"" + newName + "\".");
            } catch (IllegalArgumentException e) {
                showWarning(e.getMessage());
            }
        });
    }

    @FXML
    private void handleDelete() {
        if (selectedTemplate == null) return;

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Delete \"" + selectedTemplate.getName() + "\"? This cannot be undone.",
                ButtonType.YES, ButtonType.CANCEL);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText(null);

        confirm.showAndWait().filter(b -> b == ButtonType.YES).ifPresent(b -> {
            String name = selectedTemplate.getName();
            templateService.deleteTemplate(selectedTemplate);
            refreshCategories();
            showInfo("Template \"" + name + "\" deleted.");
        });
    }

    // Helper methods

    public static boolean isAttemptable(List<QuizTemplateQuestion> questions) {
        return questions != null && questions.size() >= 2;
    }

    @FXML
    private void handleAttemptQuiz() {
        if (selectedTemplate == null) return;

        try {
            QuizAttemptController controller = (QuizAttemptController) Router.navigateLayout(View.QUIZ_ATTEMPT);

            if (controller != null) {
                controller.initQuiz(selectedTemplate);
            }

        } catch (Exception e) {
            e.printStackTrace();
            showWarning("Failed to open quiz attempt page: " + e.getMessage());
        }
    }

    @FXML
    private void handleEditQuestions() {
        if (selectedTemplate == null) return;

        QuizTemplate template = selectedTemplate;

        templateService.loadQuestionsIntoTemplate(template);

        List<QuizTemplateQuestion> removedQuestions = new ArrayList<>();

        List<QuizTemplateQuestion> workingQuestions = new ArrayList<>(template.getQuestions());
        if (workingQuestions.isEmpty()) {
            workingQuestions.add(new QuizTemplateQuestion(template.getId(), "New Question"));
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Questions – " + template.getName());
        dialog.setHeaderText(null);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.getDialogPane().setPrefWidth(560);

        int[] currentIndex = {0};
        final int MAX_ANSWERS = 4;

        Label quizNameLabel = new Label(template.getName());
        quizNameLabel.setFont(Font.font("System", FontWeight.BOLD, 20));

        Separator titleSep = new Separator();

        Label questionCounter = new Label();
        questionCounter.setFont(Font.font("System", 12));
        questionCounter.setTextFill(Color.web("#777777")); /* need to change for dark mode to work */

        TextField questionField = new TextField();
        questionField.setPromptText("Question text…");
        questionField.setFont(Font.font("System", 16));

        TextField[] answerFields = new TextField[MAX_ANSWERS];
        CheckBox[] correctChecks = new CheckBox[MAX_ANSWERS];
        VBox answersBox = new VBox(8);

        for (int i = 0; i < MAX_ANSWERS; i++) {
            answerFields[i] = new TextField();
            answerFields[i].setPromptText("Answer " + (i + 1));
            correctChecks[i] = new CheckBox("Correct");
            HBox row = new HBox(10, answerFields[i], correctChecks[i]);
            row.setAlignment(Pos.CENTER_LEFT);
            HBox.setHgrow(answerFields[i], Priority.ALWAYS);
            answersBox.getChildren().add(row);
        }

        Button btnPrev    = new Button("<");
        Button btnNext    = new Button(">");
        Button btnAddQ    = new Button("Add");
        Button btnRemoveQ = new Button("Remove");

        HBox navBox = new HBox(10, btnPrev, questionCounter, btnNext, new Separator(), btnAddQ, btnRemoveQ);
        navBox.setAlignment(Pos.CENTER_LEFT);

        VBox content = new VBox(12,
                quizNameLabel, titleSep,
                navBox,
                questionField,
                new Label("Answers (tick at least one as correct):"),
                answersBox
        );
        content.setPadding(new Insets(16));
        dialog.getDialogPane().setContent(content);

        Runnable saveCurrentToModel = () -> {
            if (workingQuestions.isEmpty()) return;
            QuizTemplateQuestion q = workingQuestions.get(currentIndex[0]);
            q.setText(questionField.getText().trim());

            List<QuizTemplateAnswer> updated = new ArrayList<>();
            List<QuizTemplateAnswer> existing = q.getAnswers();
            for (int i = 0; i < MAX_ANSWERS; i++) {
                String aText = answerFields[i].getText().trim();
                if (!aText.isEmpty()) {
                    QuizTemplateAnswer ans = (i < existing.size())
                            ? existing.get(i)
                            : new QuizTemplateAnswer(q.getId(), aText, correctChecks[i].isSelected());
                    ans.setText(aText);
                    ans.setCorrect(correctChecks[i].isSelected());
                    updated.add(ans);
                }
            }
            q.setAnswers(updated);
        };

        Runnable loadFromModel = () -> {
            if (workingQuestions.isEmpty()) return;
            QuizTemplateQuestion q = workingQuestions.get(currentIndex[0]);
            questionCounter.setText("Question " + (currentIndex[0] + 1) + " of " + workingQuestions.size());
            questionField.setText(q.getText());
            List<QuizTemplateAnswer> answers = q.getAnswers();
            for (int i = 0; i < MAX_ANSWERS; i++) {
                if (i < answers.size()) {
                    answerFields[i].setText(answers.get(i).getText());
                    correctChecks[i].setSelected(answers.get(i).isCorrect());
                } else {
                    answerFields[i].clear();
                    correctChecks[i].setSelected(false);
                }
            }
            btnPrev.setDisable(currentIndex[0] == 0);
            btnNext.setDisable(currentIndex[0] >= workingQuestions.size() - 1 || workingQuestions.size() >= 10);
            btnAddQ.setDisable(workingQuestions.size() >= 10);
            btnRemoveQ.setDisable(workingQuestions.size() <= 1);
        };

        loadFromModel.run();

        btnPrev.setOnAction(e  -> { saveCurrentToModel.run(); currentIndex[0]--; loadFromModel.run(); });
        btnNext.setOnAction(e  -> { saveCurrentToModel.run(); currentIndex[0]++; loadFromModel.run(); });

        btnAddQ.setOnAction(e -> {
            saveCurrentToModel.run();
            workingQuestions.add(new QuizTemplateQuestion(template.getId(), "New Question"));
            currentIndex[0] = workingQuestions.size() - 1;
            loadFromModel.run();
        });

        btnRemoveQ.setOnAction(e -> {
            if (workingQuestions.size() <= 1) return;
            QuizTemplateQuestion removed = workingQuestions.remove(currentIndex[0]);
            if (removed.getId() > 0) removedQuestions.add(removed);
            if (currentIndex[0] >= workingQuestions.size()) currentIndex[0] = workingQuestions.size() - 1;
            loadFromModel.run();
        });

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isEmpty() || result.get() != ButtonType.OK) return;
        saveCurrentToModel.run();

        try {
            templateService.saveQuestions(template, workingQuestions, removedQuestions);
            showInfo("Questions saved for \"" + template.getName() + "\".");
        } catch (IllegalArgumentException e) {
            showWarning(e.getMessage());
        }
    }

    protected void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    protected void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    @FXML
    public abstract void initialise();



    @FXML
    public void navigateHome() {
        Router.navigateLayout(View.HOME);
    }
}
