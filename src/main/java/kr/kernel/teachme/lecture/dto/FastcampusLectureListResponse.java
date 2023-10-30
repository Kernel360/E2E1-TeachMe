package kr.kernel.teachme.lecture.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FastcampusLectureListResponse {
    private Data data;

    @lombok.Data
    public static class Data {
        private List<Category> categoryList;
    }

    @lombok.Data
    public static class Category {
        private List<Course> courses;
    }

    @lombok.Data
    public static class Course {
        private int id;
        private String state;
        private String slug;
        private String publicTitle;
        private String publicDescription;
        private List<String> keywords;
        private Format format;
        private String desktopCardAsset;
    }

    @lombok.Data
    public static class Format {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        private LocalDateTime createdAt;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        private LocalDateTime updatedAt;
    }
}
