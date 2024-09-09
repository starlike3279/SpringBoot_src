package com.mysite.sbb.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// 프리픽스(prefix) : URL의 접두사 또는 시작 부분을 의미
// 앞으로 URL을 매핑할 때 반드시 /question으로 시작
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

    // @RequestMapping("/question")을 사용했기 때문에 /question + /list가 되어 최종 URL 매핑은 /question/list가 된다.
    @GetMapping("/list")
    //@ResponseBody 
    public String list(Model model) { // 매개변수로 Model을 지정하면 객체가 자동으로 생성된다.
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

    @GetMapping(value = "/question/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    //"[질문 등록하기]" 버튼을 통한 /question/create 요청은 GET 요청에 해당하므로 @GetMapping 애너테이션을 사용했다. 
    // questionCreate 메서드는 question_form 템플릿을 출력한다.
    @GetMapping("/create")
    public String questionCreate() {
        return "question_form";
    }

    // questionCreate 메서드는 화면에서 입력한 제목(subject)과 내용(content)을 매개변수로 받는다.
    // 이때 질문 등록 템플릿(question_form.html)에서 입력 항목으로 사용한 subject, content의 이름과 
    // RequestParam의 value 값이 동일해야 한다.
    @PostMapping("/create")
    public String questionCreate(@RequestParam(value="subject") String subject, @RequestParam(value="content") String content) {
        // TODO 질문을 저장한다.
        // QuestionService의 create 메서드를 호출하여 질문 데이터(subject, content)를 저장하는 코드를 작성
        this.questionService.create(subject, content); 
        return "redirect:/question/list"; // 질문 저장후 질문목록으로 이동
    }
}

/*
    @GetMapping(value = "/question/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        return "question_detail";
    }
*/
// 요청한 URL인 http://localhost:8080/question/detail/2의 숫자 2처럼 변하는 id값을 얻을 때에는 @PathVariable 애너테이션을 사용한다.
// @GetMapping(value = "/question/detail/{id}")에서 사용한 id와 @PathVariable("id")의 매개변수 이름이 이와 같이 동일해야 한다.

