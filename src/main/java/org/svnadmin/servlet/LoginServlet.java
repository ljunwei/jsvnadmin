package org.svnadmin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.svnadmin.Constants;
import org.svnadmin.service.UsrService;
import org.svnadmin.util.SpringUtils;

/**
 * 登录
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 1.0
 * 
 */
public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2037344827672377791L;

	/**
	 * 日志
	 */
	private final Logger LOG = Logger.getLogger(this.getClass());

	/**
	 * 用户服务层
	 */
	protected UsrService usrService = SpringUtils.getBean(UsrService.BEAN_NAME);

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		if ("logout".equals(request.getParameter("act"))) {
			request.getSession().removeAttribute(Constants.SESSION_KEY);
			request.getSession().invalidate();
			response.sendRedirect("login.jsp");
			return;
		}

		String usr = request.getParameter("usr");
		String psw = request.getParameter("psw");
		try {

			if (StringUtils.isBlank(usr)) {
				throw new RuntimeException("请输入帐号");
			}

			request.getSession().setAttribute(Constants.SESSION_KEY,
					usrService.login(usr, psw));

			response.sendRedirect("pj");

		} catch (Exception e) {
			LOG.error(e.getMessage());
			request.setAttribute(Constants.ERROR, e.getMessage());
			request.getRequestDispatcher("login.jsp")
					.forward(request, response);
		}

	}

}
