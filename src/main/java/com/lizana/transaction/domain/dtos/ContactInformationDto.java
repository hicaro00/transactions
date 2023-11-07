package com.lizana.transaction.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactInformationDto {
  private String emailAddress;
  private String phoneNumber;
  private String billingAddress;
  private String mailingAddress;
}
