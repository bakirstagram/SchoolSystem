package kr.uteniyazov.school.service;

import kr.uteniyazov.school.entity.Book;
import kr.uteniyazov.school.entity.Student;
import kr.uteniyazov.school.repository.BookRepository;
import kr.uteniyazov.school.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public BookService(BookRepository bookRepository, StudentRepository studentRepository) {
        this.bookRepository = bookRepository;
        this.studentRepository = studentRepository;
    }

    public Page<Book> findAll(int page, int itemsPerPage, Sort sort) {

        return bookRepository.findAll(PageRequest.of(page, itemsPerPage, sort));
    }
    public Book getBookById(Long id){
        return bookRepository.findById(id).orElse(null);
    }

    public List<Student> getStudentsWhoHaveNotThisBook(Long bookId){
        return studentRepository
                .findAll()
                .stream()
                .filter(student -> student.getBooks().stream().allMatch(book -> !book.getId().equals(bookId)))
                .collect(Collectors.toList());
    }
    @Transactional
    public void setStudent(Long id, Long student_id) {
//        bookRepository.findById(id).get().getStudents().set(student_id, studentRepository.findById(student_id).orElse(null));
        if(bookRepository.findById(id).get().getStudents().isEmpty()){
            bookRepository
                    .findById(id)
                    .get()
                    .setStudents(new ArrayList<>(
                            List.of(studentRepository.findById(student_id).orElse(null))));
            studentRepository.findById(student_id)
                    .get()
                    .setBooks(new ArrayList<>(List.of(bookRepository.findById(id).orElse(null))));
            Book book = bookRepository.findById(id).orElse(null);
            Student student = studentRepository.findById(student_id).orElse(null);
            bookRepository.save(book);
            studentRepository.save(student);
        }
        else {
            bookRepository
                    .findById(id)
                    .get()
                    .getStudents()
                    .add(studentRepository.findById(student_id).orElse(null));
            studentRepository
                    .findById(student_id)
                    .get()
                    .getBooks()
                    .add(bookRepository.findById(id).orElse(null));
            Book book = bookRepository.findById(id).orElse(null);
            Student student = studentRepository.findById(student_id).orElse(null);
            bookRepository.save(book);
            studentRepository.save(student);
        }
    }

}
