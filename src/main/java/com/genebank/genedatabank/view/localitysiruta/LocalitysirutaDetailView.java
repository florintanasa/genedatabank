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
import io.jmix.flowui.component.checkbox.JmixCheckbox;
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
    @Autowired
    private MessageBundle messageBundle;
    @Autowired
    private Notifications notifications;
    @ViewComponent
    private JmixCheckbox checkbox;
    @ViewComponent
    private VerticalLayout mapContainer;
    private static final double DEFAULT_LATITUDE = 46.009628;
    private  static final double DEFAULT_LONGITUDE = 24.456255;
    private static final int ZOOM_LEVEL = 7;

    //For GoogleMap addon from  https://github.com/FlowingCode/GoogleMapsAddon
    private GoogleMap gmaps;
    // For leaflet with OpenStreetMapMap from https://github.com/xdev-software/vaadin-maps-leaflet-flow
    private LMap map;
    @Subscribe("checkbox")
    public void onCheckboxClick(final ClickEvent<Checkbox> event) {
        String message_1 = messageBundle.getMessage("google_map");
        String message_2 = messageBundle.getMessage("openstreetmap");
        if (Boolean.TRUE.equals(event.getSource().getValue())) {
            notifications.create(message_1).show();
            initGoogleMap();
            remMapFromContainer();
            addGoogleMapToContainer();
            addGoogleMapMarker();
        } else {
            notifications.create(message_2).show();
            initMap();
            remGoogleMapFromContainer();
            addMapToContainer();
            addMapMarker();
        }
    }
    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {
        if (checkbox.getValue()) {
            initGoogleMap();
            addGoogleMapToContainer();
            addGoogleMapMarker();
        }
        else {
            initMap();
            addMapToContainer();
            addMapMarker();
        }
    }
    private void addGoogleMapMarker() {
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
    private void addMapMarker() {
        Localitysiruta localitysiruta = getEditedEntity();
        if (localitysiruta.getLatitude() != null && localitysiruta.getLongitude() != null) {
            LMarker localityMarker = new LMarker(localitysiruta.getLatitude(), localitysiruta.getLongitude());
            String locality = localitysiruta.getName();
            localityMarker.setPopup("<h4>"+locality+"</h4>");
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
    private void remMapFromContainer() {
        mapContainer.remove(map);
    }
    private void drawCenterMarkers() {
        String message_1 = messageBundle.getMessage("center");
        LMarker markerCenter = new LMarker(DEFAULT_LATITUDE, DEFAULT_LONGITUDE);
        markerCenter.setPopup("<h4>"+message_1+"</h4>");
        map.addLComponents(markerCenter);
    }
    private void initGoogleMap() {
        String apiKey = "";//add your api key from Google Map
        gmaps = new GoogleMap(apiKey, null, null);
        gmaps.setMapType(GoogleMap.MapType.ROADMAP);
        gmaps.setCenter(new LatLon(DEFAULT_LATITUDE, DEFAULT_LONGITUDE));
        gmaps.setZoom(ZOOM_LEVEL-1);//low zoom little for Google Maps
        gmaps.setSizeFull();
    }
    private void addGoogleMapToContainer() {
        mapContainer.add(gmaps);
    }
    private void remGoogleMapFromContainer() {
        mapContainer.remove(gmaps);
    }
    private void drawGoogleCenterMarkers() {
        String message_1 = messageBundle.getMessage("center");
        String message_2 = messageBundle.getMessage("move_me");
        GoogleMapMarker centerMarker = gmaps.addMarker(message_1, gmaps.getCenter(), true, Markers.ORANGE_DOT);
        centerMarker.addInfoWindow(message_2);
        centerMarker.addDragEndEventListener(event ->  {
           getEditedEntity().setLongitude(event.getLongitude());
           getEditedEntity().setLatitude(event.getLatitude());
       });
    }
}