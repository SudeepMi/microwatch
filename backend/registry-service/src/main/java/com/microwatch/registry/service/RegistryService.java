package com.microwatch.registry.service;

import com.microwatch.registry.dto.ServiceRequestDto;
import com.microwatch.registry.dto.ServiceResponseDto;
import com.microwatch.registry.model.ServiceEntity;
import com.microwatch.registry.repository.ServiceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RegistryService {

    private final ServiceRepository serviceRepository;

    public RegistryService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public List<ServiceResponseDto> getAllServices() {
        return serviceRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    public ServiceResponseDto getServiceById(Long id) {
        ServiceEntity entity = serviceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Service with ID " + id + " was not found"));
        return mapToDto(entity);
    }

    public ServiceResponseDto registerService(ServiceRequestDto request) {
        if (serviceRepository.existsByName(request.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Service with name '" + request.getName() + "' already exists");
        }

        ServiceEntity entity = new ServiceEntity();
        entity.setName(request.getName());
        entity.setUrl(request.getUrl());
        entity.setHealthEndpoint(request.getHealthEndpoint());
        entity.setDescription(request.getDescription());

        ServiceEntity saved = serviceRepository.save(entity);
        return mapToDto(saved);
    }

    public ServiceResponseDto updateService(Long id, ServiceRequestDto request) {
        ServiceEntity entity = serviceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Service with ID " + id + " was not found"));

        if (!entity.getName().equals(request.getName()) && serviceRepository.existsByName(request.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Service with name '" + request.getName() + "' already exists");
        }

        entity.setName(request.getName());
        entity.setUrl(request.getUrl());
        entity.setHealthEndpoint(request.getHealthEndpoint());
        entity.setDescription(request.getDescription());

        ServiceEntity updated = serviceRepository.save(entity);
        return mapToDto(updated);
    }

    public void deleteService(Long id) {
        if (!serviceRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Service with ID " + id + " was not found");
        }
        serviceRepository.deleteById(id);
    }

    private ServiceResponseDto mapToDto(ServiceEntity entity) {
        return new ServiceResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getUrl(),
                entity.getHealthEndpoint(),
                entity.getDescription(),
                entity.getCreatedAt()
        );
    }
}
