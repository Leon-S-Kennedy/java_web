package com.libowen.oa.web.action;

import com.libowen.oa.utils.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet({"/dept/list","/dept/detail","/dept/delete","/dept/save","/dept/update","/dept/edit"})
//@WebServlet("/dept/*")
public class DeptServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        if("/dept/list".equals(servletPath)){
            doList(req,resp);
        }else if("/dept/detail".equals(servletPath)){
            doDetail(req,resp);
        }else if("/dept/delete".equals(servletPath)){
            doDel(req,resp);
        }else if("/dept/save".equals(servletPath)){
            doSave(req,resp);
        }else if("/dept/edit".equals(servletPath)){
            doEdit(req,resp);
        }else if("/dept/update".equals(servletPath)){
            doUpdate(req,resp);
        }
    }

    private void doList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
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

    private void doDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
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

    private void doDel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        String dno = req.getParameter("dno");

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count=0;
        try {
            conn = DBUtil.getConnection();

            //开启事务
            conn.setAutoCommit(false);
            String sql = "delete from dept where dno= ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, dno);
            count = ps.executeUpdate();

            //提交事务；
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            if(conn!=null){
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        if(count==1){
            //删除成功
            //req.getRequestDispatcher("/dept/list").forward(req,resp);
            resp.sendRedirect(req.getContextPath()+"/dept/list");
        }else {
            //删除失败
            //req.getRequestDispatcher("/error.html").forward(req,resp);
            resp.sendRedirect(req.getContextPath()+"/error.html");
        }
    }

    private void doSave(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //获取信息
        req.setCharacterEncoding("UTF-8");

        String dno = req.getParameter("dno");
        String dname = req.getParameter("dname");
        String loc = req.getParameter("loc");

        //连接数据库执行insert语句
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count=0;
        try {
            conn = DBUtil.getConnection();

            //开启事务
            conn.setAutoCommit(false);
            String sql = "insert into dept(dno,dname,loc) value(?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, dno);
            ps.setString(2, dname);
            ps.setString(3, loc);
            count = ps.executeUpdate();
            //提交事务；
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            if(conn!=null){
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        if(count==1){
            //保存成功
            //req.getRequestDispatcher("/dept/list").forward(req,resp);
            resp.sendRedirect(req.getContextPath()+"/dept/list");
        }else {
            //保存失败
            //req.getRequestDispatcher("/error.html").forward(req,resp);
            resp.sendRedirect(req.getContextPath()+"/error.html");
        }
    }

    private void doEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
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

    private void doUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.setCharacterEncoding("UTF-8");

        String dno = req.getParameter("dno");
        String dname = req.getParameter("dname");
        String loc = req.getParameter("loc");

        //连接数据库执行uppdate语句
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count=0;
        try {
            conn = DBUtil.getConnection();

            //开启事务
            conn.setAutoCommit(false);
            String sql = "update dept set dname=? , loc=? where dno=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, dname);
            ps.setString(2, loc);
            ps.setString(3, dno);
            count = ps.executeUpdate();
            //提交事务；
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            if(conn!=null){
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        if(count==1){
            //更新成功
            //req.getRequestDispatcher("/dept/list").forward(req,resp);
            resp.sendRedirect(req.getContextPath()+"/dept/list");
        }else {
            //更新失败
            //req.getRequestDispatcher("/error.html").forward(req,resp);
            resp.sendRedirect(req.getContextPath()+"/error.html");
        }
    }
}
