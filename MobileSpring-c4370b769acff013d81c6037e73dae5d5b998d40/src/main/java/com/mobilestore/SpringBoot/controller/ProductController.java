package com.mobilestore.SpringBoot.controller;

import com.mobilestore.SpringBoot.entity.ErrorObject;
import com.mobilestore.SpringBoot.entity.Product;
import com.mobilestore.SpringBoot.service.ProductService;
import com.mobilestore.SpringBoot.utils.FileUploadUtils;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ACER
 */
@Controller
public class ProductController {

    @Autowired
    ProductService service;

    @GetMapping("/listAll")
    public String listAll(Model model) {
        return listByPage(model, 1);
    }

    @GetMapping("/product/create")
    public String showNewProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "product-add";
    }

    @PostMapping("/product/save")
    public String createProduct(@ModelAttribute("product") Product product, @RequestParam("fileImage") MultipartFile multipartFile,
            BindingResult bindingResult, Model model) throws IOException {
        if (bindingResult.hasErrors()) {
        } else {
            boolean checkEmpty = true;
            String img = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            if (product.getName().trim().isEmpty()) {
                checkEmpty = false;
            }
            if (product.getPrice() == 0) {
                checkEmpty = false;
            }
            if (product.getUnit() == 0) {
                checkEmpty = false;
            }
            if (product.getDescription().trim().isEmpty()) {
                checkEmpty = false;
            }
            if (product.getManufacturer().trim().isEmpty()) {
                checkEmpty = false;
            }
            if (product.getCategory().trim().isEmpty()) {
                checkEmpty = false;
            }
            if (product.getCondition() == null) {
                checkEmpty = false;
            }
            if (checkEmpty) {
                Product saved = service.save(product);
                String uploadDir = "src/main/resources/static/product-images";
                FileUploadUtils.saveFile(uploadDir, multipartFile, saved.getId() + ".png");
                model.addAttribute("message", "Added successful!");
            } else {
                model.addAttribute("messageError", "Cannot empty any fields");
            }
        }
        return "product-add";
    }

    @GetMapping("/page/{pageNumber}")
    public String listByPage(Model model, @PathVariable(name = "pageNumber") Integer currentPage) {
        Page<Product> page = service.listAll(currentPage);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();
        List<Product> list = page.getContent();
        if (list != null) {
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalItems", totalItems);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("listProducts", list);
        }
        return "product-list";
    }

    @GetMapping("/detail/{id}/{pageNumber}")
    public String viewDetail(@PathVariable(name = "pageNumber") Integer currentPage, @PathVariable(name = "id") Integer id, Model model) {
        Product product = service.getProduct(id);
        model.addAttribute("product", product);
        model.addAttribute("currentPage", currentPage);
        return "product-detail";
    }

}
