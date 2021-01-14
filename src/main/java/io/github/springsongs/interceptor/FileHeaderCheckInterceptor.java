package io.github.springsongs.interceptor;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.exception.SpringSongsException;
import io.github.springsongs.util.FileHeaderUtil;

public class FileHeaderCheckInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 判断是否为文件上传请求
        if (request != null && request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> files = multipartRequest.getFileMap();
            Iterator<String> iterator = files.keySet().iterator();
            while (iterator.hasNext()) {
                String formKey = (String) iterator.next();
                MultipartFile multipartFile = multipartRequest.getFile(formKey);
                //String filename = multipartFile.getOriginalFilename();
                byte[] file = multipartFile.getBytes() ;
                int HEADER_LENGTH = 8 ;
                if(file.length>HEADER_LENGTH){
                	//转成16进制
                	StringBuilder sb = new StringBuilder();
                	for(int i=0;i<HEADER_LENGTH;i++){
                		int v = file[i] & 0xFF;     
                        String hv = Integer.toHexString(v);     
                        if (hv.length() < 2) {     
                        	sb.append(0);     
                        }     
                        sb.append(hv);
                	}
                	boolean isFound = false ;
                	String fileHead = sb.toString().toUpperCase() ;
                	List<String> headerList = FileHeaderUtil.getInstance().getHeaderList() ;
                	for(String header : headerList){
                		if(fileHead.startsWith(header)){
                			isFound = true ;
                			break ;
                		}
                	}
                	if(!isFound){
                			throw new SpringSongsException(ResultCode.FILE_CANT_UPLOAD);
                	}
                }
            }
        }
        return true;
	}
 
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

 
	}
 
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

 
	}

}
