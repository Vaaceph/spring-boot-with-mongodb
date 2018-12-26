package com.gdsd.sellpurchase.controller;

import com.gdsd.sellpurchase.model.Product;
import java.util.List;
import javax.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    MongoOperations mongoOperations;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Product> getAllProducts() {
        Query query = new Query();
        query.fields().exclude("description");
        return mongoOperations.find(query, Product.class);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Product getPetById(@PathVariable("id") ObjectId id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoOperations.findOne(query, Product.class);
    }
    
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public List<Product> searchProducts(@RequestParam(value = "search", required = false) String search, @RequestParam(value = "order", required = false) String order) {
	
        if(search == null) search = "";
        if(order == null) order = "";
	Query query = new Query();
	query.limit(10);
        if(order.equalsIgnoreCase("asc")) {
            query.with(new Sort(Sort.Direction.ASC, "price"));
        } else {
            query.with(new Sort(Sort.Direction.DESC, "price"));
        }
//        query.fields().exclude("description");
        Criteria criteria  = new Criteria();
        
        criteria.orOperator(Criteria.where("title").regex(search, "i"), Criteria.where("description").regex(search, "i"));
	query.addCriteria(criteria);
        
	return mongoOperations.find(query, Product.class);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Product createPet(@Valid @RequestBody Product product) {
        product.set_id(ObjectId.get());
        mongoOperations.save(product);
        return product;
    }

}
