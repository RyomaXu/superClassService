<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/WEB-INF/static/js/jquery-1.11.1.js"></script> --%>
<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/WEB-INF/static/js/jquery-1.12.3.min.js"></script> --%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.11.3.min.js"></script>
<script type="text/javascript">
(function(){$.post("https:\//a1.easemob.com\/yanguixu\/superclassapp\/messages",
		   function(data){
	         debugger;
	         var nydata=data;
         }, "json");
})() 
</script>
</head>


<body>
<h4>成功、、</h4>
</body>
</html>