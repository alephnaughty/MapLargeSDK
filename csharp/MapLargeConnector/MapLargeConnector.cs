using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net;
using System.Web;

namespace com.maplarge.api {

	public class MapLargeConnector {

		/**
		 * When NO_WEB_CALLS is true all MapLargeConnectors will not make remote
		 * calls. Instead, the response will be the full URL that would have been
		 * invoked.
		 */
		public static bool NO_WEB_CALLS = false;

		public const int Version = 2;

		private string _user;
		private string _token;
		private string _apiserver;
		private string _authstring;

		private const string LINE_FEED = "\r\n";

		private string GetToken(string pass) {
			NO_WEB_CALLS = false;
			string s = InvokeURL("Auth", "Login", "mluser=" + this._user + "&mlpass=" + pass);
			s = s.Substring(1, s.Length - 1);
			string[] vals = s.Split(',');
			bool success = false;
			string rettoken = "";
			for (int i = 0; i < vals.Length; i++) {
				if (vals[i].StartsWith("\"token\""))
					rettoken = vals[i].Split(':')[1].Replace("\"", "");
				if (vals[i].Equals("\"success\":true"))
					success = true;
			}
			return success ? rettoken : "";
		}

		private string InvokeURL(string controller, string actionname, Dictionary<string, string> paramlist) {
			string querystring = this._authstring;
			foreach (var kvp in paramlist) querystring += "&" + kvp.Key + "=" + kvp.Value;
			string query = string.Join("&", paramlist.Select(x => string.Format("{0}={1}", x.Key, x.Value)));
			return this.InvokeURL(controller, actionname, querystring);
		}

		private string InvokeURL(string controller, string actionname, string querystring) {
			if (NO_WEB_CALLS) {
				return this._apiserver + controller + "/" + actionname + "?" + querystring;
			} else {
				try {
					Uri url = new Uri(this._apiserver + controller + "/" + actionname + "?" + querystring);
					WebClient wc = new WebClient();
					string s = wc.DownloadString(url);
					//URLConnection conn = url.openConnection();
					//BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					//string inputLine;
					//string s = "";
					//while ((inputLine = br.readLine()) != null) s += inputLine;
					//br.close();
					return s;
				} catch (UriFormatException ufe) {
					Console.WriteLine(ufe.Message);
					Console.WriteLine(ufe.StackTrace);
				} catch (IOException ioe) {
					Console.WriteLine(ioe.Message);
					Console.WriteLine(ioe.StackTrace);
				} catch (Exception e) {
					Console.WriteLine(e.Message);
					Console.WriteLine(e.StackTrace);
				}

				return "ERROR"; // Temporary Error Code
			}
		}

		private string InvokePostURL(string controller, string actionname, Dictionary<string, string> paramlist, string[] filepaths) {
			if (NO_WEB_CALLS) return this._apiserver + controller + "/" + actionname;
			string charset = "UTF-8";
			string boundary = "===" + DateTime.Now.Ticks.ToString("X") + "===";

			Uri url = new Uri(this._apiserver + controller + "/" + actionname);
			HttpWebRequest conn = HttpWebRequest.CreateHttp(url);
			
			conn.Timeout = int.MaxValue;
			conn.Method = "POST";
			conn.ContentType = "multipart/form-data; boundary=" + boundary;
			conn.UserAgent = "MapLarge SDK C#";
			
			

			using (StreamWriter writer = new StreamWriter(conn.GetRequestStream())) {
				// HEADERS
				writer.Write("User-Agent: MapLarge SDK C#");
				writer.Write(LINE_FEED);

				// FORM FIELDS
				paramlist["mluser"] = this._user;
				paramlist["mltoken"] = this._token;
				foreach (KeyValuePair<string, string> kvp in paramlist) {
					writer.Write("--" + boundary);
					writer.Write(LINE_FEED);
					writer.Write("Content-Disposition: form-data; name=\"" + kvp.Key + "\"");
					writer.Write(LINE_FEED);
					writer.Write(LINE_FEED);
					writer.Write(kvp.Value);
					writer.Write(LINE_FEED);
				}

				// FILES
				string fname;
				foreach (string path in filepaths) {
					if (!File.Exists(path)) {
						continue; // MISSING FILE
					}
					fname = Path.GetFileName(path);
					writer.Write("--" + boundary);
					writer.Write(LINE_FEED);
					writer.Write("Content-Disposition: form-data; name=\"fileUpload\"; filename=\"" + fname + "\"");
					writer.Write(LINE_FEED);
					//writer.Write("Content-Type: " +  URLConnection.guessContentTypeFromName(fname));
					//writer.Write(LINE_FEED);
					writer.Write("Content-Transfer-Encoding: binary");
					writer.Write(LINE_FEED);
					writer.Write(LINE_FEED);
					writer.Flush();
					using (FileStream inputStream = new FileStream(path, FileMode.Open, FileAccess.Read)) {
						byte[] buffer = new byte[4096];
						int bytesRead = -1;
						while ((bytesRead = inputStream.Read(buffer, 0, 4096)) > 0) {
							writer.BaseStream.Write(buffer, 0, bytesRead);
						}
					}
					writer.Write(LINE_FEED);
				}
				// FINISH
				writer.Write("--" + boundary + "--");
				writer.Write(LINE_FEED);
			}
			// READ RESPONSE 
			string s = "";
			using (WebResponse response = conn.GetResponse()) {
				using (var reqstream = response.GetResponseStream())
				using (StreamReader r = new StreamReader(reqstream)) {
					string inputLine;
					while ((inputLine = r.ReadLine()) != null) {
						s += inputLine;
					}
				}
			}
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
		public MapLargeConnector(string urlApiServer, string username, int token) {
			this._apiserver = urlApiServer;
			if (!this._apiserver.EndsWith("/"))
				this._apiserver += '/';
			this._user = username;
			this._token = token.ToString();
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
		public MapLargeConnector(string urlApiServer, string username, string password) {
			this._apiserver = urlApiServer;
			if (!this._apiserver.EndsWith("/"))
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
		 * @param paramlist
		 *            Array of key value pairs.
		 * @return API response, usually a JSON formatted string. Returns "ERROR" on
		 *         exception.
		 */
		public string InvokeAPIRequest(string actionname, Dictionary<string, string> paramlist) {
			return InvokeURL("Remote", actionname, paramlist);
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
		public string InvokeAPIRequestPost(string actionname, Dictionary<string, string> paramlist) {
			try {
				return InvokePostURL("Remote", actionname, paramlist, new string[0]);
			} catch (IOException ioe) {
				Console.WriteLine(ioe.Message);
				Console.WriteLine(ioe.StackTrace);
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
		public string InvokeAPIRequestPost(string actionname, Dictionary<string, string> paramlist, string[] filepaths) {
			try {
				return InvokePostURL("Remote", actionname, paramlist, filepaths);
			} catch (IOException ioe) {
				Console.WriteLine(ioe.Message);
				Console.WriteLine(ioe.StackTrace);
				return "ERROR";
			}
		}

		/**
		 * 
		 * @param user			Username to create authentication token for
		 * @param password		Password for supplied username
		 * @param ipAddress		IP address of the user for whom you want to build an authentication token 
		 * @return The authentication token in string form.
		 */
		public string GetRemoteAuthToken(string user, string password, string ipAddress) {
			// NO_WEB_CALLS = false;
			return InvokeURL("Auth", "RemoteLogin", "mluser=" + user + "&mlpass=" + password + "&remoteIP=" + ipAddress);
		}

		
	}
}
