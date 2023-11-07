package com.lizana.transaction.domain.dtos;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

    private String transactionId; //identificador de la transaccion
    private Date transactionDate; //fecha de la transaccion
    private String merchantName; //
    private BigDecimal amount;
    private String currency;
    private String transactionType; // Compra, retiro, etc.
    private String status; // Aprobada, rechazada, pendiente, etc.
}
