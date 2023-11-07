package kr.kernel.teachme.domain.lecture.repository;


import com.querydsl.core.QueryResults;
import kr.kernel.teachme.domain.lecture.entity.QLecture;
import kr.kernel.teachme.domain.lecture.dto.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.kernel.teachme.domain.lecture.entity.Lecture;

public class LectureRepositoryCustomImpl extends QuerydslRepositorySupport implements LectureRepositoryCustom{
	@Autowired
	private final JPAQueryFactory queryFactory;

	public LectureRepositoryCustomImpl(JPAQueryFactory queryFactory) {
		super(Lecture.class);
		this.queryFactory = queryFactory;
	}

	@Override
	public Page<Lecture> findBySearchOption(Pageable pageable, SearchRequest search) {
		JPQLQuery<Lecture> query = queryFactory.selectFrom(QLecture.lecture)
			.where(eqFilter(search.getSearchFilter()), eqOption(search.getSearchSelect(), search.getSearchInput()), isUpdated())
			.orderBy(sortList(search.getSearchSort()));
		QueryResults<Lecture> results = this.getQuerydsl().applyPagination(pageable, query).fetchResults();
		return new PageImpl<>(results.getResults(), pageable, results.getTotal());
	}

	private BooleanExpression eqFilter(String filter) {
		if (filter == null || filter.isEmpty() || filter.equals("all")) {
			return null;
		}
		return QLecture.lecture.platform.eq(filter);
	}

	private BooleanExpression eqOption(String type,String keyword) {
		switch (type) {
            case "title":
                return QLecture.lecture.title.containsIgnoreCase(keyword);
            case "instructor":
                return QLecture.lecture.instructor.containsIgnoreCase(keyword);
            case "keywords":
                return QLecture.lecture.keywords.containsIgnoreCase(keyword);
			default:
				return QLecture.lecture.title.containsIgnoreCase(keyword);
		}

	}

	private BooleanExpression isUpdated() {
		return QLecture.lecture.detailUploadFlag.eq(true);
	}

	private OrderSpecifier<?> sortList(String sort) {
		if (sort.equals("title")) {
			return QLecture.lecture.title.asc();
		} else if (sort.equals("updateDate")) {
			return QLecture.lecture.createDate.desc();
		} else if (sort.equals("salePrice")) {
			return QLecture.lecture.discountPrice.asc();
		}
		return null;
	}
}
