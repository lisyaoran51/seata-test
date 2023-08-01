package org.example.accountService.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.accountService.domain.dto.account.AddBalanceByIdDto;
import org.example.accountService.domain.dto.account.LockDto;
import org.example.accountService.domain.po.Account;
import org.example.accountService.domain.vo.account.*;

import java.util.List;

public interface AccountService extends IService<Account> {


    List<Account> list(ListVo listVo);

    Long save(SaveVo saveVo);

    void updateById(UpdateByIdVo updateByIdVo);

    AddBalanceByIdDto addBalanceByIdSimple(AddBalanceByIdVo addBalanceByIdVo);

    AddBalanceByIdDto addBalanceByIdInTransaction(AddBalanceByIdVo addBalanceByIdVo);

    AddBalanceByIdDto addBalanceByIdInGlobalTransaction(AddBalanceByIdVo addBalanceByIdVo);

    LockDto lock(LockVo lockVo);

    LockDto unlock(LockVo lockVo);
}
