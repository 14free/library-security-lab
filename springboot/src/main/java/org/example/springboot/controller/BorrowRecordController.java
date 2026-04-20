package org.example.springboot.controller;

import org.example.springboot.common.Result;
import org.example.springboot.service.BorrowRecordService;
import org.example.springboot.shujvku.BorrowRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/borrow")
public class BorrowRecordController {
    
    @Autowired
    private BorrowRecordService borrowRecordService;

    @GetMapping("/records")
    public Result getBorrowRecords(@RequestParam Integer userId) {
        try {
            //直接用的前端传来的  没有判断用户  直接改URL里的userId=2 就能看别人记录
            List<BorrowRecord> records = borrowRecordService.getBorrowRecordsByUserId(userId);
            return Result.success(records);
        } catch (Exception e) {
            return Result.error("获取借阅记录失败: " + e.getMessage());
        }
    }
    /*
    @GetMapping("/records")
public Result getBorrowRecords(HttpServletRequest request) {
    // 修复：从 Session 中获取当前登录用户的真实 ID
    HttpSession session = request.getSession();
    User currentUser = (User) session.getAttribute("loginUser");

    if (currentUser == null) {
        return Result.error("请先登录");
    }

    // 使用 Session 里的真实 ID，完全忽略前端传来的参数
    List<BorrowRecord> records = borrowRecordService.getBorrowRecordsByUserId(currentUser.getId());
    return Result.success(records);
}

     */
    @GetMapping("/record/{id}")
    public Result getBorrowRecordDetail(@PathVariable Integer id) {
        try {
            BorrowRecord record = borrowRecordService.getBorrowRecordById(id);
            return Result.success(record);
        } catch (Exception e) {
            return Result.error("获取借阅记录详情失败: " + e.getMessage());
        }
    }

    @PostMapping("/add")
    public Result addBorrow(@RequestBody Map<String, Integer> params) {
        try {
            Integer userId = params.get("userId");
            Integer bookId = params.get("bookId");
            borrowRecordService.addBorrow(userId, bookId);
            return Result.success("借阅成功");
        } catch (Exception e) {
            return Result.error("借阅失败: " + e.getMessage());
        }
    }
}
