package kr.kernel.teachme.crawler.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.util.Assert;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Table(name = "fastcampus_lecture")
@Getter
public class FastcampusLecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long uniqueId;

    private String state;

    private String slug;

    private String publicTitle;

    @Column(columnDefinition = "TEXT")
    private String publicDescription;

    @Column(columnDefinition = "TEXT")
    private String keywords;

    @Column(columnDefinition = "TEXT")
    private String desktopCardAsset;

    private int categoryId;

    private int subCategoryId;

    private int listPrice;

    private int salePrice;

    private String instructor;

    private int totalClassHours;

    @ColumnDefault("false")
    private boolean detailUploadFlag;

    @Builder
    protected FastcampusLecture(Long uniqueId, String state, String slug, String publicTitle, String publicDescription, String keywords, String desktopCardAsset){
        this.uniqueId=uniqueId;
        this.state=state;
        this.slug=slug;
        Assert.hasLength(publicTitle, "Fastcampus lecture title must not be empty");
        this.publicTitle=publicTitle;
        this.publicDescription=publicDescription;
        this.keywords=keywords;
        this.desktopCardAsset=desktopCardAsset;
    }

    public void updateDetailInfo(int categoryId, int subCategoryId, int listPrice, int salePrice, String instructor, int totalClassHours){
        this.categoryId=categoryId;
        this.subCategoryId=subCategoryId;
        this.listPrice=listPrice;
        this.salePrice=salePrice;
        this.instructor=instructor;
        this.totalClassHours=totalClassHours;
        this.detailUploadFlag = true;
    }

}


