package org.example.orderService.domain.vo.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListVo {
    private Long id;

    private Long userId;

    Integer status;
}
