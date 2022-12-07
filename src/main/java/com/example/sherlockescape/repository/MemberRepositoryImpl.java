package com.example.sherlockescape.repository;

import com.example.sherlockescape.domain.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.sherlockescape.domain.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom{

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Page<Member> findAllMember(Pageable pageable) {
		List<Member> result = jpaQueryFactory

					.selectFrom(member)
					.where(member.achieveBadgeCnt.gt(0))
					.limit(pageable.getPageSize())
					.offset(pageable.getOffset())
					.orderBy(member.achieveBadgeCnt.desc(),member.totalAchieveCnt.desc())
					.fetch();

		long totalSize = jpaQueryFactory
				.selectFrom(member)
				.where(member.achieveBadgeCnt.gt(0))
				.fetch().size();

		return new PageImpl<>(result, pageable, totalSize);
	}

}
