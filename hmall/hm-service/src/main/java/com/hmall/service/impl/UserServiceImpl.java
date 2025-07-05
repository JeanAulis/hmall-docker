package com.hmall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmall.common.exception.BadRequestException;
import com.hmall.common.exception.BizIllegalException;
import com.hmall.common.exception.ForbiddenException;
import com.hmall.common.utils.UserContext;
import com.hmall.config.JwtProperties;
import com.hmall.domain.dto.LoginFormDTO;
import com.hmall.domain.dto.RegisterFormDTO;
import com.hmall.domain.po.User;
import com.hmall.domain.vo.UserLoginVO;
import com.hmall.enums.UserStatus;
import com.hmall.mapper.UserMapper;
import com.hmall.service.IUserService;
import com.hmall.utils.JwtTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 虎哥
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final PasswordEncoder passwordEncoder;

    private final JwtTool jwtTool;

    private final JwtProperties jwtProperties;

    @Override
    public UserLoginVO login(LoginFormDTO loginDTO) {
        // 1.数据校验
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        
        // 2.根据用户名或手机号查询
        User user = lambdaQuery()
                .eq(User::getUsername, username)
                .or()
                .eq(User::getPhone, username)
                .one();
        
        Assert.notNull(user, "用户名或手机号错误");
        
        // 3.校验是否禁用
        if (user.getStatus() == UserStatus.FROZEN) {
            throw new ForbiddenException("用户被冻结");
        }
        
        // 4.校验密码
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadRequestException("用户名或密码错误");
        }
        
        // 5.生成TOKEN
        String token = jwtTool.createToken(user.getId(), jwtProperties.getTokenTTL());
        
        // 6.封装VO返回
        UserLoginVO vo = new UserLoginVO();
        vo.setUserId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setBalance(user.getBalance());
        vo.setToken(token);
        return vo;
    }
    @Override
    public void register(RegisterFormDTO registerFormDTO) {
        // 1.校验密码一致性
        if (!registerFormDTO.getPassword().equals(registerFormDTO.getConfirmPassword())) {
            throw new BadRequestException("两次输入的密码不一致");
        }
        
        // 2.校验用户名是否已存在
        long count = lambdaQuery()
                .eq(User::getUsername, registerFormDTO.getUsername())
                .count();
        if (count > 0) {
            throw new BadRequestException("用户名已存在");
        }
        
        // 3.校验手机号是否已存在
        count = lambdaQuery()
                .eq(User::getPhone, registerFormDTO.getPhone())
                .count();
        if (count > 0) {
            throw new BadRequestException("手机号已被注册");
        }
        
        // 4.创建用户
        User user = new User();
        user.setUsername(registerFormDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerFormDTO.getPassword()));
        user.setPhone(registerFormDTO.getPhone());
        user.setStatus(UserStatus.NORMAL);
        user.setBalance(1000); // 默认余额1000
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        
        // 5.保存用户
        save(user);
    }

    @Override
    public UserLoginVO getCurrentUser() {
        Long userId = UserContext.getUser();
        if (userId == null) {
            throw new BadRequestException("用户未登录");
        }
        
        User user = getById(userId);
        if (user == null) {
            throw new BadRequestException("用户不存在");
        }
        
        UserLoginVO vo = new UserLoginVO();
        vo.setUserId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setBalance(user.getBalance());
        return vo;
    }

    @Override
    public void logout(HttpServletRequest request) {
        // 清除用户上下文
        UserContext.removeUser();
        // 这里可以添加token黑名单逻辑
        log.info("用户登出成功");
    }

    @Override
    public void deductMoney(String pw, Integer totalFee) {
        log.info("开始扣款");
        // 1.校验密码
        User user = getById(UserContext.getUser());
        if(user == null || !passwordEncoder.matches(pw, user.getPassword())){
            // 密码错误
            throw new BizIllegalException("用户密码错误");
        }

        // 2.尝试扣款
        // int i = baseMapper.updateMoney(UserContext.getUser(), totalFee);
        // if (i <= 0) {
        //     throw new RuntimeException("扣款失败，可能是余额不足！");
        // }
        // log.info("扣款成功");
    }

    /**
     * 初始化测试用户数据
     */
    @PostConstruct
    public void initTestUsers() {
        // 检查是否已有测试用户
        long count = lambdaQuery().eq(User::getUsername, "test").count();
        if (count > 0) {
            log.info("测试用户已存在，跳过初始化");
            return;
        }
        
        log.info("开始初始化测试用户数据...");
        
        // 创建测试用户
        User testUser = new User();
        testUser.setUsername("test");
        testUser.setPassword(passwordEncoder.encode("123456"));
        testUser.setPhone("13800138000");
        testUser.setStatus(UserStatus.NORMAL);
        testUser.setBalance(10000);
        testUser.setCreateTime(LocalDateTime.now());
        testUser.setUpdateTime(LocalDateTime.now());
        save(testUser);
        
        // 创建管理员用户
        User adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setPassword(passwordEncoder.encode("admin123"));
        adminUser.setPhone("13800138001");
        adminUser.setStatus(UserStatus.NORMAL);
        adminUser.setBalance(50000);
        adminUser.setCreateTime(LocalDateTime.now());
        adminUser.setUpdateTime(LocalDateTime.now());
        save(adminUser);
        
        // 创建普通用户
        User normalUser = new User();
        normalUser.setUsername("user");
        normalUser.setPassword(passwordEncoder.encode("123456"));
        normalUser.setPhone("13800138002");
        normalUser.setStatus(UserStatus.NORMAL);
        normalUser.setBalance(2000);
        normalUser.setCreateTime(LocalDateTime.now());
        normalUser.setUpdateTime(LocalDateTime.now());
        save(normalUser);
        
        log.info("测试用户初始化完成");
        log.info("测试账号1: test/123456");
        log.info("测试账号2: admin/admin123");
        log.info("测试账号3: user/123456");
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "123456";
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("Encoded password: " + encodedPassword);
    }
}
