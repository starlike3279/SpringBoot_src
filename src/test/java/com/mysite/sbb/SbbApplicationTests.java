package com.mysite.sbb;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class SbbApplicationTests {

    @Autowired
    private QuestionRepository questionRepository;

    // 질문 데이터 저장하기
    @Test
    void testJpa() {
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q1);  // 첫번째 질문 저장

        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q2);  // 두번째 질문 저장
    }

    // findAll 메서드
    @Test
    void testJpa2() {
        // findAll 메서드 : question 테이블에 저장된 모든 데이터를 조회하기 위함
        List<Question> all = this.questionRepository.findAll();
        assertEquals(2, all.size()); // assertEquals(기댓값, 실젯값)

        Question q = all.get(0);
        assertEquals("sbb가 무엇인가요?", q.getSubject());
    }

    // findById 메서드
    @Test
    void testJpa3() {
        // findById로 호출한 값이 존재할 수도 있고, 존재하지 않을 수도 있어서 리턴 타입으로 Optional 사용 
        // Optional은 그 값을 처리하기 위한(null 값을 유연하게 처리하기 위한) 클래스
        // null을 참조하더라도 NPE가 발생하지 않도록 도와줌
        // 클래스이기 때문에 각종 메소드를 제공해줌
        Optional<Question> oq = this.questionRepository.findById(1);
        if (oq.isPresent()) { // is.Present() : 값이 존재하는지 확인
            Question q = oq.get();
            assertEquals("sbb가 무엇인가요?", q.getSubject());
        }
    }

    // findBySubject 메서드
    @Test
    void testJpa4() {
        Question q = this.questionRepository.findBySubject("sbb가 무엇인가요?");
        assertEquals(1, q.getId());
    }

    // findBySubjectAndContent 메서드
    @Test
    void testJpa5() {
        Question q = this.questionRepository.findBySubjectAndContent(
                "sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
        assertEquals(1, q.getId());
    }

    // findBySubjectLike 메서드
    @Test
    void testJpa6() {
        // sbb% : sbb로 시작하는 문자열, %sbb : sbb로 끝나는 문자열, %sbb% : sbb를 포함하는 문자열
        List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");
        Question q = qList.get(0);
        assertEquals("sbb가 무엇인가요?", q.getSubject());
    }

    // 질문 데이터 수정하기
    @Test
    void testJpa7() {
        Optional<Question> oq = this.questionRepository.findById(1);
        // assertTrue : 괄호 안의 값이 True인지를 테스트
        // oq.isPresent()가 false면 에러 발생후 테스트 종료
        assertTrue(oq.isPresent()); 
        Question q = oq.get();
        q.setSubject("수정된 제목");
        this.questionRepository.save(q);
    }

    // 질문 데이터 삭제하기
    @Test
    void testJpa8() {
        assertEquals(2, this.questionRepository.count());
        Optional<Question> oq = this.questionRepository.findById(1);
        assertTrue(oq.isPresent());
        Question q = oq.get();
        this.questionRepository.delete(q);
        assertEquals(1, this.questionRepository.count());
    }

    // 답변 데이터 저장하기
    @Autowired
    private AnswerRepository answerRepository;

    @Test
    void testJpa9() {
        Optional<Question> oq = this.questionRepository.findById(2);
        assertTrue(oq.isPresent());
        Question q = oq.get();

        Answer a = new Answer();
        a.setContent("네 자동으로 생성됩니다.");
        a.setQuestion(q);  // 어떤 질문의 답변인지 알기 위해서 Question 객체가 필요하다.
        a.setCreateDate(LocalDateTime.now());
        this.answerRepository.save(a);
    }

    // 답변 데이터 조회하기
    @Test
    void testJpa10() {
        Optional<Answer> oa = this.answerRepository.findById(1);
        assertTrue(oa.isPresent());
        Answer a = oa.get();
        assertEquals(2, a.getQuestion().getId());
    }

    // 답변 데이터를 통해 질문 데이터 찾기 vs 질문 데이터를 통해 답변 데이터 찾기
    @Transactional // Transactional 사용하면 메서드가 종료될 때까지 DB 세션이 유지됨
    @Test
    void testJpa11() {
        Optional<Question> oq = this.questionRepository.findById(2);
        assertTrue(oq.isPresent());
        Question q = oq.get();

        List<Answer> answerList = q.getAnswerList();

        assertEquals(1, answerList.size());
        assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
    }
}
