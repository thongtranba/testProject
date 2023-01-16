package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ProductDAO;
import model.Product;

/**
 * Servlet implementation class categoryServlet
 */
@WebServlet("/category")
public class CategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductDAO productDAO = new ProductDAO();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CategoryServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String command = request.getParameter("command");
			int pageId = Integer.parseInt(request.getParameter("pageId"));
			HttpSession session = request.getSession();
			session.setAttribute("command", command);
			int categoryId = 0;
			switch (command) {
			case "rackets":
				categoryId = 1;
				break;
			case "bags":
				categoryId = 2;
				break;
			case "clothing":
				categoryId = 3;
				break;
			case "shoes":
				categoryId = 4;
				break;
			case "strings":
				categoryId = 5;
				break;	
			}		
			int itemPerPage = 9;
			int totalProducts = productDAO.totalCategoryProduct(categoryId);
			int totalPage = totalProducts / itemPerPage;
			request.setAttribute("totalPage", totalPage);
			List<Product> categoryList = productDAO.selectAllProductByCategoryId(categoryId, pageId, itemPerPage);
			request.setAttribute("categoryList", categoryList);	
			RequestDispatcher dispatcher = request.getRequestDispatcher("category.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
