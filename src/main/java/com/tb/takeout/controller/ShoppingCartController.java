package com.tb.takeout.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tb.takeout.common.BaseContext;
import com.tb.takeout.common.R;
import com.tb.takeout.entity.ShoppingCart;
import com.tb.takeout.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 购物车
 */
@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加购物车
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
        log.info("购物车数据"+shoppingCart);
        //指定用户id
        Long id = BaseContext.getId();
        shoppingCart.setUserId(id);
        //查询当前菜品是否在购物车中
        Long dishId = shoppingCart.getDishId();
        Long setmealId = shoppingCart.getSetmealId();
        LambdaQueryWrapper<ShoppingCart> shoppingquerywrapper = new LambdaQueryWrapper<>();
        shoppingquerywrapper.eq(ShoppingCart::getUserId,id);
        if (dishId!=null){
            shoppingquerywrapper.eq(ShoppingCart::getDishId,dishId);
        }else {
            shoppingquerywrapper.eq(ShoppingCart::getSetmealId,setmealId);
        }
        ShoppingCart shoppingCartServiceOne = shoppingCartService.getOne(shoppingquerywrapper);
        if (shoppingCartServiceOne!=null){
            Integer number = shoppingCartServiceOne.getNumber();
            shoppingCartServiceOne.setNumber(number+1);
            shoppingCartService.updateById(shoppingCartServiceOne);
        }else {
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            shoppingCartServiceOne=shoppingCart;
        }
        //
        return R.success(shoppingCartServiceOne);
    }

    @PostMapping("/sub")
    public R<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart){
        //指定用户id
        Long id = BaseContext.getId();
        shoppingCart.setUserId(id);
        //查询当前菜品是否在购物车中
        Long dishId = shoppingCart.getDishId();
        Long setmealId = shoppingCart.getSetmealId();
        LambdaQueryWrapper<ShoppingCart> shoppingquerywrapper = new LambdaQueryWrapper<>();
        shoppingquerywrapper.eq(ShoppingCart::getUserId,id);
        if (dishId!=null){
            shoppingquerywrapper.eq(ShoppingCart::getDishId,dishId);
        }else {
            shoppingquerywrapper.eq(ShoppingCart::getSetmealId,setmealId);
        }
        ShoppingCart shoppingCartServiceOne = shoppingCartService.getOne(shoppingquerywrapper);

        Integer number = shoppingCartServiceOne.getNumber();
        if (number>1){
            shoppingCartServiceOne.setNumber(number-1);
            shoppingCartService.updateById(shoppingCartServiceOne);
        }else {
            //如果数量为1，减了后就应该删除
            shoppingCartService.remove(shoppingquerywrapper);
        }
        return R.success(shoppingCartServiceOne);
    }

    /**
     * 查看购物车
     * @return
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> list(){
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,BaseContext.getId());
        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);
        return R.success(list);
    }

    /**
     * 清空购物车
     * @return
     */
    @DeleteMapping("/clean")
    public R<String> clean(){
        //SQL:delete from shopping_cart where user_id = ?
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,BaseContext.getId());
        shoppingCartService.remove(queryWrapper);
        return R.success("清空购物车成功");
    }
}