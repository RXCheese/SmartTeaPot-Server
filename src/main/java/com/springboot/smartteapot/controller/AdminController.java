package com.springboot.smartteapot.controller;

import com.springboot.smartteapot.bean.Admin;
import com.springboot.smartteapot.bean.dto.AdminCondition;
import com.springboot.smartteapot.bean.dto.AdminInfo;
import com.springboot.smartteapot.bean.vo.UserUpdate;
import com.springboot.smartteapot.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	/**
	 * 获取当前登录的管理员信息
	 * @return
	 */
	@GetMapping("/me")
	public AdminInfo me(@AuthenticationPrincipal UserDetails user) {
		AdminInfo info = new AdminInfo();
		info.setUsername(user.getUsername());
		Admin admin = adminService.findByUsername(user.getUsername());
		info.setEmail(admin.getEmail());
		info.setPhone(admin.getPhone());
		info.setId(admin.getId());
		info.setRoleId(admin.getRoles().iterator().next().getRole().getId());
		info.setRoleName(admin.getRoles().iterator().next().getRole().getName());
		return info;
	}

	/**
	 * 创建管理员
	 * @param adminInfo
	 * @return
	 */
	@PostMapping
	public AdminInfo create(@RequestBody AdminInfo adminInfo) {
		if(adminService.existsByUsername(adminInfo.getUsername()))
			return new AdminInfo();
		else
			return adminService.create(adminInfo);
	}
	
	/**
	 * 修改管理员信息
	 * @param adminInfo
	 * @return
	 */
	@PutMapping("/{id}")
	public AdminInfo update(@RequestBody AdminInfo adminInfo) {
		return adminService.update(adminInfo);
	}

	@PostMapping("/password")
	public Map<String,Object> updatePassword(@RequestBody UserUpdate userUpdate){
		if(userUpdate.getOldPassword().length()<6 ||
				userUpdate.getOldPassword().length()>12 ||
				userUpdate.getNewPassword().length()<6 ||
				userUpdate.getNewPassword().length()>12 ||
				userUpdate.getConfirmNewPassword().length()<6||
				userUpdate.getConfirmNewPassword().length()>12)
		{
			Map result = new HashMap();
			result.put("result","error");
			return result;
		}

		return adminService.updatePassword(userUpdate);
	}

	@GetMapping("/exist")
	public Map<String,Object> existsAdmin(@RequestParam(value = "id",required = false) Long id,
										  @RequestParam(value = "username",required = false) String username,
										  @RequestParam(value = "email",required = false) String email,
										  @RequestParam(value = "phone",required = false) String phone)
	{
		Map<String,Object> result = new HashMap<>();
		System.out.println(id+" "+username+"--"+email+"--"+phone);
		if(id==null){
			//新建用户
			if(adminService.existsByUsername(username)){
				result.put("username","true");
			}else{
				result.put("username","false");
			}

			if(adminService.existsByEmail(email)){
				result.put("email","true");
			}else{
				result.put("email","false");
			}

			if(adminService.existsByPhone(phone)) {
				result.put("phone","true");
			}else{
				result.put("phone","false");
			}
		}else{
			//修改用户
			AdminInfo adminInfo = adminService.getInfo(id);

			if(username!=null && !username.equals(""))
				if(adminInfo.getUsername().equals(username))
					result.put("username","false");
				else
					if(adminService.existsByUsername(username)){
						result.put("username","true");
					}else{
						result.put("username","false");
					}


			if(email!=null && !email.equals(""))
				if(adminInfo.getEmail().equals(email))
					result.put("email","false");
				else
					if(adminService.existsByEmail(email)){
						result.put("email","true");
					}else{
						result.put("email","false");
					}


			if(phone!=null && !phone.equals(""))
				if(adminInfo.getPhone().equals(phone))
					result.put("phone","false");
				else
					if(adminService.existsByPhone(phone)) {
						result.put("phone","true");
					}else{
						result.put("phone","false");
					}
		}
		return result;
	}

	/**
	 * 删除管理员
	 * @param id
	 */
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		if(adminService.findByUsername("admin").getId().equals(id)) {
			System.out.println("管理员被试图进行删除操作");
		}
		else {
			adminService.delete(id);
		}
	}

	/**
	 * 获取用户详情
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public AdminInfo getInfo(@PathVariable Long id) {
		return adminService.getInfo(id);
	}

	/**
	 * 分页查询管理员
	 * @return
	 */
	@GetMapping
	public Page<AdminInfo> query(AdminCondition condition, Pageable pageable) {
		return adminService.query(condition, pageable);
	}

}
