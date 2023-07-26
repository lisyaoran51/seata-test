package org.example.orderService.cloud.vo.account;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddBalanceByIdVo {
    @Null
    Long id;
    @NotNull
    BigDecimal addBalance;
    boolean inTransaction;
    boolean inGlobalTransaction;
}
