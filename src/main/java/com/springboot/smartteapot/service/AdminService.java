package com.springboot.smartteapot.service;

import com.springboot.smartteapot.bean.Admin;
import com.springboot.smartteapot.bean.dto.AdminCondition;
import com.springboot.smartteapot.bean.dto.AdminInfo;
import com.springboot.smartteapot.bean.vo.UserInfo;
import com.springboot.smartteapot.bean.vo.UserUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;


/**
 * 管理员服务
 */
public interface AdminService {

	/**
	 * 创建管理员
	 * @param adminInfo
	 * @return
	 */
	AdminInfo create(AdminInfo adminInfo);
	/**
	 * 修改管理员
	 * @param adminInfo
	 * @return
	 */
	AdminInfo update(AdminInfo adminInfo);
	/**
	 * 删除管理员
	 * @param id
	 */
	void delete(Long id);
	/**
	 * 获取管理员详细信息
	 * @param id
	 * @return
	 */
	AdminInfo getInfo(Long id);
	/**
	 * 分页查询管理员
	 * @param condition
	 * @return
	 */
	Page<AdminInfo> query(AdminCondition condition, Pageable pageable);


	Admin findByUsername(String username);
	Admin findByPhone(String phone);
	Admin findByEmail(String email);

	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
	boolean existsByPhone(String phone);

	void createUser(UserInfo userInfo);

	Map<String,Object> updatePassword(UserUpdate userUpdate);
}
