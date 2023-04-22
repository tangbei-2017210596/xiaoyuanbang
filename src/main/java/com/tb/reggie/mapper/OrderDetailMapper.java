package com.tb.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tb.reggie.entity.OrderDetail;
import com.tb.reggie.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

}
