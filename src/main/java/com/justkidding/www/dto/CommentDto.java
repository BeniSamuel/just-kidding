package com.justkidding.www.dto;

import lombok.Getter;

@Getter
public class CommentDto {
    private Long user_id;
    private String statement;
    private Long post_id;
}
