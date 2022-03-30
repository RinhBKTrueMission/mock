package com.example.demo.controller;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetail;
import com.example.demo.entity.Product;
import com.example.demo.model.CartInfo;
import com.example.demo.model.CartLineInfo;
import com.example.demo.model.CustomerInfo;
import com.example.demo.model.Utils;
import com.example.demo.reponsitory.OrderDetailRepository;
import com.example.demo.reponsitory.OrderRepository;
import com.example.demo.reponsitory.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "api/employee")
public class EmployeeController {
    final ProductRepository repo;
private final OrderRepository orderRepository;
private final OrderDetailRepository orderDetailRepository;
    public EmployeeController(ProductRepository repo, OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.repo = repo;
        this.orderRepository = orderRepository;
        this.orderDetailRepository=orderDetailRepository;
    }
    @RequestMapping("/buy_product/{id}")
    public ResponseEntity<?> ProductHandle(HttpServletRequest request, @PathVariable String id){
        Optional<Product> productOptional=repo.findById(id);
        return productOptional.map(product -> {
            CartInfo cartInfo=Utils.getCartInSession(request);
            cartInfo.addProduct(product,1);
            return new ResponseEntity<>(HttpStatus.OK);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @RequestMapping("/buy_product/{id}/{sl}")
    public ResponseEntity<?> listProductHandle(HttpServletRequest request, @PathVariable String id,@PathVariable int sl){
        Optional<Product> productOptional=repo.findById(id);
        return productOptional.map(product -> {
            CartInfo cartInfo=Utils.getCartInSession(request);
            cartInfo.addProduct(product,sl);
            return new ResponseEntity<>(HttpStatus.OK);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @RequestMapping("/remove_product/{id}")
    public ResponseEntity<?> removeProductHandleInCart(HttpServletRequest request, @PathVariable String id){
        Optional<Product> productOptional=repo.findById(id);
        return productOptional.map(product -> {
            CartInfo cartInfo=Utils.getCartInSession(request);
            cartInfo.removeProduct(product,1);
            return new ResponseEntity<>(HttpStatus.OK);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @RequestMapping("/remove_product/{id}/{sl}")
    public ResponseEntity<?> removeListProductHandleInCart(HttpServletRequest request, @PathVariable String id,@PathVariable int sl){
        Optional<Product> productOptional=repo.findById(id);
        return productOptional.map(product -> {
            CartInfo cartInfo=Utils.getCartInSession(request);
            cartInfo.removeProduct(product,sl);
            return new ResponseEntity<>(HttpStatus.OK);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping(value = { "/shoppingCart" })
    public String shoppingCartHandler(HttpServletRequest request) {
        CartInfo myCart = Utils.getCartInSession(request);
        List<CartLineInfo> lstCustomerInfo=  myCart.getCartLines();
        return lstCustomerInfo.toString();

    }
    @RequestMapping(value = { "/update_product/{id}/{sl}" })
    public ResponseEntity<?>  updateListProductHandleInCart(HttpServletRequest request, @PathVariable String id, @PathVariable int sl) {
        Optional<Product> productOptional=repo.findById(id);
        return productOptional.map(product -> {
            CartInfo cartInfo=Utils.getCartInSession(request);
            cartInfo.updateProduct(product.getCode(),sl);
            return new ResponseEntity<>(HttpStatus.OK);
        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
    @PostMapping(value = "shoppingCartInfoCus")
    public ResponseEntity<?> saveCustomerInfo(HttpServletRequest request,@RequestBody CustomerInfo customerInfo){
        CartInfo cartInfo = Utils.getCartInSession(request);
        cartInfo.setCustomerInfo(customerInfo);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping(value = "shoppingCartConfirm")
    public String showCart(HttpServletRequest request){
        CartInfo cartInfo = Utils.getCartInSession(request);

        return cartInfo.toString();
    }
    @PostMapping(value = "shoppingCartConfirm")
    public String saveCart(HttpServletRequest request){
        CartInfo cartInfo = Utils.getCartInSession(request);
        saveOrder(cartInfo);
        return cartInfo.toString();
    }
    public  void saveOrder(CartInfo cartInfo){
        Order order=new Order();
        order.setId(UUID.randomUUID().toString());
        order.setOrderNum(orderRepository.getMaxOrderNum().getOrderNum()+1);
        order.setOrderDate(new Date());
        order.setAmount(cartInfo.getAmountTotal());
        order.setCustomerName(cartInfo.getCustomerInfo().getName());
        order.setCustomerAddress(cartInfo.getCustomerInfo().getAddress());
        order.setCustomerEmail(cartInfo.getCustomerInfo().getEmail());
        order.setCustomerPhone(cartInfo.getCustomerInfo().getPhone());
        orderRepository.save(order);
        List<CartLineInfo> lines = cartInfo.getCartLines();

        for (CartLineInfo line : lines) {
            OrderDetail detail = new OrderDetail();
            detail.setId(UUID.randomUUID().toString());
            detail.setOrder(order);
            detail.setAmount(line.getAmount());
            detail.setPrice(line.getProduct().getPrice());
            detail.setQuality(line.getQuanity());

            String code = line.getProduct().getCode();
            Product product = repo.getById(code);
            detail.setProduct(product);
            orderDetailRepository.save(detail);


        }
        cartInfo.setOrderNum( order.getOrderNum());

    }


}