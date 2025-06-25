package com.justkidding.www.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    private String file_path;

    private String description;

    public Post (String title, User author, String file_path, String description) {
        this.title = title;
        this.author = author;
        this.file_path = file_path;
        this.description = description;
    }
}
