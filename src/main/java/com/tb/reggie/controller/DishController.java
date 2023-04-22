package com.tb.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tb.reggie.common.R;
import com.tb.reggie.dto.DishDto;
import com.tb.reggie.entity.Category;
import com.tb.reggie.entity.Dish;
import com.tb.reggie.entity.DishFlavor;
import com.tb.reggie.entity.Employee;
import com.tb.reggie.service.CategoryService;
import com.tb.reggie.service.DishFlavorService;
import com.tb.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }
    @GetMapping("/page")
    public R<Page> page(Integer page,Integer pageSize,String name){
        //分页构造器
        Page<Dish> pageInfo = new Page(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();
        //条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(name!=null,Dish::getName,name);
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        dishService.page(pageInfo,queryWrapper);
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");
        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map((item) ->{
            DishDto dishDto = new DishDto() ;
            BeanUtils.copyProperties(item,dishDto);
            Long categoryId = item. getCategoryId();//分类id
        //根据id查询分类对象
            Category category = categoryService.getById(categoryId);
            if (category!=null){
                String categoryName = category.getName () ;
                dishDto.setCategoryName (categoryName) ;
            }
            return dishDto;
        }).collect(Collectors.toList()) ;
        dishDtoPage.setRecords(list);
        return R.success(dishDtoPage);
    }

    @GetMapping("/{id}")
    private R<DishDto> getById(@PathVariable("id") Long id){
        DishDto dishDto = dishService.getByIdWithDishFlavor(id);
        return R.success(dishDto);
    }

    @PutMapping
    private R<String> update(@RequestBody DishDto dishDto){
        dishService.updateWithFlavor(dishDto);
        return R.success("菜品信息修改成功");
    }
//    @GetMapping("/list")
//    private R<List<Dish>> list(Dish dish){
//        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());
//        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
//        List<Dish> dishes = dishService.list(queryWrapper);
//        return R.success(dishes);
//    }
        @GetMapping("/list")
        private R<List<DishDto>> list(Dish dish){
            LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());
            queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
            List<Dish> list = dishService.list(queryWrapper);
            List<DishDto> dishDtoList = list.stream().map((item) ->{
                DishDto dishDto = new DishDto() ;
                BeanUtils.copyProperties(item,dishDto);
                Long categoryId = item. getCategoryId();//分类id
                //根据id查询分类对象
                Category category = categoryService.getById(categoryId);
                if (category!=null){
                    String categoryName = category.getName () ;
                    dishDto.setCategoryName (categoryName) ;
                }
                Long dishId = item.getId();
                LambdaQueryWrapper<DishFlavor> queryWrapper1 = new LambdaQueryWrapper<>();
                queryWrapper1.eq(DishFlavor::getDishId,dishId);
                List<DishFlavor> dishFlavorList = dishFlavorService.list(queryWrapper1);
                dishDto.setFlavors(dishFlavorList);
                return dishDto;
            }).collect(Collectors.toList()) ;
            return R.success(dishDtoList);
        }
}

