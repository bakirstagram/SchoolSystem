package kr.uteniyazov.school.controller;

import kr.uteniyazov.school.entity.Group;
import kr.uteniyazov.school.entity.Student;
import kr.uteniyazov.school.service.GroupService;
import kr.uteniyazov.school.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }
    @GetMapping
    public String getGroups(Model model,
                              @RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        Page<Group> groups = groupService.findAll(page, 5, Sort.by("id"));
        model.addAttribute("groups", groups);
        model.addAttribute("numbers", IntStream.range(0, groups.getTotalPages()).toArray());
        return "groups";
    }

    @GetMapping("/{id}")
    public String getGroupsWithId(@PathVariable("id")Long id, Model model){
        model.addAttribute("group", groupService.getGroupById(id));
        model.addAttribute("teachers", groupService.getFreeGroupsForTeacher(id));
        model.addAttribute("students", groupService.getStudents().stream().filter(student ->
                Objects.equals(student.getGroupp().getId(), id)).collect(Collectors.toList()));
        return "/info/group-info";
    }
    @GetMapping("/new")
    public String createGroupForm(@ModelAttribute Group group){
        return "/create/group-create";
    }
    @PostMapping("/{id}/set")
    public String setTeacher(@RequestParam("teacher_id")Long teacher_id, @PathVariable("id")Long id){
        groupService.setTeacher(id, teacher_id);
        return "redirect:/groups";
    }
    @PostMapping
    public String addGroup(@ModelAttribute Group group){
        groupService.addNewGroup(group);
        return "redirect:/groups";
    }
}
