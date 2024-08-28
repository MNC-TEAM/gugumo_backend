package sideproject.gugumo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sideproject.gugumo.domain.entity.meeting.Meeting;
import sideproject.gugumo.domain.entity.meeting.MeetingStatus;
import sideproject.gugumo.domain.entity.post.Post;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    /**
     * findByPostId를 사용하면?->postId를 받음, repository에서 post 테이블과 조인함->그 테이블의 postId 값을 찾아 비교
     * 즉 불필요한 조인이 발생함
     * 따라서 아래와 같이 하거나
     *
     * @param post
     * @return
     * @Query("select m from Meeting m where m.post.id=:postId)
     * public Optional<Meeting> findByPostId(@Param("postId") Long postId)
     * 와 같이 jpql 퀴리를 직접 작성하여 해결해야함
     */
    public Optional<Meeting> findByPost(Post post);


    @Modifying(clearAutomatically = true)
    @Query("update Meeting m set m.status='END' where m.meetingDeadline<:date and m.status='RECRUIT'")
    int expireMeetingStatus(LocalDate date);
}
