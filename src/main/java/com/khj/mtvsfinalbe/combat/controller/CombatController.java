package com.khj.mtvsfinalbe.combat.controller;

import com.khj.mtvsfinalbe.combat.domain.Combat;
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
    public ResponseEntity<Combat> saveCombat(@RequestBody Combat combat) {
        Combat savedCombat = combatService.saveCombat(combat);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCombat);
    }

    @GetMapping("/{id}")
    @Operation(summary = "특정 Combat 데이터 조회", description = "ID를 통해 특정 Combat 데이터를 조회합니다.")
    public ResponseEntity<Combat> getCombatById(@PathVariable Long id) {
        return combatService.getCombatById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/user/{memberId}")
    @Operation(summary = "특정 사용자 전투 세션 조회", description = "특정 사용자의 모든 전투 세션 데이터를 조회합니다.")
    public ResponseEntity<List<Combat>> getCombatsByMemberId(@PathVariable Long memberId) {
        List<Combat> combats = combatService.getCombatsByMemberId(memberId);
        return ResponseEntity.ok(combats);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Combat 데이터 삭제", description = "ID를 통해 특정 Combat 데이터를 삭제합니다.")
    public ResponseEntity<Void> deleteCombat(@PathVariable Long id) {
        combatService.deleteCombat(id);
        return ResponseEntity.noContent().build();
    }
}
