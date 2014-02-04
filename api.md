**MapLarge API Reference v4.2
**

[MapLarge JavaScript API v4 Documentation](#bookmark=id.e11l5uygcoaz)    Version 4.2
       [Getting Started
](#bookmark=id.rvb9jf8l99w9)            [ 1.  Create a Map Container
](#bookmark=id.1nhbvg4horrf)             [2.  Create a Map
](#bookmark=id.88qky1qq3l01)             [3.  Create Map Layers
](#bookmark=id.6lw6lceehh15)            [ Sample Code
](#bookmark=id.4mecofxb2mpt)       [Object References
](#bookmark=id.3jmgmr887gh4)             [ml.map
](#bookmark=id.6a9f7klnik5w)             [ml.layer
](#bookmark=id.o5b00m80f2lv)       [Create Client Side Map Markers
](#bookmark=id.i8j3r1pr13ga)             [Simple Marker Creation
](#bookmark=id.zebazw1n9ktb)             [Marker Removal
](#bookmark=id.cy7rclkbc7zl)             [Marker with custom onClick
](#bookmark=id.vx387fwjvh04)             [Sample Code
](#bookmark=id.nu3ju0b76akb)       [Marker Options Object
](#bookmark=id.wrlm0jlra78q)       [Data Tables
](#bookmark=id.uekwftepdfb5)             [Conditional Filters (D = Double, I = Int32, S = String)
](#bookmark=id.2r0ufeca3xgn)       [Having Clauses
](#bookmark=id.9771gyxmxva4)       [Expanded Query API
](#bookmark=id.b4b2tdu2b6e6)             [Example URLs / API Example:
](#bookmark=id.px1ef0suo6oq)             [orderby:
](#bookmark=id.31c1uxyeub57)      [Table Management
](#bookmark=id.vvj7jiw0vj2l)             [Table Permissions: Users, Groups, and Tables
](#bookmark=id.e0bp5fm5el47)             [Table Management UI
](#bookmark=id.k9z6mrxe22zz)             [Table Creation Example
](#bookmark=id.9wt0mw79u76g)       [Remote Table API
](#bookmark=id.70qphwisv24d)             [Making Requests
](#bookmark=id.yg5vuxowph5i)             [Table Actions
](#bookmark=id.63sihaqy8go1)             [Automated Table Import Actions
](#bookmark=id.u2qm6uabqeb)             [Account Actions
](#bookmark=id.mz1taoe2aq2f)             [User Actions
](#bookmark=id.ky607dr6xyto)             [Group Actions
](#bookmark=id.tyrjdezdhe7y)       [Widget API
](#bookmark=id.t1pa749z9euo)             [Making Requests
](#bookmark=id.ujqdmq89wru9)       [DiskUsage API
](#bookmark=id.a41jr8sy7kh)             [Making Requests](#bookmark=id.64cou9p41sbj)

# MapLarge JavaScript API v4 Documentation			Version: 4.2

## Getting Started

Before using MapLarge maps on a website, each page must include the javascript API publicly available on our main API server at[ http://e.maplarge.com/JS](http://e.maplarge.com/JS).

There are three main steps in displaying a map on your web page:

**1. Create a map container, and attach it to the page DOM.**

**2. Create a map instance within that div.**

**3. Create and display any map layers.**

### **1.  Create a Map Container**

Each map will reside within a div (or other block level element) on your page.  You may have several of these map panes on a single page.

<table>
  <tr>
    <td>var div = ml.create('div', "width:600px; height:600px; float:left;", document.body);</td>
  </tr>
</table>


This step can be skipped if you are creating the map container element statically in your page.  In this instance, you will need to use document.getElementById('yourMapDivID') for use in creating the actual map data object.

### **2.  Create a Map**

A map object is the data object that controls all the properties of a displayable map, including starting locations, zoom levels, rendering options, underlaying map tiles, and more.  *(See ml.map)*

You create a map object by telling it what container to display inside of and passing in a JSON list of map options.  *(See ml.ui.map.MapOptions)*

<table>
  <tr>
    <td>var map = new ml.map(div, { lat: 34, lng: -84, z: 9, api: ‘LEAFLET’ });</td>
  </tr>
</table>


### **3.  Create Map Layers**

Within each displayable map there are multiple render layers, allowing a user to create multiple map overlays on a single map space.  For each layer you wish to add to a map, you will need to create a new ml.layer object with the parent map object and a list of JSON formatted layer display options.

<table>
  <tr>
    <td>var layeropts = { query: { table:  'hotels', select: 'geo.dot' } };
var layer = new ml.layer(map, layeropts); //create a new layer
layer.show();  //show the layer</td>
  </tr>
</table>


Each layer can be programmatically turned on or off by using layer.show() or layer.hide();

### **Sample Code**

Following is an example of a simple single layer dot map.  Code is taken from[ http://maplarge.com/Example/dot-default?viewSource=true](http://maplarge.com/Example/dot-default?viewSource=true)

For more code examples see the Examples page (http://maplarge.com/Example/List)

or follow along with the tutorials found online at (http://maplarge.com/API/Tutorial).

*Sample 1: Single Layer Dot Map*

<table>
  <tr>
    <td><!DOCTYPE html><html><head>
<script type='text/javascript' src='http://alphaapi.maplarge.com/JS'></script>
 <script type='text/javascript' >
ml.onload(function () {
    //create a new div and add to document
    //you could also have called document.getElementById('yourdivid');
    //to attach to an existing DOM element.
    var div = ml.create('div', "width:600px; height:600px; float:left;", document.body);

    //create a map zoomed in on Atlanta, GA (34,-84)
    //the second parameter is a Object literal
    var map = new ml.map(div, { lat: 34, lng: -84, z: 9 });

    //add a layer with no style: default is "red dots"
    var layer = new ml.layer(map,
        {
            query: {
                table:  'hms/hotels',  //table name = hotels
                select: 'geo.dot' //geography = dots
            },
            onClick: 'debug', //show debug output when dots are clicked
            //this function runs when the mouse cursor moves over a layer object
            onHover: function (data, layer) {
                return data.data.ChainID;
            },
            //NOTE--> for hover you must list the fields you want to use
            hoverFields: ['ChainID', 'LowRate']

        }
    );
    
    //show the layer
    layer.show();

});
</script></head><body></body></html></td>
  </tr>
</table>


## Object References	

<table>
  <tr>
    <td>ml.map</td>
    <td>Ex: var map = new ml.map(div,OPTIONS)</td>
  </tr>
</table>


**Parameter: div **a reference to the html dom element that will contain the map.  

**Parameter: OPTIONS **is an object that defines how the map will be initialized.  Most options are optional, and will be provided with default values if omitted.  

<table>
  <tr>
    <td>Note:  In addition to the options described below, all the options of the underlying map api are available to you as well by by using map.getInternalMap().  For example, the following code returns the internal map.

//create a map large map using the ESRI API
var mlmap = ml.map(div,{ api:’ESRI’ });
//return the internal ESRI map object
var emap = map.getInternalMap();</td>
  </tr>
</table>


**Constructor Options**

<table>
  <tr>
    <td>Property</td>
    <td>Type</td>
    <td>Explanation</td>
  </tr>
  <tr>
    <td>api</td>
    <td>String</td>
    <td>[Optional] The provider of the map api.  Must be one of "GOOGLE", “LEAFLET”, “ESRI”</td>
  </tr>
  <tr>
    <td>lat</td>
    <td>Double</td>
    <td>[Optional] Initial latitude for the map to be set at. Must be be between -90 and 90.</td>
  </tr>
  <tr>
    <td>lng</td>
    <td>Double</td>
    <td>[Optional] Initial longitude for the map to be set at. Must be be between -180 and 180.</td>
  </tr>
  <tr>
    <td>z</td>
    <td>Integer</td>
    <td>[Optional] Initial zoom level for the map to be set at.  Must be between 0 and 22, inclusive.</td>
  </tr>
  <tr>
    <td>layers</td>
    <td>Array [ layerOpt1, layerOpt2, etc...]</td>
    <td>[Optional] An array of layer options. All layers not flagged as “hidden” will be immediately added to the map and shown</td>
  </tr>
  <tr>
    <td>viewportScalable</td>
    <td>Boolean</td>
    <td>[Optional] Used to control page zoom for touch devices.</td>
  </tr>
</table>


**Methods & Properties**

<table>
  <tr>
    <td>Function Signature</td>
    <td>Return Value</td>
    <td>Explanation</td>
  </tr>
  <tr>
    <td>api.get() / .set(string val)</td>
    <td>String</td>
    <td>Get or set the mapping api</td>
  </tr>
  <tr>
    <td>center.get() / .set(LatLng val)</td>
    <td>{ lat:#, lng:# }</td>
    <td>Get or set the map center</td>
  </tr>
  <tr>
    <td>clearAllLayers()</td>
    <td>void</td>
    <td>Clear all Layers</td>
  </tr>
  <tr>
    <td>click(LatLng,bool ignoreMotion)</td>
    <td>void</td>
    <td>Fire a click event at a specific location, and optionally, ignore the click if the map is moving. Ex: map.click({ lat:34, lng: -84});</td>
  </tr>
  <tr>
    <td>closeBalloon()</td>
    <td>void</td>
    <td>Close the singleton balloon owned by the map</td>
  </tr>
  <tr>
    <td>hover(LatLng ll, bool ignoreMotion)</td>
    <td>void</td>
    <td>Trigger a hover event over the map at a specific location and optionally ignore the event if the map is moving</td>
  </tr>
  <tr>
    <td>openBalloon(LatLng ll, String/Element html);</td>
    <td>void</td>
    <td>Opens a balloon at the specified coordinates {lat:#, lng:#} and with the specified content which can be either a literal string or a html element.</td>
  </tr>
  <tr>
    <td>setBounds(LatLng sw, LatLng ne)</td>
    <td>void</td>
    <td>Centers and zooms the map to the best fit that shows the entire bounds area.</td>
  </tr>
  <tr>
    <td>getVisibleBB()</td>
    <td>{
minLat: float,
maxLat: float,
minLng: float,
maxLng: float
};
</td>
    <td>Returns the lat/lng coordinates for the visible map area</td>
  </tr>
  <tr>
    <td>zoom.get() / .set(Int32 zoom)</td>
    <td>void</td>
    <td>Zoom the map to a specific level. Valid values are from 0-22</td>
  </tr>
  <tr>
    <td>getInternalMap()</td>
    <td>Google, ESRI or Leaflet Map Object</td>
    <td>Returns the internal map api object. Type will vary based on the api in use.</td>
  </tr>
</table>


<table>
  <tr>
    <td>ml.layer</td>
    <td>Ex var layer = new ml.layer(map,OPTIONS);</td>
  </tr>
</table>


**Parameter: map **is a reference to the ml.map where the layer will be added

**Parameter: OPTIONS **is an object that defines how the layer will be rendered.  Most options are optional, and will be provided with default values if omitted.

<table>
  <tr>
    <td>Note:  The Layer Options described below are explained in more detail with interactive code samples at http://maplarge.com/API/Tutorial  

For rapid prototyping the MapEditor UI, is also helpful.  On any of the above tutorial pages press "CTRL" “M” to edit the map in place or visit the editor directly at: http://e.maplarge.com/view/MapEditor.html  
</td>
  </tr>
</table>


**Object Properties**

<table>
  <tr>
    <td>Property</td>
    <td>Type</td>
    <td>Description</td>
  </tr>
  <tr>
    <td>hoverFields</td>
    <td>string[]</td>
    <td>An array of all column names that you wish to have accessible for use inside of the onHover function.</td>
  </tr>
  <tr>
    <td>onClick</td>
    <td>Function</td>
    <td>A function with two parameters that will be called whenever a data point or shape is clicked.  Function must return a string (which may be HTML) that is to be displayed in a popup bubble over the shape.  Information about the shape will be passed in by the first parameter, and row data can be accessed by the "data" property.  E.g. if the first parameter is named “data”, “return data.data.ColumnName;”  The second parameter contains a reference to the map layer which generated the click event.</td>
  </tr>
  <tr>
    <td>onHover</td>
    <td>Function</td>
    <td>A function with two parameters that will be called whenever a data point or shape is hovered upon.  Function must return a string (which may be HTML) that is to be displayed in a popup bubble over the shape.  Information about the shape will be passed in by the first parameter, and row data can be accessed by the “data” property.  E.g. if the first parameter is named “data”, “return data.data.ColumnName;”  The second parameter contains a reference to the map layer which generated the hover event.  Note: Any column name used in the onHover function must be present in the hoverFields array.</td>
  </tr>
  <tr>
    <td>style</td>
    <td>Object</td>
    <td>Defines how table data is rendered for this layer.</td>
  </tr>
  <tr>
    <td>style.method</td>
    <td>String</td>
    <td>The method of shading to use when rendering this layer.  Value must be one of “basic”, “interval”, or “rules”.  If omitted, method is assumed to be “basic”.

basic - Does not require any extra shading information.  Any styling defined will be applied to all features that are rendered.
Example:

interval - Features will be shaded by gradients over a defined number of ranges.  The properties style.ranges and style.colors are used to apply styling.
Example:

rules - Features will be shaded by discrete rules defined in style.rules
Example:
</td>
  </tr>
  <tr>
    <td>style.url</td>
    <td>String</td>
    <td>The absolute URI to an image to use for each XY point in the layer.</td>
  </tr>
  <tr>
    <td>style.height</td>
    <td>Integer</td>
    <td>Height in pixels to use for XY points in the Layer</td>
  </tr>
  <tr>
    <td>style.width</td>
    <td>Integer</td>
    <td>Width in pixels to use for XY points in the Layer</td>
  </tr>
  <tr>
    <td>style.size</td>
    <td>Integer</td>
    <td>A single value that is use for both height and width.  Defining either style.height or style.width will override the corresponding dimension.</td>
  </tr>
  <tr>
    <td>style.shape</td>
    <td>String</td>
    <td>The shape to draw each XY point.  Must be either “rectangle” or “round”</td>
  </tr>
  <tr>
    <td>style.fillColor</td>
    <td>String</td>
    <td>The color used to fill each XY point.  Colors may be of the form “Named-A”, “A-R-G-B”, “Named”, or “R-B-G”
A list of Named values and their RGB equivalents can be found here: http://maplarge.com/DotNetColors</td>
  </tr>
  <tr>
    <td>style.borderColor</td>
    <td>String</td>
    <td>The color used to draw border for each XY point.  See style.fillColor.  This is mutually exclusive with style.borderColorOffset</td>
  </tr>
  <tr>
    <td>style.borderColorOffset</td>
    <td>Integer</td>
    <td>A +/- offset value from style.fillColor used to draw border for each XY point.  See style.fillColor.  This is mutually exclusive with style.borderColor</td>
  </tr>
  <tr>
    <td>style.borderWidth</td>
    <td>Integer</td>
    <td>Width in pixels used to draw the border on each XY point.</td>
  </tr>
  <tr>
    <td>style.text</td>
    <td>Object</td>
    <td>Defines how to style the text placed in each XY point.
Note:  If using the style.method “interval” the value found in the style.shadeBy column will be appended to this text.</td>
  </tr>
  <tr>
    <td>style.text.x</td>
    <td>Integer</td>
    <td>Position in pixels from the left of the XY point to start rendering text.</td>
  </tr>
  <tr>
    <td>style.text.y</td>
    <td>Integer</td>
    <td>Position in pixels from the top of the XY point to start rendering text.</td>
  </tr>
  <tr>
    <td>style.text.text</td>
    <td>String</td>
    <td>Text value to render inside each XY point.</td>
  </tr>
  <tr>
    <td>style.text.size</td>
    <td>Integer</td>
    <td>Size in pixels to render the text in each XY point.</td>
  </tr>
  <tr>
    <td>style.dropShadow</td>
    <td>Boolean</td>
    <td>Whether to apply a drop shadow to each XY point.  </td>
  </tr>
  <tr>
    <td>style.dropShadowLength</td>
    <td>Integer</td>
    <td>Length in pixels of the drop shadow.  Assumes style.dropShadow is true.</td>
  </tr>
  <tr>
    <td>style.rules</td>
    <td>Array</td>
    <td>An array of rule objects that define a style and test criteria for rendering layer features.  Tests are run in order from first to last, and any data row that matches a rule is removed as a candidate for satisfying subsequent rule definitions.</td>
  </tr>
  <tr>
    <td>style.rules[n].style</td>
    <td>Object</td>
    <td>Can contain any styling that could be used for a “basic” shading method.  See style and style.method</td>
  </tr>
  <tr>
    <td>style.rules[n].where</td>
    <td>Array,
String</td>
    <td>Can contain any tests that can be used in a standard test clause.  See query.where.  Additionally, the string value “CatchAll” may be used in the place of the a final test clause to match any data rows that have not already satisfied any previous rules.</td>
  </tr>
  <tr>
    <td>style.steps</td>
    <td>Integer</td>
    <td>The number of steps used in the interpolation between the min and max values of each range.  E.G.  If there are three items in style.ranges and style.steps is set to 50, each range’s min to max values will be broken into 50 partitions with equal numbers of data points in each, and apply the appropriate style.colors item broken up into 50 evenly spaced colors.
Option only applies to the style.method “interval”</td>
  </tr>
  <tr>
    <td>style.shadeBy</td>
    <td>String</td>
    <td>The name of a column in query.table.name to use for interval shading.  This column must be a numeric column (Int or Double) and must be fully defined by the table name itself.  E.g. “census/BlockGroup.Area”.  Additionally, if using a table join, the primary table may return aggregate values based on the type of join.  These are named “avg”, “count”, and “sum”; representing the average, count, and sum of grouped secondary table rows.  E.g. “census/Zip.LowRate.avg” could be average of the lowest room rates that all hotels offer by zip code, if a table of hotel information was joined against the “census/Zip” table</td>
  </tr>
  <tr>
    <td>style.colors</td>
    <td>Array</td>
    <td>An array of color range objects that are used for each interval in style.ranges.</td>
  </tr>
  <tr>
    <td>style.colors[n].max</td>
    <td>Integer</td>
    <td>The color to use at the top of this interval range.  See style.fillColor.</td>
  </tr>
  <tr>
    <td>style.colors[n].min</td>
    <td>Integer</td>
    <td>The color to use at the bottom of this interval range.  See style.fillColor.</td>
  </tr>
  <tr>
    <td>style.ranges</td>
    <td>Array</td>
    <td>An array of min/max value range objects that define the sizes of each range which match up to style.colors.  If omitted, a number of ranges equal to the the size of the style.colors array will be used, and min/max values will be dynamically selected to attempt an equal distribution (by count) of rows in each range.</td>
  </tr>
  <tr>
    <td>style.colors[n].max</td>
    <td>Integer</td>
    <td>The value to use for the top of this interval range.</td>
  </tr>
  <tr>
    <td>style.colors[n].min</td>
    <td>Integer</td>
    <td>The value to use for the bottom of this interval range.</td>
  </tr>
  <tr>
    <td>style.cluster</td>
    <td>Object</td>
    <td>Can contain any styling that could be used for a “basic” shading method.  See style and style.method.  Cluster groups are shaded independently from any style.rules or style.colors settings.</td>
  </tr>
  <tr>
    <td>style.cluster.clusterSpacing</td>
    <td>Integer</td>
    <td>The approximate spacing in pixels between clusters. To ensure no overlap make this value larger than the largest cluster icon size.</td>
  </tr>
  <tr>
    <td>style.cluster.clusterPosition</td>
    <td>String</td>
    <td>This option determines where inside of the clustering grid box that the cluster is rendered. The valid values are “topleft”, “center”, “weighted”, “centroid”, and “location”. String data type.</td>
  </tr>
  <tr>
    <td>style.cluster.maxClusterZoom</td>
    <td>Integer</td>
    <td>The maximum zoom level that clustering should be in effect. Above this zoom level no clustering will occur and all points will be rendered individually. Integer data type.</td>
  </tr>
  <tr>
    <td>style.cluster.autoIconEnable</td>
    <td>Boolean</td>
    <td>Enables auto coloring and sizing icons. Primarily useful for the rules shading method. Valid values for this bool type are “true” or “false”.</td>
  </tr>
  <tr>
    <td></td>
    <td></td>
    <td></td>
  </tr>
  <tr>
    <td>style.cluster.autoIconSizeSpread</td>
    <td>Integer</td>
    <td>This controls the size spread of the cluster icons, based on the child count and this factor. Valid values are 1 thru 100, integer data type.</td>
  </tr>
  <tr>
    <td>style.cluster.autoIconColorSpread</td>
    <td>Integer</td>
    <td>This controls the color spread of the cluster icons, based on the child count and this factor. Valid values are 1 thru 100, integer data type.
</td>
  </tr>
  <tr>
    <td></td>
    <td></td>
    <td></td>
  </tr>
  <tr>
    <td>style.renderAlgo</td>
    <td>String</td>
    <td>Internal use only. Selects among four types of rendering algorithms (“basic”, “nearest”, “bilinear” and ‘circular”) for rendering the layer. The “circular” option does the most interpolation and the best smoothing. “Circular” is the default. Used only by layers of type Heat.</td>
  </tr>
  <tr>
    <td>style.bigAreaSize</td>
    <td>Integer</td>
    <td>This circular rendering sub-option sets the radius of the bigger area that gets interpolated. The default value is 12. Used only by layers of type Heat.</td>
  </tr>
  <tr>
    <td>style.smallAreaSize</td>
    <td>Integer</td>
    <td>Advanced option, set automatically. This circular rendering sub-option sets the radius of the smaller area that gets interpolated. The default value is 12. Used only by layers of type Heat.
</td>
  </tr>
  <tr>
    <td></td>
    <td></td>
    <td></td>
  </tr>
  <tr>
    <td>style.bigAreaWeight</td>
    <td>Integer</td>
    <td>Advanced option, set automatically. This circular rendering sub-option sets the weight of the bigger area that gets interpolated. The default value is 1. Used only by layers of type Heat.</td>
  </tr>
  <tr>
    <td>style.smallAreaWeight</td>
    <td>Integer</td>
    <td>Advanced option, set automatically. This circular rendering sub-option sets the weight of the smaller area that gets interpolated. The default value is 1. Used only by layers of type Heat.</td>
  </tr>
  <tr>
    <td>style.blurEnable</td>
    <td>bool</td>
    <td>The setting enables or disables the blur operation that occurs after any interpolation operation. The default is true. Used only by layers of type Heat.</td>
  </tr>
  <tr>
    <td>style.blurSize</td>
    <td>Integer</td>
    <td>The setting controls the diameter of the circular blur window. A setting larger than bigAreaSize results in nice fuzzy blur. The default is 11. Used only by layers of type Heat.</td>
  </tr>
  <tr>
    <td>style.blurWeight</td>
    <td>Integer</td>
    <td>Advanced option, set automatically. The setting controls how much of an impact the blur has on each pixel blurred. The default value is 3. Used only by layers of type Heat.</td>
  </tr>
  <tr>
    <td>style.blurMode</td>
    <td>String</td>
    <td>Internal use only. Valid values for this setting are “mean” and “gaussian”. The “mean” mode does a mean circular blur, while the “gaussian” mode does a pure circular gaussian blur. The default is “gaussian”. Used only by layers of type Heat.</td>
  </tr>
  <tr>
    <td>style.blockMode</td>
    <td>String</td>
    <td>Internal use only. This chooses how rendered block sizes are selected at each zoom level. Options are (“auto” and “manual”). “Auto” increases the block size with an increase in zoom. “Manual” uses a set block size, selected by the next style option or if left out the default is 1. Used only by layers of type Heat.</td>
  </tr>
  <tr>
    <td>style.blockSize</td>
    <td>String</td>
    <td>Internal use only.. The value to use for the rendering  block size when the style.blockMode is “manual”. This block size is used by all zoom levels. Used only by layers of type Heat.
</td>
  </tr>
  <tr>
    <td>style.gradientSet</td>
    <td>Integer</td>
    <td>This selects between one of three builtin in color gradient sets. These sets are used only if a custom interval or rule are not defined. Valid values are 0, 1 and 2. The default is 1. Used only by layers of type Heat.</td>
  </tr>
  <tr>
    <td>style.renderShape</td>
    <td>String</td>
    <td>Internal use only. The shape to use when rendering the layer. Current supported shapes are (“block” and “circle”). Used only by layers of type Heat.</td>
  </tr>
  <tr>
    <td>query</td>
    <td>Object</td>
    <td>Defines the basic structural information used to construct this layer.</td>
  </tr>
  <tr>
    <td>query.join</td>
    <td>Object</td>
    <td>Defines a join between two different tables.  The query.table value will be restricted based on what form of join is being constructed.</td>
  </tr>
  <tr>
    <td>query.join.method</td>
    <td>String</td>
    <td>The method to use when joining table.  Currently supported joins are:

PointInPolyJoin - This will join two tables by testing each XY point in query.join.table against the boundaries of each polygon shape in query.table.  This requires query.select.type to be “geo.poly”

Example:</td>
  </tr>
  <tr>
    <td>query.join.table</td>
    <td>String</td>
    <td>See query.table</td>
  </tr>
  <tr>
    <td>query.join.where</td>
    <td>Array,
Object</td>
    <td>See query.where</td>
  </tr>
  <tr>
    <td>query.table</td>
    <td>Object,
String</td>
    <td>Defines what table data will be used for this layer.
If this is set to an object, it must define a “name” key.  If this is a string, it is a table name, either in full ID or active name format.</td>
  </tr>
  <tr>
    <td>query.table.name</td>
    <td>String</td>
    <td>Defines what table data will be used for this layer.  This is a table name, either in full ID or active name format. E.g. “census/BlockGroup”</td>
  </tr>
  <tr>
    <td>query.select</td>
    <td>Object,
String</td>
    <td>Defines what type of geographic map will be rendered by this layer.
If this is set to an object, it must define a “type” key.  If this is a string, the value must be one of the following values: “geo.dot”, “geo.line”, “geo.poly”, “geo.dotCluster”, or “geo.dotCluster#” where # is in [16, 32, 64, 128, 256].  “geo.dotCluster” is assumed to be 32.</td>
  </tr>
  <tr>
    <td>query.select.type</td>
    <td>String</td>
    <td>Defines what type of geographic map will be rendered by this layer.  The value must be one of the following values: “geo.dot”, “geo.line”, “geo.poly”, “geo.dotCluster”, or “geo.dotCluster#” where # is in [16, 32, 64, 128, 256].  “geo.dotCluster” is assumed to be 32.</td>
  </tr>
  <tr>
    <td>query.where</td>
    <td>Array</td>
    <td>An array that defines a test clause.  The test clause may be represented by either an Array of Arrays of filter Objects, or simply an Array of filter Objects.  Any filter Objects that are in the same Array are treated as being joined by AND conditionals, and any Array in the same Array are treated as being joined by OR conditionals.
E.g.  Where a, b, c, d are filter Objects
[ a, b, c ] would satisfy the condition (a AND b AND c).
[ [ a, b ], [ c, d ] ] would satisfy the condition (a AND b) OR (c AND d)

A filter object is defined by an object with the properties “col”, “test”, and “value”.  “col” must be the name of a column in  either query.table.name or query.join.table, or one of the aggrigate column values as defined in style.shadeBy.  “test” must be a valid filter type for the column being filtered.  See section Conditional Filters for a full list.  “value” be different types depending on the filter; string, numeric, or list.  If value is expected to be a list, it is a comma-delimited string of the appropriate type.</td>
  </tr>
</table>


**Methods & Properties**

<table>
  <tr>
    <td>Function Signature</td>
    <td>Return Value</td>
    <td>Explanation</td>
  </tr>
  <tr>
    <td>hide()</td>
    <td>void</td>
    <td>Hide the layer</td>
  </tr>
  <tr>
    <td>show()</td>
    <td>void</td>
    <td>Show the layer</td>
  </tr>
  <tr>
    <td>visible.get() / .set()</td>
    <td>boolean</td>
    <td>Get or set the layer visibility</td>
  </tr>
</table>


## Create Client Side Map Markers

Client Side Map markers can be created at a given positions on a map using a high performance custom marker layer managed by the MapLarge api. At minimum, a marker consists of an image and a latitude/longitude coordinate (of type ml.geo.LatLng). Marker management is handled through an instantiated map object as shown by the example below:

### **Simple Marker Creation:**

<table>
  <tr>
    <td>//Create map
var map = new ml.map(div, { lat: 34, lng: -84, z: 9, api: ‘LEAFLET’ });

//create options object for marker
var options = new ml.MarkerOptions();
options.icon = 'http://leeapi.maplarge.com/test/dot.png';

//Create ml.geo.LatLng object with geographic coordinates
var latlng = ml.ll(33.755, -84.39);

//Add marker to map
var myMarker = map.createMarker(latlng, options);</td>
  </tr>
</table>


The options object created and passed into the createMarker function is used to communicate various customization options for the Marker. OnClick and OnHover event handlers may also be defined in this object. Full details about the options object are described below.

The object returned from the createMarker call is a reference to the actual marker created on the map. It may be used to later remove the marker via a separate function call. In addition, all markers can be removed with one function call using map.clearAllMarkers.

### **Marker Removal:**

<table>
  <tr>
    <td>//Map already created

//Add marker to map
var myMarker = map.createMarker(latlng, options);

//remove marker from map
map.removeMarker(myMarker);

//remove ALL markers
map.clearAllMarkers();</td>
  </tr>
</table>


Events may also be attached to marker objects. Via the MarkerOptions object, an onClick and onHover handler can be defined.

### **Marker with custom onClick:**

<table>
  <tr>
    <td>//Map already created

//Add marker to map
var options = new ml.MarkerOptions();
options.icon = 'http://leeapi.maplarge.com/test/dot.png';
options.onClick = myHandler;
options.data.message = ‘Message for event handler’;

var myMarker = map.createMarker(latlng, options);

//Custom marker handler
//The parameter passed in is the marker itself.
//The options is an exact copy of the options used to create the marker and //can be used to pass information. By default if a string is returned, the //result will be displayed in a popup balloon. Returning false overrides //this behavior.

function myHandler(marker) {

return marker.options.data.message;

}</td>
  </tr>
</table>


### **Sample Code**

The following is an example of a simple map creating markers as in the above examples. Code is taken from[ http://leeapi.maplarge.com/view/map_merker.html](http://leeapi.maplarge.com/view/map_merker.html)

## Marker Options Object	

<table>
  <tr>
    <td>Marker Options</td>
    <td>Ex: var marker = map.createMarker(latlng, options);</td>
  </tr>
</table>


**Parameter: obj **is an object that defines how the map will be initialized.  Most options are optional, and will be provided with default values if omitted.

**Object Properties**

<table>
  <tr>
    <td>Property</td>
    <td>Type</td>
    <td>Description</td>
  </tr>
  <tr>
    <td>icon</td>
    <td>String</td>
    <td>The URL of an image to display on the map.</td>
  </tr>
  <tr>
    <td>name</td>
    <td>String</td>
    <td>Optional marker name.</td>
  </tr>
  <tr>
    <td>data</td>
    <td>Object</td>
    <td>A general purpose object which may be used to hold any information. Intended to be used for passing data to the event handlers</td>
  </tr>
  <tr>
    <td>onHover</td>
    <td>Function</td>
    <td>A function with 1 parameter that will be called whenever a marker is hovered over.  The actual marker object is passed in the first parameter for any custom use by the handler.</td>
  </tr>
  <tr>
    <td>onClick</td>
    <td>Function</td>
    <td>A function with 1 parameter that will be called whenever a marker is clicked.  Function may return a string (which may be HTML) that by default will be shown in a popup bubble over the marker. If this behavior is not desired, returning false from the Function will prevent it. The actual marker object is passed in the first parameter for any custom use by the handler.</td>
  </tr>
  <tr>
    <td></td>
    <td></td>
    <td></td>
  </tr>
</table>


## Data Tables

Data Tables are a server side object composed of columns of equal length.  All columns are automatically indexed and both the index and the column data are loaded into RAM the first time a column is used.  It is worth noting that because MapLarge tables and columns reside entirely in RAM exhibit unique performance capabilities and hardware requirements.  A Data Table row is a collection of cells taken from the Data Table columns at the same position in each column.  Linear table scans are designed to run almost entirely in L1 processor cache so that computations can take advantage of low level processor memory cache and provide .

**Column Types:**  Each column is of a fixed type determined on creation.  During bulk imports columns are usually created by "duck typing" or down casting from the strictest to the most inclusive type until all values in a given column fit within a type.  If a column looks like an integer we treat it as an integer, if it looks like a double its a double, otherwise its a string.  Once a column is created it is strongly typed and the overhead of duck typing is avoided.  Currently, row limits can be avoided by stacking tables together.  Row length limits will soon be removed from the api allowing for rows capable of filling all available RAM.   

* **Int32: Represents a 32-bit signed integer.**

    * **Default Value: **0  

    * **Max Value: **2,147,483,647

    * **Min Value: **-2,147,483,648

    * **Max Column Length: **268,435,456 rows

* **Double: Represents a double-precision floating-point number.**

    * **Default Value: **0

    * **Max Value: **1.7976931348623157E+308

    * **Min Value: **1.7976931348623157E+308

    * **Max Column Length: **178,956,970 rows

* **String**: Represents text as a series of Unicode characters (UTF-16, 2 byte characters).  Internally, the system splits strings by spaces and indexes each "word" for faster searches.

    * **Max Size/Length**: (2GB or approximately 1,000,000,000 two byte characters)

    * **Max Column Length**: approximately 100,000,000 rows

    * **Default Value**: "" (Empty String)

* **XY**:  Each "XY" in a column of type XY is a single point {x:1, y:1 } defined by a pair of Int32 values. This is the system’s internal representation of point values based on a transformation of latitude and longitude into an internal projection format.

    * **X Min/Max**:  derived from converting Longitude -180/180 into X values

    * **Y Min/Max**: derived from converting Latitude -90/90 into Y values

    * **Default Value**:  0,0

    * **Max Column Length**: 178,956,970 rows

* **Poly**: Each "Poly" in a column of type poly holds a collection of one or more closed polygon(s) defined by a series of XY points.  For example a table might only have 10 rows, and the poly column would have 10 “poly” entries.  However, inside each entry there could be multiple polygons.  Polygon point configurations generally follow conventions similar to those of traditional[ ShapeFiles](http://en.wikipedia.org/wiki/Shapefile).  Each shape inside a poly entry is drawn separately, but selecting one shape in a polygon set results in the selection of all shapes in the polygon set.

    * **Max Polygon Count per Poly Entry:**:      67,108,864

    * **Max Point Count Per Polygon**:   268,435,456

    * **Default Value**:  0 entries

    * **Max Column Length**: 67,108,864 rows

* **Line**:  Each "Line" entry in a column of type Line is a collection of one of more lines.  Lines are composed of XY points.

    * **Max Lines Per Line Entry**: 67,108,864

    * **Max Point Count Per Line**:   268,435,456

    * **Default Value**:  0 entries	

    * **Max Column Length**: 67,108,864 rows



### **Conditional Filters (D = Double, I = Int32, S = String)**

<table>
  <tr>
    <td>Name</td>
    <td>D</td>
    <td>I</td>
    <td>S</td>
    <td>Description</td>
  </tr>
  <tr>
    <td>Between</td>
    <td>●</td>
    <td>●</td>
    <td></td>
    <td>Column row values between a minimum and maximum, inclusive.</td>
  </tr>
  <tr>
    <td>Bottom</td>
    <td>●</td>
    <td>●</td>
    <td></td>
    <td>A given number of smallest column row values.</td>
  </tr>
  <tr>
    <td>Contains</td>
    <td></td>
    <td></td>
    <td>●</td>
    <td>Column rows that contain a value, as a substring.
[Not Case Sensitive], [commas are valid search terms]</td>
  </tr>
  <tr>
    <td>ContainsAll</td>
    <td></td>
    <td></td>
    <td>●</td>
    <td>Column rows that contain all of a list of values, as a substring. [Not Case Sensitive], [list is comma delimited and commas are NOT valid search terms]</td>
  </tr>
  <tr>
    <td>ContainsAny</td>
    <td></td>
    <td></td>
    <td>●</td>
    <td>Column rows that contain any of a list of values, as a substring. [Not Case Sensitive], [list is comma delimited and commas are NOT valid search terms]</td>
  </tr>
  <tr>
    <td>ContainsNot</td>
    <td></td>
    <td></td>
    <td>●</td>
    <td>Column rows that do not contain a single value as a sub string
[Not Case Sensitive], [commas are valid search terms]</td>
  </tr>
  <tr>
    <td>ContainsNone</td>
    <td></td>
    <td></td>
    <td>●</td>
    <td>Column rows that do not contain any of a list of values, as a substring.  [Not Case Sensitive], [list is comma delimited and commas are NOT valid search terms]</td>
  </tr>
  <tr>
    <td>ContainsOr</td>
    <td></td>
    <td></td>
    <td>●</td>
    <td>Column rows that contain any of a list of values, as a substring.  Alias for "ContainsAny" [Not Case Sensitive], [list is comma delimited and commas are NOT valid search terms]</td>
  </tr>
  <tr>
    <td>Equal</td>
    <td>●</td>
    <td>●</td>
    <td>●</td>
    <td>Column rows that equal exactly some value.
[Case Sensitive]</td>
  </tr>
  <tr>
    <td>EqualAny</td>
    <td>●</td>
    <td>●</td>
    <td>●</td>
    <td>Column rows that equal any of a list of values.
[Case Sensitive]</td>
  </tr>
  <tr>
    <td>EqualNone</td>
    <td>●</td>
    <td>●</td>
    <td></td>
    <td>Column rows that do not equal any of a list of values.
[Case Sensitive]</td>
  </tr>
  <tr>
    <td>EqualNot</td>
    <td>●</td>
    <td>●</td>
    <td>●</td>
    <td>Column rows that do not equal a value exactly.
[Case Sensitive]</td>
  </tr>
  <tr>
    <td>Greater</td>
    <td>●</td>
    <td>●</td>
    <td></td>
    <td>Column rows whose values are strictly greater than some value.</td>
  </tr>
  <tr>
    <td>GreaterOR</td>
    <td>●</td>
    <td>●</td>
    <td></td>
    <td>Column rows whose values are greater than or equal to some value.</td>
  </tr>
  <tr>
    <td>Less</td>
    <td>●</td>
    <td>●</td>
    <td></td>
    <td>Column rows whose values are strictly less than some value.</td>
  </tr>
  <tr>
    <td>LessOR</td>
    <td>●</td>
    <td>●</td>
    <td></td>
    <td>Column rows whose values are less than or equal to some value.</td>
  </tr>
  <tr>
    <td>NotBetween</td>
    <td>●</td>
    <td>●</td>
    <td></td>
    <td>Column row values not between a min and max, inclusive.</td>
  </tr>
  <tr>
    <td>Top</td>
    <td>●</td>
    <td>●</td>
    <td></td>
    <td>A given number of largest column row values.</td>
  </tr>
</table>


** Filter can be applied to columns of type D = Decimal, I = Integer, S = String*

## Having Clauses

Any time you are doing a **PointInPolyJoin**, there is the "join" object key with method, table, and where.  The new option is "**having**" where you can specify aggregate where clauses (using the same format as normal where blocks) that will filter results based on the post-join aggregate values (sum, avg, count).

**Example 1:**

**JS  ****[http://alphaapi.maplarge.com/v4/ui\map\Tests\Havings\GreaterThan.j**s](http://alphaapi.maplarge.com/v4/ui/map/Tests/Havings/GreaterThan.js)

<table>
  <tr>
    <td>///<reference path="~/v4/Main.js" />

ml.onload(function () {

    var county = {
        query: {
            table: { name: 'census/BlockGroup' },
            select: { type: 'geo.poly' },
            where: [{ col: 'census/BlockGroup.inc', test: 'Greater', value: 60000 }],
            join: {
                method: "PointInPolyJoin",
                table: "hms/hotels",
                where:
                [
                    //where contains hilton && 3 star
                    [
                        { col: "ChainID", test: "Contains", value: "hilton" }
                    ],
                    //OR where contains marriott && 3.5 star
                    [
                        { col: "ChainID", test: "Contains", value: "marriott" }
                    ],
                    //OR
                    [
                        { col: "LowRate", test: "Less", value: 15000 }
                    ]
                ],
                having:
                [
                    { col: "LowRate.avg", test: "Greater", value: 100 }
                ]
            }
        },
        style: {
            //having clauses (totals on grouped points) are supported using the intervals method for shading
            //(see below)
            //however, free form rules using having clauses are not supported yet
            //so for now you can't do this, but this feature is on our api road map
            //having:[col:'hms/hotels.LowRate.avg', test:'Greater' value:100]
            //if you need this feature ASAP contact our development team for a custom build quote
            method: 'interval',
            shadeBy: "hms/hotels.LowRate.avg", //count, sum, avg
            colors: [
                      { min: 'LightBlue-128', max: 'Blue-128' },
                     { min: 'Blue-128', max: 'Red-128' }
            ],
            ranges: [
                    { min: 0, max: 75 },
                    { min: 75, max: 150 }
            ]
        },
        onHover: function (data, layer) {
            return "Avg. Low Rate: $" + Math.round(data.subTotals.LowRate.avg);
        },
        //listing the hms/hotels table here is mandatory because we have two tables
        //.avg represents the average of the "LowRate" column points in this County
        hoverFields: ['hms/hotels.LowRate.avg'],
        onClick: 'debug'

        //when you click debug notice the shape data is under data.data
        //and how the point totals are under data.subTotals
    };

    //create a new div and add to document
    //you could also fetch the element by id
    var div = ml.create('div', "width:600px; height:600px;", document.body);

    var map = new ml.map(div, { lat: 34, lng: -84, z: 5 });
    var layer = new ml.layer(map, county);
    layer.show();



});</td>
  </tr>
</table>


**MAP****[ http://alphaapi.maplarge.com/JS/Test/?path=/v4/ui\map\Tests\Havings\GreaterThan.j**s](http://alphaapi.maplarge.com/JS/Test/?path=/v4/ui/map/Tests/Havings/GreaterThan.js)![image alt text](image_0.png)`

<table>
  <tr>
    <td>Aggregate Rules:
When styling by the "rules" method, you can now include aggregate column values in the where portion of each rule.  These operate exactly like normal where clauses, except for the following:
You include the extra .sum, .avg, or .count onto the end of the column.
You must include the table name in the column definition.  eg:  account/table.column.avg (or account/table/version.column.avg).
As per normal rules, they will be applied to data in the order defined.
</td>
  </tr>
</table>


**Example 2:**

**JS  ****[http://alphaapi.maplarge.com/v4/ui/map/Tests/Havings/StyleGreater.j**s](http://alphaapi.maplarge.com/v4/ui/map/Tests/Havings/StyleGreater.js)

**MAP****[ http://alphaapi.maplarge.com/JS/Test/?path=/v4/ui\map\Tests\Havings\StyleGreater.j**s](http://alphaapi.maplarge.com/JS/Test/?path=/v4/ui/map/Tests/Havings/StyleGreater.js)![image alt text](image_1.png)

<table>
  <tr>
    <td>Aggregate Rules:
When styling by the "rules" method, you can now include aggregate column values in the where portion of each rule.  These operate exactly like normal where clauses, except for the following:
You include the extra .sum, .avg, or .count onto the end of the column.
You must include the table name in the column definition.  eg:  account/table.column.avg (or account/table/version.column.avg).
As per normal rules, they will be applied to data in the order defined.
</td>
  </tr>
</table>


**Example 2:**

**JS  ****[http://alphaapi.maplarge.com/v4/ui/map/Tests/Havings/StyleGreater.j**s](http://alphaapi.maplarge.com/v4/ui/map/Tests/Havings/StyleGreater.js)

**MAP****[ http://alphaapi.maplarge.com/JS/Test/?path=/v4/ui\map\Tests\Havings\StyleGreater.j**s](http://alphaapi.maplarge.com/JS/Test/?path=/v4/ui/map/Tests/Havings/StyleGreater.js)![image alt text](image_2.png)

**Example 3:**

**JS  ****[http://alphaapi.maplarge.com/v4/ui/map/Tests/Havings/StyleMixedAggregateRules.j**s](http://alphaapi.maplarge.com/v4/ui/map/Tests/Havings/StyleMixedAggregateRules.js)

**MAP****[ http://alphaapi.maplarge.com/JS/Test/?path=/v4/ui\map\Tests\Havings\StyleMixedAggregateRules.j**s](http://alphaapi.maplarge.com/JS/Test/?path=/v4/ui/map/Tests/Havings/StyleMixedAggregateRules.js)

**Example 4: Combined:**

**Here is an example that uses both multiple columns for having clause filtering and multiple columns for aggregate rules.**

**JS  ****[http://alphaapi.maplarge.com/v4/ui/map/Tests/Havings/MixedColumnRules.j**s](http://alphaapi.maplarge.com/v4/ui/map/Tests/Havings/MixedColumnRules.js)

**MAP****[ http://alphaapi.maplarge.com/JS/Test/?path=/v4/ui\map\Tests\Havings\MixedColumnRules.j**s](http://alphaapi.maplarge.com/JS/Test/?path=/v4/ui/map/Tests/Havings/MixedColumnRules.js)

## [E](http://alphaapi.maplarge.com/JS/Test/?path=/v4/ui/map/Tests/Havings/GreaterThan.js)xpanded Query API  

The expanded query api provides additional functionality that is currently limited to non mapping uses.  The namespace ml.query provides advanced functions that may be used to query data from any table using a[ fluent interface](http://en.wikipedia.org/wiki/Fluent_interface). Below is an example displaying how to define and run an advanced query.

<table>
  <tr>
    <td>var q = ml.query().from('maplarge/Donors').where('PartyCode', 'Contains', 'r').groupby('State');
q.select('State').select('Amount.sum').orderby('Amount');
q.run(function (data, query) {
    var dataDiv = document.getElementById('dataDiv');
    dataDiv.innerHTML = ml.util.toJSON(data);
});</td>
  </tr>
</table>


Return data is available in data object available in callback returned. This data object includes 2 properties, data and totals. The data property holds all the data other than non grouped aggregates which are held in totals property. Grouped aggregate data is returned in data object named after columnName_aggregateName.

**Return Object Description**

<table>
  <tr>
    <td>data</td>
    <td>object containing return data
{ "data":
{ “column1”:[“row1”,”row2”],
 “column2”:[“row1”,”row2”] }}</td>
  </tr>
  <tr>
    <td>totals</td>
    <td>non grouped aggregated data
{ "column1_sum":[100] }</td>
  </tr>
</table>


**Below are available method descriptions.**

<table>
  <tr>
    <td>
start
</td>
    <td>Begin at this row number.  Equivalent to the X in sql's LIMIT X,Y</td>
  </tr>
  <tr>
    <td>take</td>
    <td>Limit response to this many rows.  Equivalent to the Y in sql's LIMIT X,Y</td>
  </tr>
  <tr>
    <td>
select
</td>
    <td>If omitted, will return all non-geographic columns in the table.
If set, will only return specific columns.  Never any geographic ones
though.  Values are comma delimited.
the value "*" means all non-geo columns.  So if you wanted some
aggregates in there as well, you could do:
ex: select=*,Amount~sum,Amount~avg,Amount~uniq,Amount~count</td>
  </tr>
  <tr>
    <td>groupby
NOTE Missing fancy operator discussion
please add below</td>
    <td>If set, uses the value as a comma delimited list.  No aggregate
grouping.  Aggregate functions count, avg, sum, uniq are available
</td>
  </tr>
  <tr>
    <td>orderby</td>
    <td>If set, orders output data by that column.  Supports single.
Append ~asc, or ~desc to sort ascending or descending.  if not set,
assumes ascending order.
ex:  orderby=Amount~desc</td>
  </tr>
  <tr>
    <td>where</td>
    <td>standard where clauses, as defined by the api.
column~test~value|column~test~value_OR_column~test~value  etc.
_OR_ is or, | is and.

Between
Column row values between a minimum and maximum, inclusive
Bottom
A given number of smallest column row values.
Contains
Column rows that contain a value, as a substring.
ContainsAll
Column rows that contain all of a list of values, as a substring.
ContainsAny
Column rows that contain any of a list of values, as a substring.
ContainsNone
Column rows that do not contain any of a list of values, as a substring
ContainsOr
Column rows that contain any of a list of values, as a substring. Alias for "ContainsAny"
Equal
Column rows that equal exactly some value.
EqualAny
Column rows that equal any of a list of values.
EqualNone
Column rows that do not equal any of a list of values.
EqualNot
Column rows that do not equal a value exactly.
Greater
Column rows whose values are strictly greater than some value.
GreaterOR
Column rows whose values are greater than or equal to some value.
Less
Column rows whose values are strictly less than some value.
LessOR
Column rows whose values are less than or equal to some value.
NotBetween
Column row values not between a min and max, inclusive.
Top
A given number of largest column row values.
</td>
  </tr>
</table>


### **Example URLs / API Example:**

[http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&start=0&select=*,Amount~avg,Amount~sum,Amount~count&take=20&groupby=PartyCode](http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&start=0&select=*,Amount~avg,Amount~sum,Amount~count&take=20&groupby=PartyCode)

<table>
  <tr>
    <td>
var q = ml.create('div', "width:100%; height:700px; float:left;", document.body);
q.select(‘*’).select('Amount.avg').select('Amount.sum');
q.select('Amount.count').orderby('Amount');

q.run(function (data, query) {
    var dataDiv = document.createElement(‘div’);
    dataDiv.innerHTML = ml.util.toJSON(data);
});</td>
  </tr>
</table>


Since the row for a non grouped column contains the first value

encountered for that group, ordering that column will give you a min

or max for that group:

Amount column here is the max value for each group.

[http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&start=0&select=Amount,PartyCode,Amount~count,Amount~sum,Amount~avg,Amount~uniq&take=20&groupby=PartyCode&orderby=Amount~desc](http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&start=0&select=Amount,PartyCode,Amount~count,Amount~sum,Amount~avg,Amount~uniq&take=20&groupby=PartyCode&orderby=Amount~desc)

<table>
  <tr>
    <td>
var q = ml.query().from('maplarge/Donors').groupby(‘PartyCode’);
q.select(‘Amount’).select(‘PartyCode’).select('Amount.count').select('Amount.sum');
q.select('Amount.avg').select(‘Amount.uniq’);
q.take(‘20’).groupby(‘PartyCode’).orderby('Amount.desc');

q.run(function (data, query) {
    var dataDiv = ml.create('div', "width:100%; height:700px; float:left;",document.body);           
   dataDiv.innerHTML = ml.util.toJSON(data);
});</td>
  </tr>
</table>


Donations totals by day:

[http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=TimeStamp,Amount~count,Amount~sum,Amount~avg&groupby=TimeStamp&orderby=TimeStamp&take=760](http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=TimeStamp,Amount~count,Amount~sum,Amount~avg&groupby=TimeStamp&orderby=TimeStamp&take=760)

<table>
  <tr>
    <td>
var q = ml.query().from('maplarge/Donors');
q.select(‘TimeStamp’).select('Amount.count').select('Amount.sum');
q.select('Amount.avg').take(‘760’).groupby(‘TimeStamp’).orderby('TimeStamp’);

q.run(function (data, query) {
    var dataDiv = ml.create('div', "width:100%; height:700px; float:left;",document.body);           
   dataDiv.innerHTML = ml.util.toJSON(data);
});</td>
  </tr>
</table>


Donations by state.  Since it's ordered by Amount desc, the Amount

column contains the highest single donation for that state.

[http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=State,Amount~count,Amount~sum,Amount~avg,Amount&groupby=State&orderby=Amount~desc&take=100](http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=State,Amount~count,Amount~sum,Amount~avg,Amount&groupby=State&orderby=Amount~desc&take=100)

<table>
  <tr>
    <td>
var q = ml.query().from('maplarge/Donors');
q.select(‘State’).select('Amount.count').select('Amount.sum').select('Amount.avg');
q.select('Amount').groupby(‘State’).orderby(‘Amount.desc’).take(‘100’);

q.run(function (data, query) {
    var dataDiv = ml.create('div', "width:100%; height:700px; float:left;",document.body);           
   dataDiv.innerHTML = ml.util.toJSON(data);
});</td>
  </tr>
</table>


Donations by person:  

[http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=Contrib,FecOccEmp,Amount~sum,Amount~count&groupby=Contrib&take=100](http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=Contrib,FecOccEmp,Amount~sum,Amount~count&groupby=Contrib&take=100)

<table>
  <tr>
    <td>
var q = ml.query().from('maplarge/Donors');
q.select('Contrib').select('FecOccEm').select('Amount.sum').select('Amount.avg');
q.select('Amount').groupby('Contrib').take('100');

q.run(function (data, query) {
    var dataDiv = ml.create('div', "width:100%; height:700px; float:left;",document.body);           
   dataDiv.innerHTML = ml.util.toJSON(data);
});</td>
  </tr>
</table>


Donations by occupations:  [http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=FecOccEmp,Amount~sum,Amount~count&groupby=FecOccEmp&take=100](http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=FecOccEmp,Amount~sum,Amount~count&groupby=FecOccEmp&take=100)

<table>
  <tr>
    <td>
var q = ml.query().from('maplarge/Donors');
q.select('FecOccEmp').select('Amount.sum').select('Amount.count');
q.groupby('FecOccEmp').take('100');

q.run(function (data, query) {
    var dataDiv = ml.create('div', "width:100%; height:700px; float:left;",document.body);           
   dataDiv.innerHTML = ml.util.toJSON(data);
});</td>
  </tr>
</table>


Donations to candidates:

[http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=RecipientName,Amount~sum,Amount~count&groupby=RecipientName&take=100](http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=RecipientName,Amount~sum,Amount~count&groupby=RecipientName&take=100)

<table>
  <tr>
    <td>
var q = ml.query().from('maplarge/Donors');
q.select('FecOccEmp').select('Amount.sum').select('Amount.count');
q.groupby('FecOccEmp').take('100');

q.run(function (data, query) {
    var dataDiv = ml.create('div', "width:100%; height:700px; float:left;",document.body);           
   dataDiv.innerHTML = ml.util.toJSON(data);
});</td>
  </tr>
</table>


Donations to Barack Obama

[http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=RecipientName,Amount~sum,Amount~count&groupby=RecipientName&where=RecipientName~Contains~Barack](http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=RecipientName,Amount~sum,Amount~count&groupby=RecipientName&where=RecipientName~Contains~Barack)

Donations to Obama or McCain d[ire](http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=RecipientName,Amount~sum,Amount~count&groupby=RecipientName&where=RecipientName~Contains~Barack)ctly (no superpacs)

[http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=RecipientName,Amount~sum,Amount~count&groupby=RecipientName&where=RecipientName~ContainsAny~Obama,McCain|RecipientName~ContainsNone~Victory](http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=RecipientName,Amount~sum,Amount~count&groupby=RecipientName&where=RecipientName~ContainsAny~Obama,McCain%7CRecipientName~ContainsNone~Victory)

Donation totals to Obama or McCain directly (no superpacs) grouped by

amounts at or over $10,000

[http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=RecipientName,Amount,Amount~sum,Amount~count&groupby=RecipientName,Amount&where=RecipientName~ContainsAny~Obama,McCain|RecipientName~ContainsNone~Victory|Amount~GreaterOR~10000](http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=RecipientName,Amount,Amount~sum,Amount~count&groupby=RecipientName,Amount&where=RecipientName~ContainsAny~Obama,McCain%7CRecipientName~ContainsNone~Victory%7CAmount~GreaterOR~10000)

### **orderby:**

Group Ordering:

Order a column before grouping takes place. The group order takes place after all grouping and subtotals are calculated.

<table>
  <tr>
    <td>orderby(‘Column.asc,Column.sum.asc');</td>
  </tr>
</table>


Subtotals may be: sum, count, avg, uniq

String columns:

The sum and avg values for string columns are all based on string lengths.  So you can get things like character counts for columns (sum) or average size of a string for a column.  

orderby examples:

Total state donations, ordered by state:

[http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=State,Amount~count,Amount~sum,Amount~avg,Amount&groupby=State&orderby=State](http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=State,Amount~count,Amount~sum,Amount~avg,Amount&groupby=State&orderby=State)

<table>
  <tr>
    <td>
var q = ml.query().from('maplarge/Donors');
q.select('State','Amount.count','Amount.sum','Amount.avg','Amount');
q.groupby('State').orderby('State');

q.run(function (data, query) {
    var dataDiv = ml.create('div', "width:100%; height:700px; float:left;",document.body);           
   dataDiv.innerHTML = ml.util.toJSON(data);
});</td>
  </tr>
</table>


Total state donations, ordered by sum:

[http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=State,Amount~count,Amount~sum,Amount~avg,Amount&groupby=State&orderby=Amount~sum~desc](http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=State,Amount~count,Amount~sum,Amount~avg,Amount&groupby=State&orderby=Amount~sum~desc)

<table>
  <tr>
    <td>
var q = ml.query().from('maplarge/Donors');
q.select('State','Amount.count','Amount.sum','Amount.avg','Amount');
q.groupby('State').orderby('Amount.sum.desc');

q.run(function (data, query) {
    var dataDiv = ml.create('div', "width:100%; height:700px; float:left;",document.body);           
   dataDiv.innerHTML = ml.util.toJSON(data);
});</td>
  </tr>
</table>


Same, but just the top 10:

[http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=State,Amount~count,Amount~sum,Amount~avg,Amount&groupby=State&orderby=Amount~desc,Amount~sum~desc](http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=State,Amount~count,Amount~sum,Amount~avg,Amount&groupby=State&orderby=Amount~desc,Amount~sum~desc)

<table>
  <tr>
    <td>
var q = ml.query().from('maplarge/Donors');
q.select('State','Amount.count','Amount.sum','Amount.avg','Amount');
q.groupby('State').orderby('Amount.desc,Amount.sum.desc').take('10');

q.run(function (data, query) {
    var dataDiv = ml.create('div', "width:100%; height:700px; float:left;",document.body);           
   dataDiv.innerHTML = ml.util.toJSON(data);
});</td>
  </tr>
</table>


Because you're sorting by Amount first, the Amount value will be the

largest individual donation for that state.

Grouping:

groupby=numbercolumn~rules~rulestring

The rule string is in the format min/max|min/max|...|catch

[http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=Amount~sum,Amount~count,Amount~key&groupby=Amount~rules~0/1000|1000/10000|10000/100000|100000/1000000|catch](http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=Amount~sum,Amount~count,Amount~key&groupby=Amount~rules~0/1000%7C1000/10000%7C10000/100000%7C100000/1000000%7Ccatch)

<table>
  <tr>
    <td>
var q = ml.query().from('maplarge/Donors');
q.select('Amount.sum','Amount.count','Amount.key','Amount');
q.groupby('Amount.rules.0/1000|1000/10000|10000/100000|100000/1000000|catch');

q.run(function (data, query) {
    var dataDiv = ml.create('div', "width:100%; height:700px; float:left;",document.body);           
   dataDiv.innerHTML = ml.util.toJSON(data);
});</td>
  </tr>
</table>


Rules do not have to be in numeric order, it will just run down the

list and go with whichever one is matched first.  Currently, the catch is required to group non-matching rows. Each min max pair is [min, max)  That's the

range of min (inclusive) to max (exclusive).

Grouping by string:

groupby=stringcolumn~FirstLetter

groupby=stringcolumn~FirstLetterNoCase

[http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=State~count,State~key&groupby=State~FirstLetter&orderby=State~key~asc](http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=State~count,State~key&groupby=State~FirstLetter&orderby=State~key~asc)

<table>
  <tr>
    <td>
var q = ml.query().from('maplarge/Donors');
q.select('State.count','State.key');
q.groupby('State.FirstLetter').orderby('State.key.asc');

q.run(function (data, query) {
    var dataDiv = ml.create('div', "width:100%; height:700px; float:left;",document.body);           
   dataDiv.innerHTML = ml.util.toJSON(data);
});</td>
  </tr>
</table>


Group Keys:

You've probably noticed from the links above.. When you are grouping

on a column, you now have an aggregator function called "key" that can

be used like sum, avg, count, and uniq.

This will print out a column for the column that has they"key" value

that was used for hashing into that group.  For instance, for the

FirstLetter state example above, the "key" for each group was the

first letter.  If you were doing amount deciles:

[http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=Amount~sum,Amount~count,Amount~key&groupby=Amount~value~10](http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=Amount~sum,Amount~count,Amount~key&groupby=Amount~value~10)

<table>
  <tr>
    <td>
var q = ml.query().from('maplarge/Donors');
q.select('Amount.sum','Amount.count','Amount.key');
q.groupby('Amount.value.10');

q.run(function (data, query) {
    var dataDiv = ml.create('div', "width:100%; height:700px; float:left;",document.body);           
   dataDiv.innerHTML = ml.util.toJSON(data);
});</td>
  </tr>
</table>


Returns the "groups" that actually had data, likea normal SQL result set, also you have the "key" in that query so you can see which decile each row represents.  For a 2D example:

[http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=Amount~sum,Amount~count,Amount~key,TimeStamp~key&groupby=Amount~value~10,TimeStamp~value~10](http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=Amount~sum,Amount~count,Amount~key,TimeStamp~key&groupby=Amount~value~10,TimeStamp~value~10)

<table>
  <tr>
    <td>
var q = ml.query().from('maplarge/Donors');
q.select('Amount.sum','Amount.count','Amount.key','TimeStamp.key');
q.groupby('Amount.value.10','TimeStamp.value.10');  LEEEE

q.run(function (data, query) {
    var dataDiv = ml.create('div', "width:100%; height:700px; float:left;",document.body);           
   dataDiv.innerHTML = ml.util.toJSON(data);
});</td>
  </tr>
</table>


Here both key columns are included, so even if the results aren't

ordered (as they aren't here) you can still draw in points by using

the two as an x,y coordinate.

Here's one with both strings and numbers:

[http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=Amount~sum,Amount~count,Amount~key,State~key&groupby=Amount~value~10,State~FirstLetter&orderby=State~key~asc](http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=Amount~sum,Amount~count,Amount~key,State~key&groupby=Amount~value~10,State~FirstLetter&orderby=State~key~asc)

<table>
  <tr>
    <td>
var q = ml.query().from('maplarge/Donors');
q.select('Amount.sum','Amount.count','Amount.key','State.key');
q.groupby('Amount.value.10','State.FirstLetter').orderby('State.key.asc');

q.run(function (data, query) {
    var dataDiv = ml.create('div', "width:100%; height:700px; float:left;",document.body);           
   dataDiv.innerHTML = ml.util.toJSON(data);
});</td>
  </tr>
</table>


For rules groupings specifically, I have the "catch" all group keyed

to be -1.

[http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=Amount~sum,Amount~count,Amount~key&groupby=Amount~count~10](http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=Amount~sum,Amount~count,Amount~key&groupby=Amount~count~10)

[http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=Amount,Amount~count,Amount~key&groupby=Amount~count~20&orderby=Amount](http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=Amount,Amount~count,Amount~key&groupby=Amount~count~20&orderby=Amount)

[ht](http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=Amount~sum,Amount~count,Amount~key&groupby=Amount~count~10)[tp://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=Amount,Amount~count,Amount~min,Amount~max,Amount~key&groupby=Amou](http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=Amount,Amount~count,Amount~min,Amount~max,Amount~key&groupby=Amount~count~20&orderby=Amount)[nt~](http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=Amount,Amount~count,Amount~key&groupby=Amount~count~20&orderby=Amount)[count~20&orderby=Amount](http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=Amount,Amount~count,Amount~min,Amount~max,Amount~key&groupby=Amount~count~20&orderby=Amount)

Groupby Value		

[http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=Amount,Amount~count,Amount~key&](http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=Amount,Amount~count,Amount~key&groupby=Amount~count~10&orderby=Amount)[gro](http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=Amount,Amount~count,Amount~min,Amount~max,Amount~key&groupby=Amount~count~20&orderby=Amount)[upby=Amount~count~10&orderby=Amount](http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=Amount,Amount~count,Amount~key&groupby=Amount~count~10&orderby=Amount)

[http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=Amount,Amount~count,Amount~min,Amount~max,Amount~key&groupby=Amount~count~20&](http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=Amount,Amount~count,Amount~min,Amount~max,Amount~key&groupby=Amount~count~20&orderby=Amount)[ord](http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=Amount,Amount~count,Amount~key&groupby=Amount~count~10&orderby=Amount)[erby=Amount](http://alphaapi.maplarge.com/Table/Query?from=maplarge/Donors&select=Amount,Amount~count,Amount~min,Amount~max,Amount~key&groupby=Amount~count~20&orderby=Amount)

# Table Management

## Table Permissions: Users, Groups, and Tables

Users and tables for your account can be managed via the accounting interface at[ http://alphaapi.maplarge.com/accsys](http://alphaapi.maplarge.com/accsys)

Within your account context, you can create user accounts and create, manage, and upload table data. Tables can be secured to varying degrees once uploaded. They may be:

* Visible  and useable to the entire public ("**Public**")

* Hidden in the accounting interface but still usable for mapping ("**PublicUnlisted**")

* Protected, hidden and only usable to users explicitly given permissions ("**Private**")

Users may be managed by accessing the "Account Management" section. Users must be added to groups that define permissions and may be managed as a whole.

After creating a group and assigning its permissions, the users in your account will be presented so that they may be added or removed from the groups. What tables a user may interact with are defined by their group membership.

.

## Table Management UI

In addition to user management, tables may be imported and managed via the "Table management" section. Here, you are presented with the tables in your account (that you have access to), and also all of the public tables that are available.

For example, if you login at[ http://e.maplarge.com/accsys/managetables.html](http://e.maplarge.com/accsys/managetables.html)

You will see a screen like the one below![image alt text](image_3.png)

Some of the major functions are described below:![image alt text](image_4.png) 	**Delete **– delete the table.![image alt text](image_5.png) 	**Visualize- **visualize the table data in various maps and graphs.![image alt text](image_6.png) 	**Interactive Map** – create interactive maps with this table’s data and other table’s data.

## Table Creation Example

Tables are created via the  "Create new table" tab. In this interface, you may upload zipped files containing data for your tables. All geographic coordinates are expected to be in[ WGS84](https://en.wikipedia.org/wiki/World_Geodetic_System) format (ie standard Lat, Lng, like 34.012345, -84.123456789)

**Points: **Point tables must be in CSV format you can just include two columns named "Latitude" and “Longitude” or if you name the columns something else the system will look at all column values and make its best guess about the data types using “schemaless” type discovery.  

**Lines and Polygons: **These files may be in either[ SHP file](http://en.wikipedia.org/wiki/SHP) or[ CSV format](http://en.wikipedia.org/wiki/Comma-separated_values) with map data (Lines, and Polygons) defined in the "Well Known Text" markup language. WKT makes it possible for the shape information to be easily included as a column in a standard CSV file[ http://en.wikipedia.org/wiki/Well-known_text](http://en.wikipedia.org/wiki/Well-known_text)

For reference, an example file of world nations can be found here:[http://alphaapi.maplarge.com/docs/WKT_CSVSample.zip](http://alphaapi.maplarge.com/docs/WKT_CSVSample.zip)

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


# Widget API					

**Overview**

The Widget API has several functions for loading, saving and viewing Maps and Layers. These are created and saved in the Map Editor or elsewhere. All API requests are sent using GET parameters through the URL, and all responses will return a JSON formatted string, optionally wrapped in a JSONP style callback function. The Thumbnail set of functions only return images on the other hand, IMAGE/PNG. Typically, users of this api are using it for server to server communication but it can also be used to create client side UI components like those described in the account system above. 	

## Making Requests

Requests are made to a URL that is determined by the action that you are wanting to invoke.

EX: Widget Thumbnail invocation:[ http://alphaapi.maplarge.com](http://alphaapi.maplarge.com/Remote/CreateTable?GET-VARIABLES)**[/Widget/Thumbnai**l](http://alphaapi.maplarge.com/Remote/CreateTable?GET-VARIABLES)[?GET-VARIABLES](http://alphaapi.maplarge.com/Remote/CreateTable?GET-VARIABLES)

<table>
  <tr>
    <td>Denotes required field</td>
    <td></td>
  </tr>
</table>


Every request sent to the API is required to have two parameters for authentication purposes.  There is also an optional callback parameter that will signal to the API to return in JSONP.

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


<table>
  <tr>
    <td>Thumbnail</td>
  </tr>
</table>


**Request Parameters**

<table>
  <tr>
    <td>id</td>
    <td>long</td>
    <td>The ID of  the widget map or layer the thumbnail creation.</td>
  </tr>
  <tr>
    <td>width</td>
    <td>int</td>
    <td>The width in pixels of the desired thumbnail. Default is 128.</td>
  </tr>
  <tr>
    <td>height</td>
    <td>int</td>
    <td>The height in pixels of the desired thumbnail. Default is 128.</td>
  </tr>
  <tr>
    <td>preserveratio</td>
    <td>bool</td>
    <td>A flag for preserving the aspect ratio of the thumbnail. Default is true.</td>
  </tr>
  <tr>
    <td>cropthumb</td>
    <td>bool</td>
    <td>This will return a cropped thumbnail of size, width and height. Default is false.</td>
  </tr>
  <tr>
    <td>layeronly</td>
    <td>bool</td>
    <td>A flag to determine whether the thumbnail is rendered with an underlying base map layer or not. Default is true.</td>
  </tr>
  <tr>
    <td>padwithmap</td>
    <td>bool</td>
    <td>A flag to set whether or not the padded areas are covered with a basemap layer. Default is true.</td>
  </tr>
  <tr>
    <td>transparentpad</td>
    <td>bool</td>
    <td>A flag to set whether or not image padding will be transparent. Default is false.</td>
  </tr>
  <tr>
    <td>backgroundcolor</td>
    <td>string</td>
    <td>This sets the color of any padded areas that do not have a base map layer. Accepts "Named Colors". Default is “lightgray”.</td>
  </tr>
  <tr>
    <td>opacitylevel</td>
    <td>float</td>
    <td>This sets the alpha or transparency level for the entire thumbnail. Valid values are from 0 to 1. Default is 1.0.</td>
  </tr>
</table>


**Action Specific Response Values**

<table>
  <tr>
    <td>PNG Image Returned</td>
    <td>Image/PNG</td>
    <td>The thumbnail image requested.</td>
  </tr>
</table>


If any errors are encountered during the thumbnail creation, an error image is returned that matches the requested width and height..

<table>
  <tr>
    <td>Thumbnail (Override: Latitude/Longitude Bounding Box)</td>
  </tr>
</table>


**Request Parameters**

<table>
  <tr>
    <td>id</td>
    <td>long</td>
    <td>The ID of  the widget map or layer the thumbnail creation.</td>
  </tr>
  <tr>
    <td>latsw</td>
    <td>float</td>
    <td>The latitude in degrees of the southwest corner of the box.</td>
  </tr>
  <tr>
    <td>lngsw</td>
    <td>float</td>
    <td>The longitude in degrees of the southwest corner of the box.</td>
  </tr>
  <tr>
    <td>latne</td>
    <td>float</td>
    <td>The latitude in degrees of the northeast corner of the box.</td>
  </tr>
  <tr>
    <td>lngne</td>
    <td>float</td>
    <td>The longitude in degrees of the northeast corner of the box.</td>
  </tr>
</table>


All the rest of the GET parameters are the same as the above Thumbnail function description.

**Action Specific Response Values**

<table>
  <tr>
    <td>PNG Image Returned</td>
    <td>Image/PNG</td>
    <td>The thumbnail image requested.</td>
  </tr>
</table>


# DiskUsage API					

**Overview**

The DiskUsage API has several functions for configuring, starting, and stopping the DiskUsage monitoring worker threads. There is also a utility function for viewing drive information for the server’s disk drive that hosts it’s App_Data folder. The DiskUsage manager is system that will monitor disk usage and send status or warning emails if enabled based on a size limit threshold setting. It is also designed to keep the "Downloads" folder in the App_Data folder, cleaned up automatically if enabled, every N days.

## Making Requests

Requests are made to a URL that is determined by the action that you are wanting to invoke.

EX: Configure the DiskLimits manager:[ http://alphaapi.maplarge.com](http://alphaapi.maplarge.com/Remote/CreateTable?GET-VARIABLES)**[/Remote/DiskLimitsConfi**g](http://alphaapi.maplarge.com/Remote/CreateTable?GET-VARIABLES)[?GET-VARIABLES](http://alphaapi.maplarge.com/Remote/CreateTable?GET-VARIABLES)

<table>
  <tr>
    <td>Denotes required field</td>
    <td></td>
  </tr>
</table>


Every request sent to the API is required to have two parameters for authentication purposes.  There is also an optional callback parameter that will signal to the API to return in JSONP.

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


<table>
  <tr>
    <td>DiskLimitsConfig</td>
  </tr>
</table>


**Request Parameters**

<table>
  <tr>
    <td>configJson</td>
    <td>String</td>
    <td>A string that is in the format of a json. This json can contain one or all of the settings to alter. The format looks like this.
{
"diskspacethresholdbytes":  "100000000000",  	 
 "scanratems": 300000,
 "adminemails": [],
 "emailuser": "",
 "emailpass": "",
 "statusemailratehours": 0,
 "autostart": true
}

Defaults shown above.</td>
  </tr>
</table>


Description of configuration parameters

  **diskspacethresholdbytes**:,	The cut off limit for when forced disk cleanups and warning emails occur.

  **scanratems**:,			The scan rate in milliseconds for disk space checks.

  **adminemails**: [],		A list of send email usernames, they receive all warning/status emails.

  **emailuser**:, 			The username of the email sendee, it must be a gmail account.

  **emailpass**:,			The password of the email sendee.

  **statusemailratehours**:,	The rate at which disk usage status emails are sent, 0 disables.

  **autostart**: true		Whether or not the DiskLimits manager starts up on server start.

**Action Specific Response Values**

None

These configuration options will take place on the fly without having to start or stop the managers.

<table>
  <tr>
    <td>LimitWarningsStart</td>
  </tr>
</table>


**Request Parameters**

None

**Action Specific Response Values**

None

The fucntion  will start the DiskLimits manager worker thread if not already started.

<table>
  <tr>
    <td>LimitWarningsStop</td>
  </tr>
</table>


**Request Parameters**

None

**Action Specific Response Values**

None

The function will stop the DiskLimits manager worker thread if not already stopped. If this is stopped no warning emails will go out.

<table>
  <tr>
    <td>DiskSpaceConfig</td>
  </tr>
</table>


**Request Parameters**

<table>
  <tr>
    <td>configJson</td>
    <td>String</td>
    <td>A string that is in the format of a json. This json can contain one or all of the settings to alter. The format looks like this.
{
 "cleanupscanratems": 300000,
 "fileagelimitdays": 1,
 "fileageoffsethours": 6,
 "autostart": true
}</td>
  </tr>
</table>


Description of configuration parameters

  **cleanupscanratems**:, 	The scan rate in milliseconds for disk space remaining and age limit.

  **fileagelimitdays**:,		How often a disk cleanups should occur, forced cleanups will occur if needed.

  **fileageoffsethours**:,		The minimum file age of a file to be deleted.

  **autostart**: true		Whether or not the DiskSpace manager starts up on server start.

**Action Specific Response Values**

None

These configuration options will take place on the fly without having to start or stop the managers.

<table>
  <tr>
    <td>AutoCleanupStart</td>
  </tr>
</table>


**Request Parameters**

None

**Action Specific Response Values**

None

The function will start the DiskSpace manager worker thread if not already started.

<table>
  <tr>
    <td>AutoCleanupStop</td>
  </tr>
</table>


**Request Parameters**

None

**Action Specific Response Values**

None

The function will stop the DiskSpace manager worker thread if not already stopped. If this is stopped no ‘Downloads" folder clean ups will occur.

<table>
  <tr>
    <td>GetAppDataDriveInfo</td>
  </tr>
</table>


**Request Parameters**

None

**Action Specific Response Values**

<table>
  <tr>
    <td>driveinfo</td>
    <td>List</td>
    <td>A list is returned containing the following fields. This info is for the drive that houses the App_Data folder.
"availablefreespace" = integer,
"totalfreespace" = integer,
"totalsize" = integer,
“name" = string,
"driveformat" = string</td>
  </tr>
</table>


<table>
  <tr>
    <td></td>
  </tr>
</table>


* * *


For Additional information see:

[http://maplarge.com/API](http://maplarge.com/API)

[http://maplarge.com/API/Tutorial](http://maplarge.com/API/Tutorial)

[http://maplarge.com/Example/List](http://maplarge.com/Example/List)

© MapLarge, Inc. 2013

