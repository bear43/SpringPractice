package controller;

import model.Category;
import model.Question;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.CategoryService;
import service.QuestionService;
import service.UserService;

import java.util.List;
import java.util.Map;
import java.util.OptionalLong;

@Controller
public class MainController
{
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String main_get(Model model, RedirectAttributes attr)
    {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("user", userService.getCurrentUser());
        return "main";
    }

    @PostMapping("/getCategoryQuestions")
    @ResponseBody
    public List<Long> questionsList_post(@RequestBody Map<String, Long> param)
    {
        long catid = param.get("category");
        return categoryService.getFirstQuestionsListIds(catid);
    }

    @PostMapping("/getQuestion")
    @ResponseBody
    public Question question_post(@RequestBody Map<String, Long> param)
    {
        return questionService.getQuestionById(param.get("id"));
    }

    @PostMapping("/getNextQuestions")
    @ResponseBody
    public List<Long> nextQuestion_post(@RequestBody Map<String, Long> param)
    {
       return questionService.getNextQuestionsById(param.get("id"));
    }

    @PostMapping("/addQuestion")
    @ResponseBody
    public String addQuestion_post(@RequestBody Map<String, Object> param, RedirectAttributes attr)
    {
        try
        {
            if(!userService.getCurrentUser().isAdmin())
            {
                return "You are not admin!";
            }
            Long categoryId = (Long)param.get("categoryId");
            String categoryTitle = (String)param.get("categoryTitle");
            Integer i = ((Integer)param.get("prevId"));
            Long prevId = i != null ? i.longValue() : null;
            String message = (String)param.get("message");
            Question question = questionService.createQuestion(message, prevId);
            if (categoryId == null && categoryTitle == null)
            {
                questionService.createQuestion(question);
            }
            else
            {
                if(categoryTitle == null)
                    categoryService.addQuestion(categoryId, question);
                else
                    categoryService.addQuestion(categoryTitle, question);
            }
            return "The question is created successful";
        }
        catch (Exception ex)
        {
            return ex.getMessage();
        }
    }

    @PostMapping("/addCategory")
    public String addCategory_post(String title, RedirectAttributes attr)
    {
        if(!userService.getCurrentUser().isAdmin())
        {
            attr.addFlashAttribute("message", "You are not admin!");
            return "redirect:/";
        }
        if(title != null)
        {
            try
            {
                Category category = categoryService.createCategory(title);
                categoryService.createCategory(category);
            }
            catch (Exception ex)
            {
                attr.addFlashAttribute("message", ex.getMessage());
                return "redirect:/";
            }
        }
        else
        {
            attr.addFlashAttribute("message", "Cannot create the null named category");
            return "redirect:/";
        }
        attr.addFlashAttribute("message", "Category added successful");
        return "redirect:/";
    }

    @GetMapping("/add_category")
    public String addCategory_get(RedirectAttributes attr)
    {
        if(!userService.getCurrentUser().isAdmin())
        {
            attr.addFlashAttribute("message", "You are not admin!");
            return "redirect:/";
        }
        return "add_category";
    }

    @GetMapping("/add_question")
    public String add_question_get(Model model)
    {
        model.addAttribute("categories", categoryService.findAll());
        return "add_question";
    }
}
