package com.khj.mtvsfinalbe.profile.controller;

import com.khj.mtvsfinalbe.profile.domain.dto.ProfileGraphRequestDTO;
import com.khj.mtvsfinalbe.profile.domain.dto.ProfileGraphResponseDTO;
import com.khj.mtvsfinalbe.profile.service.ProfileGraphService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ProfileGraph API 엔드포인트
 */
@RestController
@RequestMapping("/api/profiles")
@Tag(name = "Profile API", description = "사용자 프로필 누적 그래프 API")
public class ProfileGraphController {

    private final ProfileGraphService profileGraphService;

    public ProfileGraphController(ProfileGraphService profileGraphService) {
        this.profileGraphService = profileGraphService;
    }

    /**
     * 그래프 데이터를 저장 또는 업데이트
     *
     * @param request 사용자 요청 데이터
     * @return 성공 상태
     */
    @PostMapping("/graph")
    @Operation(summary = "각각의 그래프 데이터 저장 또는 업데이트", description = "Combat 데이터를 활용하여 그래프 데이터를 저장하거나 업데이트합니다.")
    public ResponseEntity<Void> saveOrUpdateGraph(@RequestBody ProfileGraphRequestDTO request) {
        profileGraphService.saveOrUpdateGraph(request);
        return ResponseEntity.ok().build();
    }

    /**
     * 특정 사용자의 누적 그래프 데이터를 조회
     *
     * @param userId 사용자 ID
     * @return 누적 그래프 데이터 리스트
     */
    @GetMapping("/graph")
    @Operation(summary = "사용자의 누적 그래프 조회", description = "사용자의 모든 누적 그래프 데이터를 조회합니다.")
    public ResponseEntity<List<ProfileGraphResponseDTO>> getGraphsByUser(@RequestParam Long userId) {
        return ResponseEntity.ok(profileGraphService.getGraphsByUser(userId));
    }
}