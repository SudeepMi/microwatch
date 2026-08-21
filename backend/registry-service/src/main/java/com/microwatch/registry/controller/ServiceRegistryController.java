package com.microwatch.registry.controller;

import com.microwatch.registry.dto.ServiceRequestDto;
import com.microwatch.registry.dto.ServiceResponseDto;
import com.microwatch.registry.service.RegistryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/services")
@CrossOrigin(origins = "*")
public class ServiceRegistryController {

    private final RegistryService registryService;

    public ServiceRegistryController(RegistryService registryService) {
        this.registryService = registryService;
    }

    @GetMapping
    public ResponseEntity<List<ServiceResponseDto>> getAllServices() {
        return ResponseEntity.ok(registryService.getAllServices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponseDto> getServiceById(@PathVariable Long id) {
        return ResponseEntity.ok(registryService.getServiceById(id));
    }

    @PostMapping
    public ResponseEntity<ServiceResponseDto> registerService(@Valid @RequestBody ServiceRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(registryService.registerService(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponseDto> updateService(@PathVariable Long id, @Valid @RequestBody ServiceRequestDto request) {
        return ResponseEntity.ok(registryService.updateService(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        registryService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}
