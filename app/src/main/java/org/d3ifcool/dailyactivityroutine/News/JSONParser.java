package org.d3ifcool.dailyactivityroutine.News;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Fran on 01.03.2018.
 */

public class JSONParser {


    public static String getDataFrom(String webSiteHtml) {
        try
        {
            String data = getEditedDescriptionFrom(webSiteHtml);
            return data;
        } catch(Exception e) {
            e.printStackTrace();
            return "Lokasi tidak di temukan.";
        }
    }

    private static String getEditedDescriptionFrom(String webSiteHtml) throws Exception {
        JSONObject jsonObject = new JSONObject(webSiteHtml);
        String data = "";
        data += getTemperature(jsonObject);
        data += getDescription(jsonObject);
        return data;
    }

    private static String getTemperature(JSONObject jsonObject) throws Exception {
        String data = jsonObject.getJSONObject("main").getString("temp_min");
        data += "\u00b0C";
        data += "\n";
        return data;
    }

    private static String getDescription(JSONObject jsonObject) throws Exception {
        JSONArray jsonArray = jsonObject.getJSONArray("weather");
        String data = "";
        for (int i = 0; i < jsonArray.length(); i++) {
            data += jsonArray.getJSONObject(i).getString("description");
        }
        return data;
    }
}

/*
{
	"coord":
		{
			"lon":19.02,
			"lat":50.26
		},
	"weather":
		[
			{
				"id":803,
				"main":"Clouds",
				"description":"broken clouds",
				"icon":"04d"
			}
		],
	"base":"stations",
	"main":
		{
			"temp":-9.54,
			"pressure":1017,
			"humidity":56,
			"temp_min":-10,
			"temp_max":-9
		},
	"visibility":10000,
	"wind":
		{
			"speed":2.1
		},
	"clouds":
		{
			"all":75
		},
	"dt":1519900200,
	"sys":
		{
			"type":1,
			"id":5356,
			"message":0.0058,
			"country":"PL",
			"sunrise":1519882019,
			"sunset":1519921572
		},
	"id":3096472,
	"name":"Katowice",
	"cod":200
}
*/