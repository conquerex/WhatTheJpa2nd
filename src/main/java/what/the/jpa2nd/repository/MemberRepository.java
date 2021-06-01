package what.the.jpa2nd.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import what.the.jpa2nd.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;

@Repository // 스프링빈으로 등록
@RequiredArgsConstructor
public class MemberRepository {

//    @PersistenceContext // JPA에서 EM 주입
    // @Autowired 사용 가능
    // Spring boot가 이를 가능하게 함
    // 그래서 @RequiredArgsConstructor
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
