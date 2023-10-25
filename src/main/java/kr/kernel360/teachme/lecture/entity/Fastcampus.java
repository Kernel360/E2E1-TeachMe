package kr.kernel360.teachme.lecture.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Table(name = "fastcampus")
@Getter
public class Fastcampus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String state;

    private String slug;

    private String publicTitle;

    private String publicDescription;

    private String keywords;

    private String desktopCardAsset;

    @Builder
    protected Fastcampus(Long id, String state, String slug, String publicTitle, String publicDescription, String keywords, String desktopCardAsset){
        this.id=id;
        this.state=state;
        this.slug=slug;
        this.publicTitle=publicTitle;
        this.publicDescription=publicDescription;
        this.keywords=keywords;
        this.desktopCardAsset=desktopCardAsset;
    }

}


