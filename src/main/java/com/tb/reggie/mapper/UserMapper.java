package com.tb.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tb.reggie.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
