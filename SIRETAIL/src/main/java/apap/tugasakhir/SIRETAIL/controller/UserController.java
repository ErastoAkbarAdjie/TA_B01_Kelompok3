package apap.tugasakhir.SIRETAIL.controller;


import apap.tugasakhir.SIRETAIL.model.RoleModel;
import apap.tugasakhir.SIRETAIL.model.UserModel;
import apap.tugasakhir.SIRETAIL.service.RoleService;
import apap.tugasakhir.SIRETAIL.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/add")
    public String addUserFormPage(Model model) {
        UserModel user = new UserModel();
        List<RoleModel> listRole = roleService.getListRole();
        model.addAttribute("user", user);
        model.addAttribute("listRole", listRole);
        return "form-add-user";
    }

    @PostMapping(value = "/add")
    public String addUserSubmit(@ModelAttribute UserModel user, Model model) {
        userService.addUser(user);
        model.addAttribute("user", user);
        return "add-user-success";
    }

    @GetMapping("/viewAllUser")
    public String listUser(Model model){
        List<UserModel> listUser = userService.getListUser();
        List<RoleModel> listRole = roleService.getListRole();
        model.addAttribute("listUser", listUser);
        model.addAttribute("listRole", listRole);
        return "viewall-user";
    }
}