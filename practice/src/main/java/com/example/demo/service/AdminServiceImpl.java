package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Admin;
import com.example.demo.repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Override
	public void register(Admin admin) {
		adminRepository.save(admin);
	}
	
	@Override
	public Admin findByEmail(String email) {
		return adminRepository.findByEmail(email);
	}
}
