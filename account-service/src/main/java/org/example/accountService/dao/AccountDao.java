package org.example.accountService.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.accountService.domain.po.Account;

import java.util.Optional;


public interface AccountDao extends BaseMapper<Account> {

    @Select(value = "SELECT a.* FROM t_account a WHERE a.id = #{id} FOR UPDATE")
    Optional<Account> selectByIdForUpdate(@Param("id") Long id);
}