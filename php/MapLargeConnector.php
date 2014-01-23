<?php

class MapLargeConnector
{
    /**
     * When NO_WEB_CALLS is true all MapLargeConnectors will not make remote
     * calls. Instead, the response will be the full URL that would have been
     * invoked.
     */
    static public $NO_WEB_CALLS = false;

    private $_user;
    private $_token;
    private $_apiserver;
    private $_authstring;

    const VERSION = 2;

    public static function CreateFromToken($urlApiServer, $username, $token)
    {
        $inst = new self();
        $inst->_apiserver = $urlApiServer;
        if (!(substr($inst->_apiserver, -1) === '/'))
            $inst->_apiserver = $inst->_apiserver . '/';
        $inst->_user = $username;
        $inst->_token = $token;
        $inst->_authstring = 'mluser=' . $inst->_user . '&mltoken=' . $inst->_token;
        return $inst;
    }


    public static function CreateFromPassword($urlApiServer, $username, $password)
    {
        $inst = new self();
        $inst->_apiserver = $urlApiServer;
        if (!(substr($inst->_apiserver, -1) === '/'))
            $inst->_apiserver = $inst->_apiserver . '/';
        $inst->_user = $username;
        $inst->_token = $inst->GetToken($password);
        $inst->_authstring = 'mluser=' . $inst->_user . '&mltoken=' . $inst->_token;
        return $inst;
    }

    private function GetToken($pass)
    {
        $state = MapLargeConnector::$NO_WEB_CALLS;
        MapLargeConnector::$NO_WEB_CALLS = false;
        $s = $this->InvokeURLQueryString('Auth', 'Login', 'mluser=' . $this->_user . '&mlpass=' . $pass);
        $s = substr($s, 1, strlen($s) - 1);
        $vals = explode(",", $s);
        $success = false;
        $rettoken = "";
        for ($i = 0; $i < count($vals); $i++) {
            if (substr($vals[$i], 0, 7) == '"token"') {
                $part = explode(':', $vals[$i]);
                $rettoken = str_replace('"', '', $part[1]);
            }
            if ($vals[$i] == '"success":true')
                $success = true;
        }
        MapLargeConnector::$NO_WEB_CALLS = $state;
        return $success ? $rettoken : '';
    }

    private function InvokeURL($controller, $actionname, $paramlist)
    {
        $querystring = $this->_authstring;
        foreach ($paramlist as $k => $v) {
            $querystring = $querystring . '&' . urlencode($k) . '=' . urlencode($v);
        }
        return $this->InvokeURLQueryString($controller, $actionname, $querystring);
    }

    private function InvokeURLQueryString($controller, $actionname, $querystring)
    {
        if (MapLargeConnector::$NO_WEB_CALLS) return $this->_apiserver . $controller . '/' . $actionname . '?' . $querystring;
        $url = $this->_apiserver . $controller . '/' . $actionname . '?' . $querystring;
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, trim($url));
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
        curl_setopt($ch, CURLOPT_POST, false);
        curl_setopt($ch, CURLOPT_USERAGENT, "MapLarge SDK PHP");
        $response = curl_exec($ch);
        echo curl_error($ch);
        curl_close($ch);
        return $response;
    }

    private function InvokePostURL($controller, $actionname, $paramlist, $filepaths)
    {
        if (MapLargeConnector::$NO_WEB_CALLS) return $this->_apiserver . $controller . "/" . $actionname;
        $url = $this->_apiserver . $controller . "/" . $actionname;
        $paramlist['mluser'] = $this->_user;
        $paramlist['mltoken'] = $this->_token;
        $i = 0;
        foreach ($filepaths as $k => $v) {
            if (file_exists($v)) {
                $paramlist['file_' . $i++] = '@' . realpath($v);
            }
        }
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, trim($url));
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_POSTFIELDS, $paramlist);
        curl_setopt($ch, CURLOPT_USERAGENT, "MapLarge SDK PHP");
        $response = curl_exec($ch);
        echo curl_error($ch);
        curl_close($ch);
        return $response;
    }

    public function InvokeAPIRequest($actionname, $paramlist)
    {
        return $this->InvokeURL("Remote", $actionname, $paramlist);
    }

    public function InvokeAPIRequestPost($actionname, $paramlist)
    {
        try {
            return $this->InvokePostURL("Remote", $actionname, $paramlist, array());
        } catch (Exception $ioe) {
            Console . WriteLine(ioe . Message);
            Console . WriteLine(ioe . StackTrace);
            return "ERROR";
        }
    }

    public function InvokeAPIRequestPostWithFiles($actionname, $paramlist, $filepaths)
    {
        try {
            return $this->InvokePostURL("Remote", $actionname, $paramlist, $filepaths);
        } catch (Exception $ioe) {
            Console . WriteLine(ioe . Message);
            Console . WriteLine(ioe . StackTrace);
            return "ERROR";
        }
    }

    public function GetRemoteAuthToken($user, $password, $ipAddress)
    {
        return $this->InvokeURLQueryString("Auth", "RemoteLogin", "mluser=" . $user . "&mlpass=" . $password . "&remoteIP=" . $ipAddress);
    }
}


//DEFAULT CREDENTIALS
$server = "https://leeapi.maplarge.com/";
$user = "user@ml.com";
$pass = "password";
$token = 921129417;

//CREATE MAPLARGE CONNECTION WITH USER / PASSWORD
$mlconnPassword = MapLargeConnector::CreateFromPassword($server, $user, $pass);

//CREATE MAPLARGE CONNECTION WITH USER / AUTH TOKEN
$mlconnToken = MapLargeConnector::CreateFromToken($server, $user, $token);

//CREATE TABLE SYNCHRONOUS (NO WEB CALL)
$paramlist = array(
    'account' => 'test',
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

?>