package com.example.library.controller;

        import com.example.library.dao.BookDaoImpl;
        import com.example.library.dto.Page;
        import com.example.library.entity.Book;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.*;

        import javax.servlet.http.HttpServletRequest;
        import java.util.ArrayList;
        import java.util.List;

@Controller
public class BookController {

    private BookDaoImpl bookDao;

    public BookController(BookDaoImpl book) {
        this.bookDao = book;
    }

    @GetMapping(value = "/")
    public String getBooks() {
        return "index";
    }

    @ResponseBody @GetMapping(value = "/list")
    public Page getBooks(@RequestParam(required = false, defaultValue = "5") Integer limit,
                         @RequestParam(required = false, defaultValue = "0") Integer pageNumber) {
        int offset = (pageNumber - 1) * limit;
        return new Page(bookDao.findAll(limit, offset), bookDao.length());
    }


    @GetMapping(value = "/add")
    public String addBook(Model model, @ModelAttribute Book book) {
        model.addAttribute("book", book);
        return "new-book";
    }

    @PostMapping(value = "/save")
    public String saveBook(@ModelAttribute Book book) {
        bookDao.addOrEditBook(book);
        return "redirect:/";
    }

    @GetMapping(value = "/delete")
    public String deleteBook(HttpServletRequest request) {
        int bookId = Integer.parseInt(request.getParameter("id"));
        bookDao.deleteBookById(bookId);
        return "redirect:/";
    }

    @GetMapping(value = "/edit")
    public String editBook(Model model, HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        model.addAttribute("changed", bookDao.getBook(id));
        return "new-book";
    }

    @GetMapping(value = "/take")
    public String takeBook(HttpServletRequest request) {
        bookDao.takeBook(Integer.parseInt(request.getParameter("id")));
        return "redirect:/";
    }

    @GetMapping(value = "/return")
    public String returnBook(HttpServletRequest request) {
        bookDao.returnBook(Integer.parseInt(request.getParameter("id")));
        return "redirect:/";
    }

}
