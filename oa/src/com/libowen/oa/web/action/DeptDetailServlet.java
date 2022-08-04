package com.libowen.oa.web.action;

import com.libowen.oa.utils.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeptDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print("<!DOCTYPE html>");
        out.print("<html lang='zh'>");
        out.print("    <head>");
        out.print("        <meta charset='UTF-8'>");
        out.print("        <title>详情页面</title>");
        out.print("    </head>");
        out.print("    <body>");
        out.print("        <h1>详情页面</h1>");
        out.print("        <hr>");

        String dno = req.getParameter("dno");

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from dept where dno= ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, dno);
            rs = ps.executeQuery();
            if (rs.next()) {
                String dname = rs.getString("dname");
                String loc = rs.getString("loc");
                out.print("                部门名称: "+dname+"<br>");
                out.print("                部门编号: "+dno+"<br>");
                out.print("                部门位置: "+loc+"<br>");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        out.print("        <input type='button' value='后退' onclick='window.history.back()'/>");
        out.print("    </body>");
        out.print("</html>");
    }
}
