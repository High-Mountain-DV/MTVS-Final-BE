package com.khj.mtvsfinalbe.combat.controller;

import com.khj.mtvsfinalbe.combat.domain.dto.CombatRequestDTO;
import com.khj.mtvsfinalbe.combat.domain.dto.CombatResponseDTO;
import com.khj.mtvsfinalbe.combat.service.CombatService;
import com.khj.mtvsfinalbe.user.domain.User;
import com.khj.mtvsfinalbe.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.khj.mtvsfinalbe._core.utils.SecurityUtils.getCurrentUserId;

@RestController
@RequestMapping("/api/combats")
@Tag(name = "Combat API", description = "전투 데이터 관리 API")
public class CombatController {

    private final CombatService combatService;
    private final UserRepository userRepository;

    @Autowired
    public CombatController(CombatService combatService, UserRepository userRepository) {
        this.combatService = combatService;
        this.userRepository = userRepository;
    }

    @PostMapping
    @Operation(summary = "Combat 데이터 생성", description = "새로운 Combat 데이터를 생성합니다.")
    public ResponseEntity<CombatResponseDTO> saveCombat(@RequestBody CombatRequestDTO requestDto) {
        Long userId = getCurrentUserId();
        CombatResponseDTO savedCombat = combatService.saveCombat(userId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCombat);
    }

    @GetMapping("/{id}")
    @Operation(summary = "특정 Combat 데이터 조회", description = "ID를 통해 특정 Combat 데이터를 조회합니다.")
    public ResponseEntity<CombatResponseDTO> getCombatById(@PathVariable(value = "id") Long id) {
        CombatResponseDTO combat = combatService.getCombatById(id);
        return combat != null ? ResponseEntity.ok(combat) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/user")
    @Operation(summary = "특정 사용자 전투 데이터 조회", description = "특정 사용자의 모든 전투 데이터를 조회합니다.")
    public ResponseEntity<List<CombatResponseDTO>> getCombatsByUser() {
        Long userId = getCurrentUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        List<CombatResponseDTO> combats = combatService.getCombatsByUser(user);
        return ResponseEntity.ok(combats);
    }

    @GetMapping("/user/latest")
    @Operation(summary = "특정 사용자의 최신 Combat 데이터 조회", description = "특정 사용자의 최신 전투 데이터를 조회합니다.")
    public ResponseEntity<CombatResponseDTO> getLatestCombatByUser() {
        Long userId = getCurrentUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        CombatResponseDTO latestCombat = combatService.getLatestCombatByUser(user);
        return latestCombat != null ? ResponseEntity.ok(latestCombat) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // 지휘관 전용 특정 훈련생의 모든 전투 데이터를 조회할 수 있는 API (
//    @PreAuthorize("hasRole('COMMANDER')")
    @GetMapping("/commander/user/{userId}")
    @Operation(summary = "지휘관 전용 특정 훈련생 전투 데이터 조회", description = "지휘관이 특정 훈련생의 모든 전투 데이터를 조회합니다.")
    public ResponseEntity<List<CombatResponseDTO>> getCombatsByUserForUnreal(@PathVariable(value = "userId") Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        List<CombatResponseDTO> combats = combatService.getCombatsByUser(user);
        return ResponseEntity.ok(combats);
    }

    // 지휘관 전용 특정 훈련생의 최신 전투 데이터를 조회할 수 있는 API
//    @PreAuthorize("hasRole('COMMANDER')")
    @GetMapping("/commander/user/{userId}/latest")
    @Operation(summary = "지휘관 전용 특정 훈련생의 최신 Combat 데이터 조회", description = "지휘관이 특정 훈련생의 최신 전투 데이터를 조회합니다.")
    public ResponseEntity<CombatResponseDTO> getLatestCombatByUserForUnreal(@PathVariable(value = "userId") Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        CombatResponseDTO latestCombat = combatService.getLatestCombatByUser(user);
        return latestCombat != null ? ResponseEntity.ok(latestCombat) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
