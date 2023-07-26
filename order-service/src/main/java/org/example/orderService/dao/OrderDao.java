package org.example.orderService.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.orderService.domain.po.Order;


public interface OrderDao extends BaseMapper<Order> {
}
