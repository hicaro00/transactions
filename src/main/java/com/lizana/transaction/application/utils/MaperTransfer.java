package com.lizana.transaction.application.utils;

import com.lizana.transaction.domain.documents.Movement;
import com.lizana.transaction.domain.dtos.MovementDto;
import org.springframework.beans.BeanUtils;

public class MaperTransfer {

  private MaperTransfer() {
  }

  public static MovementDto entityToDto(Movement movement) {
    MovementDto movementDto = new MovementDto();
    BeanUtils.copyProperties(movement, movementDto);
    return movementDto;
  }

  public static Movement dtoToEntity(MovementDto movementDto) {
    Movement movement = new Movement();
    BeanUtils.copyProperties(movementDto, movement);
    return movement;
  }
}
