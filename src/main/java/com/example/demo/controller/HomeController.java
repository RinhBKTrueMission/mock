package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.reponsitory.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "api/home")
public class HomeController {
    private final ProductRepository repo;

    public HomeController(ProductRepository repo) {
        this.repo = repo;
    }
    @GetMapping("/getall")
    public ResponseEntity<Iterable<Product>> getAll(){
        return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
    }
    @GetMapping("/getinfo/{id}")
    public ResponseEntity<Product> getInfo(@PathVariable String id){
        Optional<Product> userOptional=repo.findById(id);
        return userOptional.map(product -> new ResponseEntity<>(product,HttpStatus.OK)).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
//    @PreAuthorize("hasRole('MODERATOR')")
//    @PostMapping(value = "/setInfo")
//    public ResponseEntity<Product> setInfo(@RequestBody Product  product){
//        return new ResponseEntity<>(repo.save(product), HttpStatus.OK);
//    }
//    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
//    @PutMapping(value = "update/{id}")
//    public ResponseEntity<Product> updateInfo(@PathVariable String id,@RequestBody Product  product){
//        Optional<Product> userOptional=repo.findById(id);
//        return userOptional.map(product1 -> {product.setCode(product1.getCode());
//            return new ResponseEntity<>(repo.save(product),HttpStatus.OK);
//        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
//    @DeleteMapping(value = "delete/{id}")
//    public ResponseEntity<Product> deleteInfo(@PathVariable String id){
//        Optional<Product> userOptional=repo.findById(id);
//        return userOptional.map(product ->
//        {
//            repo.deleteById(product.getCode());
//            return new ResponseEntity<>(product,HttpStatus.OK);
//
//        }).orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
}
