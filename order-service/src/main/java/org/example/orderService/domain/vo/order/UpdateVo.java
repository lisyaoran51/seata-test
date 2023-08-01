package org.example.orderService.domain.vo.order;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateVo {
    Long id;
    Long userId;
    BigDecimal money;
    Integer status;
    Timestamp createTime;
    Timestamp updateTime;
}
