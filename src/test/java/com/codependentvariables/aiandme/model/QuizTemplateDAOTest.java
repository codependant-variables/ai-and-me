package com.codependentvariables.aiandme.model;

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
        QuizTemplate template = new QuizTemplate("Basic Math", 1, "draft");
        quizTemplateDAO.addTemplate(template);
        assertTrue(template.getId() > 0);
    }

    @Test
    public void addTemplate_incrementsId() {
        QuizTemplate first = new QuizTemplate("Math", 1, "draft");
        QuizTemplate second = new QuizTemplate("Reading", 2, "draft");
        quizTemplateDAO.addTemplate(first);
        quizTemplateDAO.addTemplate(second);
        assertNotEquals(first.getId(), second.getId());
    }

    @Test
    public void get_returnsCorrectTemplate() {
        QuizTemplate template = new QuizTemplate("Basic Math", 1, "draft");
        quizTemplateDAO.addTemplate(template);
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
        quizTemplateDAO.addTemplate(new QuizTemplate("Math Quiz", 1, "draft"));
        quizTemplateDAO.addTemplate(new QuizTemplate("Reading Quiz", 2, "published"));
        List<QuizTemplate> all = quizTemplateDAO.getAllTemplates();
        assertEquals(2, all.size());
    }

    @Test
    public void getAllTemplates_returnsEmptyWhenNoneAdded() {
        List<QuizTemplate> all = quizTemplateDAO.getAllTemplates();
        assertTrue(all.isEmpty());
    }

    // Tests for updateTemplate

    @Test
    public void updateTemplate_changesName() {
        QuizTemplate template = new QuizTemplate("Old Name", 1, "draft");
        quizTemplateDAO.addTemplate(template);
        template.setName("New Name");
        quizTemplateDAO.updateTemplate(template);
        assertEquals("New Name", quizTemplateDAO.get(template.getId()).getName());
    }

    @Test
    public void updateTemplate_changesStatus() {
        QuizTemplate template = new QuizTemplate("Quiz", 1, "draft");
        quizTemplateDAO.addTemplate(template);
        template.setStatus("published");
        quizTemplateDAO.updateTemplate(template);
        assertEquals("published", quizTemplateDAO.get(template.getId()).getStatus());
    }

    // Tests for deleteTemplate

    @Test
    public void deleteTemplate_removesTemplate() {
        QuizTemplate template = new QuizTemplate("To Delete", 1, "draft");
        quizTemplateDAO.addTemplate(template);
        quizTemplateDAO.deleteTemplate(template);
        assertNull(quizTemplateDAO.get(template.getId()));
    }

    @Test
    public void deleteTemplate_doesNotAffectOtherTemplates() {
        QuizTemplate first = new QuizTemplate("Keep", 1, "draft");
        QuizTemplate second = new QuizTemplate("Remove", 1, "draft");
        quizTemplateDAO.addTemplate(first);
        quizTemplateDAO.addTemplate(second);
        quizTemplateDAO.deleteTemplate(second);
        List<QuizTemplate> remaining = quizTemplateDAO.getAllTemplates();
        assertEquals(1, remaining.size());
        assertEquals("Keep", remaining.get(0).getName());
    }

    // Tests for getTemplatesByCategory

    @Test
    public void getTemplatesByCategory_returnsOnlyMatchingTemplates() {
        quizTemplateDAO.addTemplate(new QuizTemplate("Math Quiz", 1, "draft"));
        quizTemplateDAO.addTemplate(new QuizTemplate("Math Quiz 2", 1, "published"));
        quizTemplateDAO.addTemplate(new QuizTemplate("Reading Quiz", 2, "draft"));
        List<QuizTemplate> mathTemplates = quizTemplateDAO.getTemplatesByCategory(1);
        assertEquals(2, mathTemplates.size());
        assertTrue(mathTemplates.stream().allMatch(t -> t.getCategoryId() == 1));
    }

    @Test
    public void getTemplatesByCategory_returnsEmptyForUnknownCategory() {
        quizTemplateDAO.addTemplate(new QuizTemplate("Math Quiz", 1, "draft"));
        List<QuizTemplate> result = quizTemplateDAO.getTemplatesByCategory(99);
        assertTrue(result.isEmpty());
    }
}
