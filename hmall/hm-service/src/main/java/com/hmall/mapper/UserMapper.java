package com.hmall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hmall.domain.po.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ghy
 * @version 1.0
 * @since 2025-06-25 10:33
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
