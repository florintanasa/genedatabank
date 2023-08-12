package com.genebank.genedatabank.view.localitysiruta;

import com.flowingcode.vaadin.addons.googlemaps.GoogleMap;
import com.flowingcode.vaadin.addons.googlemaps.GoogleMapMarker;
import com.flowingcode.vaadin.addons.googlemaps.LatLon;
import com.flowingcode.vaadin.addons.googlemaps.Markers;
import com.genebank.genedatabank.entity.Localitysiruta;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import io.jmix.flowui.Notifications;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import software.xdev.vaadin.maps.leaflet.flow.LMap;
import software.xdev.vaadin.maps.leaflet.flow.data.LCenter;
import software.xdev.vaadin.maps.leaflet.flow.data.LMarker;
import software.xdev.vaadin.maps.leaflet.flow.data.LTileLayer;

@Route(value = "localitysirutas/:id", layout = MainView.class)
@ViewController("Localitysiruta.detail")
@ViewDescriptor("localitysiruta-detail-view.xml")
@EditedEntityContainer("localitysirutaDc")
public class LocalitysirutaDetailView extends StandardDetailView<Localitysiruta> {
    @ViewComponent
    private VerticalLayout mapContainer;
    private static final double DEFAULT_LATITUDE = 46.009628;
    private  static final double DEFAULT_LONGITUDE = 24.456255;
    private static final int ZOOM_LEVEL = 7;

    //For GoogleMap addon from  https://github.com/FlowingCode/GoogleMapsAddon
    private GoogleMap gmaps;
    @Subscribe
    protected void onInit(InitEvent event) {
        initGoogleMap();
        addGoogleMapToContainer();
    }
    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {
        Localitysiruta localitysiruta = getEditedEntity();
        if (localitysiruta.getLatitude() != null && localitysiruta.getLongitude() != null) {
            GoogleMapMarker localityMarker = gmaps.addMarker(localitysiruta.getName(),
                    new LatLon(localitysiruta.getLatitude(), localitysiruta.getLongitude()),
                    false, Markers.LIGHTBLUE_DOT);
           String locality = localitysiruta.getName();
           localityMarker.addInfoWindow("<h2>"+locality+"</h2>");
        }
        else drawGoogleCenterMarkers();
    }
    private void initGoogleMap() {
        String apiKey = "";//add your api key from Google Map
        gmaps = new GoogleMap(apiKey, null, null);
        gmaps.setMapType(GoogleMap.MapType.ROADMAP);
        gmaps.setCenter(new LatLon(DEFAULT_LATITUDE, DEFAULT_LONGITUDE));
        gmaps.setZoom(ZOOM_LEVEL);
        gmaps.setSizeFull();
    }
    private void addGoogleMapToContainer() {
        mapContainer.add(gmaps);
    }
    private void drawGoogleCenterMarkers() {
        GoogleMapMarker centerMarker = gmaps.addMarker("Centru", gmaps.getCenter(), true, Markers.ORANGE_DOT);
        centerMarker.addInfoWindow("<h2>Mută-mă &#128540;</h2>");
        centerMarker.addDragEndEventListener(event ->  {
           getEditedEntity().setLongitude(event.getLongitude());
           getEditedEntity().setLatitude(event.getLatitude());
       });
    }

    // For leaflet with OpenStreetMapMap from https://github.com/xdev-software/vaadin-maps-leaflet-flow
/*
    private LMap map;
    @Subscribe
    protected void onInit(InitEvent event) {
        initMap();
        addMapToContainer();
    }
    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {
        Localitysiruta localitysiruta = getEditedEntity();

        if (localitysiruta.getLatitude() != null && localitysiruta.getLongitude() != null) {
            LMarker localityMarker = new LMarker(localitysiruta.getLatitude(), localitysiruta.getLongitude());
            localityMarker.setPopup(localitysiruta.getName());
            map.addLComponents(localityMarker);
        }
        else drawCenterMarkers();
    }
    private void initMap() {
        map = new LMap();
        map.setZoom(ZOOM_LEVEL);
        map.setCenter(new LCenter(DEFAULT_LATITUDE, DEFAULT_LONGITUDE));
        map.setTileLayer(LTileLayer.DEFAULT_OPENSTREETMAP_TILE);
        map.setSizeFull();
    }
    private void addMapToContainer() {
        mapContainer.add(map);
    }
    private void drawCenterMarkers() {
        LMarker markerCenter = new LMarker(DEFAULT_LATITUDE, DEFAULT_LONGITUDE);
        markerCenter.setPopup("Punctul de centrare");
        map.addLComponents(markerCenter);
    }
*/
}