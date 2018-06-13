package com.admin.adminsystem.jpa;

import com.admin.adminsystem.entity.Service2Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface Service2JPA extends JpaRepository<Service2Entity, String> {
}