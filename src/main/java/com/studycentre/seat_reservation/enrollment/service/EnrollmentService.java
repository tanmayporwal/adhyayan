package com.studycentre.seat_reservation.enrollment.service;

import com.studycentre.seat_reservation.enrollment.dto.CustomerDto;
import java.util.List;
import java.util.Optional;

public interface EnrollmentService {
  List<CustomerDto> findAll();

  void deleteById(String uuid);

  CustomerDto upsert(CustomerDto customerDto);

  CustomerDto update(String id, CustomerDto customerDto);

  Optional<CustomerDto> findById(String uuid);
}
