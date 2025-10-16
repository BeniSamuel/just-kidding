package com.justkidding.www.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeDto {
    private Long post_id;
    private Long user_id;
}
