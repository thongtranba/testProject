package bathongshop.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bathongshop.constant.PublicConstant;
import bathongshop.dao.ProductDAO;
import bathongshop.entity.Product;

@WebServlet(PublicConstant.SEARCH_URL)
public class SearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ProductDAO productDAO = ProductDAO.getProductDAO();
	private static Logger logger = LogManager.getLogger(SearchController.class);

	public SearchController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String search = request.getParameter(PublicConstant.SEARCH);
			List<Product> searchProducts = productDAO.searchProducts(search);
			request.setAttribute(PublicConstant.SEARCH_STRING, search);
			request.setAttribute(PublicConstant.SEARCH_PRODUCT, searchProducts);
			RequestDispatcher dispatcher = request.getRequestDispatcher(PublicConstant.SEARCH_JSP);
			dispatcher.forward(request, response);
		} catch (Exception e) {
			logger.error(PublicConstant.THIS_IS_ERROR, e.getMessage());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
