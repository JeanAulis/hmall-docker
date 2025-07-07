package com.hmall.controller;


import com.hmall.common.domain.PageDTO;
import com.hmall.domain.dto.ItemDTO;
import com.hmall.domain.query.ItemPageQuery;
import com.hmall.service.IItemService;
import com.hmall.service.ISearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "搜索相关接口")
@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final IItemService itemService;

    // @ApiOperation("搜索商品")
    // @GetMapping("/list")
    // public PageDTO<ItemDTO> search(ItemPageQuery query) {
    //     return ISearchService.search(query);
    // }
}
