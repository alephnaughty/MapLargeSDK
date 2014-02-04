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

## C#

## PHP
## Java




### Python
```python

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

