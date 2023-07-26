package org.example.accountService.domain.po;

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
@TableName("t_account")
@Builder
public class Account {
    Long id;
    BigDecimal balance;
    Timestamp createTime;
    Timestamp updateTime;
}
