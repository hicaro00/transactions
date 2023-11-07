package com.lizana.transaction.application;

import com.lizana.transaction.application.expetion.CustomExeption;
import com.lizana.transaction.domain.dtos.MovementDto;
import com.lizana.transaction.infrastructure.service.WebClientService;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class Transactions {
  @Autowired
  WebClientService webClientService;
  @Value("${URI_TRANSFER}")
  private String URI_TRANSFER;

  public Mono<MovementDto> transferBetweenAaccounts(MovementDto movementDto) {

    MovementDto updatedMovementDto = Optional.of(movementDto)
        .map(dto -> {
          dto.setMovementId("transfer" + UUID.randomUUID());
          return dto;
        })
        .orElseThrow(() -> new CustomExeption("No se pudo completar asignar un id"));
      return webClientService.webclientpost( updatedMovementDto, URI_TRANSFER, MovementDto.class)
          .onErrorResume(throwable -> {
         //error  durante la solicitud
            return Mono.error(new CustomExeption("No se pudo completar la operación. Inténtelo de nuevo más tarde."));
          });

  }
}
