package com.tb.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tb.reggie.dto.DishDto;
import com.tb.reggie.entity.Dish;

public interface DishService extends IService<Dish> {
    //新增菜品，同时插入菜品的口味
    public void saveWithFlavor(DishDto dishDto);

    //根据id查询菜品信息和对应的口味信息
    public DishDto getByIdWithDishFlavor(Long id);

    public void updateWithFlavor(DishDto dishDto);
}
