package com.smriti.smartload.controller;
import com.smriti.smartload.dto.OptimizeRequest;
import com.smriti.smartload.service.LoadOptimizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/load-optimizer")
public class LoadOptimizerController {

    @Autowired
    private LoadOptimizerService service;

    @PostMapping("/optimize")
    public ResponseEntity<?> optimize(@RequestBody OptimizeRequest request) {

        if (request.getTruck() == null || request.getOrders() == null) {
            return ResponseEntity.badRequest().body("Invalid input");
        }

        return ResponseEntity.ok(service.optimize(request));
    }
}
