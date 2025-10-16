package com.studycentre.seat_reservation.enrollment.controller;

import com.studycentre.seat_reservation.enrollment.dto.CustomerDto;
import com.studycentre.seat_reservation.enrollment.service.EnrollmentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class EnrollmentController {

  @Autowired private EnrollmentService enrollmentService;

  @GetMapping
  public List<CustomerDto> getAllCustomers() {
    return enrollmentService.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<CustomerDto> getCustomerById(@PathVariable String id) {
    return ResponseEntity.ok(enrollmentService.findById(id));
  }

  @PostMapping
  public CustomerDto saveCustomer(@RequestBody CustomerDto customerDto) {
    return enrollmentService.upsert(customerDto);
  }

  @PatchMapping
  public CustomerDto updateCustomer(@RequestBody CustomerDto customerDto){
    return enrollmentService.update(customerDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCustomerById(@PathVariable String id) {
    enrollmentService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
