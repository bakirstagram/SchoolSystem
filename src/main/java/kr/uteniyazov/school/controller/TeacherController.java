package kr.uteniyazov.school.controller;

import kr.uteniyazov.school.entity.Student;
import kr.uteniyazov.school.entity.Teacher;
import kr.uteniyazov.school.service.GroupService;
import kr.uteniyazov.school.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.stream.IntStream;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;
    private final GroupService groupService;

    @Autowired
    public TeacherController(TeacherService teacherService, GroupService groupService) {
        this.teacherService = teacherService;
        this.groupService = groupService;
    }

    @GetMapping
    public String getTeachers(Model model,
                              @RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        Page<Teacher> teachers = teacherService.findAll(page, 5, Sort.by("id"));
        model.addAttribute("teachers", teachers);
        model.addAttribute("numbers", IntStream.range(0, teachers.getTotalPages()).toArray());
        return "teachers";
    }
    @GetMapping("/new")
    public String createForm(@ModelAttribute Teacher teacher){
        return "/create/teacher-create";
    }
    @GetMapping("/{id}")
    public String getTeacher(@PathVariable("id")Long teacherId, Model model){
        model.addAttribute("teacher", teacherService.getOne(teacherId));

        return "/info/teacher-info";
    }
    @GetMapping("/{id}/edit")
    public String teacherEdit(@PathVariable("id")Long id, Model model){
        model.addAttribute("teacher", teacherService.getOne(id));
        return "/edit/teacher-edit";
    }
    @PostMapping
    public String addTeacher(@ModelAttribute Teacher teacher){
        teacherService.addNewTeacher(teacher);
        return "redirect:/teachers";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute Teacher teacher, @PathVariable("id") Long id){
        teacherService.update(teacher, id);
        return "redirect:/teachers";
    }
}
