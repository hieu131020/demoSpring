/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobilestore.SpringBoot.controller;

import com.mobilestore.SpringBoot.entity.Cart;
import com.mobilestore.SpringBoot.entity.Order;
import com.mobilestore.SpringBoot.entity.OrderDetail;
import com.mobilestore.SpringBoot.entity.Product;
import com.mobilestore.SpringBoot.service.OrderDetailService;
import com.mobilestore.SpringBoot.service.OrderService;
import com.mobilestore.SpringBoot.service.ProductService;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author ACER
 */
@Controller
public class CartController {

    public final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderDetailService orderDetailService;

    @GetMapping("/clearAll")
    public String clearAll(HttpSession session, Model model) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart.getCart() == null || cart.getCart().isEmpty()) {
            model.addAttribute("messageError", "Your cart already cleared!");
        }
        cart.setCart(new HashMap<>());
        session.setAttribute("cart", cart);
        return "cart";
    }

    @GetMapping("/checkOut")
    public String checkOut(HttpSession session, Model model, Principal principal) {
        Cart cart = (Cart) session.getAttribute("cart");
        double total = 0;
        int quantityProduct = 0;
        int quantityOrdered = 0;
        int quantityAvaiable = 0;
        int quantityLeft = 0;

        if (cart.getCart() == null || cart.getCart().isEmpty()) {
            model.addAttribute("messageError", "Nothing to buy!");
        } else {
            int idOrder = 0;
            for (Map.Entry<Integer, Product> entry : cart.getCart().entrySet()) {
                Product product = entry.getValue();
                total = total + product.getPrice() * product.getUnit();
            }
            Date now = new Date();
            Order order = new Order(null, principal.getName(), df.format(now), total);
            for (Map.Entry<Integer, Product> entry : cart.getCart().entrySet()) {
                Product product = entry.getValue();
                Product pro = productService.getProduct(product.getId());
                quantityProduct = pro.getUnit(); // 5 fullQuantity
                quantityOrdered = product.getUnit(); //3 quantity ordered
                quantityLeft = productService.sumQuantity(product.getId()); //0 quantity trong detail
                quantityAvaiable = quantityProduct - quantityOrdered - quantityLeft; //quantity con lai 2

                if (quantityAvaiable >= 0) {
                    orderService.save(order);
                    idOrder = orderService.getIdOrder();
                    OrderDetail detail = new OrderDetail(null, idOrder, product.getId(), product.getPrice(), product.getUnit());
                    orderDetailService.save(detail);
                    model.addAttribute("message", "Ordered successful!");
                } else {
                    model.addAttribute("messageError", product.getName() + " just only have " + (quantityProduct - quantityLeft) + " left!");
                }
            }
            cart.getCart().clear();
            cart.setCart(cart.getCart());
        }
        return "cart";
    }

    @GetMapping("/addInDetail")
    public String getOrderInDetail(HttpSession session, @RequestParam("id") Integer id, Model model, Principal principal) {
        int quantityProduct = 0;
        int amount = 1;
        Product productFound = productService.getProduct(id);

        Product productOrdered = new Product(productFound.getId(), productFound.getName(), productFound.getPrice(), amount,
                productFound.getDescription(), productFound.getManufacturer(), productFound.getCategory(),
                productFound.getCondition());

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart(principal.getName(), null);
            cart.add(productOrdered);
        } else {
            HashMap<Integer, Product> list = cart.getCart();
            quantityProduct = productService.getQuantityProduct(productOrdered.getId()); // 5
            int quantityLeft = productService.sumQuantity(productOrdered.getId()); // 3
            int quantityAvaiable = quantityProduct - quantityLeft;
            boolean check = productService.checkQuantity(list, productOrdered.getId(), quantityAvaiable, amount);
            if (check) {
                if (list.containsKey(id)) {
                    int quantityCart = list.get(id).getUnit();
                    productOrdered.setUnit(quantityCart + amount);
                }
                list.put(id, productOrdered);
                cart.setCart(list);
            } else {
                return "redirect:/listAll";
            }
        }
        session.setAttribute("cart", cart);
        return "redirect:/listAll";
    }

    @GetMapping("/add/{pageNumber}")
    public String getOrder(@PathVariable(name = "pageNumber") int pageNumber, HttpSession session, @RequestParam("id") Integer id, Model model, Principal principal) {
        int quantityProduct = 0;
        int amount = 1;
        Product productFound = productService.getProduct(id);

        Product productOrdered = new Product(productFound.getId(), productFound.getName(), productFound.getPrice(), amount,
                productFound.getDescription(), productFound.getManufacturer(), productFound.getCategory(),
                productFound.getCondition());

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart(principal.getName(), null);
            cart.add(productOrdered);
        } else {
            HashMap<Integer, Product> list = cart.getCart();
            quantityProduct = productService.getQuantityProduct(productOrdered.getId()); // 5
            int quantityLeft = productService.sumQuantity(productOrdered.getId()); // 3
            int quantityAvaiable = quantityProduct - quantityLeft;
            boolean check = productService.checkQuantity(list, productOrdered.getId(), quantityAvaiable, amount);
            if (check) {
                if (list.containsKey(id)) {
                    int quantityCart = list.get(id).getUnit();
                    productOrdered.setUnit(quantityCart + amount);
                }
                list.put(id, productOrdered);
                cart.setCart(list);
            } else {
                return "redirect:/listAll";
            }
        }
        session.setAttribute("cart", cart);
        return "redirect:/page/" + pageNumber;
    }

    @GetMapping("/viewCart")
    public String viewCart(HttpSession session, Principal principal) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart(principal.getName(), null);
        }
        session.setAttribute("cart", cart);
        return "cart";
    }

    @GetMapping("/delete/{id}")
    public String deleteItem(HttpSession session, @PathVariable(name = "id") int id) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            cart.delete(id);
        }
        return "cart";
    }
}
