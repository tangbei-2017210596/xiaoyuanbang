package com.tb.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tb.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
