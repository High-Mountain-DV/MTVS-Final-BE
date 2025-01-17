package com.khj.mtvsfinalbe.profile.controller;

import com.khj.mtvsfinalbe.profile.domain.dto.ProfileGraphResponseDTO;
import com.khj.mtvsfinalbe.profile.service.ProfileGraphService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.khj.mtvsfinalbe._core.utils.SecurityUtils.getCurrentUserId;

@RestController
@RequestMapping("/api/profiles")
@Tag(name = "Profile API", description = "사용자 프로필 누적 그래프 및 최신 피드백 API")
public class ProfileGraphController {

    private final ProfileGraphService profileGraphService;

    public ProfileGraphController(ProfileGraphService profileGraphService) {
        this.profileGraphService = profileGraphService;
    }

    /**
     * 현재 로그인된 사용자의 누적 그래프 데이터를 조회
     *
     * @return 프로필 그래프 응답 DTO 리스트
     */
    @GetMapping("/graph")
    @Operation(summary = "현재 로그인된 사용자의 누적 그래프 조회", description = "현재 로그인된 사용자의 Combat 데이터를 기반으로 프로필 누적 그래프 데이터를 생성 및 반환합니다.")
    public ResponseEntity<List<ProfileGraphResponseDTO>> getProfileGraphForCurrentUser() {
        Long currentUserId = getCurrentUserId();
        List<ProfileGraphResponseDTO> profileGraphs = profileGraphService.getProfileGraphs(currentUserId);
        return ResponseEntity.ok(profileGraphs);
    }

    /**
     * 특정 사용자의 누적 그래프 데이터를 조회
     *
     * @param userId 사용자 ID
     * @return 프로필 그래프 응답 DTO 리스트
     */
    @GetMapping("/graph/user")
    @Operation(summary = "특정 사용자의 누적 그래프 조회", description = "특정 사용자의 Combat 데이터를 기반으로 프로필 누적 그래프 데이터를 생성 및 반환합니다.")
    public ResponseEntity<List<ProfileGraphResponseDTO>> getProfileGraphByUserId(@RequestParam Long userId) {
        List<ProfileGraphResponseDTO> profileGraphs = profileGraphService.getProfileGraphs(userId);
        return ResponseEntity.ok(profileGraphs);
    }

    /**
     * 현재 로그인된 사용자의 대시보드 데이터를 조회
     *
     * @return 날짜별 그래프 데이터와 최신 피드백
     */
    @GetMapping("/dashboard")
    @Operation(summary = "프로필 대시보드 조회", description = "Combat 데이터를 기반으로 날짜별 그래프 데이터와 최신 피드백을 반환합니다.")
    public ResponseEntity<Map<String, Object>> getProfileDashboard() {
        Long currentUserId = getCurrentUserId();
        Map<String, Object> dashboardData = profileGraphService.getProfileDashboard(currentUserId);
        return ResponseEntity.ok(dashboardData);
    }

    @GetMapping("/graph/top5-with-feedback")
    @Operation(summary = "상위 5개의 Combat 데이터와 최신 AI 피드백 조회",
            description = "사용자의 상위 5개의 Combat 데이터와 최신 AI 피드백을 반환합니다.")
    public ResponseEntity<Map<String, Object>> getTop5GraphDataWithFeedback() {
        Long currentUserId = getCurrentUserId();
        Map<String, Object> response = profileGraphService.getProfileDashboard(currentUserId);
        return ResponseEntity.ok(response);
    }

}
