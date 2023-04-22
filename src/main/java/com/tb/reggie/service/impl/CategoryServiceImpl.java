package com.tb.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tb.reggie.common.CustomExceptiom;
import com.tb.reggie.entity.Category;
import com.tb.reggie.entity.Dish;
import com.tb.reggie.entity.Setmeal;
import com.tb.reggie.mapper.CategoryMapper;
import com.tb.reggie.service.CategoryService;
import com.tb.reggie.service.DishService;
import com.tb.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    @Override
    public void removeById(Long ids) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,ids);
        int count=dishService.count(dishLambdaQueryWrapper);
        if (count>0){
            throw new CustomExceptiom("当前分类下关联了菜品");
        }
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,ids);
        int count2=setmealService.count(setmealLambdaQueryWrapper);
        if (count2>0){
            throw new CustomExceptiom("当前分类下关联了套餐，不能删除");
        }
        super.removeById(ids);
    }
}
