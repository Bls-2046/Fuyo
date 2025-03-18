package com.github.fuyo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClazzEntity {

    private String courseName;
    private String coursePlace;
    private int[] courseIdx;

}
