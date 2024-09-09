package com.mysite.sbb.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/question")
// @RequiredArgsConstructor :
// questionRepository 객체를 주입
// 롬복이 제공하는 애너테이션
// final이 붙은 속성을 포함하는 생성자를 자동으로 만들어주는 역할을 함
// QuestionController를 생성할 때 롬복으로 만들어진 생성자에 의해 questionRepository 객체가 자동으로 주입됨
@RequiredArgsConstructor  
@Controller
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/list")
    //@ResponseBody 
    public String list(Model model) { //매개변수로 Model을 지정하면 객체가 자동으로 생성된다.
        List<Question> questionList = this.questionService.getList();
        model.addAttribute("questionList", questionList);
        return "question_list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }
}
