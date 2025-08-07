package com.nvd.electroshop.controller;

import com.nvd.electroshop.dto.request.AttributeRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.AttributeResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.entity.Attribute;
import com.nvd.electroshop.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/attributes")
public class AttributeController {

    @Autowired
    private AttributeService attributeService;

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ApiResponse<List<AttributeResponse>>> getAllAttributes() {

        return ResponseEntity.ok(attributeService.getAllAttributes());
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ApiResponse<AttributeResponse>> getAttributeById(@PathVariable Long id) {

        return ResponseEntity.ok(attributeService.getAttributeById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ApiResponse<AttributeResponse>> createAttribute(@RequestBody AttributeRequest attributeRequest) {

        return ResponseEntity.ok(attributeService.createAttribute(attributeRequest));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ApiResponse<AttributeResponse>> updateAttribute(@RequestBody AttributeRequest attributeRequest, @PathVariable Long id) {

        return ResponseEntity.ok(attributeService.updateAttribute(id, attributeRequest));
    }

    @PatchMapping("{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ApiResponse<AttributeResponse>> partialUpdateAttribute(@RequestBody Map<String, Object> requests, @PathVariable Long id) {

        return ResponseEntity.ok(attributeService.partialUpdateAttribute(id, requests));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Message> deleteAttribute(@PathVariable Long id) {

        return ResponseEntity.ok(attributeService.deleteAttribute(id));
    }
}
