package com.abelm.blog_api.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingSummaryDTO {
    private int minRating;
    private int maxRating;
    private double avgRating;
}
