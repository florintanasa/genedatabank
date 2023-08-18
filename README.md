# GeneDataBank
GeneDataBank is a genetic plant software database (Work is in progress).
  
For this project I used:  
OS - [Debian GNU/LINUX](https://www.debian.org/)  
Intellij IDEA CE from [JET BRAINS](https://www.jetbrains.com/idea/) with Java 17 from [Amazon Corretto](https://aws.amazon.com/corretto/?filtered-posts.sort-by=item.additionalFields.createdDate&filtered-posts.sort-order=desc)  
Framework Jmix 2.0 from [JMIX](https://www.jmix.io//)  

For the map I used OpenStreetMap, [leaflet addon to Vaadin](https://vaadin.com/directory/component/leafletmap-for-vaadin) , XDEV SOFTWARE working on this at [GITHUB](https://github.com/xdev-software/vaadin-maps-leaflet-flow) and made a great work  
I used version 3.0.0 because with 3.0.1 not work, I have strange errors :(
  
![Screen shoot OpenStreetMap](./img/Jmix_with_OpenStreetMap.png)

and for Google Maps I used [Google Maps Addon to Vaadin](https://vaadin.com/directory/component/google-maps-addon), thank you [Flowing Code](https://www.flowingcode.com/en/) for your great work  
  
![Screen shoot Google Maps](./img/Jmix_with_GoogleMaps.png)  
  

> **Warning**  
> Is need to add your api key, in the class **LocalitysirutaDetailView**, from Google Maps if you wish to have the maps without watermark and with full options. 
  
```java
public class LocalitysirutaDetailView extends StandardDetailView<Localitysiruta> {
    //...
    String apiKey = "";//add your api key from Google Map
}
```

Do determine the altitude I used the api and service from [Open Topo Data](https://www.opentopodata.org/) and for got the elevation from json response I used [Gson](https://en.wikipedia.org/wiki/Gson) library.
This is possible if not exist data information for latitude and longitude in the Locality screen and using Google Maps because the marker is draggable.
  
```java
public class LocalitysirutaDetailView extends StandardDetailView<Localitysiruta> {
    //...
    static int getElevation(Double Latitude, Double Longitude) throws IOException {
     String strLatitude = String.valueOf(Latitude);
     String strLongitude = String.valueOf(Longitude);

     URL url = new URL("https://api.opentopodata.org/v1/mapzen?locations=" + strLatitude + "," + strLongitude);

     HttpURLConnection connection = (HttpURLConnection) url.openConnection();
     connection.setRequestMethod("GET");
     connection.setRequestProperty("Content-Type", "application/json");
     connection.setRequestProperty("Accept", "application/json");

     try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
         StringBuilder response = new StringBuilder();
         String responseLine;
         while ((responseLine = bufferedReader.readLine()) != null) {
             response.append(responseLine.trim());
         }

         JsonElement jsonElement = JsonParser.parseString(response.toString());
         JsonObject jsonObject = jsonElement.getAsJsonObject();
         JsonArray jsonArray = jsonObject.getAsJsonArray("results");

         JsonObject jsonObject1 = new Gson().fromJson(jsonArray.asList().get(0).toString(), JsonObject.class);

         String elev = String.valueOf(jsonObject1.get("elevation"));
         double elevation = Double.parseDouble(elev);

         return (int) elevation;
     }
 }
```

The code is easy to be adapted to use Google services for elevation because the URL request have the same form:
```html
https://maps.googleapis.com/maps/api/elevation/json?locations=39.7391536,-104.9847034&key=apiKey
```  
