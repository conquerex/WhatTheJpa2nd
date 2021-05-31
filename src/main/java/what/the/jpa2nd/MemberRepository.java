package what.the.jpa2nd;

import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {
    @PersistenceContext
    private EntityManager em;

    public Long save(Member member) {
        em.persist(member);

        // 커맨드와 쿼리를 분리하기
        return member.getId();
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}