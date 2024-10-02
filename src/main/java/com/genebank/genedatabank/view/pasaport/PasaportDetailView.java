package com.genebank.genedatabank.view.pasaport;

import com.flowingcode.vaadin.addons.googlemaps.GoogleMap;
import com.flowingcode.vaadin.addons.googlemaps.GoogleMapMarker;
import com.flowingcode.vaadin.addons.googlemaps.LatLon;
import com.flowingcode.vaadin.addons.googlemaps.Markers;
import com.genebank.genedatabank.entity.*;

import com.genebank.genedatabank.view.main.MainView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.shared.Tooltip;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import io.jmix.core.FileRef;
import io.jmix.core.FileStorage;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.data.Sequence;
import io.jmix.data.Sequences;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.component.details.JmixDetails;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.image.JmixImage;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.download.Downloader;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.view.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import software.xdev.vaadin.maps.leaflet.flow.LMap;
import software.xdev.vaadin.maps.leaflet.flow.data.LCenter;
import software.xdev.vaadin.maps.leaflet.flow.data.LMarker;
import software.xdev.vaadin.maps.leaflet.flow.data.LTileLayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;


/**
 * @author : Florin Tanasă
 * @since : 04.09.2023
**/
@Route(value = "pasaports/:id", layout = MainView.class)
@ViewController("Pasaport.detail")
@ViewDescriptor("pasaport-detail-view.xml")
@EditedEntityContainer("pasaportDc")
public class PasaportDetailView extends StandardDetailView<Pasaport> {
    @Autowired
    private Downloader downloader;
    @Autowired
    private Sequences sequences;
    @Autowired
    private Notifications notifications;
    @ViewComponent
    private MessageBundle messageBundle;
    @Autowired
    private FileStorage fileStorage;
    @ViewComponent
    private VerticalLayout mapContainer;
    @ViewComponent
    private JmixCheckbox checkboxMap;
    @ViewComponent
    private JmixCheckbox checkboxElevation;
    @ViewComponent
    private JmixImage<FileRef> imageProbe;
    @ViewComponent
    private DataGrid<SysFile> probeImagesDataGrid;
    @ViewComponent
    private JmixButton downloadBtn;
    @ViewComponent
    private EntityComboBox<Localitysiruta> localitysirutasComboBox;
    @ViewComponent
    private JmixCheckbox checkboxTemp;
    // For leaflet with OpenStreetMapMap from https://github.com/xdev-software/vaadin-maps-leaflet-flow
    private LMap map;
    private GoogleMap gmaps;
    String apiKey = "";//add your api key from Google Map
    //I declared some default static variable (constants) for center and zoom the maps
    private static final double DEFAULT_LATITUDE = 46.009628;
    private  static final double DEFAULT_LONGITUDE = 24.456255;
    private static final int ZOOM_LEVEL = 7;
    //To log some messages
    private static final Logger log = LoggerFactory.getLogger(PasaportDetailView.class);
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private UiComponents uiComponents;
    @ViewComponent
    private TypedTextField<String> doiField;
    @ViewComponent
    private EntityComboBox<Institute> institutesComboBox_1;
    @ViewComponent
    private TypedTextField<String> tempnumbField;
    @ViewComponent
    private TypedTextField<String> accenumbField;
    @ViewComponent
    private TypedTextField<String> collnumbField;
    @ViewComponent
    private TypedTextField<String> collmissidField;
    @ViewComponent
    private EntityComboBox<Taxonomy> taxonomiesComboBox;
    @ViewComponent
    private TypedTextField<String> accnameField;
    @ViewComponent
    private TypedTextField<String> acqdateField;
    @ViewComponent
    private TypedTextField<String> origdateField;
    @ViewComponent
    private EntityComboBox<Country> countriesComboBox;
    @ViewComponent
    private EntityComboBox<Countysiruta> countysirutasComboBox;
    @ViewComponent
    private TypedTextField<Double> latitudeField;
    @ViewComponent
    private TypedTextField<Double> longitudeField;
    @ViewComponent
    private TypedTextField<Integer> elevationField;
    @ViewComponent
    private EntityComboBox<Georefmeth> georefmethsComboBox;
    @ViewComponent
    private EntityComboBox<Sampstat> sampstatsComboBox;
    @ViewComponent
    private TypedTextField<String> colldateField;
    @ViewComponent
    private TypedTextField<String> ancestField;
    @ViewComponent
    private EntityComboBox<Institute> institutesComboBox;
    @ViewComponent
    private TypedTextField<String> donornumbField;
    @ViewComponent
    private TypedTextField<String> othernumbField;
    @ViewComponent
    private TypedTextField<String> remarksField;
    @ViewComponent
    private TypedTextField<String> acceurlField;
    @ViewComponent
    private EntityComboBox<Acceconf> acceconfsComboBox;
    @ViewComponent
    private EntityComboBox<Mlsstat> mlsstatsComboBox;
    @ViewComponent
    private EntityComboBox<Aegisstat> aegisstatsComboBox;
    @ViewComponent
    private EntityComboBox<Historic> historicsComboBox;
    @ViewComponent
    private JmixDetails id_collcodeDetails;
    @ViewComponent
    private JmixDetails id_bredcodeDetails;
    @ViewComponent
    private JmixDetails id_duplsiteDetails;
    @ViewComponent
    private EntityComboBox<Collsrc> collsrcsComboBox;

    @Subscribe
    public void onInit(final InitEvent event) {
        initManualTooltip();
    }

    @Subscribe(target = Target.DATA_CONTEXT)
    public void onPreSave(DataContext.PreSaveEvent event) {
        // For non-temporally number
        if (getEditedEntity().getAccenumb() == null && !checkboxTemp.getValue()) {
            if (getEditedEntity().getId_instcode().getInstcode().equals("ROM007")) {
                long accNumberSVGB = sequences.createNextValue(Sequence.withName("NrAccenumbSVGB"));
                getEditedEntity().setAccenumb("SVGB-" + accNumberSVGB);
            } else if (getEditedEntity().getId_instcode().getInstcode().equals("ROM028")) {
                long accNumberSVSCA = sequences.createNextValue(Sequence.withName("NrAccenumbSVCA"));
                getEditedEntity().setAccenumb("SVSCA-" + accNumberSVSCA);
            }
            else {
                String error_message_accenumber = messageBundle.getMessage("error_message_accenumber");
                notifications.create("HOPA", error_message_accenumber)
                        .withDuration(5000).show();
            }
        }
        // For temporally number
        else if (getEditedEntity().getAccenumb() == null && checkboxTemp.getValue()) {
            if (getEditedEntity().getId_instcode().getInstcode().equals("ROM007")) {
                long accNumberSVGB = sequences.createNextValue(Sequence.withName("NrAccenumbTempSVGB"));
                getEditedEntity().setAccenumb("TSVGB-" + accNumberSVGB);
                getEditedEntity().setTempnumb("TSVGB-" + accNumberSVGB);
            } else if (getEditedEntity().getId_instcode().getInstcode().equals("ROM028")) {
                long accNumberSVSCA = sequences.createNextValue(Sequence.withName("NrAccenumbTempSVCA"));
                getEditedEntity().setAccenumb("TSVSCA-" + accNumberSVSCA);
                getEditedEntity().setTempnumb("TSVSCA-" + accNumberSVSCA);
            }
            else {
                String error_message_accenumber = messageBundle.getMessage("error_message_accenumber");
                notifications.create("HOPA", error_message_accenumber)
                        .withDuration(5000).show();
            }
        }
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        imageProbe.setVisible(false);
        downloadBtn.setEnabled(false);
        if (!Objects.equals(getEditedEntity().getAccenumb(), getEditedEntity().getTempnumb())) {
            checkboxTemp.setEnabled(false);
        } else if (Objects.equals(getEditedEntity().getAccenumb(), getEditedEntity().getTempnumb())
                && getEditedEntity().getAccenumb() != null) {
            checkboxTemp.setEnabled(true);
            checkboxTemp.setValue(true);
        }
    }

    @Subscribe("checkboxMap")
    public void onCheckboxMapClick(final ClickEvent<JmixCheckbox> event) {
        String message_1 = messageBundle.getMessage("google_map");
        String message_2 = messageBundle.getMessage("openstreetmap");
        if (Boolean.TRUE.equals(event.getSource().getValue())) {
            notifications.create(message_1).show();
            initGoogleMap();
            remMapFromContainer();
            addGoogleMapToContainer();
            addGoogleMapMarker();
        }
        else {
            notifications.create(message_2).show();
            initMap();
            remGoogleMapFromContainer();
            addMapToContainer();
            addMapMarker();
        }
    }

    @Subscribe("checkboxElevation")
    public void onCheckboxElevationClick(final ClickEvent<JmixCheckbox> event) {
        String message_1 = messageBundle.getMessage("google_elev");
        String message_2 = messageBundle.getMessage("opensource_elev");
        if (Boolean.TRUE.equals(event.getSource().getValue())) {
            notifications.create(message_1).show();
        } else {
            notifications.create(message_2).show();
        }
    }
    private void addGoogleMapMarker() {
        Pasaport pasaport = getEditedEntity();

        if (pasaport.getLatitude() != null && pasaport.getLongitude() != null) {
            GoogleMapMarker probeMarker = gmaps.addMarker(pasaport.getAccenumb(),
                    new LatLon(pasaport.getLatitude(), pasaport.getLongitude()),
                    false, Markers.LIGHTBLUE_DOT);
            String probe = pasaport.getAccenumb();
            probeMarker.addInfoWindow("<h2>"+probe+"</h2>");
        }
        else drawGoogleCenterMarkers();
    }
    private void addMapMarker() {
        Pasaport pasaport = getEditedEntity();

        if (pasaport.getLatitude() != null && pasaport.getLongitude() != null) {
            LMarker probeMarker = new LMarker(pasaport.getLatitude(), pasaport.getLongitude());
            String probe = pasaport.getAccenumb();
            probeMarker.setPopup("<h4>"+probe+"</h4>");
            map.addLComponents(probeMarker);
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
        centerMarker.addDragEndEventListener(event -> {
            getEditedEntity().setLatitude(event.getLatitude());
            getEditedEntity().setLongitude(event.getLongitude());

            // I got elevation
            if (checkboxElevation.getValue()) {
                try {
                    URL url = new URL("https://maps.googleapis.com/maps/api/elevation/json?locations="+
                            event.getLatitude()+","+event.getLongitude()+"&key="+apiKey);
                    getEditedEntity().setElevation(getAltitude(url));
                } catch (IOException e) {
                    String error_message = messageBundle.getMessage("error_message");
                    log.error(error_message, e);
                    throw  new RuntimeException(error_message, e);
                }
            }
            else {
                try {
                    URL url = new URL("https://api.opentopodata.org/v1/mapzen?locations="+
                            event.getLatitude()+","+event.getLongitude());
                    getEditedEntity().setElevation(getAltitude(url));
                } catch (IOException e) {
                    String error_message = messageBundle.getMessage("error_message");
                    log.error(error_message, e);
                    throw new RuntimeException(error_message,e);
                }
            }
        });
    }
    private int getAltitude(URL url) throws IOException {
       HttpURLConnection connection = (HttpURLConnection) url.openConnection();

       connection.setRequestMethod("GET");
       connection.setRequestProperty("Content-Type", "application/json");
       connection.setRequestProperty("Accept", "application/json");

       double altitude = 0;

       try (BufferedReader bufferedReader = new BufferedReader(
               new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
           StringBuilder response = new StringBuilder();
           String responseLine;
           while ((responseLine = bufferedReader.readLine()) != null) {
               response.append(responseLine.trim());
           }
           JsonObject jsonObject = new Gson().fromJson(response.toString(), JsonObject.class);
           JsonArray jsonArray = jsonObject.getAsJsonArray("results");

           String status = jsonObject.get("status").toString();

           if (!status.equals("\"OK\"")) {
               String error_message = messageBundle.getMessage("error_message");
               notifications.create(error_message+" "+status).show();
           }
           else {
               JsonObject jsonObject1 = new Gson().fromJson(jsonArray.asList().get(0).toString(), JsonObject.class);
               
               String alt = String.valueOf(jsonObject1.get("elevation"));
               altitude = Double.parseDouble(alt);
           }
       }
       return (int) altitude;
    }
    @Subscribe("probeMap")
    public void addOpenedChangeListener(final Details.OpenedChangeEvent event) {
        if (event.isOpened()) {
            mapContainer.setEnabled(true);
            mapContainer.setVisible(true);
            if (checkboxMap.getValue()) {
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
        else if (!event.isOpened()) {
            if (checkboxMap.getValue()) {
                remGoogleMapFromContainer();
            }
            else {
                remMapFromContainer();
            }
            mapContainer.setVisible(false);
            mapContainer.setEnabled(false);
        }
    }

    @Subscribe("probeImagesDataGrid")
    public void onProbeImagesDataGridItemClick(final ItemClickEvent<SysFile> event) {
        FileRef fileRef = event.getItem().getFile();
        if (fileRef != null) {
            StreamResource streamResource = new StreamResource(fileRef.getFileName(),
                    () -> fileStorage.openStream(fileRef));
            imageProbe.setVisible(true);
            imageProbe.setSrc(streamResource);
            downloadBtn.setEnabled(true);
        }
        else {
            imageProbe.setVisible(false);
            downloadBtn.setEnabled(false);
        }
    }

    @Subscribe("downloadBtn")
    public void onDownloadBtnClick(final ClickEvent<JmixButton> event) {
        FileRef fileRef = probeImagesDataGrid.getItems().getSelectedItem().getFile();
        downloader.download(fileRef);
    }

    @Subscribe("countysirutasComboBox")
    public void onCountysirutasComboBoxComponentValueChange(final AbstractField.ComponentValueChangeEvent<EntityComboBox<Countysiruta>, Countysiruta> event) {
        localitysirutasComboBox.clear();
    }

    // Check if Temporal checkBox was unchecked
    // if is unchecked set Accenumb to null and then Accenumb is definitely
    // if is checked back, because the user plays with component I copy the temporal number
    // because checkBox is active only when Accenumb and Tempnumb is not null and have same values
    // so the probe have a temporal number
    @Subscribe("checkboxTemp")
    public void onCheckboxTempComponentValueChange(final AbstractField.ComponentValueChangeEvent<JmixCheckbox, ?> event) {
        if (!checkboxTemp.getValue()) {
            // then when we save, the onPreSave give non-temporally number
            getEditedEntity().setAccenumb(null);
            checkboxTemp.setValue(false);
        }
        // put back the temporary number, someone plays with the checkBox, because this component is enabled only
        // when we have Accenumb and Tempnumb with the same value, because is a temporally number
        else getEditedEntity().setAccenumb(getEditedEntity().getTempnumb());
    }

    // check what user try to save, if is not from the same institute him can't save
    @Subscribe
    public void onValidation(final ValidationEvent event) {
        final User user = (User) currentAuthentication.getUser();
        if (!Objects.equals(getEditedEntity().getId_instcode(), user.getId_institute())) {
            String can_not_insert_for_over_institute = messageBundle.getMessage("can_not_insert_for_over_institute");
            event.getErrors().add(messageBundle.getMessage("can_not_insert_for_over_institute"));
            notifications.create("HOPA", can_not_insert_for_over_institute).withDuration(5000).show();
        }
    }

    // Create Tool Tips for input fields
    protected void initManualTooltip() {
        JmixButton helperButtonDoiField = createHelperButton();
        JmixButton helperButtonInstitutesComboBox_1 = createHelperButton();
        JmixButton helperButtonTempNumbField = createHelperButton();
        JmixButton helperButtonAccenumbField = createHelperButton();
        JmixButton helperButtonCollnumbField = createHelperButton();
        JmixButton helperButtonCollmissidField = createHelperButton();
        JmixButton helperButtonTaxonomiesComboBox = createHelperButton();
        JmixButton helperButtonAccnameField = createHelperButton();
        JmixButton helperButtonAcqdateField = createHelperButton();
        JmixButton helperButtonOrigdateField= createHelperButton();
        JmixButton helperButtonCountriesComboBox = createHelperButton();
        JmixButton helperButtonCountysirutasComboBox = createHelperButton();
        JmixButton helperButtonLocalitysirutasComboBox = createHelperButton();
        JmixButton helperButtonLatitudeField = createHelperButton();
        JmixButton helperButtonLongitudeField = createHelperButton();
        JmixButton helperButtonElevationField = createHelperButton();
        JmixButton helperButtonGeorefmethsComboBox = createHelperButton();
        JmixButton helperButtonColldateField  = createHelperButton();
        JmixButton helperButtonSampstatsComboBox = createHelperButton();
        JmixButton helperButtonAncestField = createHelperButton();
        JmixButton helperButtonCollsrcsComboBox = createHelperButton();
        JmixButton helperButtonInstitutesComboBox = createHelperButton();
        JmixButton helperButtonDonornumbField  = createHelperButton();
        JmixButton helperButtonOthernumbField = createHelperButton();
        JmixButton helperButtonRemarksField = createHelperButton();
        JmixButton helperButtonAcceurlField = createHelperButton();
        JmixButton helperButtonAcceconfsComboBox = createHelperButton();
        JmixButton helperButtonMlsstatsComboBox = createHelperButton();
        JmixButton helperButtonAegisstatsComboBox = createHelperButton();
        JmixButton helperButtonHistoricsComboBox  = createHelperButton();
        JmixButton helperButtonId_collcodeDetails = createHelperButton();
        JmixButton helperButtonId_bredcodeDetails = createHelperButton();
        JmixButton helperButtonId_duplsiteDetails = createHelperButton();

        Tooltip tooltipDoiField = doiField.getTooltip();
        Tooltip tooltipInstitutesComboBox_1 = institutesComboBox_1.getTooltip();
        Tooltip tooltipTempNumbField = tempnumbField.getTooltip();
        Tooltip tooltipAccenumbField = accenumbField.getTooltip();
        Tooltip tooltipCollnumbField = collnumbField.getTooltip();
        Tooltip tooltipCollmissidField = collmissidField.getTooltip();
        Tooltip tooltipTaxonomiesComboBox = taxonomiesComboBox.getTooltip();
        Tooltip tooltipAccnameField = accnameField.getTooltip();
        Tooltip tooltipAcqdateField = acqdateField.getTooltip();
        Tooltip tooltipOrigdateField = origdateField.getTooltip();
        Tooltip tooltipCountriesComboBox = countriesComboBox.getTooltip();
        Tooltip tooltipCountysirutasComboBox = countysirutasComboBox.getTooltip();
        Tooltip tooltipLocalitysirutasComboBox = localitysirutasComboBox.getTooltip();
        Tooltip tooltipLatitudeField = latitudeField.getTooltip();
        Tooltip tooltipLongitudeField = longitudeField.getTooltip();
        Tooltip tooltipElevationField = elevationField.getTooltip();
        Tooltip tooltipGeorefmethsComboBox = georefmethsComboBox.getTooltip();
        Tooltip tooltipColldateField = colldateField.getTooltip();
        Tooltip tooltipSampstatsComboBox = sampstatsComboBox.getTooltip();
        Tooltip tooltipAncestField = ancestField.getTooltip();
        Tooltip tooltipCollsrcsComboBox = collsrcsComboBox.getTooltip();
        Tooltip tooltipInstitutesComboBox = institutesComboBox.getTooltip();
        Tooltip tooltipDonornumbField = donornumbField.getTooltip();
        Tooltip tooltipOthernumbField = othernumbField.getTooltip();
        Tooltip tooltipRemarksField = remarksField.getTooltip();
        Tooltip tooltipAcceurlField = acceurlField.getTooltip();
        Tooltip tooltipAcceconfsComboBox = acceconfsComboBox.getTooltip();
        Tooltip tooltipMlsstatsComboBox = mlsstatsComboBox.getTooltip();
        Tooltip tooltipAegisstatsComboBox = aegisstatsComboBox.getTooltip();
        Tooltip tooltipHistoricsComboBox = historicsComboBox.getTooltip();
        Tooltip tooltipId_collcodeDetails = id_collcodeDetails.getTooltip();
        Tooltip tooltipId_bredcodeDetails = id_bredcodeDetails.getTooltip();
        Tooltip tooltipId_duplsiteDetails = id_duplsiteDetails.getTooltip();


        helperButtonDoiField.addClickListener(e -> tooltipDoiField.setOpened(!tooltipDoiField.isOpened()));
        helperButtonInstitutesComboBox_1.addClickListener(e -> tooltipInstitutesComboBox_1.setOpened(!tooltipInstitutesComboBox_1.isOpened()));
        helperButtonTempNumbField.addClickListener(e -> tooltipTempNumbField.setOpened(!tooltipTempNumbField.isOpened()));
        helperButtonAccenumbField.addClickListener(e -> tooltipAccenumbField.setOpened(!tooltipAccenumbField.isOpened()));
        helperButtonCollnumbField.addClickListener(e -> tooltipCollnumbField.setOpened(!tooltipCollnumbField.isOpened()));
        helperButtonCollmissidField.addClickListener(e -> tooltipCollmissidField.setOpened(!tooltipCollmissidField.isOpened()));
        helperButtonTaxonomiesComboBox.addClickListener(e -> tooltipTaxonomiesComboBox.setOpened(!tooltipTaxonomiesComboBox.isOpened()));
        helperButtonAccnameField.addClickListener(e -> tooltipAccnameField.setOpened(!tooltipAccnameField.isOpened()));
        helperButtonAcqdateField.addClickListener(e -> tooltipAcqdateField.setOpened(!tooltipAcqdateField.isOpened()));
        helperButtonOrigdateField.addClickListener(e -> tooltipOrigdateField.setOpened(!tooltipOrigdateField.isOpened()));
        helperButtonCountriesComboBox.addClickListener(e -> tooltipCountriesComboBox.setOpened(!tooltipCountriesComboBox.isOpened()));
        helperButtonCountysirutasComboBox.addClickListener(e -> tooltipCountysirutasComboBox.setOpened(!tooltipCountysirutasComboBox.isOpened()));
        helperButtonLocalitysirutasComboBox.addClickListener(e -> tooltipLocalitysirutasComboBox.setOpened(!tooltipLocalitysirutasComboBox.isOpened()));
        helperButtonLatitudeField.addClickListener(e -> tooltipLatitudeField.setOpened(!tooltipLatitudeField.isOpened()));
        helperButtonLongitudeField.addClickListener(e -> tooltipLongitudeField.setOpened(!tooltipLongitudeField.isOpened()));
        helperButtonElevationField.addClickListener(e -> tooltipElevationField.setOpened(!tooltipElevationField.isOpened()));
        helperButtonGeorefmethsComboBox.addClickListener(e -> tooltipGeorefmethsComboBox.setOpened(!tooltipGeorefmethsComboBox.isOpened()));
        helperButtonColldateField.addClickListener(e -> tooltipColldateField.setOpened(!tooltipColldateField.isOpened()));
        helperButtonSampstatsComboBox.addClickListener(e -> tooltipSampstatsComboBox.setOpened(!tooltipSampstatsComboBox.isOpened()));
        helperButtonAncestField.addClickListener(e -> tooltipAncestField.setOpened(!tooltipAncestField.isOpened()));
        helperButtonCollsrcsComboBox.addClickListener(e -> tooltipCollsrcsComboBox.setOpened(!tooltipCollsrcsComboBox.isOpened()));
        helperButtonInstitutesComboBox.addClickListener(e -> tooltipInstitutesComboBox.setOpened(!tooltipInstitutesComboBox.isOpened()));
        helperButtonDonornumbField.addClickListener(e -> tooltipDonornumbField.setOpened(!tooltipDonornumbField.isOpened()));
        helperButtonOthernumbField.addClickListener(e -> tooltipOthernumbField.setOpened(!tooltipOthernumbField.isOpened()));
        helperButtonRemarksField.addClickListener(e -> tooltipRemarksField.setOpened(!tooltipRemarksField.isOpened()));
        helperButtonAcceurlField.addClickListener(e -> tooltipAcceurlField.setOpened(!tooltipAcceurlField.isOpened()));
        helperButtonAcceconfsComboBox.addClickListener(e -> tooltipAcceconfsComboBox.setOpened(!tooltipAcceconfsComboBox.isOpened()));
        helperButtonMlsstatsComboBox.addClickListener(e -> tooltipMlsstatsComboBox.setOpened(!tooltipMlsstatsComboBox.isOpened()));
        helperButtonAegisstatsComboBox.addClickListener(e -> tooltipAegisstatsComboBox.setOpened(!tooltipAegisstatsComboBox.isOpened()));
        helperButtonHistoricsComboBox.addClickListener(e -> tooltipHistoricsComboBox.setOpened(!tooltipHistoricsComboBox.isOpened()));
        helperButtonId_collcodeDetails.addClickListener(e -> tooltipId_collcodeDetails.setOpened(!tooltipId_collcodeDetails.isOpened()));
        helperButtonId_bredcodeDetails.addClickListener(e -> tooltipId_bredcodeDetails.setOpened(!tooltipId_bredcodeDetails.isOpened()));
        helperButtonId_duplsiteDetails.addClickListener(e -> tooltipId_duplsiteDetails.setOpened(!tooltipId_duplsiteDetails.isOpened()));


        doiField.setSuffixComponent(helperButtonDoiField);
        institutesComboBox_1.setPrefixComponent(helperButtonInstitutesComboBox_1);
        tempnumbField.setSuffixComponent(helperButtonTempNumbField);
        accenumbField.setSuffixComponent(helperButtonAccenumbField);
        collnumbField.setSuffixComponent(helperButtonCollnumbField);
        collmissidField.setSuffixComponent(helperButtonCollmissidField);
        taxonomiesComboBox.setPrefixComponent(helperButtonTaxonomiesComboBox);
        accnameField.setSuffixComponent(helperButtonAccnameField);
        acqdateField.setSuffixComponent(helperButtonAcqdateField);
        origdateField.setSuffixComponent(helperButtonOrigdateField);
        countriesComboBox.setPrefixComponent(helperButtonCountriesComboBox);
        countysirutasComboBox.setPrefixComponent(helperButtonCountysirutasComboBox);
        localitysirutasComboBox.setPrefixComponent(helperButtonLocalitysirutasComboBox);
        latitudeField.setSuffixComponent(helperButtonLatitudeField);
        longitudeField.setSuffixComponent(helperButtonLongitudeField);
        elevationField.setSuffixComponent(helperButtonElevationField);
        georefmethsComboBox.setPrefixComponent(helperButtonGeorefmethsComboBox);
        colldateField.setSuffixComponent(helperButtonColldateField);
        sampstatsComboBox.setPrefixComponent(helperButtonSampstatsComboBox);
        ancestField.setSuffixComponent(helperButtonAncestField);
        collsrcsComboBox.setPrefixComponent(helperButtonCollsrcsComboBox);
        institutesComboBox.setPrefixComponent(helperButtonInstitutesComboBox);
        donornumbField.setSuffixComponent(helperButtonDonornumbField);
        othernumbField.setSuffixComponent(helperButtonOthernumbField);
        remarksField.setSuffixComponent(helperButtonRemarksField);
        acceurlField.setSuffixComponent(helperButtonAcceurlField);
        acceconfsComboBox.setPrefixComponent(helperButtonAcceconfsComboBox);
        mlsstatsComboBox.setPrefixComponent(helperButtonMlsstatsComboBox);
        aegisstatsComboBox.setPrefixComponent(helperButtonAegisstatsComboBox);
        historicsComboBox.setPrefixComponent(helperButtonHistoricsComboBox);
        id_collcodeDetails.addComponentAsFirst(helperButtonId_collcodeDetails);
        id_bredcodeDetails.addComponentAsFirst(helperButtonId_bredcodeDetails);
        id_duplsiteDetails.addComponentAsFirst(helperButtonId_duplsiteDetails);

    }

    protected JmixButton createHelperButton() {
        JmixButton helperButton = uiComponents.create(JmixButton.class);
        helperButton.setIcon(VaadinIcon.QUESTION_CIRCLE.create());
        helperButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_TERTIARY_INLINE);

        return helperButton;
    }

}