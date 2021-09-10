package com.redis.RedisDemo;

import com.redis.RedisDemo.entity.Product;
import com.redis.RedisDemo.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication

@RestController
@RequestMapping("/product")

@EnableCaching

public class RedisDemoApplication {

	@Autowired
	private ProductRepo repo;

	@PostMapping
	public Product save(@RequestBody Product product) {
		return repo.save(product);
	}

	@GetMapping("/getall")
	public List<Product> getAllProducts() {
		return repo.findAll();
	}



	@GetMapping("/{id}")
	@Cacheable(key= "#id",value = "Product")
	public Product findProduct(@PathVariable int id) {
		return repo.findProductById(id);
	}


	@DeleteMapping("/{id}")
	public String remove(@PathVariable int id)   {
		return repo.deleteProduct(id);
	}

	public static void main(String[] args) {
		SpringApplication.run(RedisDemoApplication.class, args);
	}





}
