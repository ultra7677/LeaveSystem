package com.sapanywhere.app.oauth;

import java.io.InputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sapanywhere.app.properties.ProxyProperties;

@Service
public final class HttpClient {

	private static Logger logger = Logger.getLogger(HttpClient.class);

	@Autowired
	private ProxyProperties proxyProperties;

	private Proxy getProxy(){
		if (!StringUtils.isEmpty(this.proxyProperties.getHost())
				&& !StringUtils.isEmpty(this.proxyProperties.getPort())) {
			
			if(!StringUtils.isEmpty(this.proxyProperties.getUsername()) && !StringUtils.isEmpty(this.proxyProperties.getPassword())){
				Authenticator.setDefault(new Authenticator() {
				    protected PasswordAuthentication getPasswordAuthentication() {
				        return new PasswordAuthentication(proxyProperties.getUsername(), proxyProperties.getPassword().toCharArray());
				    }
				});
			}
			
			
			return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(this.proxyProperties.getHost(), this.proxyProperties.getPort()));
		}
		
		return Proxy.NO_PROXY;
		
	}
	
	public String get(String url) {
		HttpURLConnection con = null;
		InputStream stream = null;
		try {
			URL conURL = new URL(url);
			con = (HttpURLConnection) conURL.openConnection(this.getProxy());
			
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
			if (stream != null) {
				IOUtils.closeQuietly(stream);
			}

			if (con != null) {
				con.disconnect();
			}
		}
	}

	public String post(String url) {
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
			if (stream != null) {
				IOUtils.closeQuietly(stream);
			}

			if (con != null) {
				con.disconnect();
			}
		}
	}
}
