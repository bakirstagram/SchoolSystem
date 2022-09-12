package kr.uteniyazov.school.service;

import kr.uteniyazov.school.entity.Book;
import kr.uteniyazov.school.entity.Group;
import kr.uteniyazov.school.entity.Student;
import kr.uteniyazov.school.entity.Teacher;
import kr.uteniyazov.school.repository.BookRepository;
import kr.uteniyazov.school.repository.GroupRepository;
import kr.uteniyazov.school.repository.StudentRepository;
import kr.uteniyazov.school.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MainService {

    private final GroupRepository groupRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final BookRepository bookRepository;

    @Autowired
    public MainService(GroupRepository groupRepository, TeacherRepository teacherRepository,
                       StudentRepository studentRepository, BookRepository bookRepository) {
        this.groupRepository = groupRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public List<Teacher> getTeachers() {
        return teacherRepository.findAll();
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public List<Group> getGroups() {
        return groupRepository.findAll();
    }

    public Book getBook(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Student getStudent(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Teacher getTeacher(Long id) {
        return teacherRepository.findById(id).orElse(null);
    }

    public Group getGroup(Long id) {
        return groupRepository.findById(id).orElse(null);
    }



}
