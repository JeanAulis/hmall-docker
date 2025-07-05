package com.hmall.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hmall.domain.dto.ItemDTO;
import com.hmall.domain.po.Item;
import com.hmall.service.IItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class IItemServiceImpl implements IItemService {

    @Override
    public Page<Item> page(Page<Object> updateTime) {
        return null;
    }

    @Override
    public List<ItemDTO> queryItemByIds(Collection<Long> ids) {
        return List.of();
    }

    @Override
    public Object getById(Long id) {
        return null;
    }
}
