package com.rfxlab.util;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rfxlab.vidsell.model.GeoArea;
import com.rfxlab.vidsell.model.GeoLocation;

import rfx.core.util.FileUtils;
import rfx.core.util.RandomUtil;

public class LocationDataUtil {
	
	static List<GeoArea> geoAreas = null;

	public static List<GeoArea> getGeoAreas() {
		if(geoAreas == null){
			try {
				String json = FileUtils.readFileAsString("./configs/vietnam-location.json");
				Type listType = new TypeToken<List<GeoArea>>() {}.getType();
				geoAreas = (new Gson().fromJson(json, listType));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return geoAreas;
	}
	
	public static Map<Integer,GeoArea> getGeoAreasMap() {		
		return getGeoAreas().stream().collect(Collectors.toMap(GeoArea::getId,Function.identity()));
	}
	
	public static String getRamdomLocationName(){
		String locName = "";
		int ran = RandomUtil.getRandomInteger(100, 0);
		
		Map<Integer,GeoArea> map = getGeoAreasMap();
		int areaId = 0;
		if(ran < 70){
			areaId = ran % 2 == 1? 1 : 5;
			List<GeoLocation> geoLocations = map.get(areaId).getListArea();
			int index = RandomUtil.getRandomInteger(geoLocations.size()-1, 0);
			locName = geoLocations.get(index).getLocName();
		} else {
			List<GeoLocation> geoLocations = getGeoAreas().get(RandomUtil.getRandomInteger(getGeoAreas().size()-1, 0)).getListArea();
			int index = RandomUtil.getRandomInteger(geoLocations.size()-1, 0);
			locName = geoLocations.get(index).getLocName();
		}
		return locName;
	}

	public static void main(String[] args) {
		
		
		for (int i = 0; i < 1000; i++) {
			String locName = getRamdomLocationName();
			System.out.println(locName);
		}
			
			
		
		
	}
}
