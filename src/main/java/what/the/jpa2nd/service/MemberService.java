package what.the.jpa2nd.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import what.the.jpa2nd.domain.Member;
import what.the.jpa2nd.repository.MemberRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // final 변수에 생성자 인젝션
public class MemberService {

    /**
     * 필드 인젝션 : 변경할 수 없다.
     * setter 인젝션 : 너무 쉽게 변경된다.
     * 생성자 인젝션 : 생성 시점에 명확하게 주입을 시켜준다.
     */
    // @Autowired
    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    // 중복회원 검증
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

}
