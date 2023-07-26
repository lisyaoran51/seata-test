package org.example.accountService.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.accountService.domain.dto.account.AddBalanceByIdDto;
import org.example.accountService.domain.po.Account;
import org.example.accountService.domain.vo.account.AddBalanceByIdVo;
import org.example.accountService.domain.vo.account.ListVo;
import org.example.accountService.domain.vo.account.SaveVo;
import org.example.accountService.domain.vo.account.UpdateByIdVo;

import java.util.List;

public interface AccountService extends IService<Account> {


    List<Account> list(ListVo listVo);

    Long save(SaveVo saveVo);

    void updateById(UpdateByIdVo updateByIdVo);

    AddBalanceByIdDto addBalanceById(AddBalanceByIdVo addBalanceByIdVo);
}
