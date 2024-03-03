package com.servlet.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/calc2")
public class Calc2 extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Cookie[] cookies = req.getCookies(); 
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		String v_=req.getParameter("v"); 
		String op = req.getParameter("operator");
		
		int v=0; 
		int result=0; 
		if(!v_.equals("")) v = Integer.parseInt(v_);

		if(op.equals("=")) { // 계산
			int x=0; 
			for(Cookie c :  cookies) {
				if(c.getName().equals("value")) {
					x = Integer.parseInt(c.getValue());
					break; 
				}
			}
			
			// y는 지금 사용자가 전달한 값
			int y=v; 
			//String operator = (String)application.getAttribute("op");
			//String operator = (String)session.getAttribute("op");
			String operator = ""; 
			for(Cookie c :  cookies) {
				if(c.getName().equals("op")) {
					operator = c.getValue();
					break; 
				}
			}
			if(operator.equals("+")) {
				result=x+y; 
			}else {
				result=x-y; 
			}
			resp.getWriter().println("result is " + result);
		}
		// 저장
		else { // 기본연산을 저장
			Cookie valueCookie = new Cookie("value", String.valueOf(v)); 
			Cookie opCookie = new Cookie("op", op); 
			// 쿠키가 어느 경우에 사용자에게 전달되어야하는지 경로 설정
			valueCookie.setPath("/calc2");
			opCookie.setPath("/calc2"); 
			// 앞으로 1분 후에 valueCookie 만료 
			valueCookie.setMaxAge(60*60); 
			// 쿠키를 클라이언트에게 전달 
			resp.addCookie(valueCookie); 
			resp.addCookie(opCookie); 
			
			resp.sendRedirect("calc2.html"); 
		}
	}
}
