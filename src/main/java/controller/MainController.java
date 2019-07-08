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
    public String main_get(Model model)
    {
        model.addAttribute("categories", categoryService.findAll());
        org.springframework.security.core.userdetails.User obj =
                (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByLogin(obj.getUsername());
        model.addAttribute("user", user);
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
    public String addQuestion_post(@RequestBody Map<String, Object> param)
    {
        try
        {
            Long categoryId = (Long)param.get("categoryId");
            Long prevId = (Long)param.get("prevId");
            String message = (String)param.get("message");
            Question question = questionService.createQuestion(message, prevId);
            if (categoryId == null)
            {
                questionService.createQuestion(question);
            }
            else
            {
                categoryService.addQuestion(categoryId, question);
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
        if(title != null)
        {
            try
            {
                Category category = categoryService.createCategory(title);
                categoryService.createCategory(category);
            }
            catch (Exception ex)
            {
                attr.addAttribute("message", ex.getMessage());
                return "redirect:/";
            }
        }
        else
        {
            attr.addAttribute("message", "Cannot create the null named category");
            return "redirect:/";
        }
        attr.addAttribute("message", "Category added successful");
        return "redirect:/";
    }

    @GetMapping("/add_category")
    public String addCategory_get()
    {
        return "add_category";
    }
}
