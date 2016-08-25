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
(function(){$.post("regist.do",
		   function(data){
	         debugger;
	         var nydata=data;
         }, "json");
})() 
// (function(){$.post("ajaxtest.do", 
// 		   function(data){
// 		     alert(data.user.name); // John
// 		     console.log(data.user.password); //  2pm
// 		   }, "json");

// })()
// $.ajax({  
//     //采用post方式传递数据
//     type: "POST",  
//     //struts的action请求
//     url: "/regist.do",
//     //传递到后台的数据  
//     data:"machinecode="+$("#machinecode").val(),  
//     //采用json的方式返回数据
//     dataType:"json",  
//     //后台返回数据后的方法，返回数据为result
//     success: function(result){           
// //通过eval方法后台组织的json数据可以直接转化为对象
//         var ary = eval(result);  
          
//         //获取数据时直接在引号内填写字段名称即可
//         $("#machinecode").val(ary['machinecode']);  
//         $("#orderid").val(ary['orderid']);  
//         $("#machine").val(ary['machine']);  
//         $("#num").val(ary['num']);  
          
//         if(ary['machinecode'] == null || ary['machinecode'] == ""){  
//             $("#isflowid").html("此整机条码不存在！");  
//         }else{  
//             $("#isflowid").empty();  
//         }  
//     }  
// }); 
</script>
</head>
<body>
<h4>成功、、</h4>
</body>
</html>