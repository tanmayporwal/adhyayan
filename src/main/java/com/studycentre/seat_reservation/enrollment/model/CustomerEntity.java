package com.studycentre.seat_reservation.enrollment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String customerId;

  private String firstName;
  private String lastName;
  private String parentFirstName;
  private String parentLastName;
  private String phoneNumber;
  private String permanentAddress;
  private String localAddress;
  private Date dob;
  private Gender gender;
  private String emergencyContact;
  private String email;
}
