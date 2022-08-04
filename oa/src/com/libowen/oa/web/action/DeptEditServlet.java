package com.libowen.oa.web.action;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class DeptEditServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contextPath = req.getContextPath();

        req.setCharacterEncoding("UTF-8");
        String dno = req.getParameter("dno");
        String dname = req.getParameter("dname");
        String loc = req.getParameter("loc");

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print("<!DOCTYPE html>");
        out.print("<html lang='zh'>");
        out.print("    <head>");
        out.print("        <meta charset='UTF-8'>");
        out.print("        <title>修改部门</title>");
        out.print("    </head>");
        out.print("    <body>");
        out.print("        <h1>修改部门</h1>");
        out.print("        <hr>");
        out.print("        <form action='/oa/dept/update' method='post'>");
        out.print("                部门编号: <input type='text' name='dno' value="+dno+" readonly><br>");
        out.print("                部门名称: <input type='text' name='dname' value="+dname+"><br>");
        out.print("                部门位置: <input type='text' name='loc' value="+loc+"><br>");
        out.print("            <input type='submit' value='保存'><br>");
        out.print("        </form>");
        out.print("    </body>");
        out.print("</html>");
    }
}
