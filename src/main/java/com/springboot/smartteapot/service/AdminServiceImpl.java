package com.springboot.smartteapot.service;

import com.springboot.smartteapot.bean.Admin;
import com.springboot.smartteapot.bean.RoleAdmin;
import com.springboot.smartteapot.bean.dto.AdminCondition;
import com.springboot.smartteapot.bean.dto.AdminInfo;
import com.springboot.smartteapot.bean.vo.UserInfo;
import com.springboot.smartteapot.bean.vo.UserUpdate;
import com.springboot.smartteapot.repository.AdminRepository;
import com.springboot.smartteapot.repository.RoleAdminRepository;
import com.springboot.smartteapot.repository.RoleRepository;
import com.springboot.smartteapot.repository.spec.AdminSpec;
import com.springboot.smartteapot.repository.support.QueryResultConverter;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.cache.NullUserCache;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private RoleAdminRepository roleAdminRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserDetailsService userDetailsService;

	private UserCache userCache = new NullUserCache();

	@Override
	public AdminInfo create(AdminInfo adminInfo) {
		
		Admin admin = new Admin();
		BeanUtils.copyProperties(adminInfo, admin);
		admin.setPassword(passwordEncoder.encode("123456"));
		adminRepository.save(admin);
		adminInfo.setId(admin.getId());
		
		createRoleAdmin(adminInfo, admin);
		
		return adminInfo;
	}

	@Override
	public AdminInfo update(AdminInfo adminInfo) {
		
		Admin admin = adminRepository.findById(adminInfo.getId()).get();
		adminInfo.setUsername(admin.getUsername());
		BeanUtils.copyProperties(adminInfo, admin);
		
		createRoleAdmin(adminInfo, admin);
		
		return adminInfo;
	}

	/**
	 * 创建角色用户关系数据。
	 * @param adminInfo
	 * @param admin
	 */
	private void createRoleAdmin(AdminInfo adminInfo, Admin admin) {
		if(CollectionUtils.isNotEmpty(admin.getRoles())){
			roleAdminRepository.deleteInBatch(admin.getRoles());
		}
		RoleAdmin roleAdmin = new RoleAdmin();
		roleAdmin.setRole(roleRepository.findById(adminInfo.getRoleId()).get());
		roleAdmin.setAdmin(admin);
		roleAdminRepository.save(roleAdmin);
	}


	@Override
	public void delete(Long id) {
		adminRepository.deleteById(id);
	}

	@Override
	public AdminInfo getInfo(Long id) {
		Admin admin = adminRepository.findById(id).get();
		AdminInfo info = new AdminInfo();
		BeanUtils.copyProperties(admin, info);
		return info;
	}

	@Override
	public Page<AdminInfo> query(AdminCondition condition, Pageable pageable) {
		Page<Admin> admins = adminRepository.findAll(new AdminSpec(condition), pageable);

		List<AdminInfo> adminInfoList = new ArrayList<>();
		for (Admin temp :
				admins.getContent()) {
			AdminInfo adminInfo = new AdminInfo();
			BeanUtils.copyProperties(temp,adminInfo);
			adminInfo.setRoleId(temp.getRoles().iterator().next().getRole().getId());
			adminInfo.setRoleName(temp.getRoles().iterator().next().getRole().getName());
			adminInfoList.add(adminInfo);
			//System.out.println(adminInfo);
		}
		Page<AdminInfo> adminInfoPage = new PageImpl<>(adminInfoList, admins.getPageable(), admins.getTotalElements());
		//System.out.println(adminInfoList);
		return QueryResultConverter.convert(adminInfoPage, AdminInfo.class, adminInfoPage.getPageable());
	}

	@Override
	public Admin findByUsername(String username){
		return adminRepository.findByUsername(username);
	}

	@Override
	public Admin findByPhone(String phone){
		return adminRepository.findByPhone(phone);
	}

	@Override
	public Admin findByEmail(String email){
		return adminRepository.findByEmail(email);
	}

	@Override
	public boolean existsByUsername(String username){
		return adminRepository.existsByUsername(username);
	}

	@Override
	public boolean existsByEmail(String email){
		return adminRepository.existsByEmail(email);
	}

	@Override
	public boolean existsByPhone(String phone){
		return adminRepository.existsByPhone(phone);
	}

	@Override
	public void createUser(UserInfo userInfo) {

		Admin admin = new Admin();
		AdminInfo adminInfo = new AdminInfo();
		BeanUtils.copyProperties(userInfo, admin);
		admin.setPassword(passwordEncoder.encode(userInfo.getPassword()));
		adminRepository.save(admin);

		adminInfo.setRoleId(roleRepository.findByName("用户").getId());
		BeanUtils.copyProperties(admin, adminInfo);
		adminInfo.setId(admin.getId());
		createRoleAdmin(adminInfo, admin);

		System.out.println(adminInfo.toString());
		System.out.println(admin.toString());

		//return new SimpleResponse("注册成功");
	}

	@Override
	public Map<String,Object> updatePassword(UserUpdate userUpdate) {


		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		UserDetails currentUserPrincipal = (UserDetails)currentUser.getPrincipal();
		String currentUserName = currentUserPrincipal.getUsername();

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		Admin admin = adminRepository.findByUsername(userUpdate.getUsername());
		String newPwd = passwordEncoder.encode(userUpdate.getNewPassword());
		String oldPwd = userUpdate.getOldPassword();
		Map<String, Object> result = new HashMap<>();

		if(!userUpdate.getUsername().equals(currentUserName))
		{
			result.put("result", "false");
		}

//		System.out.println(userUpdate);
//		System.out.println("登陆用户密码:"+currentUserPwd);
//		System.out.println("数据库用户密码:"+admin.getPassword());
//		System.out.println("未加密旧密码:"+oldPwd);
//		System.out.println("未加密旧密码-match-数据库用户密码 :"+encoder.matches(oldPwd,admin.getPassword()));


		if(encoder.matches(oldPwd,admin.getPassword()))
		{
			//设置新密码后JPA自动更新库中密码
			admin.setPassword(newPwd);

			//修改密码后创建新凭证
			SecurityContextHolder.getContext().setAuthentication(createNewAuthentication(currentUser, newPwd));
			//移除原本的凭证
			userCache.removeUserFromCache(currentUserName);

			result.put("result","true");
		}else{
			result.put("result", "false");
		}
		return result;

	}

	/**
	 * 创建新的当前登陆用户凭证
	 * @param currentAuth
	 * @param newPassword
	 * @return
	 */
	public Authentication createNewAuthentication(Authentication currentAuth, String newPassword)
	{
		UserDetails user = userDetailsService.loadUserByUsername(currentAuth.getName());

		UsernamePasswordAuthenticationToken newAuthentication =
				new UsernamePasswordAuthenticationToken(user, newPassword, user.getAuthorities());
		newAuthentication.setDetails(currentAuth.getDetails());

		return newAuthentication;
	}

}
