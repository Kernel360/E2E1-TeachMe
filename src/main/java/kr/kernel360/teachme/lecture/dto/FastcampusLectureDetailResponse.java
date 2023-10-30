package kr.kernel360.teachme.lecture.dto;

import kr.kernel360.teachme.lecture.entity.FastcampusLecture;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FastcampusLectureDetailResponse {
    private Data data;

    @lombok.Data
    public static class Data{
        private Products products;
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
        private String totalClassHours;
    }

//    public FastcampusLecture toEntity(){
//        if (data != null) {
//            Data.Products products = data.getProducts();
//            Data.Course course = data.getCourse();
//
//        return FastcampusLecture.builder()
//                .categoryId(categoryId)
//                .subCategoryId(subCategoryId)
//                .listPrice(listPrice)
//                .salePrice(salePrice)
//                .instructor(instructor)
//                .totalClassHours(totalClassHours)
//                .build();
//    }



}
