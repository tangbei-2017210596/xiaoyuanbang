package com.tb.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tb.reggie.entity.Orders;

public interface OrderService extends IService<Orders> {

    void submit(Orders orders);
}
