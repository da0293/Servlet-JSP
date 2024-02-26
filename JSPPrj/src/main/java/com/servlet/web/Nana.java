package com.servlet.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/hi")
public class Nana extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 사용자 본연의 코딩인코딩방식 설정(파일을 UTF-8로 보냄)
		resp.setCharacterEncoding("UTF-8");
		// 사용자가 어떻게 읽어야할지 알려줌
		// 브라우저에게 charset=UTF-8로 해석하라고 알려줌 
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		for( int i=0; i<10; i++ ) {
			out.println((i+1)+": 안녕 Servlet <br>");
		}
	}
}
