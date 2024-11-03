package com.github.Timmy8.repository;

import com.github.Timmy8.entity.EmailUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmailRepository extends JpaRepository<EmailUser, UUID> {
}
