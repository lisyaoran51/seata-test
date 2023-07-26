package org.example.accountService.domain.vo.account;

import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateByIdVo {
    @Null
    Long id;
    BigDecimal balance;
}
