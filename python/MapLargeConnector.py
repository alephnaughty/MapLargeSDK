import urllib2
import urllib
import urlparse
import json
import mimetypes
import mimetools


class MapLargeConnector(object):
    ###
    # Creates a connection to a MapLarge API server
    ###

    ###
    # When NO_WEB_CALLS is true all MapLargeConnectors will not make remote
    # calls. Instead, the response will be the full URL that would have been
    # invoked.
    ###
    NO_WEB_CALLS = False

    __user = None
    __token = None
    __apiserver = None
    __authstring = ""
    __password = None
    __defaultHeaders = [('User-agent', 'MapLarge SDK Python')]

    def __init__(self, server, user, passwordOrToken=None):
        """
        Constructor. Creates a connection to a MapLarge API server with a
        username and token as credentials.

        @param server: URL of API server. Must begin with valid protocol
        @param user: Username to use for connection credentials.
        @param passwordOrToken: Authentication token/password to use for connection credentials.
        @raise ValueError:
        """

        try:
            self.__apiserver = server
            self.__user = user

            if isinstance(passwordOrToken, int):
                self.__token = str(passwordOrToken)
            elif isinstance(passwordOrToken, str):
                self.__token = self.__GetToken(passwordOrToken)
            else:
                raise ValueError, "No Password or Token"

            self.__authstring = "mluser=" + self.__user + "&mltoken=" + self.__token;

        except Exception as e:
            raise e

    def __GetToken(self, password):

        try:
            querystring = urllib.urlencode({"mluser": self.__user, "mlpass": password}) + "&" + self.__authstring

            if self.NO_WEB_CALLS:
                retVal = querystring
            else:

                response = self.__InvokeURL("Auth", "Login", querystring)

                respObj = json.loads(response)
                success = respObj['success']

                if success == True:
                    retVal = respObj['token']
                else:
                    retVal = "ERROR"

        except Exception as e:
            print e
            retVal = "ERROR"

        return retVal

    def __InvokeURL(self, controller, actionname, params):

        try:

            urlstring = urlparse.urljoin(self.__apiserver, controller + "/" + actionname)
            urlstring = urlstring + '?' + params

            if self.NO_WEB_CALLS:
                retVal = urlstring
            else:
                flob = urllib2.urlopen(urllib2.Request(urlstring))
                retVal = flob.read()

        except Exception as e:
            print e
            retVal = "ERROR"

        return retVal

    def __InvokePostURL(self, controller, actionname, params, filepaths):

        try:
            urlstring = urlparse.urljoin(self.__apiserver, controller + "/" + actionname)

            if self.NO_WEB_CALLS:
                retVal = urlstring
            else:

                part_boundary = '--' + mimetools.choose_boundary()
                CRLF = '\r\n'

                L = []
                for (key, val) in params.items():
                    L.append('--' + part_boundary)
                    L.append('Content-Disposition: form-data; name="%s"' % key)
                    L.append('')
                    L.append(val)

                files = []
                for fileName in filepaths:
                    fileBody = open(fileName, "rb").read()

                    L.append('--' + part_boundary)
                    L.append('Content-Disposition: form-data; name="%s"; filename="%s"' % ('fileUpload', fileName))
                    fileContentType = mimetypes.guess_type(fileName)[0] or 'application/octet-stream'
                    L.append('Content-Type: %s' % fileContentType)
                    L.append('')
                    L.append(fileBody)

                L.append('--' + part_boundary + '--')
                L.append('')

                #files = {'file': {'filename': 'F.DAT', 'content': 'DATA HERE'}}

                postData = body = CRLF.join(L)

                request = urllib2.Request(urlstring)

                for name, value in self.__defaultHeaders:
                    request.add_header(name, value)
                    # conn.ContentType = "multipart/form-data; boundary=" + boundary;
                request.add_header('Content-type', 'multipart/form-data; boundary=%s' % part_boundary)
                request.add_header('Content-length', len(postData))
                request.add_data(postData)

                resp = urllib2.urlopen(request)
                retVal = resp.read()

        except Exception as e:
            print e
            retVal = "ERROR"

        return retVal

    def InvokeAPIRequestPost(self, action, params, filepaths=None):

        """
        @param action:  Name of API action being called.
        @param params:  dict of key value pairs.
        @param filepaths: List of filename(s) to upload. Do not pass of not required.
        @return :  API response, usually a JSON formatted string. Returns "ERROR" on exception.

        """

        try:

            params["mluser"] = self.__user;
            params["mltoken"] = self.__token;

            if (filepaths == None):
                retval = self.__InvokePostURL("Remote", action, params, [])
            else:
                retval = self.__InvokePostURL("Remote", action, params, filepaths)

        except Exception as e:
            print e
            retval = "ERROR"

        return retval


    def InvokeAPIRequest(self, action, params, filepaths=None):
        """
        @param action:  Name of API action being called.
        @param params:  dict of key value pairs.
        @return :  API response, usually a JSON formatted string. Returns "ERROR" on exception.

        """
        try:

            querystring = urllib.urlencode(params) + "&" + self.__authstring
            retVal = self.__InvokeURL("Remote", action, querystring)

        except Exception as e:
            print e
            retVal = "ERROR"

        return retVal

    def GetRemoteAuthToken(self, user, password, ipaddress):
        """

        @param user:  Username to create authentication token for
        @param password: Password for supplied username
        @param ipaddress: IP address of the user for whom you want to build an authentication token
        @return: The authentication token in String form.
        """

        try:
            retVal = self.__InvokeURL("Auth", "RemoteLogin",
                                      "mluser=" + user + "&mlpass=" + password + "&remoteIP=" + ipaddress);
        except Exception as e:
            print e
            retVal = "ERROR"

        return retVal


##DEFAULT CREDENTIALS

server = "http://server.maplarge.com/"
user = "user@ml.com"
pw = "pw123456"
token = 123456789

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
