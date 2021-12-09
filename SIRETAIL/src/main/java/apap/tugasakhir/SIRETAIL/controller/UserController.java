package apap.tugasakhir.SIRETAIL.controller;


import apap.tugasakhir.SIRETAIL.model.RoleModel;
import apap.tugasakhir.SIRETAIL.model.UserModel;
import apap.tugasakhir.SIRETAIL.service.RoleService;
import apap.tugasakhir.SIRETAIL.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String listUser(Model model) {
        List<UserModel> listUser = userService.getListUser();
        model.addAttribute("listUser", listUser);
        model.addAttribute("role", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
        return "view-all-user";
    }

    @GetMapping("/update/{id}")
    public String updateUserFormPage(
            @PathVariable Integer id,
            Model model
    ) {
        UserModel user = userService.getUserById(id);

        model.addAttribute("user", user);
        model.addAttribute("role", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
        return "form-update-user";
    }

    @PostMapping("/update/{id}")
    public String updateUserSubmitPage(
            @PathVariable Integer id,
            @ModelAttribute UserModel user,
            Model model
    ) {
        try {
            String password = userService.getUserById(id).getPassword();
            user.setPassword(password);
            UserModel updatedUser = userService.updateUser(user);
            model.addAttribute("message", "user berhasil di-update");
            model.addAttribute("pageTitle", "Daftar User");
            model.addAttribute("url", "/user/viewAllUser");
            return "success-page";
        } catch (Exception e) {
            model.addAttribute("error", "user tidak behasil di-update");
            model.addAttribute("pageTitle", "Daftar User");
            model.addAttribute("url", "/user/viewAllUser");
            return "error-page";
        }

    }
}