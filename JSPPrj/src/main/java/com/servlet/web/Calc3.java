package com.servlet.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.RepaintManager;

@WebServlet("/calc3")
public class Calc3 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 클라이언트로부터 전송된 쿠키를 읽어옴
        Cookie[] cookies = req.getCookies();
        
        // 사용자가 입력한 값(value), 연산자(operator), 소수점(dot)을 가져옴
        String value = req.getParameter("value");
        String operator = req.getParameter("operator");
        String dot = req.getParameter("dot");

        // 이전에 저장된 계산식을 읽어옴
        String exp = "";
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("exp")) {
                    exp = c.getValue();
                    break;
                }
            }
        }

        // '=' 연산자가 들어온 경우, 계산식(exp)을 처리
        if (operator != null && operator.equals("=")) {
        	// Graal.js 엔진을 사용하여 계산식 처리
        	ScriptEngine engine = new ScriptEngineManager().getEngineByName("graal.js"); 
        	try {
        		// 계산식을 평가하여 결과를 문자열로 변환
        		exp = String.valueOf(engine.eval(exp));
			} catch (ScriptException e) {
				// 스크립트 평가 중 오류가 발생한 경우
				e.printStackTrace();
			} 
        } else if(operator !=null && operator.equals("C")) {
        	// 
        	exp=""; 
        }else {
            // 입력값(value), 연산자(operator), 소수점(dot)을 기존의 계산식에 추가
            exp += (value == null) ? "" : value;
            exp += (operator == null) ? "" : operator;
            exp += (dot == null) ? "" : dot;
        }
        

        // 계산식을 쿠키로 저장
        Cookie expCookie = new Cookie("exp", exp);
        // C 누를 시 초기화(쿠키 삭제)
        if(operator!=null && operator.equals("C")) {
            expCookie.setMaxAge(0);
        }
        // 사이트 전역범위에서 쿠키 전달받을 수 있게 setPath()설정 
        expCookie.setPath("/");
        resp.addCookie(expCookie);
        // "calcpage"로 리다이렉트
        resp.sendRedirect("calcpage");
    }
}

