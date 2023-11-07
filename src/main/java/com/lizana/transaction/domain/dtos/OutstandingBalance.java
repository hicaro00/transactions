package com.lizana.transaction.domain.dtos;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutstandingBalance {
  private Date currentBalance; //fecha del saldo registrado
  private BigDecimal balance; // saldo en es la fecha registrada
}
