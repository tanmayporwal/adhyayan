package com.studycentre.seat_reservation.enrollment.dto;

import com.studycentre.seat_reservation.enrollment.model.Gender;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
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
