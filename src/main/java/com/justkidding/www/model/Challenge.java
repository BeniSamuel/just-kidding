package com.justkidding.www.model;

import com.justkidding.www.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @Enumerated(EnumType.STRING)
    private Status challenge_status;

    private LocalDateTime start_time;

    private LocalDateTime end_time;

    private int remaining_time;

    public Challenge (String name, String description, User author, Status status, LocalDateTime start_time, LocalDateTime end_time, int duration) {
        this.name = name;
        this.description = description;
        this.author = author;
        this.challenge_status = status;
        this.start_time = start_time;
        this.end_time = end_time;
        this.remaining_time = duration;
    }
}
