package kr.kernel.teachme.lecture.repository;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import static kr.kernel.teachme.lecture.entity.QLecture.lecture;
import kr.kernel.teachme.lecture.entity.Lecture;

public class LectureRepositoryCustomImpl extends QuerydslRepositorySupport implements LectureRepositoryCustom{
	@Autowired
	private JPAQueryFactory queryFactory;

	public LectureRepositoryCustomImpl() {
		super(Lecture.class);
	}

	@Override
	public List<Lecture> findBySearchOption(Pageable pageable, String filter, String sort, String option, String keyword) {
		JPQLQuery<Lecture> query = queryFactory.selectFrom(lecture)
			.where(eqFilter(filter), eqOption(option, keyword), isUpdated())
			.orderBy(sortList(sort))
			;
		return this.getQuerydsl().applyPagination(pageable, query).fetch();
	}

	private BooleanExpression eqFilter(String filter) {
		if (filter == null || filter.isEmpty()) {
			return null;
		}
		return lecture.platform.eq(filter);
	}

	private BooleanExpression eqOption(String type,String keyword) {
		if (type.equals("title")) {
			return lecture.title.containsIgnoreCase(keyword);
		} else if (type.equals("instructor")){
			return lecture.instructor.containsIgnoreCase(keyword);
		} else if (type.equals("keywords")) {
			return lecture.keywords.containsIgnoreCase(keyword);
		}
		return null;
	}

	private BooleanExpression isUpdated() {
		return lecture.detailUploadFlag.eq(true);
	}

	private OrderSpecifier<?> sortList(String sort) {
		if (sort.equals("title")) {
			return lecture.title.asc();
		} else if (sort.equals("updateDate")) {
			return lecture.updateDate.desc();
		} else if (sort.equals("salePrice")) {
			return lecture.discountPrice.asc();
		}
		return null;
	}
}
