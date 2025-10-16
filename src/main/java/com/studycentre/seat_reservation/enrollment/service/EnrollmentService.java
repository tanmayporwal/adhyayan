package com.studycentre.seat_reservation.enrollment.service;

import com.studycentre.seat_reservation.enrollment.dto.CustomerDto;
import java.util.List;

public interface EnrollmentService {
  List<CustomerDto> findAll();

  void deleteById(String uuid);

  CustomerDto upsert(CustomerDto customerDto);

  CustomerDto update(CustomerDto customerDto);

  CustomerDto findById(String uuid);
}
