package com.mysite.sbb.answer;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    // /answer/create/{id}와 같은 URL 요청 시 createAnswer 메서드가 호출되도록 @PostMapping으로 매핑
    // @PostMapping 애너테이션은 @GetMapping과 동일하게 URL 매핑을 담당하는 역할을 하지만, POST 요청을 처리하는 경우에 사용
    /*
        @PostMapping(value ="/create/{id}") 대신 @PostMapping("/create/{id}")처럼 value는 생략해도 된다.
    */
    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id, @RequestParam(value="content") String content) {
        Question question = this.questionService.getQuestion(id);
        this.answerService.create(question, content);
        return String.format("redirect:/question/detail/%s", id);
    }
    /*
    질문 컨트롤러의 detail 메서드와 달리 createAnswer 메서드의 매개변수에는 @RequestParam(value="content") String content가 추가되었다.
    이는 앞서 작성한 템플릿(question_detail.html)에서 답변으로 입력한 내용(content)을 얻으려고 추가한 것이다. 
    템플릿의 답변 내용에 해당하는 <textarea>의 name 속성명이 content이므로 여기서도 변수명을 content로 사용한다.
    /create/{id}에서 {id}는 질문 엔티티의 id이므로 이 id값으로 질문을 조회하고 값이 없을 경우에는 404 오류가 발생할 것이다.
    */
}
