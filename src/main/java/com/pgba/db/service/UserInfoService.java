package com.pgba.db.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pgba.db.dao.UserInfoRepository;
import com.pgba.db.model.UserInfo;

@Service
public class UserInfoService{
	
	@Autowired
	private UserInfoRepository userInfoRepository;

	public UserInfo findByUserName(String userName) {
		return userInfoRepository.findByUserName(userName);
	}
	
	public List<UserInfo> getUserInfoList(){
		return userInfoRepository.findAll();
	}
	
}
