package com.kppo.task_manager.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.kppo.task_manager.dto.role.RoleDto;

public interface RoleService {
    ResponseEntity<List<RoleDto>> getAll();

    ResponseEntity<RoleDto> getById(Long roleId);

    ResponseEntity<RoleDto> create(RoleDto roleDto);

    ResponseEntity<RoleDto> update(Long roleId, RoleDto roleDto);
}
