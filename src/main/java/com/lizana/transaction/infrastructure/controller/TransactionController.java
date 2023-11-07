package com.lizana.transaction.infrastructure.controller;

import com.lizana.transaction.application.expetion.CustomExeption;
import com.lizana.transaction.domain.dtos.MovementDto;
import com.lizana.transaction.infrastructure.service.MovemenService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/movements")
@Log4j2
public class TransactionController {
  @Autowired
  MovemenService movemenService;

  @PostMapping("/transfer")
  @ResponseBody
  public Mono<ResponseEntity<MovementDto>> transfer(@RequestBody MovementDto movementDto) {
    return movemenService.movementTransfers(movementDto)
        .map(result -> ResponseEntity.status(HttpStatus.CREATED).body(result))
        .onErrorResume(CustomExeption.class, e -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new MovementDto("Error:" + e.getMessage()))))
        .onErrorResume(Exception.class, e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new MovementDto("Error: 500" + e.getMessage()))));
  }

  @PostMapping("/withdrawal")
  @ResponseBody
  public Mono<ResponseEntity<MovementDto>> withdrawal(@RequestBody MovementDto movementDto) {
    return movemenService.movementWithdrawals(movementDto)
        .map(result -> ResponseEntity.status(HttpStatus.CREATED).body(result))
        .onErrorResume(CustomExeption.class, e -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new MovementDto("Error:" + e.getMessage()))))
        .onErrorResume(Exception.class, e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new MovementDto("Error:500 " + e.getMessage()))));

  }

  @PostMapping("/payment")
  @ResponseBody
  public Mono<ResponseEntity<MovementDto>> payment(@RequestBody MovementDto movementDto) {
    return movemenService.movementPayments(movementDto)
        .map(result -> ResponseEntity.status(HttpStatus.CREATED).body(result))
        .onErrorResume(CustomExeption.class, e -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new MovementDto("Error: " + e.getMessage()))))
        .onErrorResume(Exception.class, e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new MovementDto("Error: 500" + e.getMessage()))));
  }

  @PostMapping("/deposit")
  @ResponseBody
  public Mono<ResponseEntity
      <MovementDto>> deposits(@RequestBody MovementDto movementDto) {
    return movemenService.movementDeposits(movementDto)
        .map(result -> ResponseEntity.status(HttpStatus.CREATED).body(result))
        .onErrorResume(CustomExeption.class, e -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new MovementDto("Error: " + e.getMessage()))))
        .onErrorResume(Exception.class, e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new MovementDto("Error:500 " + e.getMessage()))));
  }


}
