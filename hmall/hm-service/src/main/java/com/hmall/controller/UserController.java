package com.hmall.controller;

import com.hmall.common.domain.R;
import com.hmall.domain.dto.LoginFormDTO;
import com.hmall.domain.dto.RegisterFormDTO;
import com.hmall.domain.vo.UserLoginVO;
import com.hmall.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "用户相关接口")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @ApiOperation("用户登录接口")
    @PostMapping("login")
    public R<UserLoginVO> login(@RequestBody @Validated LoginFormDTO loginFormDTO){
        try {
            UserLoginVO result = userService.login(loginFormDTO);
            return R.ok(result);
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

    @ApiOperation("用户注册接口")
    @PostMapping("register")
    public R<String> register(@RequestBody @Validated RegisterFormDTO registerFormDTO){
        try {
            userService.register(registerFormDTO);
            return R.ok("注册成功");
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

    @ApiOperation("获取当前用户信息")
    @GetMapping("me")
    public R<UserLoginVO> getCurrentUser(){
        try {
            UserLoginVO userInfo = userService.getCurrentUser();
            return R.ok(userInfo);
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

    @ApiOperation("用户登出")
    @PostMapping("logout")
    public R<String> logout(HttpServletRequest request){
        try {
            userService.logout(request);
            return R.ok("登出成功");
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

    @ApiOperation("扣减余额")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pw", value = "支付密码"),
            @ApiImplicitParam(name = "amount", value = "支付金额")
    })
    @PutMapping("/money/deduct")
    public R<String> deductMoney(@RequestParam("pw") String pw, @RequestParam("amount") Integer amount){
        try {
            userService.deductMoney(pw, amount);
            return R.ok("扣款成功");
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

    @ApiOperation("测试接口 - 无需登录")
    @GetMapping("test")
    public R<String> test(){
        return R.ok("测试接口调用成功");
    }
}

