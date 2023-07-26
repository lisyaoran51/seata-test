package org.example.orderService.domain.vo.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveVo {
    Long userId;
    BigDecimal money;
    Integer status;
    boolean inTransaction;
    boolean inGlobalTransaction;
    Integer lockId;
}
