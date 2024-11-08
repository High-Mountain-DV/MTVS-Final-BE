package com.khj.mtvsfinalbe.user.domain.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserResponseDTO {

    private Long id;
    private String nickname;
    private String loginId;
}
