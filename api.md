# Remote Table API

**Overview**

The MapLarge Name-Value Pair API allows a user to remotely make changes to their account, groups, users, table resources, or permissions.  All API requests are sent using GET parameters through the URL, and all responses will return a JSON formatted string, optionally wrapped in a JSONP style callback function.  Typically, users of this api are using it for server to server communication but it can also be used to create client side UI components like those described in the account system above. 	

## Making Requests

Requests URLs are determined by the action you want to invoke.

EX: Create Table invocation:[ http://alphaapi.maplarge.com](http://alphaapi.maplarge.com/Remote/CreateTable?GET-VARIABLES)**[/Remote/CreateTabl**e](http://alphaapi.maplarge.com/Remote/CreateTable?GET-VARIABLES)[?GET-VARIABLES](http://alphaapi.maplarge.com/Remote/CreateTable?GET-VARIABLES)

<table>
  <tr>
    <td>Denotes required field</td>
    <td></td>
  </tr>
</table>


Every request sent to the API is required to have three parameters for authentication purposes.  There is also an optional callback parameter that will signal to the API to return in JSONP.

**Required Request Parameters**

<table>
  <tr>
    <td>mluser</td>
    <td>string</td>
    <td>User name to sign in with.</td>
  </tr>
  <tr>
    <td>mlpass  -or-
mltoken</td>
    <td>string</td>
    <td>Either the password for the mluser account or the MapLarge generated auth token for the client profile.</td>
  </tr>
  <tr>
    <td>callback</td>
    <td>string</td>
    <td>The name of the function in which to wrap the JSON return data.</td>
  </tr>
  <tr>
    <td>customid</td>
    <td>string</td>
    <td>A custom id provided by the calling script that will be returned in the response.</td>
  </tr>
</table>


Responses will be returned as a JSON formatted string, with an optional callback wrapper for JSONP requests.

**Response Values**

<table>
  <tr>
    <td>success</td>
    <td>true/false</td>
    <td>Whether this action was successful in its task.</td>
  </tr>
  <tr>
    <td>id</td>
    <td>string</td>
    <td>A unique 128-bit hexadecimal value that identifies this action.  Length 32.</td>
  </tr>
  <tr>
    <td>errors</td>
    <td>string[]</td>
    <td>A list of any errors encountered in the execution of this action.</td>
  </tr>
  <tr>
    <td>timestamp</td>
    <td>int</td>
    <td>Integer "linux-style" timestamp.  Time is in UTC.</td>
  </tr>
  <tr>
    <td>[extra data]</td>
    <td></td>
    <td>Any additional action specific values.  Defined per-action below.</td>
  </tr>
</table>


<table>
  <tr>
    <td>Note: Any errors in one part of an API call will cause the action to fail in its entirety, and no changes will be made.</td>
  </tr>
</table>


## Table Actions

<table>
  <tr>
    <td>CreateTable	</td>
  </tr>
</table>


**Request Parameters**

<table>
  <tr>
    <td>account</td>
    <td>string</td>
    <td>The account code for the Account that will own this table</td>
  </tr>
  <tr>
    <td>tablename</td>
    <td>string</td>
    <td>Name to use for table, without account code prefix.  Must be alpha-numeric.</td>
  </tr>
  <tr>
    <td>fileurl</td>
    <td>string[]</td>
    <td>A comma separated list of file URIs to download.</td>
  </tr>
  <tr>
    <td>replyto</td>
    <td>string</td>
    <td>If present, server will reply to this address when import completes.</td>
  </tr>
  <tr>
    <td>authurl</td>
    <td>string</td>
    <td>For use when authentication is needed before files are downloaded.</td>
  </tr>
  <tr>
    <td>authpostdata</td>
    <td>string</td>
    <td>For use when authentication is needed before files are downloaded.</td>
  </tr>
</table>


**Action Specific Response Values**

*None initially*

**Action Specific Response Values using replyto**

<table>
  <tr>
    <td>table</td>
    <td>string</td>
    <td>The full table name (ID) of the newly created table.</td>
  </tr>
</table>


<table>
  <tr>
    <td>Note: This is an asynchronous operation.  When the table import has been completed a HTTP request to the address provided in the "replyto" optional parameter will be sent, if present.  The API response is returned immediately, and signifies that the request has been received and is currently processing.</td>
  </tr>
</table>


<table>
  <tr>
    <td>CreateTableSynchronous	</td>
  </tr>
</table>


**Request Parameters**

<table>
  <tr>
    <td>account</td>
    <td>string</td>
    <td>The account code for the Account that will own this table</td>
  </tr>
  <tr>
    <td>tablename</td>
    <td>string</td>
    <td>Name to use for table, without account code prefix.  Must be alpha-numeric.</td>
  </tr>
  <tr>
    <td>fileurl</td>
    <td>string[]</td>
    <td>A comma separated list of file URIs to download.</td>
  </tr>
  <tr>
    <td>authurl</td>
    <td>string</td>
    <td>For use when authentication is needed before files are downloaded.</td>
  </tr>
  <tr>
    <td>authpostdata</td>
    <td>string</td>
    <td>For use when authentication is needed before files are downloaded.</td>
  </tr>
</table>


**Action Specific Response Values**

<table>
  <tr>
    <td>table</td>
    <td>string</td>
    <td>The full table name (ID) of the newly created table.</td>
  </tr>
</table>


<table>
  <tr>
    <td>Note: This is a synchronous operation.  The request to the API will remain open while the table datafiles are downloaded and imported.  </td>
  </tr>
</table>


<table>
  <tr>
    <td>CreateTableWithFilesSynchronous</td>
  </tr>
</table>


**Request Parameters**

<table>
  <tr>
    <td>account</td>
    <td>string</td>
    <td>The account code for the Account that will own this table</td>
  </tr>
  <tr>
    <td>tablename</td>
    <td>string</td>
    <td>Name to use for table, without account code prefix.  Must be alpha-numeric.</td>
  </tr>
</table>


**Action Specific Response Values**

<table>
  <tr>
    <td>table</td>
    <td>string</td>
    <td>The full table name (ID) of the newly created table.</td>
  </tr>
</table>


<table>
  <tr>
    <td>Note: This is a synchronous operation.  The request to the API will remain open while the table datafiles are downloaded and imported.  This operation also supports zipped packages.</td>
  </tr>
</table>


<table>
  <tr>
    <td>DeleteTable</td>
  </tr>
</table>


**Request Parameters**

<table>
  <tr>
    <td>tablename</td>
    <td>string</td>
    <td>Full table ID to delete in the form "account/table/version"</td>
  </tr>
</table>


**Action Specific Response Values**

*None*

<table>
  <tr>
    <td>DeleteAllVersions</td>
  </tr>
</table>


**Request Parameters**

<table>
  <tr>
    <td>tablename</td>
    <td>string</td>
    <td>Either a full table ID or active table name to delete.</td>
  </tr>
</table>


**Action Specific Response Values**

*None*

<table>
  <tr>
    <td>ExportTable</td>
  </tr>
</table>


**Request Parameters**

<table>
  <tr>
    <td>tablename</td>
    <td>string</td>
    <td>Active table name to get the full ID for.  This is the active ID "account/table"</td>
  </tr>
  <tr>
    <td>replyto</td>
    <td>string</td>
    <td>URL of a page to send a reply to when the import completes.</td>
  </tr>
</table>


<table>
  <tr>
    <td>Note: ExportTable action is not currently supported.</td>
  </tr>
</table>


<table>
  <tr>
    <td>GetActiveTableID</td>
  </tr>
</table>


**Request Parameters**

<table>
  <tr>
    <td>tablename</td>
    <td>string</td>
    <td>Active table name to get the full ID for.  This is in the form "account/table"</td>
  </tr>
</table>


**Action Specific Response Values**

<table>
  <tr>
    <td>table</td>
    <td>string</td>
    <td>A full table ID in the form "account/table/version"</td>
  </tr>
</table>


<table>
  <tr>
    <td>GetTableVersions</td>
  </tr>
</table>


**Request Parameters**

<table>
  <tr>
    <td>tablename</td>
    <td>string</td>
    <td>Active table name to get the full ID for.  This is the active ID "account/table"</td>
  </tr>
</table>


**Action Specific Response Values**

<table>
  <tr>
    <td>versions</td>
    <td>mixed[]</td>
    <td>A list of table information for all versions of the given table.  Contains keys id (long), acctcode (string), name (string), version (long), description (string), created (int), inram (true/false),  columns (mixed[] [ id (long), type (string)]).</td>
  </tr>
</table>


<table>
  <tr>
    <td>GetAllTables</td>
  </tr>
</table>


**Request Parameters**

<table>
  <tr>
    <td>account</td>
    <td>string</td>
    <td>If present, response value will only contain tables from this account.</td>
  </tr>
  <tr>
    <td>verbose</td>
    <td>true/false</td>
    <td>Whether to use the long format OO keys for the response.  Defaults to false if not present.</td>
  </tr>
</table>


**Action Specific Response Values**

<table>
  <tr>
    <td>tables</td>
    <td>mixed[]</td>
    <td>A list of all tables in accounts with valid permissions, and all public tables.  The response can be in one of two formats:

Verbose:
id (string), acctcode (string), name (string), version (long), created (int), isactive (true/false), inram (true/false), columns (mixed[] [ id (long), type (string), created (long)]).

Short:
id (int), isactive (true/false), columns (mixed[] [ names (comma delimited string of column names), types (comma delimited string of column names)]).</td>
  </tr>
</table>


<table>
  <tr>
    <td>Note: The verbose list can potentially generate long wait times and a large amount of response data, depending on how many tables the user has access to.  Verbose mode is not advised for time sensitive operations.</td>
  </tr>
</table>


<table>
  <tr>
    <td>GetActiveTables</td>
  </tr>
</table>


**Request Parameters**

<table>
  <tr>
    <td>account</td>
    <td>string</td>
    <td>If present, response value will only contain tables from this account.</td>
  </tr>
  <tr>
    <td>verbose</td>
    <td>true/false</td>
    <td>Whether to use the long format OO keys for the response.  Defaults to false.</td>
  </tr>
</table>


**Action Specific Response Values**

<table>
  <tr>
    <td>tables</td>
    <td>mixed[]</td>
    <td>A list of all active tables in accounts with valid permissions, and all public tables.  The response can be in one of two formats:

Verbose:
id (string), acctcode (string), name (string), version (long), created (int), inram (true/false), columns (mixed[] [ id (long), type (string), created (long)]).

Short:
id (int), columns (mixed[] [ names (comma delimited string of column names), types (comma delimited string of column names)]).</td>
  </tr>
</table>


<table>
  <tr>
    <td>Note: The verbose list can potentially generate long wait times and a large amount of response data, depending on how many tables the user has access to.  Verbose mode is not advised for time sensitive operations.</td>
  </tr>
</table>


<table>
  <tr>
    <td>SetActiveTable</td>
  </tr>
</table>


**Request Parameters**

<table>
  <tr>
    <td>tablename</td>
    <td>string</td>
    <td>The full table ID "account/table/version" to make the active version.</td>
  </tr>
</table>


**Action Specific Response Values**

*None*

## Automated Table Import Actions

<table>
  <tr>
    <td>CreateAutoImport</td>
  </tr>
</table>


**Request Parameters**

<table>
  <tr>
    <td>account</td>
    <td>string</td>
    <td>The account code for the Account that will own tables created by this import.</td>
  </tr>
  <tr>
    <td>importname</td>
    <td>string</td>
    <td>The name to assign to this automated import listing.  Alpha-numeric.</td>
  </tr>
  <tr>
    <td>tablename</td>
    <td>string</td>
    <td>Table name to give to tables imported with this listing.  Alpha-numeric.</td>
  </tr>
  <tr>
    <td>updatefrequency</td>
    <td>int</td>
    <td>The frequency to run auto import for this table.  Value is in minutes, and must be a minimum of 5.</td>
  </tr>
  <tr>
    <td>remoteurl</td>
    <td>string</td>
    <td>A file URI to download and process.</td>
  </tr>
  <tr>
    <td>description</td>
    <td>string</td>
    <td>Text description of this automated import listing.</td>
  </tr>
  <tr>
    <td>versionstokeep</td>
    <td>int</td>
    <td>The number of previous versions of the table to keep.  Defaults to keeping all.</td>
  </tr>
  <tr>
    <td>authurl</td>
    <td>string</td>
    <td>URL to send authentication requests to before downloading files from remoterurl.</td>
  </tr>
  <tr>
    <td>authpostdata</td>
    <td>string</td>
    <td>Data that will be sent with the authentication request.  Data will be sent as a POST request.</td>
  </tr>
</table>


**Action Specific Response Values**

<table>
  <tr>
    <td>autoimportid</td>
    <td>long</td>
    <td>The ID of the newly created autoimport item.</td>
  </tr>
</table>


<table>
  <tr>
    <td>EditAutoImport</td>
  </tr>
</table>


**Request Parameters**

<table>
  <tr>
    <td>id</td>
    <td>string</td>
    <td>The ID for the autoimport to edit.</td>
  </tr>
  <tr>
    <td>importname</td>
    <td>string</td>
    <td>The name to assign to this automated import listing.  Alpha-numeric.</td>
  </tr>
  <tr>
    <td>tablename</td>
    <td>string</td>
    <td>Table name to give to tables imported with this listing.  Alpha-numeric.</td>
  </tr>
  <tr>
    <td>updatefrequency</td>
    <td>int</td>
    <td>The frequency to run auto import for this table.  Value is in minutes, and must be a minimum of 5.</td>
  </tr>
  <tr>
    <td>remoteurl</td>
    <td>string[]</td>
    <td>A list of file URLs to download and process.</td>
  </tr>
  <tr>
    <td>description</td>
    <td>string</td>
    <td>Text description of this automated import listing.</td>
  </tr>
  <tr>
    <td>versionstokeep</td>
    <td>int</td>
    <td>The number of previous versions of the table to keep.  Defaults to keeping all.</td>
  </tr>
  <tr>
    <td>authurl</td>
    <td>string</td>
    <td>URL to send authentication requests to before downloading files from remoterurl.</td>
  </tr>
  <tr>
    <td>authpostdata</td>
    <td>string</td>
    <td>Data that will be sent with the authentication request.  Data will be sent as a POST request.</td>
  </tr>
</table>


**Action Specific Response Values**

<table>
  <tr>
    <td>autoimportid</td>
    <td>long</td>
    <td>The ID of the modified autoimport item.</td>
  </tr>
</table>


<table>
  <tr>
    <td>DeleteAutoImport</td>
  </tr>
</table>


**Request Parameters**

<table>
  <tr>
    <td>id</td>
    <td>long</td>
    <td>The ID of the auto import item to delete.  This will not remove any tables created through this autoimport.</td>
  </tr>
</table>


**Action Specific Response Values**

<table>
  <tr>
    <td>autoimportid</td>
    <td>long</td>
    <td>The ID of the deleted autoimport item.</td>
  </tr>
</table>


<table>
  <tr>
    <td>ForceRunImport</td>
  </tr>
</table>


**Request Parameters**

<table>
  <tr>
    <td>id</td>
    <td>long</td>
    <td>The ID of the auto import item to force execution for.</td>
  </tr>
</table>


**Action Specific Response Values**

*None*

<table>
  <tr>
    <td>GetImportHistory</td>
  </tr>
</table>


**Request Parameters**

<table>
  <tr>
    <td>id</td>
    <td>long</td>
    <td>The ID of the auto import item to get a history for.</td>
  </tr>
</table>


**Action Specific Response Values**

<table>
  <tr>
    <td>history</td>
    <td>string[]</td>
    <td>A list of all previous import actions.</td>
  </tr>
</table>


<table>
  <tr>
    <td>Note: GetImportHistory action is not currently supported.</td>
  </tr>
</table>


<table>
  <tr>
    <td>ListAutoImports</td>
  </tr>
</table>


**Request Parameters**

<table>
  <tr>
    <td>account</td>
    <td>string</td>
    <td>If present, response value will only contain auto imports from this account.</td>
  </tr>
</table>


**Action Specific Response Values**

<table>
  <tr>
    <td>autoimports</td>
    <td>mixed[]</td>
    <td>A list of all autoimports in this account.  Each autoimport item contains keys: id (long), account (string), name (string), description (string), frequency (int), remoteurl (string), authurl (string), authpostdata (string).</td>
  </tr>
</table>


## Account Actions	

<table>
  <tr>
    <td>CreateAccount</td>
  </tr>
</table>


**Request Parameters**

<table>
  <tr>
    <td>name</td>
    <td>string</td>
    <td>The name of the account to be created.</td>
  </tr>
  <tr>
    <td>description</td>
    <td>string</td>
    <td>A short description to use for the new account.</td>
  </tr>
  <tr>
    <td>code</td>
    <td>string</td>
    <td>The account to be used for the new account.</td>
  </tr>
  <tr>
    <td>limitNumTables</td>
    <td>long</td>
    <td>The maximum number of tables this account may have. Default is 25.</td>
  </tr>
  <tr>
    <td>limitNumGroups</td>
    <td>long</td>
    <td>The maximum number of groups this account may have. Default is 25.</td>
  </tr>
</table>


**Action Specific Response Values**

None.

<table>
  <tr>
    <td>ListAccounts</td>
  </tr>
</table>


**Request Parameters**

*None*

**Action Specific Response Values**

<table>
  <tr>
    <td>accounts</td>
    <td>mixed[]</td>
    <td>A list of accounts containing the following fields: id, name, code.</td>
  </tr>
</table>


<table>
  <tr>
    <td>GetAccounts (Deprecated)</td>
  </tr>
</table>


**Request Parameters**

*None*

**Action Specific Response Values**

<table>
  <tr>
    <td>accounts</td>
    <td>mixed[]</td>
    <td>A list of accounts containing the following fields: id, name, code.</td>
  </tr>
</table>


## User Actions

<table>
  <tr>
    <td>AddUserToGroups</td>
  </tr>
</table>


**Request Parameters**

<table>
  <tr>
    <td>id</td>
    <td>long</td>
    <td>The ID for the user to be added to list of groups.</td>
  </tr>
  <tr>
    <td>groups</td>
    <td>string</td>
    <td>A comma delimited list of group IDs to add the given user to.</td>
  </tr>
</table>


**Action Specific Response Values**

<table>
  <tr>
    <td>userid</td>
    <td>long</td>
    <td>The ID of the user that has been modified.</td>
  </tr>
</table>


<table>
  <tr>
    <td>CreateUser</td>
  </tr>
</table>


**Request Parameters**

<table>
  <tr>
    <td>username</td>
    <td>string</td>
    <td>User name for this user.</td>
  </tr>
  <tr>
    <td>password</td>
    <td>string</td>
    <td>Password for this user.</td>
  </tr>
  <tr>
    <td>email</td>
    <td>string</td>
    <td>Email address for this user.</td>
  </tr>
  <tr>
    <td>tags</td>
    <td>string</td>
    <td>A comma delimited list of tags for this user</td>
  </tr>
  <tr>
    <td>groups</td>
    <td>string</td>
    <td>A comma delimited list of group IDs to add the given user to.</td>
  </tr>
</table>


**Action Specific Response Values**

<table>
  <tr>
    <td>userid</td>
    <td>long</td>
    <td>The ID of the newly created user.</td>
  </tr>
</table>


<table>
  <tr>
    <td>DeleteUser</td>
  </tr>
</table>


**Request Parameters**

<table>
  <tr>
    <td>id</td>
    <td>long</td>
    <td>The ID for the user to be deleted.</td>
  </tr>
</table>


**Action Specific Response Values**

<table>
  <tr>
    <td>userid</td>
    <td>long</td>
    <td>The ID of the user that has been deleted.</td>
  </tr>
</table>


<table>
  <tr>
    <td>EditUser</td>
  </tr>
</table>


**Request Parameters**

<table>
  <tr>
    <td>id</td>
    <td>long</td>
    <td>The ID for the group to be deleted.</td>
  </tr>
  <tr>
    <td>username</td>
    <td>string</td>
    <td>User name for this user.</td>
  </tr>
  <tr>
    <td>password</td>
    <td>string</td>
    <td>Password for this user.</td>
  </tr>
  <tr>
    <td>email</td>
    <td>string</td>
    <td>Email address for this user.</td>
  </tr>
  <tr>
    <td>tags</td>
    <td>string</td>
    <td>A comma delimited list of tags for this user</td>
  </tr>
  <tr>
    <td>groups</td>
    <td>string</td>
    <td>A comma delimited list of group IDs to add the given user to.</td>
  </tr>
</table>


**Action Specific Response Values**

<table>
  <tr>
    <td>userid</td>
    <td>long</td>
    <td>The ID of the user that has been deleted.</td>
  </tr>
</table>


<table>
  <tr>
    <td>Note: EditUser action is not currently supported.</td>
  </tr>
</table>


<table>
  <tr>
    <td>ListUsers</td>
  </tr>
</table>


**Request Parameters**

*None*

**Action Specific Response Values**

<table>
  <tr>
    <td>users</td>
    <td>mixed[]</td>
    <td>A list of all users connected to groups in this account.  Each user item contains keys: id (long), name (string), email(string), tags(string[]), groupcount(int).</td>
  </tr>
</table>


<table>
  <tr>
    <td>RemoveUserFromGroups</td>
  </tr>
</table>


**Request Parameters**

<table>
  <tr>
    <td>id</td>
    <td>long</td>
    <td>The ID for the user to be removed from list of groups.</td>
  </tr>
  <tr>
    <td>groups</td>
    <td>string</td>
    <td>A comma delimited list of group IDs to remove the given user from.</td>
  </tr>
</table>


**Action Specific Response Values**

<table>
  <tr>
    <td>userid</td>
    <td>long</td>
    <td>The ID of the user that has been modified.</td>
  </tr>
</table>


## Group Actions

<table>
  <tr>
    <td>CreateGroup</td>
  </tr>
</table>


**Request Parameters**

<table>
  <tr>
    <td>account</td>
    <td>string</td>
    <td>The account code for the Account that will own this group.</td>
  </tr>
  <tr>
    <td>groupname</td>
    <td>string</td>
    <td>The name to use for this group.</td>
  </tr>
  <tr>
    <td>description</td>
    <td>string</td>
    <td>A description to assign to this group.</td>
  </tr>
  <tr>
    <td>parentgroup</td>
    <td>long</td>
    <td>The ID of the parent group to use for this group.  If not passed, this will be a top level group.</td>
  </tr>
  <tr>
    <td>permissionlevel</td>
    <td>string</td>
    <td>The permission settings to apply to the newly created group.  Must be one of the values administrator, editor, viewer.</td>
  </tr>
</table>


**Action Specific Response Values**

<table>
  <tr>
    <td>groupid</td>
    <td>long</td>
    <td>The ID of the newly created group.</td>
  </tr>
</table>


<table>
  <tr>
    <td>DeleteGroup</td>
  </tr>
</table>


**Request Parameters**

<table>
  <tr>
    <td>id</td>
    <td>long</td>
    <td>The ID for the group to be deleted.</td>
  </tr>
</table>


**Action Specific Response Values**

<table>
  <tr>
    <td>groupid</td>
    <td>long</td>
    <td>The ID of the group that has been deleted.</td>
  </tr>
</table>


<table>
  <tr>
    <td>Edit Group</td>
  </tr>
</table>


**Request Parameters**

<table>
  <tr>
    <td>id</td>
    <td>long</td>
    <td>The ID for the group to be edited.</td>
  </tr>
  <tr>
    <td>groupname</td>
    <td>string</td>
    <td>A name to assign to this group.</td>
  </tr>
  <tr>
    <td>description</td>
    <td>string</td>
    <td>A description to assign to this group.</td>
  </tr>
  <tr>
    <td>parentgroup</td>
    <td>long</td>
    <td>The ID of the parent group to use for this group.  If not passed, this will be a top level group.</td>
  </tr>
  <tr>
    <td>permissionlevel</td>
    <td>string</td>
    <td>The permission settings to apply to the newly created group.  Must be one of the values administrator, editor, viewer.</td>
  </tr>
</table>


**Action Specific Response Values**

<table>
  <tr>
    <td>versions</td>
    <td>string[]</td>
    <td>A list of table versions for "tablename" in the form “account/table/version”</td>
  </tr>
</table>


<table>
  <tr>
    <td>ListGroups</td>
  </tr>
</table>


**Request Parameters**

<table>
  <tr>
    <td>account</td>
    <td>string</td>
    <td>If present, response value will only contain groups from this account.</td>
  </tr>
</table>


**Action Specific Response Values**

<table>
  <tr>
    <td>groups</td>
    <td>mixed[]</td>
    <td>A list of all groups present in this account.  Each group item contains the keys: id (long), account (string), name (string), description (string).</td>
  </tr>
</table>


* * *


For Additional information see:

[http://maplarge.com/API](http://maplarge.com/API)

[http://maplarge.com/API/Tutorial](http://maplarge.com/API/Tutorial)

[http://maplarge.com/Example/List](http://maplarge.com/Example/List)

© MapLarge, Inc. 2014

