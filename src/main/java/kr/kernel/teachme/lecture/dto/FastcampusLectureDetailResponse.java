package kr.kernel.teachme.lecture.dto;

import kr.kernel.teachme.lecture.entity.FastcampusLecture;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FastcampusLectureDetailResponse {
    private Data data;

    @lombok.Data
    public static class Data{
        private List<Products> products;
        private Course course;
    }
    @lombok.Data
    public static class Products{
        private int categoryId;
        private int subCategoryId;
        private int listPrice;
        private int salePrice;
    }

    @lombok.Data
    public static class Course{
        private String instructor;
        private int totalClassHours;
    }


}