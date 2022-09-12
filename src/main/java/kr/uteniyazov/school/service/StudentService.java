package kr.uteniyazov.school.service;

import kr.uteniyazov.school.entity.Student;
import kr.uteniyazov.school.repository.GroupRepository;
import kr.uteniyazov.school.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class StudentService {

    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, GroupRepository groupRepository) {
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
    }

    public Page<Student> findAll(int page, int itemsPerPage, Sort sort) {

        return studentRepository.findAll(PageRequest.of(page, itemsPerPage, sort));
    }

    public Student getOne(Long id){
        return studentRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Student student, Long group_id){
        student.setGroupp(groupRepository.findById(group_id).get());
        studentRepository.save(student);
    }

    @Transactional
    public void update(Student studentToBeUpdated, Long id){
        Student student = studentRepository.findById(id).orElse(null);
        student.setName(studentToBeUpdated.getName());
        student.setAge(studentToBeUpdated.getAge());
        studentRepository.save(student);
    }
    @Transactional
    public void setGroup(Long group_id) {

    }

    @Transactional
    public void delete(Long id){
        studentRepository.deleteById(id);
    }

}
