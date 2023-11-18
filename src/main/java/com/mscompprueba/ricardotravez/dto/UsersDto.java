package com.mscompprueba.ricardotravez.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String image;
    private byte[] file;
}
