package org.example.accountService.controller;

import javax.validation.constraints.NotNull;

import lombok.extern.slf4j.Slf4j;
import org.example.accountService.domain.dto.account.AddBalanceByIdDto;
import org.example.accountService.domain.po.Account;
import org.example.accountService.domain.vo.account.AddBalanceByIdVo;
import org.example.accountService.domain.vo.account.ListVo;
import org.example.accountService.domain.vo.account.SaveVo;
import org.example.accountService.domain.vo.account.UpdateByIdVo;
import org.example.accountService.http.HttpResult;
import org.example.accountService.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
@Slf4j
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/accounts")
    public HttpResult<List<Account>> list(@RequestBody ListVo listVo) {
        return HttpResult.buildSuccess(accountService.list(listVo));
    }

    @PostMapping("/accounts")
    public HttpResult<Long> save(@RequestBody SaveVo saveVo) {
        return HttpResult.buildSuccess(accountService.save(saveVo));
    }

    @PutMapping("/accounts/{id}")
    public HttpResult<Long> updateById(@NotNull @PathVariable Long id, @RequestBody UpdateByIdVo updateByIdVo) {
        updateByIdVo.setId(id);
        accountService.updateById(updateByIdVo);
        return HttpResult.buildSuccess();
    }

    @PutMapping("/accounts/{id}/addBalance")
    HttpResult<AddBalanceByIdDto> addBalanceById(@NotNull @PathVariable Long id, @Validated @RequestBody AddBalanceByIdVo addBalanceByIdVo) {
        log.info("addBalanceById: {}", addBalanceByIdVo);
        addBalanceByIdVo.setId(id);

        if (addBalanceByIdVo.isInTransaction()) {
            return HttpResult.buildSuccess(accountService.addBalanceByIdInTransaction(addBalanceByIdVo));
        }
        if (addBalanceByIdVo.isInGlobalTransaction()) {
            return HttpResult.buildSuccess(accountService.addBalanceByIdInGlobalTransaction(addBalanceByIdVo));
        }
        return HttpResult.buildSuccess(accountService.addBalanceByIdSimple(addBalanceByIdVo));

    }
}
