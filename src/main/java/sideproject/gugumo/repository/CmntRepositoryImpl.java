package sideproject.gugumo.repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import sideproject.gugumo.domain.dto.CmntDto;
import sideproject.gugumo.domain.dto.memberDto.CustomUserDetails;

import sideproject.gugumo.domain.dto.QCmntDto;
import sideproject.gugumo.domain.entity.Member;
import sideproject.gugumo.domain.entity.MemberStatus;

import java.util.List;


import static sideproject.gugumo.domain.entity.QCmnt.cmnt;
import static sideproject.gugumo.domain.entity.QMember.member;
import static sideproject.gugumo.domain.entity.post.QPost.post;


public class CmntRepositoryImpl implements CmntRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final MemberRepository memberRepository;


    public CmntRepositoryImpl(EntityManager em, MemberRepository memberRepository) {
        this.queryFactory = new JPAQueryFactory(em);
        this.memberRepository = memberRepository;
    }


    @Override
    public List<CmntDto> findComment(Long postId, CustomUserDetails principal, Pageable pageable) {

        Member user =
                principal == null ?
                        null : memberRepository.findByUsername(principal.getUsername()).get();

        if (user != null && user.getStatus() != MemberStatus.active) {
            user = null;
        }


        //isYours, isAuthorExpired 추가
        List<CmntDto> result = queryFactory.select(new QCmntDto(
                        cmnt.id,
                        cmnt.member.nickname,
                        user != null ? cmnt.member.eq(user) : Expressions.FALSE,
                        cmnt.member.isNull().or(cmnt.member.status.eq(MemberStatus.delete)),
                        cmnt.content,
                        cmnt.createDate,
                        cmnt.isNotRoot,
                        cmnt.parentCmnt.id,
                        cmnt.orderNum
                ))
                .from(cmnt)
                .join(cmnt.post, post)
                .leftJoin(cmnt.member, member)
                .where(
                        cmnt.post.id.eq(postId), cmnt.isDelete.isFalse()
                )
                .orderBy(cmnt.orderNum.asc(), cmnt.createDate.asc())
                .fetch();

        return result;
    }
}
