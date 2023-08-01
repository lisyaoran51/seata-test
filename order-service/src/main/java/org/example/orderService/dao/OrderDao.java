package org.example.orderService.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.orderService.domain.po.Order;

import java.util.Optional;


public interface OrderDao extends BaseMapper<Order> {}
