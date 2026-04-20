package org.example.springboot.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.example.springboot.shujvku.BorrowRecord;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface BorrowRecordMapper extends tk.mybatis.mapper.common.Mapper<BorrowRecord> {
    
    @Results({
        @Result(column = "id", property = "id"),
        @Result(column = "user_id", property = "userId"),
        @Result(column = "book_id", property = "bookId"),
        @Result(column = "borrow_time", property = "borrowTime"),
        @Result(column = "return_time", property = "returnTime"),
        @Result(column = "status", property = "status"),
        @Result(column = "create_time", property = "createTime"),
        @Result(column = "update_time", property = "updateTime"),
        @Result(column = "book_name", property = "bookName")
    })
    @Select("SELECT br.*, b.book_name FROM borrow_records br LEFT JOIN books b ON br.book_id = b.id WHERE br.user_id = #{userId} ORDER BY br.borrow_time DESC")
    List<BorrowRecord> selectByUserId(@Param("userId") Integer userId);
    
    @Results({
        @Result(column = "id", property = "id"),
        @Result(column = "user_id", property = "userId"),
        @Result(column = "book_id", property = "bookId"),
        @Result(column = "borrow_time", property = "borrowTime"),
        @Result(column = "return_time", property = "returnTime"),
        @Result(column = "status", property = "status"),
        @Result(column = "create_time", property = "createTime"),
        @Result(column = "update_time", property = "updateTime"),
        @Result(column = "book_name", property = "bookName")
    })
    @Select("SELECT br.*, b.book_name FROM borrow_records br LEFT JOIN books b ON br.book_id = b.id WHERE br.id = #{id}")
    BorrowRecord selectById(@Param("id") Integer id);
}
