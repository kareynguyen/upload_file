package controller;

import model.Employee;
import model.EmployeeForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import service.EmployeeService;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService = new EmployeeService();
    @GetMapping("")
    public String index(Model model){
        List<Employee> employees = employeeService.findAll();
        model.addAttribute("employees", employees);
        return "/index";
    }

    @Value("${file-upload}")
    private String fileUpload;

    @GetMapping("/create")
    public String showCreateForm(Model model){
        model.addAttribute("employeeForm", new Employee());
        return "/create";
    }

    @PostMapping("/save")
    public ModelAndView saveProduct(@ModelAttribute EmployeeForm employeeForm) {
        MultipartFile multipartFile = employeeForm.getAvatar();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(employeeForm.getAvatar().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Employee employee = new Employee(employeeForm.getId(), employeeForm.getName(), employeeForm.getDate(), fileName, employeeForm.isGender());

        employeeService.save(employee);

        ModelAndView modelAndView = new ModelAndView("/create");
        modelAndView.addObject("employeeForm", employeeForm);
        modelAndView.addObject("message", "Created new employee successfully !");
        return modelAndView;
    }
}
