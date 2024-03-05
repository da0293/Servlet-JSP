package com.servlet.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/spag")
public class Spag extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int num = 0; 
		String num_=  request.getParameter("n"); 
		if(num_ != null && !num_.equals(""))
			num = Integer.parseInt(num_); 
		String result; 
		if(num%2!=0)
			result="홀수"; 
		else
			result="짝수"; 
		
		request.setAttribute("result", result);
		// redirect - 현재 작업과 상관없는 새로윤 요청을 함
		// forward - 현재 작업한 내용을 이어가게 해줌
		RequestDispatcher dispatcher = request.getRequestDispatcher("spag.jsp");
		// 현재 작업한 내용을 인자로 담아 그 내용이 dispatcher경로인 spag.jsp로 이어지게 함
		// forward관계에 있는 Spag.java와 spag.jsp의 저장소는 request가 사용된다.
		dispatcher.forward(request, response); 
	}
}
