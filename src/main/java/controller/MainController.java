package controller;

import model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import service.CategoryService;
import service.QuestionService;

@Controller
public class MainController
{
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String main_get(Model model)
    {
        model.addAttribute("categories", categoryService.findAll());
        return "main";
    }

    @GetMapping("/question")
    public String question_get(Model model, long catid, long queid, boolean positive)
    {
        if(queid == -1)
            model.addAttribute("questions", categoryService.getFirstQuestionsList(catid));
        else
        {
            Question question = questionService.getQuestionById(queid);
            if(question.isNextOnPositiveAvailable()) ;
        }
        return "question";
    }
}
