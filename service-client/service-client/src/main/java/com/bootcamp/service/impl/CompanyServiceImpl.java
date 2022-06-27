package com.bootcamp.service.impl;

import com.bootcamp.dao.Company;
import com.bootcamp.repository.CompanyRepository;
import com.bootcamp.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final Logger LOGGER = LoggerFactory.getLogger("CompanyServiceImpl");
    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }


    @Override
    public void create(Company company) {
        companyRepository.save(company);
    }


    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }


    @Override
    public void update(Company company, Company newCompany) {
        company.setState(newCompany.getState());
        company.setName(newCompany.getName());
        company.setRuc(newCompany.getRuc());
        companyRepository.save(company);
        LOGGER.info("UPDATE SUCCESS");
    }


    @Override
    public void delete(String id) {
        Optional<Company> companyOption = companyRepository.findById(id);
        if (companyOption.isPresent()) {
            Company company = companyOption.get();
            company.setState(0);
            companyRepository.save(company);
        }

    }

    @Override
    public Optional<Company> findById(String id) {
        return companyRepository.findById(id);
    }
}
