package what.the.jpa2nd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import what.the.jpa2nd.domain.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // select m from Member m where ...
    List<Member> findByName(String name);
}
