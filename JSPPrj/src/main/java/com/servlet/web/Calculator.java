package com.servlet.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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
    	Cookie[] cookies = req.getCookies();
    	
    	String exp = "0"; 
    	// 쿠키배열이 null이 아닌 경우만
    	if(cookies!=null) {
    		for(Cookie c :  cookies) {
    			if(c.getName().equals("exp")) {
    				exp = c.getValue();
    				break; 
    			}
    		}
    	}
    	
    	resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("        <title>Calc</title>");
        out.println("        <style>");
        out.println("            input{");
        out.println("                width: 50px;");
        out.println("                height: 50px;");
        out.println("            }");
        out.println("            .output{");
        out.println("                height: 50px;");
        out.println("                font-size: 24px;");
        out.println("                font-weight: bold;");
        out.println("                background: gray;");
        out.println("                text-align: right;");
        out.println("                padding-right: 5px;");
        out.println("            }");
        out.println("        </style>");
        out.println("     </head>");
        out.println("     <body>");
        out.println("      <form method=\"post\">");
        out.println("        <table>");
        out.println("            <tr>");
        out.printf("               <td class=\"output\" colspan=\"4\">%s</td>\n", exp);
        out.println("            </tr>");
        out.println("            <tr>");
        out.println("                <td><input type=\"submit\" name=\"operator\" value=\"CE\"/></td>");
        out.println("                <td><input type=\"submit\" name=\"operator\" value=\"C\"/></td>");
        out.println("                <td><input type=\"submit\" name=\"operator\" value=\"≪\"/></td>");
        out.println("                <td><input type=\"submit\" name=\"operator\" value=\"/\"/></td>");
        out.println("            </tr>");
        out.println("            <tr>");
        out.println("                <td><input type=\"submit\" name=\"value\" value=\"7\"/></td>");
        out.println("                <td><input type=\"submit\" name=\"value\" value=\"8\"/></td>");
        out.println("                <td><input type=\"submit\" name=\"value\" value=\"9\"/></td>");
        out.println("                <td><input type=\"submit\" name=\"operator\" value=\"*\"/></td>");
        out.println("            </tr>");
        out.println("            <tr>");
        out.println("                <td><input type=\"submit\" name=\"value\" value=\"4\"/></td>");
        out.println("                <td><input type=\"submit\" name=\"value\" value=\"5\"/></td>");
        out.println("                <td><input type=\"submit\" name=\"value\" value=\"6\"/></td>");
        out.println("                <td><input type=\"submit\" name=\"operator\" value=\"-\"/></td>");
        out.println("            </tr>");
        out.println("            <tr>");
        out.println("                <td><input type=\"submit\" name=\"value\" value=\"1\"/></td>");
        out.println("                <td><input type=\"submit\" name=\"value\" value=\"2\"/></td>");
        out.println("                <td><input type=\"submit\" name=\"value\" value=\"3\"/></td>");
        out.println("               <td><input type=\"submit\" name=\"operator\" value=\"+\"/></td>");
        out.println("            </tr>");
        out.println("            <tr>");
        out.println("                <td></td>");
        out.println("                <td><input type=\"submit\" name=\"value\" value=\"0\"/></td>");
        out.println("                <td><input type=\"submit\" name=\"dot\" value=\".\"/></td>");
        out.println("                <td><input type=\"submit\" name=\"operator\" value=\"=\"/></td>");
        out.println("            </tr>");
        out.println("        </table>");
        out.println("      </form>");
        out.println("    </body>");
        out.println("</html>");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        // /calculator외에는 쿠키 전달 불가
        expCookie.setPath("/calculator");
        resp.addCookie(expCookie);
        // "calcpage"로 리다이렉트
        resp.sendRedirect("calculator");
	}
}
