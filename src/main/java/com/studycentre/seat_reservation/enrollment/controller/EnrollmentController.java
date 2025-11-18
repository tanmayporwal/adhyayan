package com.studycentre.seat_reservation.enrollment.controller;

import com.studycentre.seat_reservation.enrollment.dto.CustomerDto;
import com.studycentre.seat_reservation.enrollment.service.EnrollmentService;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/customers")
public class EnrollmentController {
  private final EnrollmentService enrollmentService;

  public EnrollmentController(EnrollmentService enrollmentService) {
    this.enrollmentService = enrollmentService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<CustomerDto> getCustomerById(@PathVariable String id) {
    Optional<CustomerDto> customer = enrollmentService.findById(id);

    return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<CustomerDto>> getAllCustomers() {
    return ResponseEntity.ok(enrollmentService.findAll());
  }

  @PostMapping
  public ResponseEntity<CustomerDto> saveCustomer(@RequestBody CustomerDto customerDto) {
    CustomerDto saved = enrollmentService.upsert(customerDto);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(saved.getCustomerId())
            .toUri();
    return ResponseEntity.created(location).body(saved);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<CustomerDto> updateCustomer(
      @PathVariable String id, @RequestBody CustomerDto customerDto) {
    return ResponseEntity.ok(enrollmentService.update(id, customerDto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCustomerById(@PathVariable String id) {
    enrollmentService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
