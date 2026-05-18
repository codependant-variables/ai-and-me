package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.model.*;
import com.codependentvariables.aiandme.model.dao.*;
import com.codependentvariables.aiandme.state.AppState;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DataExportService {
    private static DataExportService instance;

    private static final AppState appState = AppState.getInstance();
    private final IUserDAO userDAO;
    private final IPreferredCategoryDAO prefCatDAO;
    private final ICheckInDAO checkInDAO;
    private final IQuizTemplateDAO quizTempDAO;
    private final IQuizTemplateQuestionDAO quizTempQuestionDAO;
    private final IQuizTemplateAnswerDAO quizTempAnswerDAO;
    private final IQuizAttemptDAO quizAttDAO;
    private final IQuizAttemptQuestionDAO quizAttQuestionDAO;
    private final IQuizAttemptAnswerDAO quizAttAnswerDAO;

    private DataExportService(
            IUserDAO userDAO,
            IPreferredCategoryDAO prefCatDAO,
            ICheckInDAO checkInDAO,
            IQuizTemplateDAO quizTempDAO,
            IQuizTemplateQuestionDAO quizTempQuestionDAO,
            IQuizTemplateAnswerDAO quizTempAnswerDAO,
            IQuizAttemptDAO quizAttDAO,
            IQuizAttemptQuestionDAO quizAttQuestionDAO,
            IQuizAttemptAnswerDAO quizAttAnswerDAO
    ) {
        this.userDAO = userDAO;
        this.prefCatDAO = prefCatDAO;
        this.checkInDAO = checkInDAO;
        this.quizTempDAO = quizTempDAO;
        this.quizTempQuestionDAO = quizTempQuestionDAO;
        this.quizTempAnswerDAO = quizTempAnswerDAO;
        this.quizAttDAO = quizAttDAO;
        this.quizAttQuestionDAO = quizAttQuestionDAO;
        this.quizAttAnswerDAO = quizAttAnswerDAO;
    }

    public static DataExportService getInstance() {
        if (instance == null) {
            instance = new DataExportService(
                    new SqliteUserDAO(),
                    new SqlitePreferredCategoryDAO(),
                    new SqliteCheckInDAO(),
                    new SqliteQuizTemplateDAO(),
                    new SqliteQuizTemplateQuestionDAO(),
                    new SqliteQuizTemplateAnswerDAO(),
                    new SqliteQuizAttemptDAO(),
                    new SqliteQuizAttemptQuestionDAO(),
                    new SqliteQuizAttemptAnswerDAO()
            );
        }
        return instance;
    }

    public void exportUserData() {
        if (appState.getCurrentUser() != null) {
            JSONArray userData = new JSONArray();

            userData.put(exportUsers());
            userData.put(exportPrefCats());
            userData.put(exportCheckIns());
            userData.put(exportQuizTemplates());
            userData.put(exportQuizAttempts());

            try (FileWriter writer = new FileWriter("userdata.json")) {
                userData.write(writer, 4, 0);
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    private JSONArray exportUsers() {
        JSONArray JsonUser = new JSONArray();
        JsonUser.put(new JSONObject(userDAO.get(appState.getCurrentUser().getId())));
        return JsonUser;
    }

    private JSONArray exportPrefCats() {
        List<Category> prefCats = prefCatDAO.getByUserId(appState.getCurrentUser().getId());
        JSONArray JsonPrefCats = new JSONArray();
        prefCats.forEach(prefCat -> {
            JsonPrefCats.put(new JSONObject(prefCat));
        });
        return JsonPrefCats;
    }

    private JSONArray exportCheckIns() {
        List<CheckIn> checkIns = checkInDAO.getAllByUserId(appState.getCurrentUser().getId());
        JSONArray JsonCheckIns = new JSONArray();
        checkIns.forEach(checkIn -> {
            JsonCheckIns.put(new JSONObject(checkIn));
        });
        return JsonCheckIns;
    }

    private JSONArray exportQuizTemplates() {
        JSONArray JsonQuizTemplates = new JSONArray();



        List<QuizTemplate> quizTemplates = quizTempDAO.getByUserId(appState.getCurrentUser().getId());
        quizTemplates.forEach(quizTemplate -> {
            JSONArray JsonQuizTemplateQuestions = new JSONArray();
            JsonQuizTemplates.put(new JSONObject(quizTemplate));
            List<QuizTemplateQuestion> quizTemplateQuestions = quizTempQuestionDAO.getQuestionsByTemplate(quizTemplate.getId());
            quizTemplateQuestions.forEach(quizTemplateQuestion -> {
                JSONArray JsonQuizTemplateQuestionAnswers = new JSONArray();
                JsonQuizTemplateQuestions.put(new JSONObject(quizTemplateQuestion));
                List<QuizTemplateAnswer> quizTemplateQuestionAnswers = quizTempAnswerDAO.getAnswersByQuestion(quizTemplateQuestion.getId());
                quizTemplateQuestionAnswers.forEach(quizTemplateQuestionAnswer -> {
                    JsonQuizTemplateQuestionAnswers.put(new JSONObject(quizTemplateQuestionAnswer));
                });
                JsonQuizTemplateQuestions.put(JsonQuizTemplateQuestionAnswers);
            });
            JsonQuizTemplates.put(JsonQuizTemplateQuestions);
        });
        return JsonQuizTemplates;
    }

    private JSONArray exportQuizAttempts() {
        JSONArray JsonQuizAttempts = new JSONArray();
        List<QuizAttempt> quizAttempts = quizAttDAO.getByUserId(appState.getCurrentUser().getId());
        quizAttempts.forEach(quizAttempt -> {
            JSONArray JsonQuizAttemptQuestions = new JSONArray();
            JsonQuizAttempts.put(new JSONObject(quizAttempt));
            List<QuizAttemptQuestion> quizAttemptQuestions = quizAttQuestionDAO.getByQuizAttemptId(quizAttempt.getId());
            quizAttemptQuestions.forEach(quizAttemptQuestion -> {
                JSONArray JsonQuizAttemptQuestionAnswers = new JSONArray();
                JsonQuizAttemptQuestions.put(new JSONObject(quizAttemptQuestion));
                List<QuizAttemptAnswer> quizAttemptQuestionAnswers = quizAttAnswerDAO.getByQuizAttemptQuestionId(quizAttemptQuestion.getId());
                quizAttemptQuestionAnswers.forEach(quizAttemptQuestionAnswer -> {
                    JsonQuizAttemptQuestionAnswers.put(new JSONObject(quizAttemptQuestionAnswer));
                });
                JsonQuizAttemptQuestions.put(JsonQuizAttemptQuestionAnswers);
            });
            JsonQuizAttempts.put(JsonQuizAttemptQuestions);
        });
        return JsonQuizAttempts;
    }
}
