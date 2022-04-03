package com.java.controller;

import java.security.Principal;
import java.util.List;
import java.util.Locale;

import com.cerner.beadledom.jaxrs.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.java.entity.Customer;
import com.java.entity.Order;
import com.java.repository.CustomersRepository;
import com.java.repository.OrderRepository;
import com.java.service.ShoppingCartService;
import com.java.service.WishListService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.security.PermitAll;

@Controller
public class AccountController extends CommonController {

	@Autowired
	ShoppingCartService shoppingCartService;

	@Autowired
	CustomersRepository customersRepository;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	WishListService wishListService;

	@GetMapping(value = "/account")
	public String account(Model model, Principal principal) {

		model.addAttribute("customer", new Customer());
		Customer customer = customersRepository.FindByEmail(principal.getName()).get();
		model.addAttribute("customer", customer);

		List<Order> listO2 = orderRepository.findByCustomerId(customer.getCustomerId());
		model.addAttribute("orders2", listO2);

		model.addAttribute("totalCartItemWishs", wishListService.getCount());
		model.addAttribute("totalCartItems", shoppingCartService.getCount());
		model.addAttribute("flag", 1);
		return "site/account";
	}

	@GetMapping(value = "/editAccount")
	public String showEditForm(Model model, Principal principal){
		model.addAttribute("customer", new Customer());
		Customer customer = customersRepository.FindByEmail(principal.getName()).get();
		model.addAttribute("customer", customer);

		List<Order> listO2 = orderRepository.findByCustomerId(customer.getCustomerId());
		model.addAttribute("orders2", listO2);

		model.addAttribute("totalCartItemWishs", wishListService.getCount());
		model.addAttribute("totalCartItems", shoppingCartService.getCount());
		model.addAttribute("flag", 0);

		return "site/account";
	}

	@PostMapping(value = "/editAccount")
	public String editCustomer(Model model, Principal principal, @RequestParam("fullname") String fullname, @RequestParam("email") String email){
		model.addAttribute("customer", new Customer());
		Customer customer = customersRepository.FindByEmail(principal.getName()).get();
		customer.setEmail(email);
		customer.setFullname(fullname);
		customersRepository.save(customer);

		model.addAttribute("customer", new Customer());
		Customer customer2 = customersRepository.FindByEmail(email).get();
		model.addAttribute("customer", customer2);

		List<Order> listO2 = orderRepository.findByCustomerId(customer2.getCustomerId());
		model.addAttribute("orders2", listO2);

		model.addAttribute("totalCartItemWishs", wishListService.getCount());
		model.addAttribute("totalCartItems", shoppingCartService.getCount());
		model.addAttribute("flag", 1);
		return "site/account";
	}

	@GetMapping("/updatePassword")
	public String showUpdateForm(Model model, Principal principal){
		model.addAttribute("customer", new Customer());
		Customer customer = customersRepository.FindByEmail(principal.getName()).get();
		model.addAttribute("customer", customer);

		List<Order> listO2 = orderRepository.findByCustomerId(customer.getCustomerId());
		model.addAttribute("orders2", listO2);

		model.addAttribute("totalCartItemWishs", wishListService.getCount());
		model.addAttribute("totalCartItems", shoppingCartService.getCount());
		model.addAttribute("flag", 2);
		return "site/account";
	}

}
