package com.tb.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tb.reggie.common.CustomExceptiom;
import com.tb.reggie.dto.SetmealDto;
import com.tb.reggie.entity.Setmeal;
import com.tb.reggie.entity.SetmealDish;
import com.tb.reggie.mapper.SetmealMapper;
import com.tb.reggie.service.DishService;
import com.tb.reggie.service.SetmealDishService;
import com.tb.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper,Setmeal> implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;
    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     * @param setmealDto
     */
    @Override
    public void saveWithDish(SetmealDto setmealDto) {
        this.save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        for (SetmealDish setmealDish:setmealDishes){
            setmealDish.setSetmealId(setmealDto.getId());
        }
        setmealDishService.saveBatch(setmealDishes);
    }

    @Override
    public void delMealWithDish(List<Long> ids) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,ids).eq(Setmeal::getStatus,1);
        if (this.count(queryWrapper)>0){
            throw new CustomExceptiom("勾选的套餐正在售卖中，不能删除");
        }
        this.removeByIds(ids);
        LambdaQueryWrapper<SetmealDish> dishQueryWrapper = new LambdaQueryWrapper<>();
        dishQueryWrapper.in(SetmealDish::getSetmealId,ids);
        setmealDishService.remove(dishQueryWrapper);
    }

    @Override
    public void modStatus(List<Long> ids,Integer status) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,ids);
        List<Setmeal> list = this.list(queryWrapper);
        for (Setmeal setmeal:list){
            setmeal.setStatus(status);
            this.updateById(setmeal);
        }

    }
}
