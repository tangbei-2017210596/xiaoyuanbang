package com.tb.reggie.dto;

import com.tb.reggie.entity.Setmeal;
import com.tb.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
