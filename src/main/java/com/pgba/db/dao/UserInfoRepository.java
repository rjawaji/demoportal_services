package com.pgba.db.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pgba.db.model.UserInfo;

@Repository
@Transactional
public interface UserInfoRepository extends JpaRepository<UserInfo, Long>{
	
	UserInfo findByUserName(String userName);
	
	

}
