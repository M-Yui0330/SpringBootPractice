package com.example.demo.service;

import com.example.demo.entity.Admin;

public interface AdminService {
	void register(Admin admin);
	
	Admin findByEmail(String email);
}
