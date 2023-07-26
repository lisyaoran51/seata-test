package org.example.accountService.domain.vo.account;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddBalanceByIdVo {
    @Null
    Long id;
    @NotNull
    BigDecimal addBalance;
    boolean inTransaction;
    boolean inGlobalTransaction;
}
