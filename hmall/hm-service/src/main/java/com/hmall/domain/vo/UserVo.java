package com.hmall.domain.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDateTime;

/**
 * @Description UserVo
 * @Author jeanAulis
 * @Date 2025-07-07
 */
@Data
public class UserVo {
    private BigDecimal id;
    private String username;
    private int phone;
    private int status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private int balance;
}
