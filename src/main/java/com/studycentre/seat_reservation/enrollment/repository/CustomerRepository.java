package com.studycentre.seat_reservation.enrollment.repository;

import com.studycentre.seat_reservation.enrollment.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {}
