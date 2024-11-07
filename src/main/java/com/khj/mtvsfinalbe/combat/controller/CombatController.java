package com.khj.mtvsfinalbe.combat.controller;

import com.khj.mtvsfinalbe.combat.dto.CombatRequestDTO;
import com.khj.mtvsfinalbe.combat.dto.CombatResponseDTO;
import com.khj.mtvsfinalbe.combat.service.CombatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/combat")
@Tag(name = "Combat API", description = "시가전 전투 데이터 관리 API")
public class CombatController {

    private final CombatService combatService;

    @Autowired
    public CombatController(CombatService combatService) {
        this.combatService = combatService;
    }

    @PostMapping
    @Operation(summary = "Combat 데이터 생성", description = "새로운 Combat 데이터를 생성합니다.")
    public ResponseEntity<CombatResponseDTO> saveCombat(@RequestBody CombatRequestDTO requestDto) {
        CombatResponseDTO savedCombat = combatService.saveCombat(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCombat);
    }

    @GetMapping("/{id}")
    @Operation(summary = "특정 Combat 데이터 조회", description = "ID를 통해 특정 Combat 데이터를 조회합니다.")
    public ResponseEntity<CombatResponseDTO> getCombatById(@PathVariable Long id) {
        CombatResponseDTO combat = combatService.getCombatById(id);
        return combat != null ? ResponseEntity.ok(combat) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/user/{memberId}")
    @Operation(summary = "특정 사용자 전투 세션 조회", description = "특정 사용자의 모든 전투 세션 데이터를 조회합니다.")
    public ResponseEntity<List<CombatResponseDTO>> getCombatsByMemberId(@PathVariable Long memberId) {
        List<CombatResponseDTO> combats = combatService.getCombatsByMemberId(memberId);
        return ResponseEntity.ok(combats);
    }
}
