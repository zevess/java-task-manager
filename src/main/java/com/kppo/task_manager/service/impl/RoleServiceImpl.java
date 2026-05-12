package com.kppo.task_manager.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kppo.task_manager.dto.role.RoleDto;
import com.kppo.task_manager.exception.ResourceNotFoundException;
import com.kppo.task_manager.model.Role;
import com.kppo.task_manager.repository.RoleRepository;
import com.kppo.task_manager.service.RoleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public ResponseEntity<List<RoleDto>> getAll() {
        List<RoleDto> roles = roleRepository.findAll().stream().map(role -> new RoleDto(role.getId(), role.getName()))
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(roles);
    }

    @Override
    public ResponseEntity<RoleDto> getById(Long roleId) {
        Role foundRole = roleRepository.findById(roleId).orElseThrow(
                () -> new ResourceNotFoundException("Роль не найдена"));
        RoleDto role = new RoleDto(foundRole.getId(), foundRole.getName());
        return ResponseEntity.status(HttpStatus.OK).body(role);
    }

    @Override
    public ResponseEntity<RoleDto> create(RoleDto roleDto) {
        Role role = Role.builder().name(roleDto.name()).build();
        Role savedRole = roleRepository.save(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(new RoleDto(savedRole.getId(), savedRole.getName()));
    }

    @Override
    public ResponseEntity<RoleDto> update(Long roleId, RoleDto roleDto) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Роль не найдена"));
        role.setName(roleDto.name());
        Role savedRole = roleRepository.save(role);
        return ResponseEntity.status(HttpStatus.OK).body(new RoleDto(savedRole.getId(), savedRole.getName()));
    }
}
