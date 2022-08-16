package com.talentchek.setup.users;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.talentchek.common.FileUploadUtill;
import com.talentchek.core.util.ResultResponse;
import com.talentchek.filesupload.FileUploadBean;
import com.talentchek.filesupload.FileUploadResultBean;

import org.springframework.http.MediaType;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;

@RestController
@RequestMapping("/api/auth/app/userMaster")
public class UsersMasterController {
	@Autowired
	UsersMasterService usersMasterService;
	
	@Value("${file.upload.tcProfileUpload}")
	private String tcProfileUpload;
	
	@Value("${file.upload.tcProfileUploadServerPath}")
	private String tcProfileUploadServerPath;
	
	//Added by gokul for Malicious file upload
		private static final List<String> contentTypes = Arrays.asList("application/pdf");
		private static final List<Character> ILLEGAL_CHARACTERS = Arrays.asList('/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':' );
		private static final List<String> contentImageTypes = Arrays.asList("image/png", "image/jpeg", "image/jpg");

		
	@RequestMapping(value="/save")
	public UsersMasterResultBean save(@RequestBody UsersMasterBean bean) {
		UsersMasterResultBean objbean = new UsersMasterResultBean();
		try {
			objbean = usersMasterService.save(bean);
		}catch(Exception e){
			e.printStackTrace();	
		}
		return objbean;
		
	}
	
	
	@RequestMapping(value = "/getList")
   	public UsersMasterResultBean getList() throws Exception {
		UsersMasterResultBean objResultBean = new UsersMasterResultBean();
		objResultBean.setUsersMasterDetails(usersMasterService.getList());
		objResultBean.setSuccess(true);
   		return objResultBean;
   	}
	
	@RequestMapping(value = "/edit")
	public UsersMasterResultBean edit(@RequestParam("usersId") String usersId ) throws Exception {
		UsersMasterResultBean objResultBean = new UsersMasterResultBean();
		try {
			objResultBean = usersMasterService.edit(usersId);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return objResultBean;
	}
	
	@RequestMapping(value="/update")
	public UsersMasterResultBean update(@RequestBody UsersMasterBean bean) {
		UsersMasterResultBean objResultBean = new UsersMasterResultBean();
		try {
			objResultBean = usersMasterService.update(bean);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return objResultBean;
	}
	
	@RequestMapping(value="/delete")
	public UsersMasterResultBean delete(@RequestParam("deleteUser") Integer deleteUser) {
		UsersMasterResultBean objResultBean = new UsersMasterResultBean();
		try {
			objResultBean = usersMasterService.delete(deleteUser);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return objResultBean;
	}
	
	@RequestMapping(value = "/getRoleList")
   	public UsersMasterResultBean getRoleList() throws Exception {
   		return usersMasterService.getRoleList();
   	}
	
	// File Upload
	
//		@RequestMapping(value = "/uploadFile")
//		public @ResponseBody UsersMasterResultBean uploadFile(@RequestParam MultipartFile file,@RequestParam("fileName") String fileName) {
//			UsersMasterResultBean resultBean = new UsersMasterResultBean();
//			String filePath = "";
//			if (!fileName.isEmpty()) {
//				try {	
//					filePath = FileUploadUtill.uploadFileHandlerWithOutRandom(file, drugCustomerUpload, drugCustomerUploadServerPath, fileName);
//					resultBean.setFileName(fileName);
//					resultBean.setFilePath(filePath);
//					resultBean.setSuccess(true);
//				} catch (Exception e) {
//					resultBean.setSuccess(false);
//					resultBean.setMessage(e.getMessage());
//				}
//			} else {
//				resultBean.setSuccess(false);
//				resultBean
//						.setMessage("You failed to upload " + fileName + " because the file was empty.");
//			}
//			return resultBean;
//		}
		
		@RequestMapping(value = "/uploadFile")
		public @ResponseBody ImageResultBean uploadFile(@RequestParam MultipartFile file,@RequestParam("fileName") String fileName) throws Exception {
			ImageResultBean resultBean = new ImageResultBean();
		//Added by gokul for Malicious file upload 
		
				if (null != file) {
						Tika tika = new Tika();
						File files=convert(file);
						String fileContentType = tika.detect(files);
					if(!ILLEGAL_CHARACTERS.contains(file.getOriginalFilename()) && file.getOriginalFilename().length()<=150) {
						String extension = FilenameUtils.getExtension(file.getOriginalFilename());
						if(contentImageTypes.contains(fileContentType) && extension.equalsIgnoreCase("jpg") || contentImageTypes.contains(fileContentType) && extension.equalsIgnoreCase("jpeg") || contentImageTypes.contains(fileContentType) && extension.equalsIgnoreCase("png")) {
					    	if(file.getSize() <= 100000) {
					    		System.out.print("File Uploaded- "+file.getOriginalFilename());
					    	}else {
						    	throw new Exception("File too Large- "+file.getOriginalFilename());
					    	}
					    } else {
					    	throw new Exception("Invalid File Type- "+file.getOriginalFilename());
					    }
					} else {
				    	throw new Exception("Invalid File Name- "+file.getOriginalFilename());
				    }
					
				}
				if (!fileName.isEmpty()) {
				String imagePath = FileUploadUtill.uploadFileHandlerWithOutRandom(file, tcProfileUpload, tcProfileUploadServerPath, fileName);
				Path destination = Paths.get(imagePath);// retrieve the image by its name
				resultBean.setFile(Files.readAllBytes(destination));
				resultBean.setSuccess(true);
				resultBean.setFilePath(imagePath);
			    
				}
				
			 return resultBean;				
}
		
		
		public File convert(MultipartFile file) throws IOException {
			File convFile = new File(file.getOriginalFilename());
			convFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(convFile);
			fos.write(file.getBytes());
			fos.close();
			return convFile;
		}
		
		private void saveFile(String fileUploadDir, MultipartFile multipartFile) throws IOException {
			Path uploadPath = Paths.get(fileUploadDir);
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			try (InputStream inputStream = multipartFile.getInputStream()) {
				Files.copy(inputStream, uploadPath, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException ioe) {
				ioe.printStackTrace();
				throw new IOException("Could not save image file: " + fileUploadDir, ioe);
			}
		}
		
		
		
		
		@RequestMapping(value = "/oldPasswordValidation")
	   	public UsersMasterResultBean oldPasswordValidation(@RequestParam("validatePassword") String validatePassword,@RequestParam("userId") String userId) throws Exception {
	   		return usersMasterService.oldPasswordValidation(validatePassword,userId);
	   	}
		
		@RequestMapping(value = "/updatePassword")
	   	public UsersMasterResultBean updatePassword(@RequestBody UsersMasterBean bean)throws Exception {
			UsersMasterResultBean objResultBean = new UsersMasterResultBean();
			try {
				objResultBean = usersMasterService.updatePassword(bean);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			return objResultBean;
	   	}
		
		@RequestMapping(value = "/resetPasswordCheck")
	   	public boolean resetPasswordCheck(@RequestParam("resetPasswordPopUp") String userId) throws Exception {
	   		return usersMasterService.resetPasswordCheck(userId);
	   	}
		
//		@RequestMapping(value = "/get-image")
//		public ResultResponse getImage() throws IOException {
//					ResultResponse resultResponse=new ResultResponse();
//				    String imagePath = "/root/drug_upload/omega_logo.jpg";
//					Path destination = Paths.get(imagePath);// retrieve the image by its name
//					resultResponse.setFile(Files.readAllBytes(destination));
//					resultResponse.setText("Sucess");
//				    return resultResponse;
//				    
//				}
	

}
