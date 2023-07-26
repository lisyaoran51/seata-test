package org.example.accountService.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.example.accountService.dao.AccountDao;
import org.example.accountService.domain.dto.account.AddBalanceByIdDto;
import org.example.accountService.domain.po.Account;
import org.example.accountService.domain.vo.account.AddBalanceByIdVo;
import org.example.accountService.domain.vo.account.ListVo;
import org.example.accountService.domain.vo.account.SaveVo;
import org.example.accountService.domain.vo.account.UpdateByIdVo;
import org.example.accountService.service.AccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AccountServiceImpl extends ServiceImplBase<AccountDao, Account> implements AccountService {

    Map<Integer, Integer> locks = new HashMap<>();

    @Override
    public List<Account> list(ListVo listVo) {
        log.info("list: {}", listVo);
        LambdaQueryWrapper<Account> queryWrapper = Wrappers.lambdaQuery(Account.class);
        queryWrapper
                .eq(has(listVo.getId()), Account::getId, listVo.getId());

        return list(queryWrapper);
    }

    @Override
    public Long save(SaveVo saveVo) {
        log.info("save: {}", saveVo);
        Account account = new Account();
        BeanUtils.copyProperties(saveVo, account);
        if (!save(account)) {
            throw new RuntimeException("fail to save: " + account.toString());
        }
        return account.getId();
    }

    @Override
    public void updateById(UpdateByIdVo updateByIdVo) {
        log.info("updateById: {}", updateByIdVo);
        Account account = new Account();
        BeanUtils.copyProperties(updateByIdVo, account);
        if (!updateById(account)) {
            throw new RuntimeException("fail to update: " + account.toString());
        }
    }

    @Override
    public AddBalanceByIdDto addBalanceById(AddBalanceByIdVo addBalanceByIdVo) {
        log.info("updateById: {}", addBalanceByIdVo);
        if (addBalanceByIdVo.isInTransaction()) {
            return addBalanceByIdInTransaction(addBalanceByIdVo);
        }
        if (addBalanceByIdVo.isInGlobalTransaction()) {
            return addBalanceByIdInGlobalTransaction(addBalanceByIdVo);
        }
        return addBalanceByIdSimple(addBalanceByIdVo);
    }

    AddBalanceByIdDto addBalanceByIdSimple(AddBalanceByIdVo addBalanceByIdVo) {
        Account account = getById(addBalanceByIdVo.getId());
        if (account == null) {
            throw new RuntimeException("fail to get account: " + addBalanceByIdVo.toString());
        }

        if (account.getBalance().add(addBalanceByIdVo.getAddBalance()).compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("balance not enough: " + account.toString());
        }

        account.setBalance(account.getBalance().add(addBalanceByIdVo.getAddBalance()));
        if (!updateById(account)) {
            throw new RuntimeException("fail to update: " + account.toString());
        }
        return AddBalanceByIdDto.builder().balance(account.getBalance()).build();
    }

    @GlobalTransactional
    AddBalanceByIdDto addBalanceByIdInGlobalTransaction(AddBalanceByIdVo addBalanceByIdVo) {
        var account = baseMapper.selectByIdForUpdate(addBalanceByIdVo.getId())
                .orElseThrow(() -> new RuntimeException("fail to get account: " + addBalanceByIdVo.toString()));

        if (account.getBalance().add(addBalanceByIdVo.getAddBalance()).compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("balance not enough: " + account.toString());
        }

        account.setBalance(account.getBalance().add(addBalanceByIdVo.getAddBalance()));
        if (!updateById(account)) {
            throw new RuntimeException("fail to update: " + account.toString());
        }
        return AddBalanceByIdDto.builder().balance(account.getBalance()).build();
    }

    @Transactional
    AddBalanceByIdDto addBalanceByIdInTransaction(AddBalanceByIdVo addBalanceByIdVo) {
        var account = baseMapper.selectByIdForUpdate(addBalanceByIdVo.getId())
                .orElseThrow(() -> new RuntimeException("fail to get account: " + addBalanceByIdVo.toString()));

        if (account.getBalance().add(addBalanceByIdVo.getAddBalance()).compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("balance not enough: " + account.toString());
        }

        account.setBalance(account.getBalance().add(addBalanceByIdVo.getAddBalance()));
        if (!updateById(account)) {
            throw new RuntimeException("fail to update: " + account.toString());
        }
        return AddBalanceByIdDto.builder().balance(account.getBalance()).build();
    }
}
