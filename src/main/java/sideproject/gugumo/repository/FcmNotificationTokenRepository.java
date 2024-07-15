package sideproject.gugumo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sideproject.gugumo.domain.entity.notification.FcmNotificationToken;
import sideproject.gugumo.domain.entity.member.Member;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FcmNotificationTokenRepository extends JpaRepository<FcmNotificationToken, Long> {


    public boolean existsByMemberAndToken(Member member, String token);

    public Optional<FcmNotificationToken> findByMemberAndToken(Member member, String token);

    @Modifying(clearAutomatically = true)
    @Query("delete from FcmNotificationToken t where t.token=:token")
    @Transactional
    public void deleteAllByToken(@Param("token") String token);

    public List<FcmNotificationToken> findByMember(Member member);

    public List<FcmNotificationToken> findByLastUsedDateBefore(LocalDateTime expire);

}
