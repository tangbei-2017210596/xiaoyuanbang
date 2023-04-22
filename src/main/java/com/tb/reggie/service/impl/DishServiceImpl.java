package com.tb.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tb.reggie.dto.DishDto;
import com.tb.reggie.entity.Dish;
import com.tb.reggie.entity.DishFlavor;
import com.tb.reggie.mapper.DishMapper;
import com.tb.reggie.service.DishFlavorService;
import com.tb.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper,Dish> implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;

    @Override
    public DishDto getByIdWithDishFlavor(Long id) {
        Dish dish = this.getById(id);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);
        return dishDto;
    }

    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        //保存菜品基本信息到菜品表
        this.save(dishDto);

        //保存菜品口味到dishflavor
        Long dishid = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor dishFlavor:flavors){
            dishFlavor.setDishId(dishid);
        }

        dishFlavorService.saveBatch(flavors);
    }

    @Override
    public void updateWithFlavor(DishDto dishDto) {
        Long id = dishDto.getId();
        this.updateById(dishDto);
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,id);
        dishFlavorService.remove(queryWrapper);
        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor flavor:flavors){
            flavor.setDishId(id);
        }
        dishFlavorService.saveBatch(flavors);
    }
}
