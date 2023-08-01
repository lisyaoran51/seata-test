package org.example.orderService.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.example.orderService.domain.dto.order.LockDto;
import org.example.orderService.domain.po.Order;
import org.example.orderService.domain.vo.order.ListVo;
import org.example.orderService.domain.vo.order.LockVo;
import org.example.orderService.domain.vo.order.SaveVo;
import org.example.orderService.domain.vo.order.UpdateVo;

import java.util.List;
import java.util.Map;

/**
 * @author josh_sinrui
 */
public interface OrderService extends IService<Order> {

    List<Order> list(ListVo listVo);

    Long saveSimple(SaveVo saveVo);

    Long saveInTransaction(SaveVo saveVo);

    Long saveInGlobalTransaction(SaveVo saveVo);

    int update(UpdateVo updateVo);

    LockDto lock(LockVo lockVo);

    LockDto unlock(LockVo lockVo);
}