package com.nvd.electroshop.controller;

import com.nvd.electroshop.dto.request.AttributeRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.AttributeResponse;
import com.nvd.electroshop.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attributes")
public class AttributeController {

    @Autowired
    private AttributeService attributeService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AttributeResponse>>> getAllAttributes() {

        return ResponseEntity.ok(attributeService.getAllAttributes());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AttributeResponse>> createAttribute(@RequestBody AttributeRequest attributeRequest) {

        return ResponseEntity.ok(attributeService.createAttribute(attributeRequest));
    }
}
