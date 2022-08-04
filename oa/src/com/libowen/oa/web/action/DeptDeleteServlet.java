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

public class DeptDeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
}
