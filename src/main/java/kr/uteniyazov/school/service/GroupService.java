package kr.uteniyazov.school.service;

import kr.uteniyazov.school.entity.Group;
import kr.uteniyazov.school.entity.Student;
import kr.uteniyazov.school.entity.Teacher;
import kr.uteniyazov.school.repository.GroupRepository;
import kr.uteniyazov.school.repository.StudentRepository;
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
public class GroupService {
    private final GroupRepository groupRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository, TeacherRepository teacherRepository, StudentRepository studentRepository) {
        this.groupRepository = groupRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    public Page<Group> findAll(int page, int itemsPerPage, Sort sort) {

        return groupRepository.findAll(PageRequest.of(page, itemsPerPage, sort));
    }
    public List<Group> getGroups(){
        return groupRepository.findAll();
    }

    public Group getGroupById(Long id) {
        return groupRepository.findById(id).orElse(null);
    }
//    public Student getStudent(Long id) {
//        return studentRepository.findById(id).orElse(null);
//    }
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }
    public List<Teacher> getFreeGroupsForTeacher(Long id) {
        return teacherRepository
                .findAll()
                .stream()
                .filter(teacher -> teacher
                        .getGroupps()
                        .stream()
                        .noneMatch(group -> group.getId().equals(id)))
                .collect(Collectors.toList());
    }

    @Transactional
    public void setTeacher(Long id, Long teacher_id) {
        if (teacherRepository.findById(id).get().getGroupps().isEmpty()) {
            groupRepository
                    .findById(id)
                    .get()
                    .setTeacher(new ArrayList<>(
                            List.of(teacherRepository.findById(teacher_id).orElse(null))));

            teacherRepository.findById(teacher_id)
                    .get()
                    .setGroupps(new ArrayList<>(List.of(groupRepository.findById(id).orElse(null))));

            Group group = groupRepository.findById(id).orElse(null);
            Teacher teacher = teacherRepository.findById(teacher_id).orElse(null);
            groupRepository.save(group);
            teacherRepository.save(teacher);
        } else {
            groupRepository
                    .findById(id)
                    .get()
                    .getTeacher()
                    .add(teacherRepository.findById(teacher_id).orElse(null));

            teacherRepository
                    .findById(teacher_id)
                    .get()
                    .getGroupps()
                    .add(groupRepository.findById(id).orElse(null));

            Group group = groupRepository.findById(id).orElse(null);
            Teacher teacher = teacherRepository.findById(teacher_id).orElse(null);
            groupRepository.save(group);
            teacherRepository.save(teacher);
        }
    }

    @Transactional
    public void addNewGroup(Group group) {
        groupRepository.save(group);
    }

//    model.addAttribute("books", teacherService.getFreeGroupsF
//    orTeacher(id));
}
