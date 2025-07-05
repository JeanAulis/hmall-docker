package com.hmall.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hmall.domain.dto.ItemDTO;
import com.hmall.domain.po.Item;

import java.util.Collection;
import java.util.List;

public interface IItemService {
    Page<Item> page(Page<Object> updateTime);

    List<ItemDTO> queryItemByIds(Collection<Long> ids);

    Object getById(Long id);
}
