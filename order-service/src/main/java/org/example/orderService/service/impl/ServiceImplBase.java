package org.example.orderService.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

public class ServiceImplBase<TDao extends BaseMapper<T>, T> extends ServiceImpl<TDao, T> {

    protected boolean has(Object obj) {
        return ObjectUtil.isNotEmpty(obj);
    }
}
