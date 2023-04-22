package com.tb.takeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tb.takeout.entity.Category;

public interface CategoryService extends IService<Category> {
    public void  removeById(Long ids);
}
