package org.example.springboot.service;

import org.example.springboot.mapper.BookMapper;
import org.example.springboot.shujvku.Book;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BookService {
    @Resource
    private BookMapper bookMapper;

    public List<Book> getBook() {
        return bookMapper.selectAll();
    }

    public List<Book> searchBooks(String bookName, String author) {
        /*Example example = new Example(Book.class);
        Example.Criteria criteria = example.createCriteria();
        
        if (bookName != null && !bookName.trim().isEmpty()) {
            criteria.andLike("book_name", "%" + bookName.trim() + "%");
        }
        
        if (author != null && !author.trim().isEmpty()) {
            criteria.andLike("author", "%" + author.trim() + "%");
        }
        
        example.setOrderByClause("id ASC");
        return bookMapper.selectByExample(example);
        */
        // 如果两个条件都为空，直接返回所有数据（分页）
        boolean nameEmpty = (bookName == null || bookName.trim().isEmpty());
        boolean authorEmpty = (author == null || author.trim().isEmpty());

        if (nameEmpty && authorEmpty) {
            return bookMapper.selectAll();
        }

        // 漏洞查询：空条件传 "%" 通配符
        return bookMapper.searchBoooksVuln(
                nameEmpty ? "%" : bookName,
                authorEmpty ? "%" : author
        );
    }
    /*
    在 Java 层拼接通配符，SQL 使用 #{} 预编译
    return bookMapper.searchBooksSafe(
            nameEmpty ? "%" : "%" + bookName + "%",
            authorEmpty ? "%" : "%" + author + "%"
    );
    */

}
