<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>
<%	
	//设置返回码200，避免浏览器自带的错误页面
	response.setStatus(200);
	//记录日志
	Logger logger = LoggerFactory.getLogger("500.jsp");
	logger.error(exception.getMessage(), exception);
	
	//String jsonString = "{\"success\":false,\"msg\":\"" + exception.getMessage()
	//		+ "\"}";
	String jsonString = "{\"success\":false,\"msg\":\"操作超时,请稍后再试!\"}";
//	out.print(jsonString);
%>
