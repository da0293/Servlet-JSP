package com.servlet.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/calculator")
public class Calculator extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getMethod().equals("GET")) {
			System.out.println("GET요청이 왔습니다.");
		} else if(req.getMethod().equals("POST")){
			System.out.println("POST요청이 왔습니다.");
		}
		// 부모가 가진 서비스함수는 사용자가 요청이오면 그에 따른 do함수 실행
		// 그것이 get요청이면 doGet() 실행, post는 doPost()실행  
		super.service(req, resp);
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("doGet() 요청이 왔습니다.");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("doPost() 요청이 왔습니다.");
	}
}
