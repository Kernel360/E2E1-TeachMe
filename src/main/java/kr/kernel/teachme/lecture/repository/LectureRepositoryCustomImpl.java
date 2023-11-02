package kr.kernel.teachme.lecture.repository;


import com.querydsl.core.QueryResults;
import kr.kernel.teachme.lecture.dto.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
	private final JPAQueryFactory queryFactory;

	public LectureRepositoryCustomImpl(JPAQueryFactory queryFactory) {
		super(Lecture.class);
		this.queryFactory = queryFactory;
	}

	@Override
	public Page<Lecture> findBySearchOption(Pageable pageable, SearchRequest search) {
		JPQLQuery<Lecture> query = queryFactory.selectFrom(lecture)
			.where(eqFilter(search.getSearchFilter()), eqOption(search.getSearchSelect(), search.getSearchInput()), isUpdated())
			.orderBy(sortList(search.getSearchSort()));
		QueryResults<Lecture> results = this.getQuerydsl().applyPagination(pageable, query).fetchResults();
		return new PageImpl<>(results.getResults(), pageable, results.getTotal());
	}

	private BooleanExpression eqFilter(String filter) {
		if (filter == null || filter.isEmpty() || filter.equals("all")) {
			return null;
		}
		return lecture.platform.eq(filter);
	}

	private BooleanExpression eqOption(String type,String keyword) {
		switch (type) {
            case "title":
                return lecture.title.containsIgnoreCase(keyword);
            case "instructor":
                return lecture.instructor.containsIgnoreCase(keyword);
            case "keywords":
                return lecture.keywords.containsIgnoreCase(keyword);
			default:
				return lecture.title.containsIgnoreCase(keyword);
		}

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
