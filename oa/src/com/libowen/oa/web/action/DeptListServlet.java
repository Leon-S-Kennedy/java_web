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

public class DeptListServlet extends HttpServlet {
    //此处为无奈之举，因为插入成功之后转发的是dopost请求，我们如果不写的话会405
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取应用的根路径
        String contextPath = req.getContextPath();

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        out.print("<!DOCTYPE html>");
        out.print("<html lang='zh'>");
        out.print("    <head>");
        out.print("        <meta charset='UTF-8'>");
        out.print("        <title>部门列表页面</title>");

        out.print("<script type='text/javascript'>");
        out.print("        function del(dno){");
        out.print("             if(window.confirm('删除不可恢复哦！确认删除么？')){");
        out.print("                 document.location.href='"+contextPath+"/dept/delete?dno='+dno");
        out.print("    }");
        out.print(" }");
        out.print("</script>");

        out.print("    </head>");
        out.print("    <body>");
        out.print("        <h1 align='center'>部门列表</h1>");
        out.print("        <hr>");
        out.print("        <table border='1px' align='center' width='50%'>");
        out.print("            <tr>");
        out.print("                <th>序号</th>");
        out.print("                <th>部门编号</th>");
        out.print("                <th>部门名称</th>");
        out.print("                <th>操作</th>");
        out.print("            </tr>");

        //连接数据库,查询所有结果
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from dept";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            int i=0;
            while (rs.next()) {
                String dno = rs.getString("dno");
                String dname = rs.getString("dname");
                String loc = rs.getString("loc");

                out.print("            <tr>");
                out.print("                <td align='center'>"+(++i)+"</td>");
                out.print("                <td align='center'>"+dno+"</td>");
                out.print("                <td align='center'>"+dname+"</td>");
                out.print("                <td align='center'>");
                out.print("                    <a href='javascript:void(0)' onclick='del("+dno+")'>删除</a>");
                out.print("                    <a href='"+contextPath+"/dept/edit?dno="+dno+"&dname="+dname+"&loc="+loc+"'>修改</a>");
                out.print("                    <a href='"+contextPath+"/dept/detail?dno="+dno+"'>详情</a>");
                out.print("                </td>");
                out.print("            </tr>");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        out.print("        </table>");
        out.print("        <hr>");
        out.print("        <a href='"+contextPath+"/add.html'>新增部门</a>");
        out.print("    </body>");
        out.print("</html>");
    }
}
