package apap.tugasakhir.SIRETAIL.controller;

import apap.tugasakhir.SIRETAIL.model.CabangModel;
import apap.tugasakhir.SIRETAIL.model.UserModel;
import apap.tugasakhir.SIRETAIL.service.CabangService;
import apap.tugasakhir.SIRETAIL.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class CabangController {
    @Autowired
    private UserService userService;

    @Autowired
    private CabangService cabangService;

    @GetMapping("/cabang/add")
    public String addCabang(Model model){
        model.addAttribute("cabang", new CabangModel());
        return "form-add-cabang";
    }

    @PostMapping(value = "/cabang/add", params = {"save"})
    public String addCabangSubmitPage(
            @ModelAttribute CabangModel cabang,
            Model model
    ){
        //user yang membuat
        //UserModel user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getUsername());
        //cabang.setUser(user);
        // status disetujui
        cabang.setStatus(2);
        cabangService.addCabang(cabang);
        model.addAttribute("idCabang", cabang.getNama());
        return "add-cabang-success";
    }
}
