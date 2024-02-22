package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// user 실행되는지 확인
		System.out.println("UserController");

		// user에서 업무구분
		String action = request.getParameter("action");
		System.out.println(action);

		if ("joinform".equals(action)) {
			System.out.println("user>joinform");

			// 회원가입폼 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");

		} else if ("join".equals(action)) {
			System.out.println("user>join");

			// 파라미터에서 데이터 꺼내기
			String id = request.getParameter("id");
			String password = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");

			// 데이터를 vo 묶어준다
			UserVo userVo = new UserVo(id, password, name, gender);
			System.out.println(userVo);

			// dao의 메소드로 회원가입
			UserDao userDao = new UserDao();
			userDao.insertUser(userVo);

			// joinOk.jsp 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");
			
			//login
		} else if ("loginForm".equals(action)) {
			System.out.println("user>loginForm");

			// loginForm.jsp 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");

		} else if ("login".equals(action)) {
			System.out.println("user>login");

			String id = request.getParameter("id");
			String password = request.getParameter("pw");

			UserVo userVo = new UserVo(id, password);

			UserDao userDao = new UserDao();
			UserVo authUser = userDao.selectUserByIdPw(userVo);// id pw
			// no name

			if (authUser != null) { // null 이 아니면 => 로그인 성공

				HttpSession session = request.getSession();
				session.setAttribute("authUser", authUser);

				WebUtil.redirect(request, response, "/mysite3/main");

			} else { // 로그인 실패

				WebUtil.redirect(request, response, "/mysite3/user?action=loginForm");
			}
			
		} else if("logout".equals(action)) {
			System.out.println("user>logout");
			
			HttpSession session = request.getSession();
			session.invalidate();
			
			WebUtil.redirect(request, response, "/mysite3/main");
			
		//수정
		} else if ("modifyForm".equals(action)) {
			System.out.println("user>modifyForm");
				
			// loginForm.jsp 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");

		} else if ("modify".equals(action)) {
						System.out.println("user>modify");
				
		}

	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
