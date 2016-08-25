package com.yangui.utils;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * 
 * @Description: TODO
 * @author : Tian (Ten)
 * @date ： 2015-1-20 上午11:31:08
 */
public class HttpClientUtil
{

	public static String REQ_METHOD_GET = "GET";
	public static String REQ_METHOD_POST = "POST";

	// 请求地址
	private String address;

	// 链接超时时间
	public int conn_timeout = 10000;

	// 读取超时
	public int read_timeout = 10000;

	// 请求方式
	public String method = REQ_METHOD_POST;

	// connection
	private HttpURLConnection conn;

	public HttpClientUtil(String address) throws KeyManagementException,
			NoSuchAlgorithmException, IOException
	{
		this.address = address;
		this.conn = build();
	}

	/**
	 * 上传数据
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String post(String params) throws Exception
	{
		// post
		if (params != null)
		{
			conn.setDoOutput(true);
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			out.write(params.getBytes(Charset.forName("UTF-8")));
			out.flush();
			out.close();
		}
		conn.connect();
		// get result
		if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
		{
			String result = parsRtn(conn.getInputStream());
			release();
			return result;
		}
		else
		{
			throw new Exception(conn.getResponseCode() + " "
					+ conn.getResponseMessage());
		}
	}

	private HttpURLConnection build() throws NoSuchAlgorithmException,
			KeyManagementException, IOException
	{

		HttpURLConnection conn = null;
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager()
		{
			public X509Certificate[] getAcceptedIssuers()
			{
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs,
					String authType)
			{
			}

			public void checkServerTrusted(X509Certificate[] certs,
					String authType)
			{
			}
		} };

		// Install the all-trusting trust manager
		SSLContext sc = SSLContext.getInstance("TLS");
		sc.init(null, trustAllCerts, new SecureRandom());

		// ip host verify
		HostnameVerifier hv = new HostnameVerifier()
		{
			public boolean verify(String urlHostName, SSLSession session)
			{
				return urlHostName.equals(session.getPeerHost());
			}
		};

		// set ip host verify
		HttpsURLConnection.setDefaultHostnameVerifier(hv);

		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		URL url = new URL(address);
		conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod(method);// POST
		conn.setConnectTimeout(conn_timeout);
		conn.setReadTimeout(read_timeout);

		return conn;
	}

	public void release()
	{
		if (conn != null)
		{
			conn.disconnect();
		}
	}

	/**
	 * 获取返回数据
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	private String parsRtn(InputStream is) throws IOException
	{

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuffer buffer = new StringBuffer();
		String line = null;
		boolean first = true;
		while ((line = reader.readLine()) != null)
		{
			if (first)
			{
				first = false;
			}
			else
			{
				buffer.append("\n");
			}
			buffer.append(line);
		}
		return buffer.toString();
	}

	/**
	 * InputStream转换成String 
	 * 
	 * @param in
	 * @param encoding
	 *            编码
	 * @return String
	 * @throws Exception
	 */
	public static String InputStreamTOString(InputStream in, String encoding)
			throws IOException
	{
		
	        ByteArrayOutputStream   baos   =   new   ByteArrayOutputStream(); 
	        int   i=-1; 
	        while((i=in.read())!=-1)
	        baos.write(i); 
	        System.out.println(baos.toString());
	       return   baos.toString();
		
		/*int BUFFER_SIZE = 4096;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[BUFFER_SIZE];
		int count = -1;
		while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
			outStream.write(data, 0, count);
		data = null;
		byte[] outByte = outStream.toByteArray();
		
		System.out.println("size : " + outByte.length);
		
		outStream.close();
		return new String(outByte, encoding);*/
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

}
