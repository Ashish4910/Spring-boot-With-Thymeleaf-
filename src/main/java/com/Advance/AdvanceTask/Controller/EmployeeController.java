package com.Advance.AdvanceTask.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Advance.AdvanceTask.entity.Employee;
import com.Advance.AdvanceTask.service.EmployeeService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class EmployeeController {

//	private EmployeeDao theEmployeeDao;

	private EmployeeService employeeService;

	@Autowired
	public EmployeeController(EmployeeService theEmployeeService) {

		employeeService = theEmployeeService;
	}

	@GetMapping("/RegisterForm")
	public String registerForm(Model theModel) {

		Employee employee = new Employee();
		theModel.addAttribute("employee", employee);

		return "HTML/RegisterForm";
	}

	/*
	 * Note :- Initiallize BindingResult first and then initiallize Model if is not
	 * initillized first it will not give error of Entity Validation to the form
	 */
	@PostMapping("/registerEmployee")
	public String registerEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult,
			Model model, HttpSession httpSession) {

		if (bindingResult.hasErrors()) {

			model.addAttribute("employee", employee);

			/* also send the binding result for better practice */
//			model.addAttribute("bindingResult", bindingResult);

			return "HTML/RegisterForm";
		}

		String email = employee.getEmail();
		long mobile = employee.getMobile();

		List<Employee> duplicateEnteries = employeeService.duplicateEntry(email, mobile);

		if (!duplicateEnteries.isEmpty()) {
			for (Employee duplicateEmployee : duplicateEnteries) {
				if (duplicateEmployee.getEmail().equals(email)) {
					bindingResult.rejectValue("email", "duplicate.email", "Email already exists");
					return "HTML/RegisterForm";

				} else if (duplicateEmployee.getMobile().equals(mobile)) {
					bindingResult.rejectValue("mobile", "duplicate.mobile", "mobile already exists");
					return "HTML/RegisterForm";
				}
			}
			return "HTML/RegisterForm";
		} else {

			employee.setId(0);

			employeeService.save(employee);
			System.out.println("Inserted value ");

			model.addAttribute("employee", employee);

			return loginForm(model);

		}

	}

	@PostMapping("/loginEmployee")
	public String loginEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult,
			Model model, RedirectAttributes redirectAttributes, HttpSession session) {

		String email = employee.getEmail();
		String password = employee.getPassword();

		System.out.println(" Login Form post " + email + " " + password);
		Boolean valid_Emp = employeeService.loginEmployee(email, password);

		if (valid_Emp) {

			redirectAttributes.addAttribute("email", email);
			redirectAttributes.addAttribute("password", password);

			session.setAttribute("loginSession", employee);
			return "redirect:/EmployeeList";
		} else {

			model.addAttribute("emailNotFound", "Email not found in our database");

			model.addAttribute("employee", employee);
			model.addAttribute("bindingResult", bindingResult);
			return "HTML/LoginForm";
		}

	}

	@GetMapping("/logout")
	public String logout(HttpSession session, HttpServletResponse response) {

		session.invalidate();
		// Set cache control headers to prevent caching of the login page
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setHeader("Expires", "0"); // Proxies
		return "redirect:/loginForm";
	}

	@GetMapping("/loginForm")
	public String loginForm(Model theModel) {
		Employee employee = new Employee();

		theModel.addAttribute("employee", employee);

		return "HTML/LoginForm";

	}

	@GetMapping("/EmployeeList")
	public String EmployeeList(@RequestParam String email, @RequestParam String password, Model model,
			HttpSession session) {

		System.out.println("Employee List : " + email);
		System.out.println("Employee List : " + password);

		model.addAttribute("email", email);
		model.addAttribute("password", password);

		List<Employee> employeeList = employeeService.getEmployeeExcludeCurrentEmail(email);
		System.out.println("Employee List:");
		for (Employee emp : employeeList) {
			System.out.println("ID: " + emp.getId() + ", Name: " + emp.getName() + ", Email: " + emp.getEmail());
			// Print other fields as needed
		}

		if (session.getAttribute("loginSession") != null) {
			model.addAttribute("employees", employeeList);

//			model.addAttribute("session", session);
			return "HTML/EmployeeList";
		} else {

			return "redirect:/loginForm";
		}

	}

	@GetMapping("/getEmployee")
	public List<Employee> getEmployee(Employee theEmployee) {

		List<Employee> getEmployee = employeeService.getEmployee(theEmployee);

		return getEmployee;

	}

	@PutMapping("/updateEmployee")
	public Employee updateEmployee(@RequestBody Employee theEmployee) {
		Employee getEmployee = employeeService.getEmployeeById(theEmployee.getId());

		System.out.println("Get Employee in the Update Employee " + getEmployee);
		if (getEmployee != null) {

			System.out.println("Enter in the Where value is Present in update Employee Mapping ");
			if (theEmployee.getName() != null) {
				getEmployee.setName(theEmployee.getName());
			}
			if (theEmployee.getEmail() != null) {
				getEmployee.setEmail(theEmployee.getEmail());
			}
			if (theEmployee.getPassword() != null) {
				getEmployee.setPassword(theEmployee.getPassword());
			}
			if (theEmployee.getGender() != null) {
				getEmployee.setGender(theEmployee.getGender());
			}
			Long mobile = theEmployee.getMobile();
			if (mobile != null) {
				getEmployee.setMobile(theEmployee.getMobile());
			}

		} else {
			System.out.println("Entered in the else block in update Employee Mapping ");
			return theEmployee;

		}

		Employee employee = employeeService.save(getEmployee);

		return employee;

	}

	@GetMapping("/editEmployee")
	public String editEmployee(@RequestParam String email, Model model) {

//		public String editEmployee(@RequestParam String email, @RequestParam String password, @RequestParam String session,
//				Model model) {
		System.out.println("Edit Employee " + email);

//		System.out.println("Edit Employee " + password);

//		System.out.println("edit Employee Session " + session);

		Employee employee = employeeService.getSingleEmployee(email);

		model.addAttribute("employees", employee);

		return "HTML/EditEmployee";

	}

	@PostMapping("/editEmployeePost")
	public String editEmployee(@Valid @ModelAttribute("employees") Employee employee, BindingResult bindingResult,
			Model model) {

//		if (bindingResult.hasErrors()) {
//			String email = employee.getEmail();
//
//			model.addAttribute("employees", employee);
//			model.addAttribute("bindingResult", bindingResult);
//			return "redirect:/editEmployee?email=" + email;
//		}

		byte[] imageName = employee.getImageData();
		System.out.println("Image Data :" + imageName);

		if (bindingResult.hasErrors()) {
			model.addAttribute("bindingResult", bindingResult);
			return "HTML/EditEmployee";
		}

		String email = employee.getEmail();
		System.out.println("EditEmployeePost : " + email);
		String password = employee.getPassword();

		employeeService.updateEmployee(employee);

		return "redirect:/EmployeeList?email=" + email + "&password=" + password;

	}
}
