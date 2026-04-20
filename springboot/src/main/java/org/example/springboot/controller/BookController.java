package org.example.springboot.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.example.springboot.common.Result;
import org.example.springboot.service.BookService;
import org.example.springboot.shujvku.Book;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin
@RestController
@RequestMapping("/book")//匹配前缀地址
public class BookController {
    @Resource
    private BookService bookService;
    
    @GetMapping("/getBook")
    public Result getBook(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(required = false) String bookName,
            @RequestParam(required = false) String author){
        try {
            Integer pageSize = 5;
            PageHelper.startPage(pageNum, pageSize);
            List<Book> list = bookService.searchBooks(bookName, author);
            PageInfo<Book> pageInfo = new PageInfo<>(list);
            
            Map<String, Object> result = new HashMap<>();
            result.put("list", list);
            result.put("total", pageInfo.getTotal());
            result.put("pageNum", pageInfo.getPageNum());
            result.put("pageSize", pageInfo.getPageSize());
            result.put("pages", pageInfo.getPages());
            
            return Result.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("获取图书列表失败: " + e.getMessage());
        }
    }
}