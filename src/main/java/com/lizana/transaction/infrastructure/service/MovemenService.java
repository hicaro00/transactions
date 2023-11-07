package com.lizana.transaction.infrastructure.service;

import com.lizana.transaction.domain.dtos.MovementDto;
import reactor.core.publisher.Mono;

public interface MovemenService {

  Mono<MovementDto> movementTransfers(MovementDto movementDto);
  Mono<MovementDto> movementPayments(MovementDto movementDto);
  Mono<MovementDto> movementWithdrawals(MovementDto movementDto);
  Mono<MovementDto> movementDeposits(MovementDto movementDto);

}
