package com.justkidding.www.dto;

import com.justkidding.www.enums.Status;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChallengeDto {
    private String name;
    private Long user_id;
    private String description;
    private Status status;
    private LocalDateTime start_time = LocalDateTime.now();
    private LocalDateTime end_time;
}
