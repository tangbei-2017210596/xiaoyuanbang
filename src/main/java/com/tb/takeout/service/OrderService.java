package com.tb.takeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tb.takeout.entity.Orders;

public interface OrderService extends IService<Orders> {

    void submit(Orders orders);
}
