package com.lizana.transaction.application.serviceimpl;

import com.lizana.transaction.application.Transactions;
import com.lizana.transaction.application.utils.MaperTransfer;
import com.lizana.transaction.application.validaciones.ValidateBalance;
import com.lizana.transaction.domain.dtos.BankAccountDto;
import com.lizana.transaction.domain.dtos.MovementDto;
import com.lizana.transaction.infrastructure.repository.MovementRepository;
import com.lizana.transaction.infrastructure.service.MovemenService;
import com.lizana.transaction.infrastructure.service.WebClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import reactor.core.publisher.Mono;

public class MovemenServiceImp implements MovemenService {
  @Autowired
  Transactions transactions;
  @Autowired
  MovementRepository movementRepository;
  @Autowired
  ValidateBalance validateBalance;

  @Override
  public Mono<MovementDto> movementTransfers(MovementDto movementDto) {

      return Mono.just(movementDto)
          .doOnNext(validateBalance::validateBalanceAndStatus) // Valida balance y estado
          .doOnNext(validateBalance::validateAccountexist) // Valida cuenta de d3estino
          .flatMap(transactions::transferBetweenAaccounts) // Realiza la transferencia
          .flatMap(result -> movementRepository.insert(MaperTransfer.dtoToEntity(result))
              .map(MaperTransfer::entityToDto)); // Inserta y convierte
  }

  @Override
  public Mono<MovementDto> movementPayments(MovementDto movementDto) {
    return null;
  }

  @Override
  public Mono<MovementDto> movementWithdrawals(MovementDto movementDto) {
    return null;
  }

  @Override
  public Mono<MovementDto> movementDeposits(MovementDto movementDto) {
    return null;
  }
}

