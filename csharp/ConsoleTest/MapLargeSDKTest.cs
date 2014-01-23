using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using com.maplarge.api;

namespace ConsoleTest {
	class MapLargeSDKTest {
		static void Main(string[] args) {
			
			//DEFAULT CREDENTIALS
			string server = "https://test.maplarge.com/";
			string user = "username@domain.com";
			string pass = "password";
			int token = 123456789;

			Dictionary<string, string> paramlist = new Dictionary<string, string>();

			//CREATE MAPLARGE CONNECTION WITH USER / PASSWORD
			MapLargeConnector mlconnPassword = new MapLargeConnector(server, user, pass);

			//CREATE MAPLARGE CONNECTION WITH USER / AUTH TOKEN
			MapLargeConnector mlconnToken = new MapLargeConnector(server, user, token);

			//CREATE TABLE SYNCHRONOUS (NO WEB CALL)
			paramlist.Add("account", "test");
			paramlist.Add("tablename", "testJavaSdkTable");
			paramlist.Add("fileurl", "http://www.domain.com/testfile.csv");
			MapLargeConnector.NO_WEB_CALLS = true;
			string response = mlconnPassword.InvokeAPIRequest("CreateTableSynchronous", paramlist);
			Console.WriteLine(response);
			MapLargeConnector.NO_WEB_CALLS = false;

			//RETRIEVE REMOTE USER AUTH TOKEN 
			response = mlconnPassword.GetRemoteAuthToken(user, pass, "255.255.255.255");
			Console.WriteLine(response);

			//LIST GROUPS
			paramlist.Clear();
			paramlist.Add("account", "test");
			response = mlconnToken.InvokeAPIRequestPost("ListGroups", paramlist);
			Console.WriteLine(response);

			//CREATE TABLE WITH FILES SYNCHRONOUS
			paramlist.Clear();
			paramlist.Add("account", "test");
			paramlist.Add("tablename", "PostedTableImport");
			response = mlconnToken.InvokeAPIRequestPost("CreateTableWithFilesSynchronous", paramlist,
					new string[] { "C:\\Data\\TestFile.csv" });
			Console.WriteLine(response);
		}
	}
}
