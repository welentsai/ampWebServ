package com.momo.amp;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;

/**
 * Servlet Filter implementation class Router
 */
public class Router implements Filter {
	
	private static Logger logger = null;

    /**
     * Default constructor. 
     */
    public Router() {
    	logger = AmpLogger.getLogger();
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// pass the request along the filter chain
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		httpRequest.setCharacterEncoding("UTF-8");
		String url  = httpRequest.getRequestURL().toString();
		String requestURI  = httpRequest.getRequestURI().toString();	
		
		logger.debug("Router -- doFilter !!");
		logger.debug("Router -- url={}", url );
		logger.debug("Router -- uri={}", requestURI );
		
		if (requestURI.startsWith("/") && requestURI.length() > 2) { // specify catecode and page number
			String[] paraList = requestURI.substring(1).split("/");
			if(paraList.length > 2) {
				logger.debug("Router -- cateCode={}", paraList[0] ); // cateCode
				logger.debug("Router -- curPage={}", paraList[1] ); // curPage
				request.getRequestDispatcher("/amp?cateCode="+ paraList[0] + "&curPage="+paraList[1]).forward(request, response);
			} else {
				request.getRequestDispatcher("/amp").forward(request, response);
			}
		} else { 
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
