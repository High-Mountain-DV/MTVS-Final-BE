package com.khj.mtvsfinalbe.combat.controller;

import com.khj.mtvsfinalbe.combat.domain.dto.CombatRequestDTO;
import com.khj.mtvsfinalbe.combat.domain.dto.CombatResponseDTO;
import com.khj.mtvsfinalbe.combat.service.CombatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.khj.mtvsfinalbe._core.utils.SecurityUtils.getCurrentUserId;

@RestController
@RequestMapping("/api/combats")
@Tag(name = "Combat API", description = "전투 데이터 관리 API")
public class CombatController {

    private final CombatService combatService;

    @Autowired
    public CombatController(CombatService combatService) {
        this.combatService = combatService;
    }

    /**
     * 새로운 Combat 데이터를 생성하는 엔드포인트
     * @param requestDto 전투 데이터 요청 객체
     * @return 생성된 Combat 데이터 응답 DTO
     */
    @PostMapping
    @Operation(summary = "Combat 데이터 생성", description = "새로운 Combat 데이터를 생성합니다.")
    public ResponseEntity<CombatResponseDTO> saveCombat(@RequestBody CombatRequestDTO requestDto) {
        Long memberId = getCurrentUserId();
        CombatResponseDTO savedCombat = combatService.saveCombat(memberId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCombat);
    }

    /**
     * 특정 Combat 데이터를 ID를 사용해 조회하는 엔드포인트
     * @param id 조회할 Combat 데이터 ID
     * @return 조회된 Combat 데이터 응답 DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "특정 Combat 데이터 조회", description = "ID를 통해 특정 Combat 데이터를 조회합니다.")
    public ResponseEntity<CombatResponseDTO> getCombatById(@PathVariable(value = "id") Long id) {  // <- 변경된 부분
        CombatResponseDTO combat = combatService.getCombatById(id);
        return combat != null ? ResponseEntity.ok(combat) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /**
     * 특정 사용자의 모든 전투 데이터를 최신순으로 조회하는 엔드포인트
     * @param memberId 조회할 사용자 ID
     * @return 전투 데이터 리스트
     */
    @GetMapping("/user/{memberId}")
    @Operation(summary = "특정 사용자 전투 데이터 조회", description = "특정 사용자의 모든 전투 데이터를 조회합니다.")
    public ResponseEntity<List<CombatResponseDTO>> getCombatsByMemberId(@PathVariable Long memberId) {
        List<CombatResponseDTO> combats = combatService.getCombatsByMemberId(memberId);
        return ResponseEntity.ok(combats);
    }

    /**
     * 특정 사용자의 최신 Combat 데이터를 조회하는 엔드포인트
     * @param memberId 조회할 사용자 ID
     * @return 최신 Combat 데이터 응답 DTO
     */
    @GetMapping("/user/{memberId}/latest")
    @Operation(summary = "특정 사용자의 최신 Combat 데이터 조회", description = "특정 사용자의 최신 전투 데이터를 조회합니다.")
    public ResponseEntity<CombatResponseDTO> getLatestCombatByMemberId(@PathVariable Long memberId) {
        CombatResponseDTO latestCombat = combatService.getLatestCombatByMemberId(memberId);
        return latestCombat != null ? ResponseEntity.ok(latestCombat) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
