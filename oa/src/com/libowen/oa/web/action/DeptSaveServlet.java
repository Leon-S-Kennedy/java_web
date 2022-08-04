package com.libowen.oa.web.action;

import com.libowen.oa.utils.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeptSaveServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
}
