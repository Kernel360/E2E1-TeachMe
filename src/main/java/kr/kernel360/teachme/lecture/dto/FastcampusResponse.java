package kr.kernel360.teachme.lecture.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class FastcampusResponse {
    private Data data;
    @Getter
    @Setter
    @ToString
    public static class Data {
        private CategoryInfo categoryInfo;
        private List<Course> courses;
    }

    @Getter
    @Setter
    @ToString
    public static class CategoryInfo {
        private int id;
        private String type;
        private String state;
        private int flags;
        private Date updatedAt;
    }
    @Getter
    @Setter
    @ToString
    public static class Course {
        private int id;
        private String type;
        private String state;
        private String publicTitle;
        private String desktopCardAsset;
    }

}
