package cn.com.hvit.workspace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("/hvit-help")
public class HomeController {

  @RequestMapping(method = GET)
  public String home(Model model) {
    System.out.println("just do something");
    return "hvit-help";
  }

}
