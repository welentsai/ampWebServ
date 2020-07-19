package com.momo.amp;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;

public class ItmTag {
	private static Logger logger;
	private MomoItm momoItm;
	private Document doc;
	
	public ItmTag(String jsonStr) {
		logger = AmpLogger.getLogger();
		//JSONObject itmJson = new JSONObject(jsonStr);
		this.momoItm = new MomoItm(jsonStr);
		String tagHtml = "<li></li>";
		doc = Jsoup.parse(tagHtml);
		makeItmTag();
	}	
	
	public String getCateUrl() {
		return momoItm.getCategoryUrl();
	}
	
	public String getHtml() {
		return doc.body().html();
	}
	private void makeItmTag() {
		makeInputTag();
		Element aElem = makeATag();
		makeDivTag(aElem);
		makePriceTag(aElem);	
		//logger.debug((doc.body().html()));
	}
	
	private Element makeInputTag() {
		Element e = doc.select("li").first().appendElement("input");
		e.attr("type", "hidden");
		e.attr("name", "goodsCode");
		e.attr("id", "goodsCode");
		e.attr("value", momoItm.getGoodsCode());
		//System.out.println(doc.body().html());
		return e;
	}
	
	
	private Element makeATag() {
		Element e = doc.select("li").first().appendElement("a");
		e.attr("href", momoItm.getGoodsUrl());
		e.attr("title", momoItm.getGoodsName());
		//System.out.println(doc.body().html());
		return e;
	}
	
	private void makeDivTag(Element parent) {
		Element divElem = parent.appendElement("div");
		divElem.attr("class", "prdImgWrap");
		//System.out.println(doc.body().html());
		Element ampImg = divElem.appendElement("amp-img");
		ampImg.attr("class", "goodsImg");
		ampImg.attr("alt", momoItm.getGoodsName());
		ampImg.attr("title", momoItm.getGoodsName());
		ampImg.attr("src", momoItm.getImgUrl());
		ampImg.attr("layout", "responsive");
		ampImg.attr("width", momoItm.getImgWidth());
		ampImg.attr("height", momoItm.getImgHeight());
		
		//System.out.println(divElem.toString());
	}

	private void makePriceTag(Element parent) {
		Element pElem = parent.appendElement("p");
		pElem.attr("class", "prdEvent");
		pElem.text(momoItm.getPrdEvent());
		
		pElem = parent.appendElement("p");
		pElem.attr("class", "prdName");
		pElem.text(momoItm.getGoodsName());
		
		pElem = parent.appendElement("p");
		pElem.attr("class", "priceArea");
		
		Element spanElem = pElem.appendElement("span");
		spanElem.attr("class", "priceSymbol");
		spanElem.text("$");
		
		Element bElem = spanElem.appendElement("b");
		bElem.attr("class", "price");
		bElem.text(momoItm.getGoodsPrice());
		
		bElem = spanElem.appendElement("b");
		bElem.attr("class", "priceText");
		bElem.text(momoItm.getGoodsPriceText());
		
		spanElem = pElem.appendElement("span");
		spanElem.attr("class", "discountArea");
		
//		if(momoItm.getFastIcon() != null) {
//			bElem = spanElem.appendElement("b");
//			bElem.attr("class", "fastIcon");
//			bElem.attr("style", getIconStyle(momoItm.getFastIcon()));
//			bElem.text(momoItm.getFastIcon().getContent());
//		}
		
		if(momoItm.getCouponIcons() != null ) { // not empty
			for (Icon icon : momoItm.getCouponIcons()) {
				bElem = spanElem.appendElement("b");
				bElem.attr("class", "icon");
				bElem.attr("style", getIconStyle(icon));
				bElem.text(icon.getContent());
			}
		}
		
		//System.out.println(pElem.toString());
	}
	
	private String getIconStyle(Icon icon) {
		String style = "background-color:" + icon.getBgColor() + ";";
		style += "color:" + icon.getContentColor() + ";";
		return style;
	}
}
