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
