package com.library.service.implement;

import com.library.mapper.ReaderMapper;
import com.library.pojo.Book;
import com.library.pojo.Borrow;
import com.library.pojo.Reader;
import com.library.service.ReaderService;
import com.library.util.DeleteSame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReaderServiceImp implements ReaderService {

    @Autowired
    private ReaderMapper readerMapper;

    @Override
    public List<Book> select(Reader reader) {
        return readerMapper.select(reader);
    }

    @Override
    public void insert(Borrow borrow) {
        readerMapper.insert(borrow);
    }

    @Override
    public void update(Borrow borrow) {
        readerMapper.update(borrow);
    }

    @Override
    public int selectMinId(Borrow borrow) {
        return readerMapper.selectMinId(borrow);
    }

    /**
     * 归还书籍
     */
    @Override
    public void bookReturn(Borrow borrow) {
        //查询书名所对应的ID
        int id = readerMapper.search(borrow);
        //传入ID参数
        borrow.setBookId(id);
        //删除borrow记录
        readerMapper.delete(borrow);
        //将书库中ID所对应的书名状态置为1
        readerMapper.updateBookName(borrow);
    }

    @Override
    public List<Book> selectAllBook(Borrow borrow) {
        return readerMapper.selectAllBook(borrow);
    }

    /**
     * 返回所有书籍，相同的书名只会展示一个
     */
    @Override
    public List<Book> selectAllBooks() {
        List<Book> bookList = readerMapper.selectAllBooks();
        DeleteSame.deleteSame(bookList);
        for (Book book : bookList
        ) {
            if (book.getState() == 1) {
                book.setStateMessage("可借 有剩余");
            } else {
                book.setStateMessage("书本无剩余");
            }
        }
        return bookList;
    }

    /**
     * 查询所借阅的书籍
     */
    @Override
    public List<Book> selectBorrowedBooks(Reader reader) {
        List<Borrow> borrowList = readerMapper.selectBorrowedBooks(reader);
        List<Book> bookListResult = new ArrayList<>();
//        System.out.println(borrowList);
        for (Borrow borrow : borrowList
        ) {
            //循环遍历，得到对应ID的书本的信息
            List<Book> bookList = readerMapper.selectBorrowedBookList(borrow);
            bookListResult.add(bookList.get(0));
        }
//        System.out.println(bookListResult);
        //返回查询到的书的信息
        return bookListResult;
    }

    /**
     * 根据书名查询图书
     */
    @Override
    public List<Book> searchByBookName(Book book) {
        return readerMapper.searchByBookName(book);

    }
}
