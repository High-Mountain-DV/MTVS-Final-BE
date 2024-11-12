package com.khj.mtvsfinalbe.combat.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AIResponseDTO {
    private String base64Image;
    private String result;
}
