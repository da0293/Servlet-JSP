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
		// ServletContext : Application저장소
		ServletContext application = req.getServletContext();
		// Session객체
		HttpSession session = req.getSession();
		// 쿠키 배열
		Cookie[] cookies = req.getCookies(); 
		
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
			// int x=(Integer)application.getAttribute("value"); 
			// int x=(Integer)session.getAttribute("value"); 
			int x=0; 
			// 내가 심어놨던  value쿠키 키값 찾기
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
			// Application에 v와 op를 저장 (Map Collection과 유사)
			//application.setAttribute("value", v); 
			//application.setAttribute("op", op); 
			
			// session.setAttribute("value", v); 
			// session.setAttribute("op", op);
			
			// 1. 
			// 쿠키값은 문자열로만 보내야 한다. 
			Cookie valueCookie = new Cookie("value", String.valueOf(v)); 
			Cookie opCookie = new Cookie("op", op); 
			// 쿠키가 어느 경우에 사용자에게 전달되어야하는지 경로 설정
			valueCookie.setPath("/add"); 
			opCookie.setPath("/add"); 
			resp.addCookie(valueCookie); 
			resp.addCookie(opCookie); 
		}
	}
}
