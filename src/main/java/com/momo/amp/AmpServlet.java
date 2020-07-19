package com.momo.amp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;

@WebServlet("/amp")
public class AmpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = null;

	private static String HTML_DOC = "<!DOCTYPE html>\r\n" + "<html ⚡ lang=\"en\">\r\n" + "<head>\r\n"
			+ "	<meta charset=\"utf-8\" />\r\n"
			+ "	<meta name=\"viewport\" content=\"width=device-width,minimum-scale=1,initial-scale=1\">\r\n" + "\r\n"
			+ "	<link rel=\"canonical\" href=\"/article.html\">\r\n" + "\r\n" + "	<title>Momo AMP</title>\r\n"
			+ "\r\n"
			+ "	<style amp-boilerplate>body{-webkit-animation:-amp-start 8s steps(1,end) 0s 1 normal both;-moz-animation:-amp-start 8s steps(1,end) 0s 1 normal both;-ms-animation:-amp-start 8s steps(1,end) 0s 1 normal both;animation:-amp-start 8s steps(1,end) 0s 1 normal both}@-webkit-keyframes -amp-start{from{visibility:hidden}to{visibility:visible}}@-moz-keyframes -amp-start{from{visibility:hidden}to{visibility:visible}}@-ms-keyframes -amp-start{from{visibility:hidden}to{visibility:visible}}@-o-keyframes -amp-start{from{visibility:hidden}to{visibility:visible}}@keyframes -amp-start{from{visibility:hidden}to{visibility:visible}}</style><noscript><style amp-boilerplate>body{-webkit-animation:none;-moz-animation:none;-ms-animation:none;animation:none}</style></noscript>\r\n"
			+ "	<style amp-custom>\r\n" + "		header {\r\n" + "			height: 44px;\r\n"
			+ "			overflow: hidden;\r\n" + "			position: relative;\r\n" + "			z-index: 4;\r\n"
			+ "			display: block;\r\n" + "		}\r\n" + "		\r\n" + "		table[Attributes Style] {\r\n"
			+ "			width: 100%;\r\n" + "			border-top-width: 0px;\r\n"
			+ "			border-right-width: 0px;\r\n" + "			border-bottom-width: 0px;\r\n"
			+ "			border-left-width: 0px;\r\n" + "			-webkit-border-horizontal-spacing: 0px;\r\n"
			+ "			-webkit-border-vertical-spacing: 0px;\r\n" + "		}\r\n" + "		\r\n" + "		table {\r\n"
			+ "			background-color: #D62872;\r\n" + "			display: table;\r\n"
			+ "			border-collapse: separate;\r\n" + "			border-spacing: 2px;\r\n"
			+ "			border-color: grey;\r\n" + "		}\r\n" + "		\r\n" + "		header table th {\r\n"
			+ "			position: relative;\r\n" + "			text-align: center;\r\n"
			+ "			height: 44px;\r\n" + "			font-weight: bold;\r\n" + "			display: table-cell;\r\n"
			+ "			vertical-align: inherit;\r\n" + "			font-size: 0;\r\n" + "		}\r\n" + "		\r\n"
			+ "		header table th .logo {\r\n" + "			position: relative;\r\n" + "			z-index: 2;\r\n"
			+ "			text-align: center;\r\n" + "		}\r\n" + "		\r\n" + "		header table th h1 {\r\n"
			+ "			background-color: #D62872;\r\n" + "			margin: 0px;\r\n" + "			padding: 0px;\r\n"
			+ "			font: bold 26px/44px Century Gothic;\r\n" + "			color: #FFFFFF;\r\n"
			+ "			position: relative;\r\n" + "			z-index: 1;\r\n" + "		}\r\n" + "		\r\n"
			+ "		header table th .logo img {\r\n" + "			position: relative;\r\n"
			+ "			top: 0px;\r\n" + "			z-index: 2;\r\n" + "			border: 0px;\r\n"
			+ "			width: 110px;\r\n" + "			height: 17px;\r\n" + "		}\r\n" + "		\r\n"
			+ "		.pathArea {\r\n" + "			max-width: 640px;\r\n" + "			margin: 0 auto;\r\n"
			+ "			padding: 5px 0px 5px 10px;\r\n" + "			background-color: #fff;\r\n"
			+ "			z-index: 2;\r\n" + "			border: 0px;\r\n" + "			box-shadow: 0px 0px 0px;\r\n"
			+ "			overflow: hidden;\r\n" + "			height: 100%;\r\n" + "			max-height: 40px;\r\n"
			+ "			position: relative;\r\n" + "			display: block;\r\n"
			+ "			vertical-align: middle;\r\n" + "		}\r\n" + "		\r\n" + "		.pathArea ul {\r\n"
			+ "			margin: 0px;\r\n" + "			padding: 0px;\r\n" + "			list-style: none;\r\n"
			+ "			display: inline;\r\n" + "			white-space: normal;\r\n"
			+ "			list-style-type: disc;\r\n" + "			margin-block-start: 1em;\r\n"
			+ "			margin-block-end: 1em;\r\n" + "			margin-inline-start: 0px;\r\n"
			+ "			margin-inline-end: 0px;\r\n" + "			padding-inline-start: 0px;\r\n" + "		}\r\n"
			+ "		\r\n" + "		.pathArea li {\r\n" + "			height: 24px;\r\n"
			+ "			font: 15px/24px Helvetica;\r\n" + "			color: #888888;\r\n"
			+ "			margin-left: 0px;\r\n" + "			display: list-item;\r\n"
			+ "			text-align: -webkit-match-parent;\r\n" + "			padding: 0px;\r\n"
			+ "			list-style: none;\r\n" + "			display: inline;\r\n"
			+ "			white-space: normal;\r\n" + "		}\r\n" + "		\r\n"
			+ "		.pathArea li:first-child {\r\n" + "			margin: 0;\r\n" + "		}\r\n" + "		\r\n"
			+ "		.pathArea ul li  {\r\n" + "			vertical-align: middle;\r\n" + "		}\r\n" + "		\r\n"
			+ "		body {\r\n" + "			background-color: #eee;\r\n" + "			margin: 0 auto;\r\n"
			+ "			display: block;\r\n" + "		}\r\n" + "		.prdListArea ul {\r\n"
			+ "			list-style: none;\r\n" + "			margin:0px;\r\n" + "			padding:0px;\r\n"
			+ "			display: block;\r\n" + "			margin-block-start: 1em;\r\n"
			+ "			margin-block-end: 1em;\r\n" + "			margin-inline-start: 0px;\r\n"
			+ "			margin-inline-end: 0px;\r\n" + "			padding-inline-start: 0px;\r\n" + "		}\r\n"
			+ "		\r\n" + "		.prdListArea ul li {	\r\n" + "			display: list-item;\r\n"
			+ "			text-align: -webkit-match-parent;\r\n" + "		}\r\n" + "		\r\n"
			+ "		.prdListArea ul li a {\r\n" + "			text-decoration: none;\r\n"
			+ "			cursor: pointer;\r\n" + "		}\r\n" + "\r\n" + "		.prdListArea {\r\n"
			+ "			background-color: transparent;\r\n" + "			margin: 0px auto;\r\n"
			+ "			border: 0px;\r\n" + "			max-width: 640px;\r\n" + "		}\r\n" + "		\r\n"
			+ "		p {\r\n" + "			display: block;\r\n" + "			margin-block-start: 1em;\r\n"
			+ "			margin-block-end: 1em;\r\n" + "			margin-inline-start: 0px;\r\n"
			+ "			margin-inline-end: 0px;\r\n" + "		}\r\n" + "		\r\n"
			+ "		.prdListArea ul li {\r\n" + "			float: left;\r\n" + "			width: 50%;\r\n"
			+ "			margin: 0px 0px 10px;\r\n" + "			position: relative;\r\n"
			+ "			padding: 0px;\r\n" + "			list-style: none;\r\n" + "		}\r\n" + "\r\n"
			+ "		.prdListArea ul li:nth-child(odd) a {margin: 0px 5px 0px 0px;}\r\n" + "\r\n"
			+ "		.prdListArea ul li:nth-child(even) a {margin:0px 0px 0px 5px}\r\n" + "\r\n"
			+ "		.prdListArea ul li a {\r\n" + "			background-color: #FFFFFF;\r\n"
			+ "			text-align: center;\r\n" + "			padding: 0px 0px 10px;\r\n"
			+ "			display: block;\r\n" + "			border: 1px solid #ccc;\r\n"
			+ "			box-sizing: border-box;\r\n" + "		}\r\n" + "\r\n"
			+ "		.prdListArea ul li .trackbtn {\r\n" + "			margin: 0px 5px 0px 0px;\r\n"
			+ "			padding: 0px;\r\n" + "			position: absolute;\r\n" + "			bottom: 10px;\r\n"
			+ "			right: 5px;\r\n" + "			z-index: 1;\r\n" + "			border: none;\r\n"
			+ "			background-color: #FFFFFF;\r\n" + "			text-align: center;\r\n"
			+ "			display: block;\r\n" + "			box-sizing: border-box;\r\n" + "		}\r\n" + "\r\n"
			+ "		.prdListArea ul li .prdImgWrap {\r\n" + "			width: 100%;\r\n"
			+ "			height: 100%;\r\n" + "			position: relative;\r\n"
			+ "			display: inline-block;\r\n" + "		}\r\n" + "\r\n"
			+ "		.prdListArea ul li a .prdEvent {\r\n" + "			height: 20px;\r\n"
			+ "			font: 13px/20px Helvetica;\r\n" + "			color: #dd2726;\r\n"
			+ "			text-align: left;\r\n" + "			margin: 5px 0px 0px;\r\n"
			+ "			padding: 0px 5px;\r\n" + "			overflow: hidden;\r\n" + "		}\r\n" + "\r\n"
			+ "		.prdListArea ul li a .prdName {\r\n" + "			height: 40px;\r\n"
			+ "			font: 15px/20px Helvetica;\r\n" + "			color: #484848;\r\n"
			+ "			text-align: left;\r\n" + "			margin: 0px;\r\n" + "			padding: 0px 5px;\r\n"
			+ "			overflow: hidden;\r\n" + "			margin-top: 5px;\r\n" + "		}\r\n" + "\r\n"
			+ "		.prdListArea ul li a .priceArea {\r\n" + "			margin: 0px;\r\n"
			+ "			padding: 0px;\r\n" + "			text-align: left;\r\n" + "		}\r\n" + "\r\n"
			+ "		.prdListArea ul li a .priceArea .priceSymbol {\r\n" + "			font: 13px/24px Century Gothic;\r\n"
			+ "			color: #D62872;\r\n" + "			margin: 0px 5px;\r\n" + "			text-align: left;\r\n"
			+ "		}\r\n" + "\r\n" + "		ul li a .priceArea .discountArea {\r\n" + "			height: 20px;\r\n"
			+ "			padding: 0px 5px;\r\n" + "			display: block;\r\n" + "			position: relative;\r\n"
			+ "			text-align: left;\r\n" + "		}\r\n" + "\r\n"
			+ "		ul li a .priceArea .discountArea b {\r\n" + "			background-color: #FF4C76;\r\n"
			+ "			font: 9px/9px Helvetica;\r\n" + "			color: #FFFFFF;\r\n"
			+ "			margin: 2px;\r\n" + "			padding: 2px 4px;\r\n" + "			border-radius: 3px;\r\n"
			+ "			text-align: left;\r\n" + "		}\r\n" + "\r\n"
			+ "		ul li a .priceArea .discountArea .fastIcon {\r\n" + "			background-color: #BE0211;\r\n"
			+ "		}\r\n" + "		ul li a .priceArea .priceSymbol .price {\r\n" + "			font-size: 22px;\r\n"
			+ "			font-weight: normal;\r\n" + "		}\r\n" + "\r\n"
			+ "		ul li a .priceArea .priceSymbol .priceText {\r\n" + "			font: 10px/20px Helvetica;\r\n"
			+ "			color: #A6A6A6;\r\n" + "			margin: 0px 0px 0px 3px;\r\n" + "		}\r\n" + "\r\n"
			+ "		.prdListArea ul li a .priceArea .discountArea.forsoldout::after {\r\n"
			+ "			width: 100%;\r\n" + "			/* max-width: 200px; */\r\n"
			+ "			color: rgb(255, 255, 255);\r\n" + "			content: \"售完補貨中\";\r\n"
			+ "			text-align: center;\r\n" + "			position: absolute;\r\n"
			+ "			bottom: 117px;\r\n" + "			left: 0px;\r\n" + "			font: 15px/26px Helvetica;\r\n"
			+ "			margin: 0px;\r\n" + "			padding: 0px;\r\n" + "		}\r\n"
			+ "		.prdListArea ul li a .priceArea .discountArea.forsoldout::after {\r\n"
			+ "			background: rgba(0%,0%,0%,0.7);\r\n" + "		}\r\n" + "	</style>\r\n"
			+ "	<script async src=\"https://cdn.ampproject.org/v0.js\"></script>\r\n" + "\r\n" + "</head>\r\n"
			+ "<body>\r\n" + "	<div id=\"momoHeader\" style=\"display:block\">\r\n" + "		<header>\r\n"
			+ "		  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n"
			+ "			<tbody><tr>\r\n"
			+ "			  <th id=\"headMomoLogoTh\"><h1 class=\"logo\" title=\"momo\"><amp-img src=\"https://m.momoshop.com.tw/img/momoActionLogo.png?t=201405080001\" width=\"110\" height=\"17\" alt=\"momo\" title=\"momo\"></amp-img></h1></th>\r\n"
			+ "		  </tbody></table>\r\n" + "		</header>\r\n" + "	</div>\r\n"
			+ "	<article class=\"pathArea\">\r\n" + "        <ul>\r\n" + "        </ul>\r\n" + "    </article>\r\n"
			+ "	<article class=\"prdListArea\">\r\n" + "		<ul>\r\n" + "		</ul>\r\n" + "	</article>\r\n"
			+ "</body>\r\n" + "</html>";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AmpServlet() {
		super();
		logger = AmpLogger.getLogger();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		logger.debug("Momo.Amp -- GET request from client !!");
		response.setCharacterEncoding("UTF-8");
		String cateCode = request.getParameter("cateCode");
		String curPage = request.getParameter("curPage");
		logger.debug("Momo.Amp -- cateCode={} curPage={}", cateCode, curPage);
		if (cateCode != null && curPage != null) {
			response.getWriter().print(getAmpPage(cateCode, curPage));
			response.getWriter().flush();
			response.getWriter().close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.debug("Momo.Amp -- POST request from client !!");
		logger.debug("Momo.Amp -- Redirect to Get request handler ...");
		doGet(request, response);
	}

	private String getAmpPage(String cate, String curPage) {
		// System.setProperty("log4j2.debug", "TRUE");
		String htmlStr = "";

		// default value for params
		String postUrl = "http://www.momoshop.com.tw/api/moecapp/getCategoryGoodsV2";
		String host = "AMP";

		if (!ConfigParams.getPropertyValue("URL").equals("")) {
			postUrl = ConfigParams.getPropertyValue("URL");
			logger.debug("Momo.Amp -- URL={}", postUrl);
		}

		if (!ConfigParams.getPropertyValue("HOST").equals("")) {
			host = ConfigParams.getPropertyValue("HOST");
		}

		String jsonStr = sendPost(postUrl, getJsonPayload(host, cate, curPage));

		logger.debug("Response={}", jsonStr);

		try {
			if (!jsonStr.equals("")) {
				JSONObject jsonResp = new JSONObject(jsonStr);
				if (jsonResp.getBoolean("success")) { // success return
					String pageTitle = jsonResp.getString("shareTitle");

					JSONArray categoryCrumbs = jsonResp.getJSONObject("rtnGoodsData").getJSONArray("categoryCrumbs");
					List<String> cateList = new ArrayList<String>();

					for (int i = 0; i < categoryCrumbs.length(); i++) {
						cateList.add(categoryCrumbs.getJSONObject(i).getString("categoryName"));
					}

					String cateHtml = CateCrumbs.getHtml(cateList);

					JSONArray goodsList = jsonResp.getJSONObject("rtnGoodsData").getJSONArray("goodsInfoList");

					List<String> itmList = new ArrayList<String>();
					String categoryUrl = "";
					for (int i = 0; i < goodsList.length(); i++) {

						ItmTag tmpTag = new ItmTag(goodsList.get(i).toString());
						itmList.add(tmpTag.getHtml());
						categoryUrl = tmpTag.getCateUrl();
					}

					logger.debug("Momo.Amp -- Generating Amp Page ---");
					htmlStr = makeAmpPage(cateHtml, itmList, categoryUrl, pageTitle);
				} else {
					logger.debug("Got False return!! Closing...");
				}
			}

		} catch (Exception e) {
			logger.error("!! JSONObject Exception !!");
			logger.error(e.getMessage(), e);
		}

		logger.debug("Momo.Amp -- Process END ---");
		return htmlStr;
	}

	private String getJsonPayload(String host, String cateCode, String curPage) {
		JSONObject jsonPayload = new JSONObject();
		jsonPayload.put("host", host);

		JSONObject dataElem = new JSONObject();
		dataElem.put("cateCode", cateCode);
		dataElem.put("curPage", curPage);

		jsonPayload.put("data", dataElem);

		logger.debug("Request payload={}", jsonPayload.toString());

		return jsonPayload.toString();
	}

	private static String sendPost(String url, String jsonPayload) {
		CloseableHttpClient client = HttpClients.createDefault();
		String jsonStr = "";
		try {
			HttpPost httpPost = new HttpPost(url);
			StringEntity entity = new StringEntity(jsonPayload);

			httpPost.setHeader("user-agent", "my-app/0.0.1");
			httpPost.setHeader("Accept", "*/*");
			httpPost.setHeader("Accept-Encoding", "gzip, deflate");
			httpPost.setHeader("Connection", "keep-alive");
			httpPost.setHeader("Content-Type", "application/json");
			httpPost.setEntity(entity);

			CloseableHttpResponse response = client.execute(httpPost);

			if (response.getStatusLine().getStatusCode() == 200) { // response OK
				jsonStr = EntityUtils.toString(response.getEntity());
			}

			client.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return jsonStr;
	}

	private String makeAmpPage(String cateHtml, List<String> itmList, String cateUrl, String pageTitle) {
		Document doc = Jsoup.parse(HTML_DOC);

		Element linkElem = doc.select("link").first();
		linkElem.attr("href", cateUrl);

		Element titleElem = doc.select("title").first();
		titleElem.text(pageTitle);

		doc.select("ul").first().append(cateHtml);

		if (itmList != null) {
			for (String itm_html : itmList) {
				doc.select("ul").last().append(itm_html);
			}
		}
		return doc.html();
	}
}