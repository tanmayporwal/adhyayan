package com.studycentre.seat_reservation.enrollment.service;

import com.studycentre.seat_reservation.common.exception.ResourceNotFoundException;
import com.studycentre.seat_reservation.common.utility.CustomerDtoMapper;
import com.studycentre.seat_reservation.enrollment.dto.CustomerDto;
import com.studycentre.seat_reservation.enrollment.model.CustomerEntity;
import com.studycentre.seat_reservation.enrollment.repository.CustomerRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EnrollmentServiceImpl implements EnrollmentService {

  @Autowired private CustomerRepository customerRepository;

  @Autowired private CustomerDtoMapper customerDtoMapper;

  /**
   * Returns all the existing customer
   *
   * @return A list of {@link CustomerEntity}
   */
  public List<CustomerDto> findAll() {
    List<CustomerEntity> customers;
    try {
      customers = customerRepository.findAll();
    } catch (Exception e) {
      log.error(
          "An exception occurred while fetching customers from database. Error msg: {}, stack trace: {}",
          e.getMessage(),
          Arrays.toString(e.getStackTrace()));
      throw new RuntimeException("Failed to fetch from the database. Please try again");
    }

    List<CustomerDto> customerdtos = new ArrayList<>();

    for (CustomerEntity customer : customers) {
      customerdtos.add(converter(customer));
    }
    log.info("Getting all customers");
    return customerdtos;
  }

  /**
   * Delete the record of the customer with given {@link CustomerDto} customerId
   *
   * @param uuid
   */
  public void deleteById(String uuid) {
    Optional<CustomerEntity> customer = customerRepository.findById(uuid);
    if (customer.isEmpty()) {
      log.error("Delete attempted for a non-existent customer id: {}", uuid);
      throw new ResourceNotFoundException(
          String.format("No customer found for given id: %s", uuid));
    } else {
      try {
        customerRepository.deleteById(uuid);
        log.info("Customer with customer id: {} deleted successfully", uuid);
      } catch (Exception e) {
        log.error(
            "Failed to delete customer with id: {}, error msg: {}, stack trace: {}",
            uuid,
            e.getMessage(),
            e.getStackTrace());
      }
    }
  }

  /**
   * Generate and save a new customer
   *
   * @param customerDto
   * @return Saved {@link CustomerDto} with the generated UUID
   */
  public CustomerDto upsert(CustomerDto customerDto) {
    CustomerEntity savedCustomer;
    try{
      CustomerEntity customer = converter(customerDto);
      savedCustomer = customerRepository.save(customer);
      if(customerDto.getCustomerId() == null){
        log.info("Customer enrolled successfully");
      } else {
        log.info("Customer information updated successfully");
      }
    } catch (Exception e){
      log.error("Customer enrollment failed for {}, with exception: {}, stack trace: {}",
              customerDto.getFirstName() + customerDto.getLastName(),
              e.getMessage(),
              e.getStackTrace());
      throw new RuntimeException(String.format("Customer enrollment failure for %s %s", customerDto.getFirstName(), customerDto.getLastName()));
    }
    return converter(savedCustomer);
  }

  /**
   * Dummy function for now Update customer record
   *
   * @param customerDto
   * @return Updated {@link CustomerDto}
   */
  public CustomerDto update(CustomerDto customerDto) {
    if(customerDto.getCustomerId() == null) {
      log.error("Customer id not present");
      throw new RuntimeException("Customer id can not be null for update customer");
    }

    CustomerDto customer = findById(customerDto.getCustomerId());

    if(customer == null){
      log.error("Update information attempted for non-existing customer");
      throw new ResourceNotFoundException(String.format("No records of customer found for id: %s", customerDto.getCustomerId()));
    }
    customerDtoMapper.updateCustomerFromDto(customerDto, customer);
    return upsert(customer);
  }

  /**
   * Find customer with the given Id
   *
   * @param uuid
   * @return {@link CustomerDto} with the given id if exist or null
   */
  public CustomerDto findById(String uuid) {
    if(uuid == null || uuid.isEmpty() ){
      log.error("Customer id provided is not appropriate");
      throw new RuntimeException("Customer id provided is not appropriate");
    }
    try{
      Optional<CustomerEntity> customer = customerRepository.findById(uuid);
      if(customer.isEmpty()){
        log.info("No customer exist for the given id: {}", uuid);
        return CustomerDto.builder().build();
      } else{
        log.info("Customer found for customer id: uuid");
        return converter(customer.get());
      }

    } catch (Exception e) {
      log.error("Customer fetch failed for id: {}. error msg: {}, stack trace: {}",
              uuid,
              e.getMessage(),
              e.getStackTrace());
    }
    return CustomerDto.builder().build();
  }

  public CustomerDto converter(CustomerEntity customerEntity) {
    return CustomerDto.builder()
        .customerId(customerEntity.getCustomerId())
        .dob(customerEntity.getDob())
        .email(customerEntity.getEmail())
        .emergencyContact(customerEntity.getEmergencyContact())
        .firstName(customerEntity.getFirstName())
        .gender(customerEntity.getGender())
        .lastName(customerEntity.getLastName())
        .localAddress(customerEntity.getLocalAddress())
        .parentFirstName(customerEntity.getParentFirstName())
        .parentLastName(customerEntity.getParentLastName())
        .permanentAddress(customerEntity.getPermanentAddress())
        .phoneNumber(customerEntity.getPhoneNumber())
        .build();
  }

  public CustomerEntity converter(CustomerDto customerDto) {
    return CustomerEntity.builder()
        .customerId(customerDto.getCustomerId())
        .dob(customerDto.getDob())
        .email(customerDto.getEmail())
        .emergencyContact(customerDto.getEmergencyContact())
        .firstName(customerDto.getFirstName())
        .gender(customerDto.getGender())
        .lastName(customerDto.getLastName())
        .localAddress(customerDto.getLocalAddress())
        .parentFirstName(customerDto.getParentFirstName())
        .parentLastName(customerDto.getParentLastName())
        .permanentAddress(customerDto.getPermanentAddress())
        .phoneNumber(customerDto.getPhoneNumber())
        .build();
  }
}
