package com.lizana.transaction.application.serviceimpl;

import com.lizana.transaction.application.Transactions;
import com.lizana.transaction.application.utils.MaperTransfer;
import com.lizana.transaction.application.validaciones.ValidateBalance;
import com.lizana.transaction.domain.dtos.MovementDto;
import com.lizana.transaction.infrastructure.repository.MovementRepository;
import com.lizana.transaction.infrastructure.service.MovemenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
@Service
public class MovemenServiceImp implements MovemenService {
  @Autowired
  Transactions transactions;
  @Autowired
  MovementRepository movementRepository;
  @Autowired
  ValidateBalance validateBalance;

  @Override
  public Mono<MovementDto> movementTransfers(MovementDto movementDto) {
//esto es para trasnferencias de ceunta a cuenta de el mismo cliente
    return Mono.just(movementDto)
        .doOnNext(validateBalance::validateBalanceAndStatus) // Valida balance y estado
        .doOnNext(validateBalance::validateAccountexist) // Valida cuenta de d3estino
        .flatMap(transactions::transferBetweenAaccounts) // Realiza la transferencia
        .flatMap(result -> movementRepository.insert(MaperTransfer.dtoToEntity(result))
            .map(MaperTransfer::entityToDto)); // Inserta y convierte
  }

  @Override
  public Mono<MovementDto> movementPayments(MovementDto movementDto) {
    //para pago de creditos desde una ceunta con saldo o desde un cajero o ventanilla

    return Mono.just(movementDto)
        .doOnNext(validateBalance::validateBalanceAndStatus)
        .doOnNext(validateBalance::validateAccountexist)
        .flatMap(transactions::creditToPay)
        .flatMap(result->movementRepository.insert(MaperTransfer.dtoToEntity(result))
            .map(MaperTransfer::entityToDto));//guarda en base de datos los detalles del pago
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

