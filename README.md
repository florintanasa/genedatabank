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
> Is need to add your api key, in the class **LocalitysirutaDetailView**, from Google Maps if you wish to have the maps without watermark and with full options, looking at **initGoogleMap()** method  
  
```java
public class LocalitysirutaDetailView extends StandardDetailView<Localitysiruta> {
    //...
    private void initGoogleMap() {
        String apiKey = "";//add your api key from Google Maps
        //...
    }
}
```
