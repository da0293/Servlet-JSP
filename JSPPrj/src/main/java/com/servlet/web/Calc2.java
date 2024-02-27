package com.servlet.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/calc2")
public class Calc2 extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ServletContext : Application저장소
		ServletContext application = req.getServletContext();
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		String v_=req.getParameter("v"); 
		String op = req.getParameter("operator");
		
		// 기본값(값을 입력하지 않은 경우)
		int v=0; 
		int result=0; 
		if(!v_.equals("")) v = Integer.parseInt(v_);
		
		// op에 따라 값을 계산하거나 저장한다.
		// 계산
		if(op.equals("=")) { // 계산
			// x는 앞에서 저장했던값(Application저장소에 담겨진 내용)
			int x=(Integer)application.getAttribute("value"); 
			System.out.println(x);
			// y는 지금 사용자가 전달한 값
			int y=v; 
			String operator = (String)application.getAttribute("op");
			if(operator.equals("+")) {
				result=x+y; 
			}else {
				result=x-y; 
			}
			resp.getWriter().println("result is " + result);
		}
		// 저장
		else { // 기본연산을 저장
			// Application에 v와 op를 저장 (Map Collection과 유사)
			application.setAttribute("value", v); 
			application.setAttribute("op", op); 
		}
	}
}
