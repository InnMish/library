package com.example.library.controller;

        import com.example.library.dao.BookDaoImpl;
        import com.example.library.entity.Book;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.ModelAttribute;
        import org.springframework.web.bind.annotation.PostMapping;
        import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BookController {

    private BookDaoImpl bookDao;

    public BookController(BookDaoImpl book) {
        this.bookDao = book;
    }

    @GetMapping(value = "/")
    public String getBooks(Model model) {
        model.addAttribute("books", bookDao.findAll());
        return "index";
    }

    @GetMapping(value = "/add")
    public String addBook(Model model, @ModelAttribute Book book) {
        //bookDao.addBook("book", book);
        model.addAttribute("book", book);
        return "new-book";
    }

    @PostMapping(value = "/save")
    public String saveBook(@ModelAttribute Book book) {
        bookDao.addBook(book);
        return "redirect:/";
    }

}
