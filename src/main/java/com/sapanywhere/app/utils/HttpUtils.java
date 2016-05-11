package com.sapanywhere.app.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public final class HttpUtils {
	
	private static Logger logger = Logger.getLogger(HttpUtils.class);
	
	public static String get(String url) {
		HttpURLConnection con = null;
		InputStream stream = null;
		try {
			URL locationURL = new URL(url);
			con = (HttpURLConnection) locationURL.openConnection();
			con.setInstanceFollowRedirects(false);
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("ContentType", "application/json");
			con.connect();
			int responseCode = con.getResponseCode();

			logger.info("Sending 'GET' request to URL : " + url);
			logger.info("Response Code : " + responseCode);

			stream = con.getInputStream();
			String content = IOUtils.toString(con.getInputStream(), "UTF-8");
			return content;
		} catch (Exception ex) {

			if (con != null) {
				con.disconnect();
			}
			
			logger.error(ex);
			return null;
			
		} finally {
			if(stream != null){
				IOUtils.closeQuietly(stream);
			}
			
			if (con != null) {
				con.disconnect();
			}
		}
	}
	
	public static String post(String url) {
		HttpURLConnection con = null;
		InputStream stream = null;
		try {
			URL locationURL = new URL(url);
			con = (HttpURLConnection) locationURL.openConnection();
			con.setInstanceFollowRedirects(false);
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("ContentType", "application/json");
			con.connect();
			int responseCode = con.getResponseCode();

			logger.info("Sending 'GET' request to URL : " + url);
			logger.info("Response Code : " + responseCode);
			
			stream = con.getInputStream();
			String content = IOUtils.toString(con.getInputStream(), "UTF-8");
			IOUtils.closeQuietly(stream);
			return content;
		} catch (Exception ex) {

			if (con != null) {
				con.disconnect();
			}
			
			logger.error(ex);
			return null;
			
		} finally {
			if(stream != null){
				IOUtils.closeQuietly(stream);
			}
			
			if (con != null) {
				con.disconnect();
			}
		}
	}
}
