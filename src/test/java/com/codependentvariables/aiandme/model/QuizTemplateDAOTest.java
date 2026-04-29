package com.codependentvariables.aiandme.model;

import com.codependentvariables.aiandme.model.dao.IQuizTemplateDAO;
import com.codependentvariables.aiandme.model.mock.MockQuizTemplateDAO;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuizTemplateDAOTest {
    private IQuizTemplateDAO quizTemplateDAO;

    @BeforeEach
    public void setUp() {
        quizTemplateDAO = new MockQuizTemplateDAO();
    }

    // Tests for addTemplate

    @Test
    public void addTemplate_assignsId() {
        QuizTemplate template = new QuizTemplate("Basic Math", 1, 1, "draft");
        quizTemplateDAO.add(template);
        assertTrue(template.getId() > 0);
    }

    @Test
    public void addTemplate_incrementsId() {
        QuizTemplate first = new QuizTemplate("Math", 1, 1, "draft");
        QuizTemplate second = new QuizTemplate("Reading", 2, 1, "draft");
        quizTemplateDAO.add(first);
        quizTemplateDAO.add(second);
        assertNotEquals(first.getId(), second.getId());
    }

    @Test
    public void get_returnsCorrectTemplate() {
        QuizTemplate template = new QuizTemplate("Basic Math", 1, 1, "draft");
        quizTemplateDAO.add(template);
        QuizTemplate retrieved = quizTemplateDAO.get(template.getId());
        assertNotNull(retrieved);
        assertEquals("Basic Math", retrieved.getName());
    }

    @Test
    public void get_returnsNullForNonExistentId() {
        QuizTemplate retrieved = quizTemplateDAO.get(9999);
        assertNull(retrieved);
    }

    // Tests for getAllTemplates

    @Test
    public void getAllTemplates_returnsAllAdded() {
        quizTemplateDAO.add(new QuizTemplate("Math Quiz", 1, 1, "draft"));
        quizTemplateDAO.add(new QuizTemplate("Reading Quiz", 2, 1, "published"));
        List<QuizTemplate> all = quizTemplateDAO.getAll();
        assertEquals(2, all.size());
    }

    @Test
    public void getAllTemplates_returnsEmptyWhenNoneAdded() {
        List<QuizTemplate> all = quizTemplateDAO.getAll();
        assertTrue(all.isEmpty());
    }

    // Tests for updateTemplate

    @Test
    public void updateTemplate_changesName() {
        QuizTemplate template = new QuizTemplate("Old Name", 1, 1, "draft");
        quizTemplateDAO.add(template);
        template.setName("New Name");
        quizTemplateDAO.update(template);
        assertEquals("New Name", quizTemplateDAO.get(template.getId()).getName());
    }

    @Test
    public void updateTemplate_changesStatus() {
        QuizTemplate template = new QuizTemplate("Quiz", 1, 1, "draft");
        quizTemplateDAO.add(template);
        template.setStatus("published");
        quizTemplateDAO.update(template);
        assertEquals("published", quizTemplateDAO.get(template.getId()).getStatus());
    }

    @Test
    public void updateTemplate_changesUserId() {
        QuizTemplate template = new QuizTemplate("Quiz", 1, 1, "draft");
        quizTemplateDAO.add(template);
        template.setUserId(2);
        quizTemplateDAO.update(template);
        assertEquals(2, quizTemplateDAO.get(template.getId()).getUserId());
    }

    // Tests for deleteTemplate

    @Test
    public void deleteTemplate_removesTemplate() {
        QuizTemplate template = new QuizTemplate("To Delete", 1, 1, "draft");
        quizTemplateDAO.add(template);
        quizTemplateDAO.delete(template);
        assertNull(quizTemplateDAO.get(template.getId()));
    }

    @Test
    public void deleteTemplate_doesNotAffectOtherTemplates() {
        QuizTemplate first = new QuizTemplate("Keep", 1, 1, "draft");
        QuizTemplate second = new QuizTemplate("Remove", 1, 1, "draft");
        quizTemplateDAO.add(first);
        quizTemplateDAO.add(second);
        quizTemplateDAO.delete(second);
        List<QuizTemplate> remaining = quizTemplateDAO.getAll();
        assertEquals(1, remaining.size());
        assertEquals("Keep", remaining.get(0).getName());
    }

    // Tests for getTemplatesByCategory

    @Test
    public void getTemplatesByCategory_returnsOnlyMatchingTemplates() {
        quizTemplateDAO.add(new QuizTemplate("Math Quiz", 1, 1, "draft"));
        quizTemplateDAO.add(new QuizTemplate("Math Quiz 2", 1, 1, "published"));
        quizTemplateDAO.add(new QuizTemplate("Reading Quiz", 2, 1, "draft"));
        List<QuizTemplate> mathTemplates = quizTemplateDAO.getByCategoryId(1);
        assertEquals(2, mathTemplates.size());
        assertTrue(mathTemplates.stream().allMatch(t -> t.getCategoryId() == 1));
    }

    @Test
    public void getTemplatesByCategory_returnsEmptyForUnknownCategory() {
        quizTemplateDAO.add(new QuizTemplate("Math Quiz", 1, 1, "draft"));
        List<QuizTemplate> result = quizTemplateDAO.getByCategoryId(99);
        assertTrue(result.isEmpty());
    }

    // Tests for getTemplatesByUser

    @Test
    public void getTemplatesByUser_returnsOnlyMatchingTemplates() {
        quizTemplateDAO.add(new QuizTemplate("Math Quiz", 1, 1, "draft"));
        quizTemplateDAO.add(new QuizTemplate("Science Quiz", 2, 1, "published"));
        quizTemplateDAO.add(new QuizTemplate("History Quiz", 1, 2, "draft"));
        List<QuizTemplate> userTemplates = quizTemplateDAO.getByUserId(1);
        assertEquals(2, userTemplates.size());
        assertTrue(userTemplates.stream().allMatch(t -> t.getUserId() == 1));
    }

    @Test
    public void getTemplatesByUser_returnsEmptyForUnknownUser() {
        quizTemplateDAO.add(new QuizTemplate("Math Quiz", 1, 1, "draft"));
        List<QuizTemplate> result = quizTemplateDAO.getByUserId(99);
        assertTrue(result.isEmpty());
    }
}
