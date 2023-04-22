package com.tb.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tb.reggie.entity.Category;
import com.tb.reggie.entity.Employee;

public interface CategoryService extends IService<Category> {
    public void  removeById(Long ids);
}
