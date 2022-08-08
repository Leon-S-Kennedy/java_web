package com.libowen.servlet03;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

@WebServlet(name="hello",urlPatterns = {"/hello01","/hello02","/hello03"},loadOnStartup = 1,
initParams = {@WebInitParam(name = "username",value = "root"),@WebInitParam(name = "password",value = "libowen")})
public class HelloServlet extends HttpServlet {
    public HelloServlet(){
        System.out.println("无参构造方法执行！！！");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        //获取servlet name
        String servletName = getServletName();
        out.print("servletName:"+servletName+"<br>");
        //获取servlet path
        String servletPath =req.getServletPath();
        out.print("servletPath:"+servletPath+"<br>");
        //获取初始化参数
        Enumeration<String> initParameterNames = getInitParameterNames();
        while (initParameterNames.hasMoreElements()){
            String name=initParameterNames.nextElement();
            String value=getInitParameter(name);
            out.print("name:"+name+" value:"+value+"<br>");
        }
    }
}
