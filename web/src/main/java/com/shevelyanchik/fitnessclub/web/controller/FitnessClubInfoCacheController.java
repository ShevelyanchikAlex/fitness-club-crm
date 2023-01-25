package com.shevelyanchik.fitnessclub.web.controller;

import com.shevelyanchik.fitnessclub.service.FitnessClubInfoCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cache-task")
public class FitnessClubInfoCacheController {
    private final FitnessClubInfoCacheService fitnessClubInfoCacheService;

    @GetMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('USER_PERMISSION')")
    public ResponseEntity<String> getDataWithFirstLevelCache() {
        fitnessClubInfoCacheService.getDataWithFirstLevelCache();
        return new ResponseEntity<>("Request has been completed.", HttpStatus.OK);
    }
}
