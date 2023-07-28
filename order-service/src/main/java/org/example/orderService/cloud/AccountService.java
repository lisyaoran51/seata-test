package org.example.orderService.cloud;

import javax.validation.constraints.NotNull;
import org.example.orderService.cloud.dto.account.AddBalanceByIdDto;
import org.example.orderService.cloud.po.Account;
import org.example.orderService.cloud.vo.account.AddBalanceByIdVo;
import org.example.orderService.cloud.vo.account.ListVo;
import org.example.orderService.cloud.vo.account.SaveVo;
import org.example.orderService.cloud.vo.account.UpdateByIdVo;
import org.example.orderService.http.HttpResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@FeignClient(name = "account-service")//, url = "127.0.0.1:8095")
public interface AccountService {


    @GetMapping("/account/accounts")
    public HttpResult<List<Account>> list(@RequestBody ListVo listVo);

    @PostMapping("/account/accounts")
    public HttpResult<Long> save(@RequestBody SaveVo saveVo);

    @PutMapping("/account/accounts/{id}")
    public HttpResult<Long> updateById(@NotNull @PathVariable(value = "id") Long id, @RequestBody UpdateByIdVo updateByIdVo);

    @PutMapping("/account/accounts/{id}/addBalance")
    HttpResult<AddBalanceByIdDto> addBalanceById(@NotNull @PathVariable(value = "id") Long id, @Validated @RequestBody AddBalanceByIdVo addBalanceByIdVo);
}