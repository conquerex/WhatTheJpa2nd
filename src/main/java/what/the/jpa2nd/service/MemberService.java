package what.the.jpa2nd.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import what.the.jpa2nd.domain.Member;
import what.the.jpa2nd.repository.MemberRepository;
import what.the.jpa2nd.repository.OldMemberRepository;

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
//    private final OldMemberRepository oldMemberRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
//        oldMemberRepository.save(member);
        memberRepository.save(member);
        return member.getId();
    }

    // 중복회원 검증
    private void validateDuplicateMember(Member member) {
//        List<Member> findMembers = oldMemberRepository.findByName(member.getName());
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
//        return oldMemberRepository.findAll();
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId).get();
//        return oldMemberRepository.findOne(memberId);
    }

    /**
     * Member를 return 하지 않는 이유
     * - 커맨드와 쿼리를 구분
     */
    @Transactional
    public void update(Long id, String name) {
//        Member member = oldMemberRepository.findOne(id);
        Member member = memberRepository.findById(id).get();
        member.setName(name);
    }
}
