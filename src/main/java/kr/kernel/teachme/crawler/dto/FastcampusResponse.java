package kr.kernel.teachme.crawler.dto;

import lombok.Data;

import java.util.List;

@Data
public class FastcampusResponse {
    private Data data;

    @lombok.Data
    public static class Data {
        private CategoryInfo categoryInfo;
    }

    @lombok.Data
    public static class CategoryInfo {
        private int id;
        private String type;
        private String state;
        private int flags;
        private String createdAt;
        private String updatedAt;
        private String code;
        private String slug;
        private String title;
        private String description;
        private List<Course> courses;
    }

    @lombok.Data
    public static class Course {
        private int id;
        private String type;
        private String state;
        private String slug;
        private String publicTitle;
        private String publicDescription;
        private List<String> keywords;
        private Extras extras;
        private String desktopCardAsset;
        private String pageTemplate;
        private CardInfo cardInfo;
    }

    @lombok.Data
    public static class Extras {
        private Survey survey;
        private Preview preview;
        private String vimeoId;
        private boolean isOpenAll;
        private boolean hidePeriod;
        private boolean hasCodeEditor;
        private boolean hasCodeEditorOnB2C;
        private boolean usePassCertification;
        private String representativeVimeoId;
    }

    @lombok.Data
    public static class Survey {
        private String id;
        private boolean onUse;
        private String targetProgress;
    }

    @lombok.Data
    public static class Preview {
        private B2BPreview b2B;
        private B2CPreview b2C;
    }

    @lombok.Data
    public static class B2BPreview {
        private int previewRate;
        private List<String> courseClipIds;
        private boolean isPreviewUsed;
    }

    @lombok.Data
    public static class B2CPreview {
        private int previewRate;
        private List<String> courseClipIds;
        private boolean isPreviewUsed;
    }

    @lombok.Data
    public static class Format {
        private int id;
        private String type;
        private String state;
        private int flags;
        private String createdAt;
        private String updatedAt;
        private String code;
        private String title;
        private String description;
    }

    @lombok.Data
    public static class CardInfo {
        private String text;
        private String bgColor;
        private String textColor;
    }
}
