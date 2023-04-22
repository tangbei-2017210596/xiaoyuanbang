package com.tb.takeout.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tb.takeout.common.CustomExceptiom;
import com.tb.takeout.entity.Category;
import com.tb.takeout.entity.Dish;
import com.tb.takeout.entity.Setmeal;
import com.tb.takeout.mapper.CategoryMapper;
import com.tb.takeout.service.CategoryService;
import com.tb.takeout.service.DishService;
import com.tb.takeout.service.SetmealService;
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
