package kernel360.trackyweb.member.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kernel360.trackyweb.member.infrastructure.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByMemberId(String memberId);

}
