package com.lizana.transaction.domain.dtos;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferDto {
  private String offerId;
  private String offerTitle;
  private String offerDescription;
  private Date offerExpirationDate;
}
