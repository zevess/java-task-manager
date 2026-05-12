package com.kppo.task_manager.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kppo.task_manager.dto.role.RoleDto;
import com.kppo.task_manager.service.impl.RoleServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Role", description = "API для получения, создания и редактирования ролей")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
public class RoleController {
    private final RoleServiceImpl roleService;

    @Operation(summary = "Получение всех ролей", description = "Получение всех ролей")
    @GetMapping()
    public ResponseEntity<List<RoleDto>> getAll() {
        return roleService.getAll();
    }

    @Operation(summary = "Создание роли", description = "Создание роли. Передаётся имя роли")
    @PostMapping()
    public ResponseEntity<RoleDto> create(@RequestBody @Valid RoleDto roleDto) {
        return roleService.create(roleDto);
    }

    @Operation(summary = "Получение роли", description = "Получение роли по id. Передаётся id роли")
    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getById(@PathVariable Long id) {
        return roleService.getById(id);
    }

    @Operation(summary = "Изменение роли", description = "Изменение роли. Передаётся id роли и имя роли")
    @PatchMapping("/{id}")
    public ResponseEntity<RoleDto> update(@PathVariable Long id, @RequestBody @Valid RoleDto roleDto) {
        return roleService.update(id, roleDto);
    }

}
