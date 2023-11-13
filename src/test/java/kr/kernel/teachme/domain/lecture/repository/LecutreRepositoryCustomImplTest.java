package kr.kernel.teachme.domain.lecture.repository;

import com.querydsl.core.QueryModifiers;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.kernel.teachme.domain.lecture.dto.SearchRequest;
import kr.kernel.teachme.domain.lecture.entity.Lecture;
import kr.kernel.teachme.domain.lecture.entity.QLecture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LecutreRepositoryCustomImplTest {

    private LectureRepositoryCustomImpl lectureRepository;
    private JPAQueryFactory queryFactory;

    @BeforeEach
    void setUp() {
        queryFactory = mock(JPAQueryFactory.class);
        lectureRepository = new LectureRepositoryCustomImpl(queryFactory);

        JPAQuery mockQuery = mock(JPAQuery.class);
        when(queryFactory.selectFrom(any(QLecture.class))).thenReturn(mockQuery);
        when(mockQuery.where(any(Predicate.class))).thenReturn(mockQuery);
        when(mockQuery.orderBy(any(OrderSpecifier.class))).thenReturn(mockQuery);
        when(mockQuery.fetchResults()).thenReturn(new QueryResults<>(new ArrayList<Lecture>(), QueryModifiers.EMPTY, 0L));
    }

    @DisplayName("findBySearchOption이 페이징 처리를 잘 하는지")
    @Test
    void findBySearchOptionReturnsPageOfLecture() {
        Pageable pageable = PageRequest.of(0, 10);
        SearchRequest searchRequest = new SearchRequest();

        Page<Lecture> result = lectureRepository.findBySearchOption(pageable, searchRequest);

        assertTrue(result instanceof Page);
        assertTrue(result.getContent().isEmpty());
    }
}
