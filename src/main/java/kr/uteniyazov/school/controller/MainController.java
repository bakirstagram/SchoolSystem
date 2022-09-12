package kr.uteniyazov.school.controller;

import kr.uteniyazov.school.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/index")
public class MainController {

    private final MainService mainService;

    @Autowired
    public MainController(MainService mainService) {
        this.mainService = mainService;
    }


    @GetMapping()
    public String index(){
        return "index";
    }

    @GetMapping("/groups")
    public String getGroups(Model model){
        model.addAttribute("groups", mainService.getGroups());

        return "groups";
    }



    @GetMapping("/books/{id}")
    public String getBook(@PathVariable("id")Long id, Model model){
        model.addAttribute("book", mainService.getBook(id));

        return "/info/book-info";
    }
    @GetMapping("/groups/{id}")
    public String getGroup(@PathVariable("id")Long id, Model model){
        model.addAttribute("group", mainService.getGroup(id));
        model.addAttribute("students", mainService.getStudents().stream().filter(student ->
                student.getGroupp().getId() == id).collect(Collectors.toList()));

        return "/info/group-info";
    }
    @GetMapping("/teachers/{id}")
    public String getTeacher(@PathVariable("id")Long id, Model model){
        model.addAttribute("teacher", mainService.getTeacher(id));

        return "/info/teacher-info";
    }


}
