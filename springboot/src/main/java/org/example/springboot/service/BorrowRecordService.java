package org.example.springboot.service;

import org.example.springboot.mapper.BorrowRecordMapper;
import org.example.springboot.shujvku.BorrowRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BorrowRecordService {
    
    @Autowired
    private BorrowRecordMapper borrowRecordMapper;
    
    /**
     * 查询用户的借阅记录列表
     */
    public List<BorrowRecord> getBorrowRecordsByUserId(Integer userId) {
        if (userId == null) {
            throw new RuntimeException("用户ID不能为空");
        }
        return borrowRecordMapper.selectByUserId(userId);
    }
    
    /**
     * 查询单条借阅记录详情
     */
    public BorrowRecord getBorrowRecordById(Integer id) {
        if (id == null) {
            throw new RuntimeException("订单ID不能为空");
        }
        BorrowRecord record = borrowRecordMapper.selectById(id);
        if (record == null) {
            throw new RuntimeException("借阅记录不存在");
        }
        return record;
    }

    /**
     * 添加借阅记录
     */
    public void addBorrow(Integer userId, Integer bookId) {
        if (userId == null || bookId == null) {
            throw new RuntimeException("参数不能为空");
        }
        BorrowRecord record = new BorrowRecord();
        record.setUserId(userId);
        record.setBookId(bookId);
        record.setBorrowTime(new Date());
        record.setStatus("借阅中");
        borrowRecordMapper.insert(record);
    }
}
