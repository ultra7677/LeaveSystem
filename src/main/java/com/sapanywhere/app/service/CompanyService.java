package com.sapanywhere.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapanywhere.app.entity.Company;
import com.sapanywhere.app.repository.CompanyRepository;

@Service
public class CompanyService {
	@Autowired CompanyRepository companyRepository;
	
	public void create(Company company){
		this.companyRepository.save(company);
	}
	
	public Company findById(Long id){
		return this.companyRepository.findOne(id);
	}
	
	public void save(Company company){
		this.companyRepository.save(company);
	}
}
