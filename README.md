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

## Usage

### C#
``` csharp

```

### PHP

``` php
<?php

include 'MapLargeConnector.php';

//DEFAULT CREDENTIALS
$server = "https://alphaapi.maplarge.com/";
$user = "***REMOVED***";
$pass = "***REMOVED***";
$token = 921129417;

//CREATE MAPLARGE CONNECTION WITH USER / PASSWORD
$mlconnPassword = MapLargeConnector::CreateFromPassword($server, $user, $pass);

//CREATE MAPLARGE CONNECTION WITH USER / AUTH TOKEN
$mlconnToken = MapLargeConnector::CreateFromToken($server, $user, $token);

//CREATE TABLE SYNCHRONOUS (NO WEB CALL)
$paramlist = array(
    'account'  => 'test',
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
    'account'  => 'test',
);
$response = $mlconnToken->InvokeAPIRequestPost("ListGroups", $paramlist);
echo $response . PHP_EOL;

//CREATE TABLE WITH FILES SYNCHRONOUS
$paramlist = array(
    'account'  => 'test',
    'tablename' => 'PostedTableImportPHP',
);
$response = $mlconnToken->InvokeAPIRequestPostWithFiles("CreateTableWithFilesSynchronous", $paramlist, array ( "N:\\MergedPhoenix.csv" ));
echo $response . PHP_EOL;
echo 'DONE' . PHP_EOL;

?>
```

## Java

``` java
import com.maplarge.api.MapLargeConnector;

import java.util.HashMap;
import java.util.Map;

public class MapLargeSDKTest {

    /***********************
     *  MAIN, FOR TESTING  *
     ***********************/
    public static void main(String[] args) {
        //DEFAULT CREDENTIALS
        String server = "http://127.0.0.1/";
        String user = "root@ml.com";
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
        response = mlconnToken.InvokeAPIRequestPost("CreateTableWithFilesSynchronous", params,	new String[] { "C:\\temp\\usa.csv" });
        //System.out.println(response);
    }
}
```

### Python
``` python

ml = MapLargeConnector(server, user, pw)

#ml.InvokeAPIRequest("CreateTableSynchronous", {"account": "aidsvualpha",  "tablename": "testPythonSdkTable", "fileurl": "http://localhost/testfile.csv"})

#print "authtoken: " + ml.GetRemoteAuthToken(user, pw, "255.255.255.255")

#ml.InvokeAPIRequest("CreateTableSynchronous", {"account": "aidsvualpha",  "tablename": "testPythonSdkTable", "fileurl": "http://localhost/testfile.csv"})

#params = {"account": "aidsvualpha"}
#print ml.InvokeAPIRequestPost("ListGroups", params)

#print "authtoken: " + ml.GetRemoteAuthToken(user, pw, "255.255.255.255")

#ml.InvokeAPIRequest("CreateTableSynchronous", {"account": "aidsvualpha",  "tablename": "testPythonSdkTable", "fileurl": "http://localhost/testfile.csv"})

params = {"account": "aidsvualpha"}
response = ml.InvokeAPIRequestPost("ListGroups", params)
print response
print ml.InvokeAPIRequestPost("CreateTableWithFilesSynchronous", {"account": "aidsvualpha",  "tablename": "testPythonSdkTable2"}, ["/temp/usa.csv"])
print ml.InvokeAPIRequestPost("CreateTableWithFilesSynchronous", {"account": "aidsvualpha",  "tablename": "testPythonSdkTable2"}, ["c:\\temp\\usa.csv"])
```


For more samples 
[www.maplarge.com](https://www.maplarge.com)

