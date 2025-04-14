package com.bug_tracking_system.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.bug_tracking_system.dao.CommentDAO;
import com.bug_tracking_system.model.Comment;
import com.bug_tracking_system.model.User;

@WebServlet("/comment")
public class CommentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        
        if (currentUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        int bugId = Integer.parseInt(request.getParameter("bugId"));
        String commentText = request.getParameter("commentText");
        
        Comment comment = new Comment();
        comment.setBugId(bugId);
        comment.setUserId(currentUser.getUserId());
        comment.setComment(commentText);
        
        CommentDAO commentDAO = new CommentDAO();
        commentDAO.addComment(comment);
        
        response.sendRedirect("bug?action=view&id=" + bugId);
    }
}