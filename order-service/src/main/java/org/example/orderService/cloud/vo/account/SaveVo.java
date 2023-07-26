package org.example.orderService.cloud.vo.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveVo {
    Long id;
    BigDecimal balance;
}
