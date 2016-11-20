package com.rfxlab.vidsell.model;

import java.util.ArrayList;
import java.util.List;

import rfx.core.util.StringUtil;

public class GeoArea {

	String AreaId;
	String AreaName;
	List<GeoLocation> ListArea = new ArrayList<>();
	public String getAreaId() {
		return AreaId;
	}
	
	public int getId() {
		return StringUtil.safeParseInt(AreaId);
	}
	
	public void setAreaId(String areaId) {
		AreaId = areaId;
	}
	public String getAreaName() {
		return AreaName;
	}
	public void setAreaName(String areaName) {
		AreaName = areaName;
	}
	public List<GeoLocation> getListArea() {
		return ListArea;
	}
	public void setListArea(List<GeoLocation> listArea) {
		ListArea = listArea;
	}
	
	
}
