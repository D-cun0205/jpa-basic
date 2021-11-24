import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * JUnit으로 작성된 테스트를 스프링 프레임워크와 통합하는 설정
 * 설정이 완료되면 테스트가 스프링 컨테이너안에서 실행되므로 스프링 프레임워크가 제공하는 기능(ex: @Autowired)들을 사용할 수 있다.
 * */
@RunWith(SpringJUnit4ClassRunner.class)
//테스트 케이스를 실행할 때 사용할 스프링 설정 정보 지정
@ContextConfiguration(locations = "classpath:appConfig.xml")
@Transactional // test 디렉토리에서의 @Transactional은 모든 테스트후 자동 롤백 진행
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void join() throws Exception {
        Member member = new Member();
        member.setUsername("kim");

        Long saveId = memberService.join(member);

        assertEquals(member, memberRepository.findOne(saveId));
    }

    //@Test의 속성 값으로 expected를 설정하고 실행 했을 때 동일한 Exception이 발생해야 정상 처리
    @Test(expected = IllegalStateException.class)
    public void sameUser() throws Exception {
        Member member1 = new Member();
        member1.setUsername("kim");

        Member member2 = new Member();
        member2.setUsername("kim");

        memberService.join(member1);
        memberService.join(member2); //예외 발생

        fail("예외가 발생해야 한다.");
    }
}
