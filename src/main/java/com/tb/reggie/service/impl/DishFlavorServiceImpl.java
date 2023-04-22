package com.tb.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tb.reggie.entity.Dish;
import com.tb.reggie.entity.DishFlavor;
import com.tb.reggie.mapper.DishFlavorMapper;
import com.tb.reggie.mapper.DishMapper;
import com.tb.reggie.service.DishFlavorService;
import com.tb.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
