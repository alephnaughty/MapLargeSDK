# MapLarge SDK Overview

# MapLargeConnector

MapLargeConnecor exists in each API and is used to establish and manage a session with the Server. This class exposes several convenience methods for accessing functionality on a MapLarge server.

##InvokeAPIRequest
Used to Invoke simpler API requests that can be handled sufficiently by GET requests. This would be for relatively short data, such as Account or Table Listings. Files can be uploaded, but only in the limited case of passing files by URI. Uploading of file data muse use the POST method below.

##InvokeAPIRequestPost
Performs the same API Requests as the first method, but can handle sending and recieveing of large amounts of data as HTTP POST is used. This also allows for full featured Binary file uploads directly from your client application.

##GetRemoteAuthToken
A Convenience method to allow retrieval of an Auth Token -- a common use case amongst many of our clients. 
Languages Implentation Notes

## Example Usage

### C#: 

``` csharp

//DEFAULT CREDENTIALS
string server = "http://server.maplarge.com/";
string user = "user@ml.com";
string pass = "pw123456";
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
			
		
```

### PHP


``` php


//DEFAULT CREDENTIALS
$server = "http://server.maplarge.com/";
$user = "user@ml.com";
$pass = "pw123456";
$token = 921129417;

//CREATE MAPLARGE CONNECTION WITH USER / PASSWORD
$mlconnPassword = MapLargeConnector::CreateFromPassword($server, $user, $pass);

//CREATE MAPLARGE CONNECTION WITH USER / AUTH TOKEN
$mlconnToken = MapLargeConnector::CreateFromToken($server, $user, $token);

//CREATE TABLE SYNCHRONOUS (NO WEB CALL)
$paramlist = array(
    'account' => 'aidsvualpha',
    'tablename' => 'testPHPSDKTable',
    'fileurl' => 'http://www.domain.com/testfile.csv'
);
MapLargeConnector::$NO_WEB_CALLS = true;
$response = $mlconnPassword->InvokeAPIRequest("CreateTableSynchronous", $paramlist);
echo $response . PHP_EOL;
MapLargeConnector::$NO_WEB_CALLS = false;

//RETRIEVE REMOTE USER AUTH TOKEN
$response = $mlconnPassword->GetRemoteAuthToken($user, $pass, "255.255.255.255");
echo $response . PHP_EOL;

//LIST GROUPS
$paramlist = array(
    'account' => 'test',
);
$response = $mlconnToken->InvokeAPIRequestPost("ListGroups", $paramlist);
echo $response . PHP_EOL;

//CREATE TABLE WITH FILES SYNCHRONOUS
$paramlist = array(
    'account' => 'test',
    'tablename' => 'PostedTableImportPHP',
);
$response = $mlconnToken->InvokeAPIRequestPostWithFiles("CreateTableWithFilesSynchronous", $paramlist, array("N:\\MergedPhoenix.csv"));
echo $response . PHP_EOL;
echo 'DONE' . PHP_EOL;
```

## Java

``` java

//DEFAULT CREDENTIALS
String server = "http://server.maplarge.com/";
String user = "user@ml.com";
String pass = "pw123456";
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
response = mlconnToken.InvokeAPIRequestPost("CreateTableWithFilesSynchronous", params,	new String[] { "C:\\temp\\usa.csv" });
System.out.println(response);

```

### Python

``` python

server = "http://server.maplarge.com/"
user = "user@ml.com"
pw = "pw123456"

#CREATE MAPLARGE CONNECTION WITH USER / PASSWORD
mlconnPassword = MapLargeConnector(server, user, pw)

#CREATE MAPLARGE CONNECTION WITH USER / AUTH TOKEN
mlconnToken = MapLargeConnector(server, user, token)

#CREATE TABLE SYNCHRONOUS (NO WEB CALL)
params = {"account": "aidsvualpha", "tablename": "testPythonSdkTable", "fileurl": "http://localhost/testfile.csv"}
mlconnToken.NO_WEB_CALLS = True
response = mlconnToken.InvokeAPIRequest("CreateTableSynchronous", params)
print response

mlconnPassword.NO_WEB_CALLS = False

#RETRIEVE REMOTE USER AUTH TOKEN
response = mlconnPassword.GetRemoteAuthToken(user, pw, "255.255.255.255")
print response

#List Groups
params = {"account": "aidsvualpha"}
response = mlconnPassword.InvokeAPIRequestPost("ListGroups", params)
print response

#CREATE TABLE WITH FILES SYNCHRONOUS
params = {"account": "aidsvualpha", "tablename": "testTable"}
fileList = ["c:\\temp\\usa.csv"]
print mlconnPassword.InvokeAPIRequestPost("CreateTableWithFilesSynchronous", params, fileList)

```


For more samples 
[www.maplarge.com](https://www.maplarge.com)

