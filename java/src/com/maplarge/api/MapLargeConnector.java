package com.maplarge.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class MapLargeConnector {

	/**
	 * When NO_WEB_CALLS is true all MapLargeConnectors will not make remote
	 * calls. Instead, the response will be the full URL that would have been
	 * invoked.
	 */
	public static boolean NO_WEB_CALLS = false;
	
	public final int Version = 2;

	private String _user;
	private String _token;
	private String _apiserver;
	private String _authstring;
	
	private static final String LINE_FEED = "\r\n";

	private String GetToken(String pass) {
		NO_WEB_CALLS = false;
		String s = InvokeURL("Auth", "Login", "mluser=" + this._user + "&mlpass=" + pass);
		s = s.substring(1, s.length() - 1);
		String[] vals = s.split(",");
		boolean success = false;
		String rettoken = "";
		for (int i = 0; i < vals.length; i++) {
			if (vals[i].startsWith("\"token\""))
				rettoken = vals[i].split(":")[1].replaceAll("\"", "");
			if (vals[i].equals("\"success\":true"))
				success = true;
		}
		return success ? rettoken : "";
	}

	private String InvokeURL(String controller, String actionname, Map<String, String> params) {
            String querystring = this._authstring;
		for (String key : params.keySet()) querystring += "&" + key + "=" + params.get(key);
		return this.InvokeURL(controller, actionname, querystring);
	}
	
	private String InvokeURL(String controller, String actionname, String querystring) {
		if (NO_WEB_CALLS) {
			return this._apiserver + controller + "/" + actionname + "?" + querystring;
		} else {
			try {
				URL url = new URL(this._apiserver + controller + "/" + actionname + "?" + querystring);
				URLConnection conn = url.openConnection();
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String inputLine;
				String s = "";
				while ((inputLine = br.readLine()) != null) s += inputLine;
				br.close();
				return s;
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "ERROR"; // Temporary Error Code
		}
	}

	private String InvokePostURL(String controller, String actionname, Map<String, String> params, String[] filepaths) throws IOException {
		if (NO_WEB_CALLS) return this._apiserver + controller + "/" + actionname;
		String charset = "UTF-8";
		String boundary = "===" + Long.toHexString(System.currentTimeMillis()) + "===";
		OutputStream outputStream;
		PrintWriter writer;
		URL url = new URL(this._apiserver + controller + "/" + actionname);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setUseCaches(false);
		conn.setDoOutput(true); // indicates POST method
		conn.setDoInput(true);
		conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
		conn.setRequestProperty("User-Agent", "CodeJava Agent");
		conn.setRequestProperty("Test", "Bonjour");
		outputStream = conn.getOutputStream();
		writer = new PrintWriter(new OutputStreamWriter(outputStream, charset), true);

		// HEADERS
		writer.append("User-Agent: MapLargeJavaSDK").append(LINE_FEED);

		// FORM FIELDS
		params.put("mluser", this._user);
		params.put("mltoken", this._token);
		for (String key : params.keySet()) {
			writer.append("--" + boundary).append(LINE_FEED);
			writer.append("Content-Disposition: form-data; name=\"" + key + "\"").append(LINE_FEED);
			//writer.append("Content-Type: text/plain; charset=" + charset).append(LINE_FEED);
			writer.append(LINE_FEED);
			writer.append(params.get(key)).append(LINE_FEED);
		}

		// FILES
		for (String path : filepaths) {
			File f = new File(path);
			if (!f.exists()) {
				continue; // MISSING FILE
			}
			writer.append("--" + boundary).append(LINE_FEED);
			writer.append("Content-Disposition: form-data; name=\"fileUpload\"; filename=\"" + f.getName() + "\"").append(LINE_FEED);
			writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(f.getName())).append(LINE_FEED);
			writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
			writer.append(LINE_FEED);
			writer.flush();
			FileInputStream inputStream = new FileInputStream(path);
			byte[] buffer = new byte[4096];
			int bytesRead = -1;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			outputStream.flush();
			inputStream.close();
			writer.append(LINE_FEED);
		}

		// FINISH
		writer.append("--" + boundary + "--").append(LINE_FEED);
		writer.flush();
		writer.close();

		// READ RESPONSE 
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		String s = "";
		while ((inputLine = br.readLine()) != null) {
			s += inputLine;
		}
		br.close();
		return s;
	}

	/**********************
	 *    CONSTRUCTORS    *
	 **********************/

	/**
	 * Constructor. Creates a connection to a MapLarge API server with a
	 * username and token as credentials.
	 * 
	 * @param urlApiServer
	 *            URL of API server. Must begin with valid protocol
	 *            (http/https).
	 * @param username
	 *            Username to use for connection credentials.
	 * @param token
	 *            Authentication token to use for connection credentials.
	 */
	public MapLargeConnector(String urlApiServer, String username, int token) {
		this._apiserver = urlApiServer;
		if (this._apiserver.charAt(this._apiserver.length() - 1) != '/')
			this._apiserver += '/';
		this._user = username;
		this._token = new Integer(token).toString();
		this._authstring = "mluser=" + this._user + "&mltoken=" + this._token;
	}

	/**
	 * Constructor. Creates a connection to a MapLarge API server with a
	 * username and token as credentials.
	 * 
	 * @param urlApiServer
	 *            URL of API server. Must begin with valid protocol
	 *            (http/https).
	 * @param username
	 *            Username to use for connection credentials.
	 * @param password
	 *            Authentication token to use for connection credentials.
	 */
	public MapLargeConnector(String urlApiServer, String username, String password) {
		this._apiserver = urlApiServer;
		if (this._apiserver.charAt(this._apiserver.length() - 1) != '/')
			this._apiserver += '/';
		this._user = username;
		this._token = GetToken(password);
		// if (this._token == "") throw new Exception("Authentication Failed")
		// OR SOMETHING ELSE.
		this._authstring = "mluser=" + this._user + "&mltoken=" + this._token;
	}

	/**
	 * 
	 * @param actionname
	 *            Name of API action being called.
	 * @param params
	 *            Array of key value pairs.
	 * @return API response, usually a JSON formatted string. Returns "ERROR" on
	 *         exception.
	 */
	public String InvokeAPIRequest(String actionname, Map<String, String> params) {
		return InvokeURL("Remote", actionname, params);
	}

	/**
	 * 
	 * @param actionname
	 *            Name of API action being called.
	 * @param kvp
	 *            Array of key value pairs.
	 * @param filepaths
	 *            Array of files to attach to request. Use full file path.
	 * @return API response, usually a JSON formatted string. Returns "ERROR" on
	 *         exception.
	 */
	public String InvokeAPIRequestPost(String actionname, Map<String, String> params) {
		try {
			return InvokePostURL("Remote", actionname, params, new String[0]);
		} catch (IOException e) {
			e.printStackTrace();
			return "ERROR";
		}
	}
	
	/**
	 * 
	 * @param actionname
	 *            Name of API action being called.
	 * @param kvp
	 *            Array of key value pairs.
	 * @param filepaths
	 *            Array of files to attach to request. Use full file path.
	 * @return API response, usually a JSON formatted string. Returns "ERROR" on
	 *         exception.
	 */
	public String InvokeAPIRequestPost(String actionname, Map<String, String> params, String[] filepaths) {
		try {
			return InvokePostURL("Remote", actionname, params, filepaths);
		} catch (IOException e) {
			e.printStackTrace();
			return "ERROR";
		}
	}

	/**
	 * 
	 * @param user			Username to create authentication token for
	 * @param password		Password for supplied username
	 * @param ipAddress		IP address of the user for whom you want to build an authentication token 
	 * @return The authentication token in String form.
	 */
	public String GetRemoteAuthToken(String user, String password, String ipAddress) {
		// NO_WEB_CALLS = false;
		return InvokeURL("Auth", "RemoteLogin", "mluser=" + user + "&mlpass=" + password + "&remoteIP=" + ipAddress);
	}

	/***********************
	 *  MAIN, FOR TESTING  *
	 ***********************/
	public static void main(String[] args) {
		//DEFAULT CREDENTIALS

		String server = "http://leeapi.maplarge.com/";
		String user = "***REMOVED***";
		String pass = "***REMOVED***";
		int token = 123456789;

		Map<String, String> params = new HashMap<String, String>();

		//CREATE MAPLARGE CONNECTION WITH USER / PASSWORD
		MapLargeConnector mlconnPassword = new MapLargeConnector(server, user, pass);

		//CREATE MAPLARGE CONNECTION WITH USER / AUTH TOKEN
		MapLargeConnector mlconnToken = new MapLargeConnector(server, user, token);

		//CREATE TABLE SYNCHRONOUS (NO WEB CALL)
		params.put("account", "test");
		params.put("tablename", "testJavaSdkTable");
		params.put("fileurl", "http://localhost/testfile.csv");
		MapLargeConnector.NO_WEB_CALLS = true;
		String response = mlconnPassword.InvokeAPIRequest("CreateTableSynchronous", params);
		System.out.println(response);
		MapLargeConnector.NO_WEB_CALLS = false;

		//RETRIEVE REMOTE USER AUTH TOKEN
		response = mlconnPassword.GetRemoteAuthToken(user, pass, "255.255.255.255");
		System.out.println(response);

		//LIST GROUPS
		params.clear();
		params.put("account", "test");
		response = mlconnToken.InvokeAPIRequestPost("ListGroups", params);
		System.out.println(response);

		//CREATE TABLE WITH FILES SYNCHRONOUS
		params.clear();
		params.put("account", "test");
		params.put("tablename", "PostedTableImport");
		//response = mlconnToken.InvokeAPIRequestPost("CreateTableWithFilesSynchronous", params,
		//		new String[] { "C:\\Data\\TestFile.csv" });
		//System.out.println(response);
	}
}
