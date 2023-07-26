package org.example.orderService.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_order")
public class Order {
    Long id;
    Long userId;
    BigDecimal money;
    Integer status;
    Timestamp createTime;
    Timestamp updateTime;
}
