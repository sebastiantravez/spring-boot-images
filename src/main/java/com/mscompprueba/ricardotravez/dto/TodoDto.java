package com.mscompprueba.ricardotravez.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto {
    private Long id;
    private String todo;
    private boolean completed;
}
