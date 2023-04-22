package com.tb.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tb.reggie.entity.SetmealDish;
import com.tb.reggie.mapper.SetmealDishMpper;
import com.tb.reggie.service.SetmealDishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMpper, SetmealDish> implements SetmealDishService{
}
