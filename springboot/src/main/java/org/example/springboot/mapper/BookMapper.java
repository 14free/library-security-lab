package org.example.springboot.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.springboot.shujvku.Book;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface BookMapper extends tk.mybatis.mapper.common.Mapper<Book> {
    //【漏洞代码】导致 SQL 注入：${} 会将参数直接拼接到 SQL 语句中，不进行预编译处理
    @Select("SELECT * FROM books WHERE book_name LIKE '%${bookName}%' AND author LIKE '%${author}%'")
    List<Book> searchBoooksVuln(@Param("bookName") String bookName,
    @Param("author") String author);

    //【修复代码】使用 #{} 预编译参数，防止 SQL 注入
    // 使用 #{} 后，通配符 % 需要在 Java 代码中拼接
    //@Select("SELECT * FROM books WHERE book_name LIKE #{bookName} AND author LIKE #{author}")
    //List<Book> searchBooksSafe(@Param("bookName") String bookName,
    //@Param("author") String author);
}
