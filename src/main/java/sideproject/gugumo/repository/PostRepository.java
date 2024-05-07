package sideproject.gugumo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sideproject.gugumo.domain.Member;
import sideproject.gugumo.domain.post.Post;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    public Optional<Post> findByIdAndIsDeleteFalse(Long id);

    public Page<Post> findByMemberAndIsDeleteFalse(Pageable pageable, Member member);
}
