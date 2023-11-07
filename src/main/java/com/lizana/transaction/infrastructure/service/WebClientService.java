package com.lizana.transaction.infrastructure.service;

import com.lizana.transaction.domain.dtos.BankAccountDto;
import reactor.core.publisher.Mono;

public interface WebClientService {

  <T> Mono<T> webclientpost(T dto, String endpoint, Class<T> responseType);
  Mono<BankAccountDto> getFromExternalServiceBankAccount(String bankAccountId);
}
