package com.example;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductController {
	@Autowired
	private ProductRepository repo;
	@Autowired
	private ProductService service;
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("/product")
	public String showProduct(Model model) {
		List<Product> product = repo.findAll();
		model.addAttribute("product", product);
		return "view_product";
	}
	
	@GetMapping("/add")
	public String addProduct(Model model) {
		Product product = new Product();
		model.addAttribute("newproduct", product);
		return "new_product";
	}
	
	@PostMapping("/save")
	public String saveProduct(Model model ,@ModelAttribute("newproduct") Product product, @RequestParam(name="save" , required=false) String save, @RequestParam(name="cancel", required=false) String cancel){
		Product product1 = service.get(product.getCode());
		int dem=0;
		if(save!=null) {
			if(product.getCode().equals("")) {
				model.addAttribute("mess", "Code không được để trống");
				dem++;
			}
			if(product1!=null) {
				model.addAttribute("mess1", "Code đã tồn tại");
				dem++;
			}
			if(product.getDescription().equals("")) {
				model.addAttribute("mess2", "Description không được để trống");
				dem++;
			}
			if(product.getPrice()==null) {
	            model.addAttribute("mess3", "Price không được để trống");
	            dem++;
	        } 
			if(dem!=0) {
				return "new_product";
			}
			repo.save(product);
		}
		return "redirect:/product";
	}
	
	@PostMapping("/{id}/edit")
	public String editPr(Model model,@PathVariable String id) {
		Product product = service.get(id);
		model.addAttribute("editproduct", product);
		return "edit";
	}
	@PostMapping("/edit")
	public String SaveEdit(Model model, @ModelAttribute("editproduct") Product product, @RequestParam(name="update", required=false) String update, @RequestParam(name="cancel", required=false) String cancel) {
		int dem=0;
		if(update!=null) {
			if(product.getDescription().equals("")) {
				model.addAttribute("mess", "Description không được để trống");
				dem++;
			}
			if(product.getPrice()==null) {
	            model.addAttribute("mess1", "Price không được để trống");
	            dem++;
	        } 
			if(dem!=0) {
				return "edit";
			}
			Product pr = service.get(product.getCode());
			if(pr!=null) {
				pr.setDescription(product.getDescription());
				pr.setPrice(product.getPrice());
				repo.save(pr);
			}
		}
		return "redirect:/product";
	}
	
	@PostMapping("/{id}/delete")
	public String deletePr(Model model, @PathVariable String id) {
		Product product = service.get(id);
		model.addAttribute("deleteproduct", product);
		return "delete";
	}
	@PostMapping("/delete")
	public String delete(Product product, @RequestParam(name="confirm", required=false) String confirm )  {
		if(confirm!=null) {
			repo.delete(product);
		}
		return "redirect:/product";
	}
	
}







































































