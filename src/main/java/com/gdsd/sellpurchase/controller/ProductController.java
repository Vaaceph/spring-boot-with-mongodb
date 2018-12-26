package com.gdsd.sellpurchase.controller;

import com.gdsd.sellpurchase.model.Product;
import com.gdsd.sellpurchase.repositories.ProductRepository;
import java.util.List;
import javax.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository repository;
    
    @Autowired
    MongoOperations mongoOperations;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Product> getAllProducts() {
        return mongoOperations.findAll(Product.class);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Product getPetById(@PathVariable("id") ObjectId id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoOperations.findOne(query, Product.class);
    }

//    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
//    public void modifyPetById(@PathVariable("id") ObjectId id, @Valid @RequestBody Product product) {
//        product.set_id(id);
//        mongoOperations.save(product);
//    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Product createPet(@Valid @RequestBody Product product) {
        product.set_id(ObjectId.get());
        mongoOperations.save(product);
        return product;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deletePet(@PathVariable ObjectId id) {
        repository.delete(repository.findBy_id(id));
    }

}
