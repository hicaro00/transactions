package com.lizana.transaction.application.utils;

import com.lizana.transaction.application.expetion.CustomExeption;
import com.lizana.transaction.domain.dtos.BankAccountDto;
import com.lizana.transaction.infrastructure.service.WebClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WebClientServiceImpl implements WebClientService {
  @Autowired
  WebClient webClient;

  private static final String URI_SERVICE_CREDIT = "http://localhost:9090/v1/credit/{id}";
  private static final String URI_SERVICE_BANKACCOUNT = "http://localhost:8090/account/info/{id}";



  @Override
  public <T> Mono<T> webclientpost(T dto, String endpoint, Class<T> responseType) {


    return webClient.post()
        .uri(endpoint)
        .body(Mono.just(dto), dto.getClass())
        .retrieve()
        .onStatus(HttpStatus::is4xxClientError, clientResponse ->
            Mono.error(new CustomExeption("Error no se encuentra la ruta")))
        .onStatus(HttpStatus::is5xxServerError, clientResponse ->
            Mono.error(new CustomExeption("Error eroro en el objeto q se envia")))
        .bodyToMono(responseType);
  }



  @Override
  public Mono<BankAccountDto> getFromExternalServiceBankAccount(String bankAccountId) {
    return webClient.get()
        .uri(URI_SERVICE_BANKACCOUNT, bankAccountId) // Reemplaza con el endpoint específico
        .retrieve()
        .bodyToMono(BankAccountDto.class)
        .onErrorResume(throwable -> Mono.just(new BankAccountDto()));
  }
}
