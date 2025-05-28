package com.example.demo.controller;

import java.util.Optional;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Admin;
import com.example.demo.entity.Contact;
import com.example.demo.form.AdminForm;
import com.example.demo.repository.ContactRepository;
import com.example.demo.service.AdminService;

@Controller
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private ContactRepository contactRepository;
	
	@GetMapping("/admin/signup")
	public String showSignupForm(Model model) {
		model.addAttribute("adminForm", new AdminForm());
		return "admin/signup";
	}
	
	@PostMapping("/admin/signup")
	public String registerAdmin(@ModelAttribute("adminForm") AdminForm adminForm) {
		Admin admin = new Admin();
		
		admin.setLastName(adminForm.getLastName());
		admin.setFirstName(adminForm.getFirstName());
		admin.setEmail(adminForm.getEmail());
		admin.setPassword(adminForm.getPassword());
		
		adminService.register(admin);
		
		return "redirect:/admin/signin";
	}
	
	@GetMapping("/admin/signin")
	public String showSigninForm(Model model) {
		model.addAttribute("adminForm", new AdminForm());
		return "admin/signin";
	}
	
	@PostMapping("/admin/signin")
	public String login(@ModelAttribute("adminForm") AdminForm adminForm, HttpSession session, Model model) {
		Admin admin = adminService.findByEmail(adminForm.getEmail());
		
		if (admin == null || !admin.getPassword().equals(adminForm.getPassword())) {
			model.addAttribute("loginError", "メールアドレスまたはパスワードが正しくありません");
			return "admin/signin";
		}
		
		session.setAttribute("admin", admin);
		return "redirect:/admin/contacts";
		
	}
	
	@PostMapping("/admin/signout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/admin/signin";
	}
	
	 @GetMapping("/admin/contacts")
	 public String showContactList(Model model, HttpSession session) {
		 Admin admin = (Admin) session.getAttribute("admin");
		 if (admin == null) {
			 return "redirect:/admin/signin";
		 }
		 
		 model.addAttribute("contactList", contactRepository.findAll());
		 return "admin/contacts";
	 }
	 
	 @GetMapping("/admin/contacts/{id}")
	 public String showContactDetail(@PathVariable("id") Long id, Model model, HttpSession session) {
		 Admin admin = (Admin) session.getAttribute("admin");
		 if (admin == null) {
			 return "redirect:/admin/signin";
		 }
		 
		 Optional<Contact> contact = contactRepository.findById(id);
		 if (contact.isEmpty()) {
			 return "redirect:/admin/contacts";
		 }
		 
		 model.addAttribute("contact", contact.get());
		 return "admin/detail";
	 }
	 
	 
	 @GetMapping("/admin/contacts/{id}/edit")
	 public String showEditForm(@PathVariable("id") Long id, Model model, HttpSession session) {
		 Admin admin = (Admin) session.getAttribute("admin");
		 if (admin == null) {
			 return "redirect:/admin/signin";
		 }
		 
		 Optional<Contact> contact = contactRepository.findById(id);
		 if (contact.isEmpty()) {
			 return "redirect:/admin/contacts";
		 }
		 
		 model.addAttribute("contact", contact.get());
		 return "admin/edit";
	 }
	 
	 @PostMapping("/admin/contacts/{id}/edit")
	 public String updateContact (
		 @PathVariable("id") Long id,
		 @ModelAttribute("contact") @Valid Contact contactForm,
		 BindingResult result,
		 Model model,
		 HttpSession session
	 ) {
		 Admin admin = (Admin) session.getAttribute("admin");
		 if (admin == null) {
			 return "redirect:/admin/signin";
		 }
		 
		 if (result.hasErrors()) {
			 model.addAttribute("contact", contactForm);
			 return "admin/edit";
		 }
		 
		 Optional<Contact> contactOptional = contactRepository.findById(id);
		 if (contactOptional.isEmpty()) {
			 return "redirect:/admin/contacts";
		 }
		 
		 Contact contact = contactOptional.get();
		 contact.setLastName(contactForm.getLastName());
		 contact.setFirstName(contactForm.getFirstName());
		 contact.setEmail(contactForm.getEmail());
		 contact.setPhone(contactForm.getPhone());
		 contact.setZipCode(contactForm.getZipCode());
		 contact.setAddress(contactForm.getAddress());
		 contact.setBuildingName(contactForm.getBuildingName());
		 contact.setContactType(contactForm.getContactType());
		 contact.setBody(contactForm.getBody());
		 
		 contactRepository.save(contact);
		 
		 return "redirect:/admin/contacts/" + id;
	 }
	 
	 @PostMapping("/admin/contacts/{id}/delete")
	 public String deleteContact(@PathVariable("id") Long id, HttpSession session) {
		 Admin admin = (Admin) session.getAttribute("admin");
		 if (admin == null) {
			 return "redirect:/admin/signin";
		 }
		 
		 contactRepository.deleteById(id);
		 return "redirect:/admin/contacts";
	 }
}
