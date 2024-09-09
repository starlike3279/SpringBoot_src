package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @GetMapping("/sbb")
    @ResponseBody
    public String index() {
        return "안녕하세요 sbb에 오신것을 환영합니다.";
    }

    // root 메서드를 추가하고 / URL 매핑
    @GetMapping("/")
    public String root() {
        // redirect:/question/list는 question/list URL로 페이지를 리다이렉트하라는 명령어
        return "redirect:/question/list";
    }
    // localhost:8080로 접속하면 localhost:8080/question/list로 주소가 바뀌면서 질문 목록이 있는 웹 페이지로 연결
}
