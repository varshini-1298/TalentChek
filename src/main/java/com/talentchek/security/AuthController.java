package com.talentchek.security;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talentchek.common.EmailService;
import com.talentchek.common.services.CommonServicesService;
import com.talentchek.core.util.ResultResponse;
import com.talentchek.employeeMaster.EmployeeMasterBean;
import com.talentchek.employeeMaster.EmployeeMasterService;
import com.talentchek.setup.roles.RolesMasterBean;
import com.talentchek.setup.roles.RolesMasterService;
import com.talentchek.setup.users.UsersMasterBean;
import com.talentchek.usermanagement.User;
import com.talentchek.usermanagement.UserDetailsImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(tags="Authentication", description="Manages Authentication operation")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	
	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	private EmployeeMasterService employeeMasterService;
	
	@Autowired
	private RolesMasterService rolesMasterService;
	
	@Autowired
	private CommonServicesService commonServicesService;
	
//	private EmailService emailService;
	
	@ApiOperation(value = "Sign In")
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		//loginRequest.setPassword(jwtUtils.decrypt(loginRequest.getPassword()));
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<RolesMasterBean>  roles = rolesMasterService.getLoginRoleList(userDetails.getUsername());
		Boolean isRoleAdmin=false;
		
		for (int i = 0; i < roles.size(); i++) {
			 if(roles.get(i).getRoleId()==1){
				 		isRoleAdmin = true;
			        break;
			      }else{
			    	  isRoleAdmin = false;
			      } 
	    }  
		
		UsersMasterBean usersMasterBean = commonServicesService.getUserDetails(userDetails.getEmail());
		
		
		Integer defaultRoleId =  roles.get(0).getRoleId();
		String defaultRole = roles.get(0).getRoleName();
		
		
		boolean isSuccess = true;
		String message = "";
	//	}
		/*
		 * else { isSuccess = false; message =
		 * "Please Click on Google Captcha Checkbox and then submit again"; }
		 */
		
		byte[] file = null;
		if(usersMasterBean.getImgUrl() !=null && !usersMasterBean.getImgUrl().isEmpty() && usersMasterBean.getImgUrl()!=""){
			Path destination = Paths.get(usersMasterBean.getImgUrl());// retrieve the image by its name
			try {
				file=Files.readAllBytes(destination);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return ResponseEntity.ok(
				new JwtResponse(jwt, userDetails.getUsername(), userDetails.getEmail(),
						roles,isSuccess,message,defaultRoleId,defaultRole,usersMasterBean.getCompanyCode(),
						file,usersMasterBean.getFirstNameLastName(),isRoleAdmin));
	}

	@ApiOperation(value = "Get user info by token")
	@GetMapping("/userbytoken")
	public Optional<User> getUserDetail(@RequestParam("token") String jwtToken) {
		Optional<User> userDetails = null;
		if (jwtToken != null && jwtUtils.validateJwtToken(jwtToken)) {
			String username = jwtUtils.getUserNameFromJwtToken(jwtToken);
			//userDetails = userRepository.findByUsername(username); // by Kannan for JPA
			try {
				userDetails = employeeMasterService.getEmployeeUserName(username);
			} catch (Exception e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			userDetails.get().setPassword(null);

		}
		return userDetails;
	}

	@ApiOperation(value = "Sign Up")
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody EmployeeMasterBean employeeMasterBean) {
//		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
//			
//			return ResponseEntity.badRequest().body(new MessageResponse("Username is already taken!"));
//		}
//
//		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
//			return ResponseEntity.badRequest().body(new MessageResponse("Email is already in use!"));
//		}

		// Create new user's account
		/*
		 * User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
		 * encoder.encode(signUpRequest.getPassword()));
		 */

		/*
		 * Set<String> strRoles = signUpRequest.getRole(); Set<Role> roles = new
		 * HashSet<>();
		 * 
		 * if (strRoles == null) { Role userRole =
		 * roleRepository.findByName(ERole.ROLE_USER) .orElseThrow(() -> new
		 * RuntimeException("Error: Role is not found.")); roles.add(userRole); } else {
		 * strRoles.forEach(role -> { switch (role) { case "admin": Role adminRole =
		 * roleRepository.findByName(ERole.ROLE_ADMIN) .orElseThrow(() -> new
		 * RuntimeException("Error: Role is not found.")); roles.add(adminRole);
		 * 
		 * break; default: Role userRole = roleRepository.findByName(ERole.ROLE_USER)
		 * .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		 * roles.add(userRole); } }); }
		 * 
		 * user.setRoles(roles); userRepository.save(user);
		 */
		
//		employeeMasterBean.setPassword(encoder.encode(employeeMasterBean.getPassword()));
//		EmployeeMasterResultBean insertAppUserMaster = employeeMasterService.addEmployeeMaster(employeeMasterBean); 
//		
		
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	
	
	@ApiOperation(value = "OTP Validation")
	@PostMapping("/validateOtp")
	public HashMap<String,Object> validateOtp(@RequestBody LoginRequest loginRequest) {
		HashMap<String,Object> resultMap = commonServicesService.validateOtp(loginRequest.getUsername(), loginRequest.getOtpValue());
		return resultMap;
	}
	
	@ApiOperation(value = "Resend OTP")
	@PostMapping("/resendOtpvalidate")
	public HashMap<String,Object> resendOtpvalidate(@RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		HashMap<String,Object> result = new HashMap<String,Object>();
		List<RolesMasterBean>  roles = rolesMasterService.getLoginRoleList(userDetails.getUsername());
		UsersMasterBean usersMasterBean = commonServicesService.getUserDetails(userDetails.getEmail());
		String otp = RandomStringUtils.random(6, "0123456789");
		System.out.println("OTP is "+otp);
		Integer count = commonServicesService.getCountValue(userDetails.getUsername());
		if(count <= 5) {
			try {
				//save OTP
				commonServicesService.insertOtp(userDetails.getUsername(), usersMasterBean.getEmailId(), otp);
				EmailService.sendOtpMail(usersMasterBean.getEmailId(),userDetails.getUsername(),otp);
				result.put("success", true);
			}
			catch(Exception e) {
				e.printStackTrace();
				result.put("success", false);
				result.put("message", "Failed to get OTP. Try again!");
			}
		}
		else {
			result.put("success", false);
			result.put("message", "You have Reached your maximum OTP Request. Please try again after 1 hour");
		}
		
		return result;
	}
	
	@ApiOperation(value = "Forgotten Password")
	@PostMapping("/forgotPassword")
	public HashMap<String,Object> forgotPassword(@RequestBody LoginRequest loginRequest) {
		String otpForForgotPassword = RandomStringUtils.random(8, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
		HashMap<String,Object> resultMap = commonServicesService.forgotPassword(loginRequest.getUserNameEmailId(),otpForForgotPassword);
		return resultMap;
	}
	
	
	//add by Gokul for mobile app
	@ApiOperation(value = "Update FCMToken")
	@PostMapping(value="/updateFcmToken")
	public ResultResponse updateFcmToken(@RequestBody FcmtokenBean fcmtokenBean) throws Exception {
			return commonServicesService.updateFcmToken(fcmtokenBean);	
	}
	
}
