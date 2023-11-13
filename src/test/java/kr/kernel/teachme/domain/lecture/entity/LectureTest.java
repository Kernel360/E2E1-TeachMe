package kr.kernel.teachme.domain.lecture.entity;

import kr.kernel.teachme.domain.crawler.dto.FastcampusLectureUpdateResponse;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LectureTest {

    @Test
    void testLectureBuilder() {
        Date testDate = new Date();
        Lecture lecture = Lecture.builder()
                .lectureId(1L)
                .platform("Fastcampus")
                .title("테스트 강의")
                .description("테스트 설명")
                .keywords("테스트, 강의")
                .url("http://test.com")
                .img("http://test.com/img.jpg")
                .price(10000)
                .discountPrice(8000)
                .instructor("홍길동")
                .createDate(testDate)
                .updateDate(testDate)
                .build();

        assertEquals(1L, lecture.getLectureId());
        assertEquals("Fastcampus", lecture.getPlatform());
        assertEquals("테스트 강의", lecture.getTitle());
        assertEquals("테스트 설명", lecture.getDescription());
        assertEquals("테스트, 강의", lecture.getKeywords());
        assertEquals("http://test.com", lecture.getUrl());
        assertEquals("http://test.com/img.jpg", lecture.getImg());
        assertEquals(10000, lecture.getPrice());
        assertEquals(8000, lecture.getDiscountPrice());
        assertEquals("홍길동", lecture.getInstructor());
        assertEquals(testDate, lecture.getCreateDate());
        assertEquals(testDate, lecture.getUpdateDate());
    }

    @Test
    void testUpdateFastcampusDetailInfo() {
        Lecture lecture = new Lecture();
        FastcampusLectureUpdateResponse updateResponse = new FastcampusLectureUpdateResponse();
        updateResponse.setPrice(12000);
        updateResponse.setDiscountPrice(10000);
        updateResponse.setInstructor("이몽룡");
        updateResponse.setTotalClassHours(10);
        updateResponse.setCreatedAt(null);
        updateResponse.setUpdatedAt(null);
        updateResponse.setPublicDescription("업데이트된 설명");
        updateResponse.setKeywords("업데이트, 강의");

        lecture.updateFastcampusDetailInfo(updateResponse);

        assertEquals(12000, lecture.getPrice());
        assertEquals(10000, lecture.getDiscountPrice());
        assertEquals("이몽룡", lecture.getInstructor());
        assertEquals(600, lecture.getDuration());
        assertEquals(true, lecture.isDetailUploadFlag());
        assertEquals("업데이트된 설명", lecture.getDescription());
        assertEquals("업데이트, 강의", lecture.getKeywords());
    }

}
