package apap.tugasakhir.SIRETAIL.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
  @RequestMapping("/")
  public String home(Model model){
    model.addAttribute("role", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
    return "home";
  }

  @RequestMapping ("/login")
  public String login() {
    return "login";
  }

  @RequestMapping ("/viewalluser")
  public String user() {
    return "view-all-user";
  }
}