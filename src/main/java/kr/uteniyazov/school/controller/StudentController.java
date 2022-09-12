package kr.uteniyazov.school.controller;

import kr.uteniyazov.school.entity.Student;
import kr.uteniyazov.school.service.GroupService;
import kr.uteniyazov.school.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.stream.IntStream;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final GroupService groupService;

    @Autowired
    public StudentController(StudentService studentService, GroupService groupService) {
        this.studentService = studentService;
        this.groupService = groupService;
    }

    @GetMapping
    public String getStudents(Model model,
                              @RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        Page<Student> students = studentService.findAll(page, 5, Sort.by("id"));
        model.addAttribute("students", students);
        model.addAttribute("numbers", IntStream.range(0, students.getTotalPages()).toArray());
        return "students";
    }

    @GetMapping("/{id}")
    public String getStudent(@PathVariable("id") Long id, Model model) {
        model.addAttribute("student", studentService.getOne(id));

        return "/info/student-info";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("student", studentService.getOne(id));
        return "/edit/student-edit";
    }

    @GetMapping("/create")
    public String getCreateForm(@ModelAttribute Student student, Model model) {
        model.addAttribute("groups", groupService.getGroups());
        return "/create/student-create";
    }

    @PostMapping
    public String create(@RequestParam("group_id") Long group_id, @ModelAttribute Student student) {

        studentService.save(student, group_id);
        return "redirect:/students";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute Student student, @PathVariable("id") Long id) {
        studentService.update(student, id);
        return "redirect:/students";
    }

}
