package com.lizana.transaction.application.validaciones;

import com.lizana.transaction.domain.dtos.BankAccountDto;
import com.lizana.transaction.domain.dtos.MovementDto;
import com.lizana.transaction.infrastructure.service.WebClientService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ValidateBalance {
  @Autowired
  WebClientService webClientService;


  public Mono<Boolean> validateBalanceAndStatus(MovementDto movementDto) {

    Mono<BankAccountDto> validateaccount =
        webClientService.getFromExternalServiceBankAccount(movementDto.getOriginMovement());

    return validateaccount.map(bankAccountDto -> {
      boolean temporaryBlock = bankAccountDto.isTemporaryBlock();
      BigDecimal availableBalance = bankAccountDto.getAvailableBalance();
      BigDecimal amount = movementDto.getAmount();
      if (!temporaryBlock && availableBalance.compareTo(amount) >= 0) {
        return true;
      } else {
        return false;
      }
    });
  }

  public Mono<Boolean> validateAccountexist(MovementDto movementDto) {
    Mono<BankAccountDto> destinationAccountMono =
        webClientService.getFromExternalServiceBankAccount(movementDto.getDestinationMovement());

    return destinationAccountMono
        .flatMap(bankAccountDto -> {
          if (!bankAccountDto.isTemporaryBlock()) {
            return Mono.just(true);
          } else {
            return Mono.just(false);
          }
        })
        .switchIfEmpty(Mono.just(false)); // En caso de que la cuenta de destino no exista
  }
}
