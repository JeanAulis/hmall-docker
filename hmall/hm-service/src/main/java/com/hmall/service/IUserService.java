package com.hmall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hmall.common.domain.PageQuery;
import com.hmall.common.domain.R;
import com.hmall.domain.dto.LoginFormDTO;
import com.hmall.domain.po.User;
import com.hmall.domain.vo.UserLoginVO;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author 虎哥
 * @since 2023-05-05
 */
public interface IUserService extends IService<User> {

    UserLoginVO login(LoginFormDTO loginFormDTO);

    void deductMoney(String pw, Integer totalFee);

    // 用户信息分页查询
    R findPage(PageQuery pageQuery);
}
