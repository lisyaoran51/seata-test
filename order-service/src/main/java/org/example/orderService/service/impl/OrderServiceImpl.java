package org.example.orderService.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.example.orderService.dao.OrderDao;
import org.example.orderService.domain.dto.order.LockDto;
import org.example.orderService.domain.po.Order;
import org.example.orderService.domain.vo.order.ListVo;
import org.example.orderService.domain.vo.order.LockVo;
import org.example.orderService.domain.vo.order.SaveVo;
import org.example.orderService.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class OrderServiceImpl extends ServiceImplBase<OrderDao, Order> implements OrderService {

    Map<Integer, Integer> locks = new HashMap<>();

    @Override
    public List<Order> list(ListVo listVo) {
        log.info("list: {}", listVo);
        LambdaQueryWrapper<Order> queryWrapper = Wrappers.lambdaQuery(Order.class);
        queryWrapper
                .eq(has(listVo.getId()), Order::getId, listVo.getId())
                .eq(has(listVo.getUserId()), Order::getUserId, listVo.getUserId())
                .eq(has(listVo.getStatus()), Order::getStatus, listVo.getStatus());

        return list(queryWrapper);
    }

    @Override
    public Long save(SaveVo saveVo) {
        log.info("save: {}", saveVo);
        if (saveVo.isInTransaction()) {
            return saveInTransaction(saveVo);
        }
        if (saveVo.isInGlobalTransaction()) {
            return saveInGlobalTransaction(saveVo);
        }
        return saveSimple(saveVo);
    }

    @Override
    public LockDto lock(LockVo lockVo) {
        locks.put(lockVo.getId(), 1);
        var clone = new HashMap<Integer, Integer>();
        clone.putAll(locks);
        return new LockDto(clone);
    }

    @Override
    public LockDto unlock(LockVo lockVo) {
        locks.put(lockVo.getId(), 0);
        var clone = new HashMap<Integer, Integer>();
        clone.putAll(locks);
        return new LockDto(clone);
    }

    public Long saveSimple(SaveVo saveVo) {
        Order order = new Order();
        BeanUtils.copyProperties(saveVo, order);
        if (!save(order)) {
            throw new RuntimeException("fail to save: " + order.toString());
        }
        return order.getId();
    }

    @Transactional
    public Long saveInTransaction(SaveVo saveVo) {
        var result = saveSimple(saveVo);
        while(saveVo.getLockId() != null && locks.containsKey(saveVo.getLockId()) && locks.get(saveVo.getLockId()) > 0) {
            try{
                Thread.sleep(100);
            } catch (InterruptedException e) {
                log.error("{}", e);
            }
        }
        return result;
    }

    @GlobalTransactional
    public Long saveInGlobalTransaction(SaveVo saveVo) {
        var result = saveSimple(saveVo);
        while(saveVo.getLockId() != null && locks.containsKey(saveVo.getLockId()) && locks.get(saveVo.getLockId()) > 0) {
            try{
                Thread.sleep(100);
            } catch (InterruptedException e) {
                log.error("{}", e);
            }
        }
        return result;
    }



}