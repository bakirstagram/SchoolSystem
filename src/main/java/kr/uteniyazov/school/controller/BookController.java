package kr.uteniyazov.school.controller;

import kr.uteniyazov.school.entity.Book;
import kr.uteniyazov.school.entity.Student;
import kr.uteniyazov.school.entity.Teacher;
import kr.uteniyazov.school.service.BookService;
import kr.uteniyazov.school.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.stream.IntStream;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String getBooks(Model model,
                              @RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        Page<Book> books = bookService.findAll(page, 5, Sort.by("id"));
        model.addAttribute("books", books);
        model.addAttribute("numbers", IntStream.range(0, books.getTotalPages()).toArray());
        return "books";
    }

    @GetMapping("/{id}")
    public String getBook(@PathVariable("id")Long id, Model model){
        model.addAttribute("book", bookService.getBookById(id));
        model.addAttribute("students", bookService.getStudentsWhoHaveNotThisBook(id));
        return "/info/book-info";
    }
    @PostMapping("/{id}/set")
    public String setStudent(@RequestParam("student_id")Long student_id, @PathVariable("id")Long id){
        bookService.setStudent(id, student_id);
        return "redirect:/books/"+id;
    }

}
