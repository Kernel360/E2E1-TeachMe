package kr.kernel360.teachme.lecture.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Table(name = "platform")
public class Platform {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Builder
    protected Platform(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
