package com.studycentre.seat_reservation.common.utility;

import com.studycentre.seat_reservation.enrollment.dto.CustomerDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CustomerDtoMapper {

  void updateCustomerFromDto(CustomerDto incomingDto, @MappingTarget CustomerDto existingDto);
}
