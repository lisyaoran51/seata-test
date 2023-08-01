package org.example.orderService.controller;

import org.example.orderService.domain.dto.order.LockDto;
import org.example.orderService.domain.vo.order.LockVo;
import org.example.orderService.domain.vo.order.SaveVo;
import org.example.orderService.http.HttpResult;
import org.example.orderService.domain.po.Order;
import org.example.orderService.domain.vo.order.ListVo;
import org.example.orderService.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/orders")
    public HttpResult<List<Order>> list(@RequestBody ListVo listVo) {
        return HttpResult.buildSuccess(orderService.list(listVo));
    }

    @PostMapping("/orders")
    public HttpResult<Long> save(@RequestBody SaveVo saveVo) {
        log.info("save: {}", saveVo);
        if (saveVo.isInTransaction()) {
            return HttpResult.buildSuccess(orderService.saveInTransaction(saveVo));
        }
        if (saveVo.isInGlobalTransaction()) {
            return HttpResult.buildSuccess(orderService.saveInGlobalTransaction(saveVo));
        }
        return HttpResult.buildSuccess(orderService.saveSimple(saveVo));
    }

    @PostMapping("/lock")
    public HttpResult<LockDto> lock(@RequestBody LockVo lockVo) {
        return HttpResult.buildSuccess(orderService.lock(lockVo));
    }

    @PostMapping("/unlock")
    public HttpResult<LockDto> unlock(@RequestBody LockVo lockVo) {
        return HttpResult.buildSuccess(orderService.unlock(lockVo));
    }
}
