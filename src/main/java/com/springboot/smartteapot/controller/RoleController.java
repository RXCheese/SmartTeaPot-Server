package com.springboot.smartteapot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.springboot.smartteapot.bean.dto.RoleInfo;
import com.springboot.smartteapot.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**

 */
@RestController
@RequestMapping("/role")
public class RoleController {
	
	@Autowired
	private RoleService roleService;

	/**
	 * 创建角色
	 * @param roleInfo
	 * @return
	 */
	@PostMapping
	public RoleInfo create(@RequestBody RoleInfo roleInfo) {
		if(roleService.existsRoleName(roleInfo.getName()))
			return new RoleInfo();
		else
			return roleService.create(roleInfo);
	}
	
	/**
	 * 修改角色信息
	 * @param roleInfo
	 * @return
	 */
	@PutMapping("/{id}")
	public RoleInfo update(@RequestBody RoleInfo roleInfo) {
		if(roleService.existsRoleName(roleInfo.getName()))
			return new RoleInfo();
		else
			return roleService.update(roleInfo);
	}
	
	/**
	 * 删除角色
	 * @param id
	 */
	@DeleteMapping("/{id}")
	public Map<String,Object> delete(@PathVariable Long id) {
		Map<String,Object> result = new HashMap<>();
		if(roleService.delete(id))
			result.put("result","true");
		else
			result.put("result","false");
		return result;
	}

	/**
	 * 获取角色详情
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public RoleInfo getInfo(@PathVariable Long id) {
		return roleService.getInfo(id);
	}

	/**
	 * 获取所有角色
	 * @return
	 */
	@GetMapping
	public List<RoleInfo> findAll() {
		return roleService.findAll();
	}
	
	/**
	 * 获取角色的所有资源
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}/resource")
	public String[] getRoleResources(@PathVariable Long id){
		return roleService.getRoleResources(id);
	}
	
	/**
	 * 创建用户的资源
	 * @param id
	 * @param ids
	 */
	@PostMapping("/{id}/resource")
	public void createRoleResource(@PathVariable Long id, String ids){
		roleService.setRoleResources(id, ids);
	}

	@GetMapping("/{name}/exist")
	public Map<String,Object> existsRoleName(@PathVariable String name)
	{
		Map<String,Object> result = new HashMap<>();
		if(roleService.existsRoleName(name))
			result.put("result","true");
		else
			result.put("result","false");

		return result;
	}
}
