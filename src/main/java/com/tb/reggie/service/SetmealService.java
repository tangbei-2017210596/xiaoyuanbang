package com.tb.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tb.reggie.dto.SetmealDto;
import com.tb.reggie.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    public void saveWithDish(SetmealDto setmealDto);
    public void delMealWithDish(List<Long> ids);
    public void modStatus(List<Long> ids,Integer status);
}
