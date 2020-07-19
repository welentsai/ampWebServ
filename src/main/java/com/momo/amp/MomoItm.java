package com.momo.amp;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class MomoItm {
	private String categoryUrl;
	private String goodsCode;
	private String goodsUrl;
	private String goodsName;
	private String goodsSubName;
	private String imgUrl;
	private String imgHeight;
	private String imgWidth;
	private String goodsPrice;
	private String goodsPriceText;
	private String icons;
	//private Icon fastIcon;
	private List<Icon> couponIcons;
	
	public MomoItm(String jsonStr) {
		JSONObject itmJson = new JSONObject(jsonStr);
		
		this.categoryUrl = itmJson.getString("categoryUrl");
		this.goodsCode = itmJson.getString("goodsCode");
		this.goodsUrl = itmJson.getString("goodsUrl");
		this.goodsName = itmJson.getString("goodsName");
		this.goodsSubName = itmJson.getString("goodsSubName");
		this.imgUrl = itmJson.getString("imgUrl");
		this.imgHeight = itmJson.getString("imgHeight");
		this.imgWidth = itmJson.getString("imgWidth");
		this.goodsPrice = getPrice(itmJson.getString("goodsPrice"));
		this.goodsPriceText = getPriceText(itmJson.getString("goodsPrice"));
		
		if(itmJson.has("icon")) {
			this.icons = itmJson.get("icon").toString();
			this.couponIcons = getCouponIcons(itmJson.getJSONArray("icon"));
		} else {
			this.icons = null;
			//this.fastIcon = null;
			this.couponIcons = null;
		}
		
		//showItm();
	}
	
	public String getCategoryUrl() {
		return this.categoryUrl;
	}

	public String getGoodsCode() {
		return this.goodsCode;
	}
	
	public String getGoodsUrl() {
		return this.goodsUrl;
	}
	
	public String getGoodsName() {
		return this.goodsName;
	}
	
	public String getImgUrl() {
		return this.imgUrl;
	}
	
	public String getImgWidth() {
		return this.imgWidth;
	}
	
	public String getImgHeight() {
		return this.imgHeight;
	}
	
	public String getPrdEvent() {
		return this.goodsSubName;
	}
	
	public String getGoodsPrice() {
		return this.goodsPrice;
	}
	
	public String getGoodsPriceText() {
		return this.goodsPriceText;
	}
	
//	public Icon getFastIcon() {
//		return this.fastIcon;
//	}
	
	public List<Icon> getCouponIcons() {
		return this.couponIcons;
	}
	
	
//	private Icon getFastIcon(JSONArray iconArry) {
//		if(iconArry.length() > 0) {
//			String content = iconArry.getJSONObject(0).getString("iconContent");
//			String bgColor = iconArry.getJSONObject(0).getString("iconBgColor");
//			String contentColor = iconArry.getJSONObject(0).getString("iconContentColor");
//			return new Icon(content, bgColor, contentColor);
//		}
//		return null;
//	}
	
	private List<Icon> getCouponIcons(JSONArray iconArry) {
		List<Icon>  couponList = new ArrayList<Icon>();
		if(iconArry.length() > 0) {
			for(int i = 0; i<iconArry.length(); i++) {
				String content = iconArry.getJSONObject(i).getString("iconContent");
				String bgColor = iconArry.getJSONObject(i).getString("iconBgColor");
				String contentColor = iconArry.getJSONObject(i).getString("iconContentColor");
				couponList.add(new Icon(content, bgColor, contentColor));
			}
			return couponList;
		}
		
		return null;
	}
	
	private String getPrice(String priceStr) {
		String[] splited = priceStr.split("\\s+");
		if(splited.length > 0 ) {
			return splited[0];
		}
		return "";
	}
	
	private String getPriceText(String priceStr) {
		String[] splited = priceStr.split("\\s+");
		if(splited.length > 1 ) {
			return splited[1];
		}
		return "";
	}
	
	public void showItm() {
		System.out.println(categoryUrl);
		System.out.println(goodsCode);
		System.out.println(goodsUrl);
		System.out.println(goodsName);
		System.out.println(goodsSubName);
		System.out.println(imgUrl);
		System.out.println(imgHeight);
		System.out.println(goodsPrice);
		System.out.println(goodsPriceText);
		System.out.println(icons);	
		//System.out.println(fastIcon);
		System.out.println(couponIcons);
	}
}
