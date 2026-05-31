package com.codependentvariables.aiandme.controller;

import com.codependentvariables.aiandme.model.*;
import com.codependentvariables.aiandme.modules.dialogue.Dialogue;
import com.codependentvariables.aiandme.modules.dialogue.DialogueMessage;
import com.codependentvariables.aiandme.modules.dialogue.DialogueType;
import com.codependentvariables.aiandme.modules.router.Router;
import com.codependentvariables.aiandme.modules.router.View;
import com.codependentvariables.aiandme.modules.state.AppState;
import com.codependentvariables.aiandme.modules.toast.Toast;
import com.codependentvariables.aiandme.modules.toast.ToastMessageType;
import com.codependentvariables.aiandme.services.CategoryService;
import com.codependentvariables.aiandme.services.QuizTemplateService;
import com.codependentvariables.aiandme.services.UserService;
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
import javafx.stage.FileChooser;
import javafx.util.Callback;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Handles displaying quiz templates and quiz management actions.
 */
public class QuizLibraryController {
    private final AppState appState = AppState.getInstance();
    private final CategoryService categoryService = CategoryService.getInstance();
    private final QuizTemplateService templateService = QuizTemplateService.getInstance();
    private final UserService userService = UserService.getInstance();

    @FXML private Label pageTitle;
    @FXML private FlowPane cardContainer;
    @FXML private Button btnCreate;
    @FXML private Button btnEdit;
    @FXML private Button btnRename;
    @FXML private Button btnDelete;
    @FXML private Button btnStart;
    @FXML private ComboBox<Category> categoryFilter;
    private final Callback<ListView<Category>, ListCell<Category>> cellFactory = new Callback<>() {
        @Override
        public ListCell<Category> call(ListView<Category> l) {
            return new ListCell<>() {
                @Override
                protected void updateItem(Category item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getName());
                    }
                }
            };
        }
    };

    // Currently selected template and its UI card
    private QuizTemplate selectedTemplate;
    private HBox selectedCard;

    private List<QuizTemplate> quizTemplates;
    private List<Category> categories;
    private final Category categoryAll = new Category("All");

    /**
     * Initialises UI state and loads templates.
     */
    @FXML
    public void initialize() {
        pageTitle.setText(getPageName() + " Library");
        btnCreate.setDisable(false);
        btnEdit.setDisable(true);
        btnRename.setDisable(true);
        btnDelete.setDisable(true);
        btnStart.setText("Start " + getPageName());
        btnStart.setDisable(true);
        loadCategoryFilter();
        loadTemplates(appState.getIsPuzzle());
    }

    /**
     * Loads quiz or puzzle templates determined by the isPuzzle parameter.
     * @param isPuzzle Puzzles are loaded if true, else quizzes.
     */
    public void loadTemplates(boolean isPuzzle) {
        quizTemplates = appState.getIsPuzzle() ? templateService.getAllPuzzles() : templateService.getAllTemplates();
        refreshCards();
    }

    private String getPageName() {
        return appState.getIsPuzzle() ? "Puzzle" : "Quiz";
    }

    /**
     * Loads category names into the filter dropdown.
     * Adds an "All" option to show every quiz.
     */
    private void loadCategoryFilter() {
        categories = categoryService.getAll();
        categoryFilter.setCellFactory(cellFactory);
        categoryFilter.getItems().add(categoryAll);
        categoryFilter.getItems().addAll(categories);
        categoryFilter.setValue(categoryAll);
        categoryFilter.setButtonCell(cellFactory.call(null));
    }

    /**
     * Reloads all quiz templates and rebuilds UI cards.
     */
    public void refreshCards() {
        cardContainer.getChildren().clear();
        selectedTemplate = null;
        selectedCard = null;
        updateActionButtons();

        Category selectedCategory = categoryFilter.getValue();

        for (QuizTemplate template : quizTemplates) {
            if (selectedCategory == categoryAll || selectedCategory.getId() == template.getCategoryId()) {
                cardContainer.getChildren().add(buildTemplateCard(template, categories.stream().filter(x -> template.getCategoryId() == x.getId()).findFirst().get().getName()));
            }
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
            User creator = userService.get(template.getUserId());
            creatorName = (creator != null) ? creator.getName() : "Unknown";
        }

        String getCatagorieIcon = switch(categoryName) {
            case "Pattern Recognition" -> "/com/codependentvariables/aiandme/Images/CatagoryPatternRecognition.png";
            case "Critical Thinking" -> "/com/codependentvariables/aiandme/Images/CatagoryCriticalThinking.png";
            case "Mental Maths" -> "/com/codependentvariables/aiandme/Images/CatagoryComprehension.png";
            case "Arithmetic" -> "/com/codependentvariables/aiandme/Images/CatagoryArithmetic.png";
            default -> "/com/codependentvariables/aiandme/Images/CatagoryOther.png";
        };

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
        btnEdit.setDisable(!hasSelection);
        btnRename.setDisable(!hasSelection);
        btnDelete.setDisable(!hasSelection);
        btnStart.setDisable(!hasSelection);
    }

    private String cardStyle(boolean selected, String colour) {
        boolean isDarkMode = appState.getIsDarkMode();

        String bg = selected
                ? (!isDarkMode ? "-fx-background-color: #e3f2fd;" : "-fx-background-color: #51728A;") //selected

                : (isDarkMode ? "-fx-background-color: #2e3440;" : "-fx-background-color: #ffffff;" ); //not selected

        return bg + "-fx-border-color: " + colour + "; -fx-border-width: 4; -fx-background-radius: 20px; -fx-border-radius: 15px; -fx-cursor: hand; -fx-effect: dropshadow(three-pass-box, #00000033, 15, 0.1, 5, 5);";
    }

    // Action Handlers

    /**
     * Opens a pop-up dialogue to create a new template.
     * The user can select an existing category from a drop-down or tick
     * "New Category?" to type a new category name which is persisted
     * and immediately reflected in the library.
     */
    @FXML
    protected void handleCreate() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Create Template");
        dialog.setHeaderText(String.format("Create a new %s template", appState.getIsPuzzle() ? "puzzle" : "quiz"));
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
            targetCategory = new Category(newCategoryField.getText().trim());
            categoryService.add(targetCategory);
        } else {
            targetCategory = categoryCombo.getValue();
        }

        try {
            User currentUser = AppState.getInstance().getCurrentUser();
            int userId = (currentUser != null) ? currentUser.getId() : 0;
            templateService.createTemplate(templateName, targetCategory.getId(), userId);
            refreshCards();
            Toast.addMessage("Created", String.format("Template '%s' created in category '%s'.", templateName, targetCategory.getName()), ToastMessageType.INFORMATION);
        } catch (IllegalArgumentException e) {
            Toast.addMessage("Error", e.getMessage(), ToastMessageType.WARNING);
        }
    }

    @FXML
    private void handleRename() {
        if (selectedTemplate == null) {
            return;
        }

        TextInputDialog nameDialog = new TextInputDialog(selectedTemplate.getName());
        nameDialog.setTitle("Rename Template");
        nameDialog.setHeaderText("Rename \"" + selectedTemplate.getName() + "\"");
        nameDialog.setContentText("New name:");

        Optional<String> nameResult = nameDialog.showAndWait();
        nameResult.map(String::trim).filter(s -> !s.isEmpty()).ifPresent(newName -> {
            templateService.renameTemplate(selectedTemplate, newName);
            refreshCards();
            Toast.addMessage("Renamed", (String.format("Template renamed to '%s'.", newName)), ToastMessageType.INFORMATION);
        });
    }

    @FXML
    private void handleDelete() {
        if (selectedTemplate == null) {
            return;
        }

        String name = selectedTemplate.getName();
        Dialogue.show(new DialogueMessage(String.format("Delete '%s'? This cannot be undone.", name), DialogueType.YES_NO_CANCEL, result -> {
            if (result != null && result) {
                templateService.deleteTemplate(selectedTemplate);
                refreshCards();
                Toast.addMessage("Deleted", (String.format("Template '%s' delete.", name)), ToastMessageType.INFORMATION);
            }
        }));
    }

    // Helper methods

    public static boolean isAttemptable(List<QuizTemplateQuestion> questions) {
        return questions != null && questions.size() >= 2;
    }

    @FXML
    protected void handleStart() {
        if (selectedTemplate == null) {
            return;
        }

        QuizAttemptController controller = (QuizAttemptController) Router.navigateLayout(View.QUIZ_ATTEMPT);
        assert controller != null;
        controller.initQuiz(selectedTemplate);
    }

    @FXML
    private void handleEdit() {
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

        // Image panel only shows in PuzzleLibraryController (showsTemplateImage() == true)
        ImageView questionImageView = new ImageView();
        questionImageView.setFitWidth(200);
        questionImageView.setFitHeight(150);
        questionImageView.setPreserveRatio(true);

        Label noImageLabel = new Label("No image for this question.");
        noImageLabel.setTextFill(Color.web("#999999"));

        Button btnUploadImage = new Button("+ Upload Image");
        Button btnDeleteImage = new Button("X Remove Image");
        btnDeleteImage.setStyle("-fx-text-fill: red;");
        btnDeleteImage.setVisible(false);
        btnDeleteImage.setManaged(false);

        HBox imageButtons = new HBox(8, btnUploadImage, btnDeleteImage);
        imageButtons.setAlignment(Pos.CENTER);

        VBox imagePanel = new VBox(6, questionImageView, noImageLabel, imageButtons);
        imagePanel.setAlignment(Pos.CENTER);
        imagePanel.setVisible(appState.getIsPuzzle());
        imagePanel.setManaged(appState.getIsPuzzle());

        btnUploadImage.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Question Image");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
            );
            File file = fileChooser.showOpenDialog(dialog.getDialogPane().getScene().getWindow());
            if (file != null) {
                try {
                    byte[] imageBytes = Files.readAllBytes(file.toPath());
                    workingQuestions.get(currentIndex[0]).setImage(imageBytes);
                    questionImageView.setImage(new Image(new ByteArrayInputStream(imageBytes)));
                    questionImageView.setVisible(true);
                    noImageLabel.setVisible(false);
                    btnDeleteImage.setVisible(true);
                    btnDeleteImage.setManaged(true);
                } catch (IOException ex) {
                    Toast.addMessage("Error", String.format("Failed to load image: %s", ex.getMessage()), ToastMessageType.ERROR);
                }
            }
        });

        btnDeleteImage.setOnAction(e -> {
            workingQuestions.get(currentIndex[0]).setImage(null);
            questionImageView.setImage(null);
            questionImageView.setVisible(false);
            noImageLabel.setVisible(true);
            btnDeleteImage.setVisible(false);
            btnDeleteImage.setManaged(false);
        });

        VBox content = new VBox(12,
                quizNameLabel, titleSep,
                navBox,
                questionField,
                imagePanel,
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
            // Update image panel for puzzle questions
            if (appState.getIsPuzzle()) {
                byte[] imgBytes = q.getImage();
                boolean hasImage = imgBytes != null && imgBytes.length > 0;
                if (hasImage) {
                    questionImageView.setImage(new Image(new ByteArrayInputStream(imgBytes)));
                    questionImageView.setVisible(true);
                    noImageLabel.setVisible(false);
                } else {
                    questionImageView.setImage(null);
                    questionImageView.setVisible(false);
                    noImageLabel.setVisible(true);
                }
                btnDeleteImage.setVisible(hasImage);
                btnDeleteImage.setManaged(hasImage);
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
            templateService.saveQuestions(workingQuestions, removedQuestions);
            Toast.addMessage("Saved", String.format("Questions saved for '%s'.", template.getName()), ToastMessageType.INFORMATION);
        } catch (IllegalArgumentException e) {
            Toast.addMessage("Error", e.getMessage(), ToastMessageType.ERROR);
        }
    }
}