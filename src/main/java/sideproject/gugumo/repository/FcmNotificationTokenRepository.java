package sideproject.gugumo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sideproject.gugumo.domain.entity.FcmNotificationToken;
import sideproject.gugumo.domain.entity.member.Member;

import java.util.List;
import java.util.Optional;

@Repository
public interface FcmNotificationTokenRepository extends JpaRepository<FcmNotificationToken, Long> {

    public boolean existsByMember(Member member);

    public void deleteAllByMember(Member member);

    public List<FcmNotificationToken> findByMember(Member member);

}
