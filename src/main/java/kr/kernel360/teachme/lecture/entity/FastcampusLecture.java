package kr.kernel360.teachme.lecture.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    private String publicDescription;

    private String keywords;

    @Column(columnDefinition = "TEXT")
    private String desktopCardAsset;

    @Builder
    protected FastcampusLecture(Long uniqueId, String state, String slug, String publicTitle, String publicDescription, String keywords, String desktopCardAsset){
        this.uniqueId=id;
        this.state=state;
        this.slug=slug;
        Assert.hasLength(publicTitle, "Fastcampus lecture title must not be empty");
        this.publicTitle=publicTitle;
        this.publicDescription=publicDescription;
        this.keywords=keywords;
        this.desktopCardAsset=desktopCardAsset;
    }

}


