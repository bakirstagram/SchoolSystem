package kr.uteniyazov.school.service;

import kr.uteniyazov.school.entity.Book;
import kr.uteniyazov.school.entity.Group;
import kr.uteniyazov.school.entity.Student;
import kr.uteniyazov.school.entity.Teacher;
import kr.uteniyazov.school.repository.GroupRepository;
import kr.uteniyazov.school.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final GroupRepository groupRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository, GroupRepository groupRepository) {
        this.teacherRepository = teacherRepository;
        this.groupRepository = groupRepository;
    }

    public Page<Teacher> findAll(int page, int itemsPerPage, Sort sort) {

        return teacherRepository.findAll(PageRequest.of(page, itemsPerPage, sort));
    }

    public Teacher getOne(Long id) {
        return teacherRepository.findById(id).orElse(null);
    }

    @Transactional
    public void addNewTeacher(Teacher teacher){
        teacherRepository.save(teacher);
    }

    @Transactional
    public void update(Teacher teacherToBeUpdated, Long id) {
        Teacher teacher = teacherRepository.findById(id).orElse(null);
        teacher.setName(teacherToBeUpdated.getName());
        teacher.setAge(teacherToBeUpdated.getAge());
        teacher.setSpecification(teacherToBeUpdated.getSpecification());
        teacherRepository.save(teacher);
    }

    @Transactional
    public void delete(Long id) {
        teacherRepository.deleteById(id);
    }


}

