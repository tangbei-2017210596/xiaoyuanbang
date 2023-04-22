package com.tb.takeout.dto;

import com.tb.takeout.entity.Setmeal;
import com.tb.takeout.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
