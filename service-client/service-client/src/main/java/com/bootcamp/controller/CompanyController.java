package com.bootcamp.controller;

import com.bootcamp.dao.Company;
import com.bootcamp.dao.Person;
import com.bootcamp.handler.ClientException;
import com.bootcamp.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final Logger LOGGER= LoggerFactory.getLogger("CompanyController");
    private CompanyService companyService;
    public  CompanyController(CompanyService companyService){
        this.companyService=companyService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@RequestBody Company company){
        companyService.create(company);
    }
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<Company> findById(@PathVariable String id){
        Optional<Company> company=companyService.findById(id);
        if(company.isPresent()) return Mono.just(company.get());
        else {
            LOGGER.error("error with id: "+id);
            throw new ClientException(HttpStatus.NOT_FOUND,"Don't found the business with id: "+id);}
    }
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Company company, @PathVariable String id){
        Optional<Company> personOptional=companyService.findById(id);
        if(personOptional.isPresent()){
            companyService.update(personOptional.get(),company);
        }else{
            LOGGER.error("error with ud: "+id);
            throw new ClientException(HttpStatus.NOT_FOUND,"don't found person with id: "+id);
        }

    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id){
        companyService.delete(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Company> getAllPersons(){
        return companyService.findAll();
    }
}
