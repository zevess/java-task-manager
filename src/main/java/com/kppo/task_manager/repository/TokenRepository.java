package com.kppo.task_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kppo.task_manager.model.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {

}
