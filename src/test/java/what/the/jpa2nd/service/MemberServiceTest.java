package what.the.jpa2nd.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import what.the.jpa2nd.domain.Member;
import what.the.jpa2nd.repository.MemberRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
//    @Rollback(value = false)
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("NNNName");

        // when
        Long savedId = memberService.join(member);

        // then
        assertEquals(member, memberRepository.findOne(savedId));

    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예약() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("mr.11111");

        Member member2 = new Member();
        member2.setName("mr.11111");

        // when
        memberService.join(member1);
        memberService.join(member2); // expected 옵션 덕분에 아래 try 코드가 필요 없음
/*
        try {
            memberService.join(member2);
        } catch (IllegalStateException e) {
            return;
        }
*/

        // then
        fail("예외가 발생해야 한다.");

    }

}