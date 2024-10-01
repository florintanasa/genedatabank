package com.genebank.genedatabank.view.duplicateline;

import com.genebank.genedatabank.entity.Deposit;
import com.genebank.genedatabank.entity.DuplicateLine;
import com.genebank.genedatabank.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.view.*;

@Route(value = "duplicateLines/:id", layout = MainView.class)
@ViewController("DuplicateLine.detail")
@ViewDescriptor("duplicate-line-detail-view.xml")
@EditedEntityContainer("duplicateLineDc")
public class DuplicateLineDetailView extends StandardDetailView<DuplicateLine> {
    @ViewComponent
    private EntityComboBox<Deposit> id_depositField;
    @ViewComponent
    private TypedTextField<String> dDepositCodeField;
    @ViewComponent
    private TypedTextField<String> dStorageField;
    @ViewComponent
    private TypedTextField<String> dYearstorageField;
    @ViewComponent
    private TypedTextField<Integer> dYearmultiField;
    @ViewComponent
    private TypedTextField<Integer> dMultiplyField;
    @ViewComponent
    private TypedTextField<Integer> dYeargermField;
    @ViewComponent
    private TypedTextField<Integer> dPercentageField;
    @ViewComponent
    private TypedTextField<Integer> dStockField;
    @ViewComponent
    private TypedTextField<Double> dHumidityField;
    @ViewComponent
    private TypedTextField<Double> dMmbField;
    @ViewComponent
    private JmixCheckbox dOriginalField;
    @ViewComponent
    private TypedTextField<String> pAccenumbField;
    @ViewComponent
    private TypedTextField<String> pDoiField;
    @ViewComponent
    private TypedTextField<String> pCollnumbField;
    @ViewComponent
    private TypedTextField<String> pCollmissidField;
    @ViewComponent
    private TypedTextField<String> tFammilyField;
    @ViewComponent
    private TypedTextField<String> tGenusField;
    @ViewComponent
    private TypedTextField<String> tSpeciesField;
    @ViewComponent
    private TypedTextField<String> tSpauthorField;
    @ViewComponent
    private TypedTextField<String> tSubtaxaField;
    @ViewComponent
    private TypedTextField<String> tSubauthorField;
    @ViewComponent
    private TypedTextField<String> tCropnumeField;
    @ViewComponent
    private TypedTextField<String> tCropnameField;
    @ViewComponent
    private TypedTextField<String> pAccenameField;
    @ViewComponent
    private TypedTextField<String> pAcqdateField;
    @ViewComponent
    private TypedTextField<String> pOrigdateField;
    @ViewComponent
    private TypedTextField<String> stateNameField;
    @ViewComponent
    private TypedTextField<String> countyNameField;
    @ViewComponent
    private TypedTextField<String> localityNameField;
    @ViewComponent
    private TypedTextField<Double> pLatitudeField;
    @ViewComponent
    private TypedTextField<Double> pLongitudeField;
    @ViewComponent
    private TypedTextField<Integer> pElevationField;
    @ViewComponent
    private TypedTextField<String> gNameField;
    @ViewComponent
    private TypedTextField<String> pColldateField;
    @ViewComponent
    private TypedTextField<String> sNameField;
    @ViewComponent
    private TypedTextField<String> pAncestField;
    @ViewComponent
    private TypedTextField<String> cNameField;
    @ViewComponent
    private TypedTextField<String> donorInstcodeField;
    @ViewComponent
    private TypedTextField<String> pOthernumbField;
    @ViewComponent
    private TypedTextField<String> pTempnumbField;
    @ViewComponent
    private TypedTextField<String> pAcceurlField;
    @ViewComponent
    private TypedTextField<String> pAcceconfField;
    @ViewComponent
    private TypedTextField<String> mNameField;
    @ViewComponent
    private TypedTextField<String> aNameField;
    @ViewComponent
    private TypedTextField<String> hNameField;
    @ViewComponent
    private TypedTextField<String> tCultCategField;
    @ViewComponent
    private TypedTextField<Integer> quantityField;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        //set default 500 seeds
        quantityField.setValue("500");
        //check if is choose a new deposit code
        id_depositField.addValueChangeListener(valueChangeEvent -> {
            if (valueChangeEvent.getValue() != null) {
                //declared String variables used in text components
                String VdYearStorage, YearMulti, Multiply, YearGerm, Percentage, Humidity, Mmb, Doi, CollNumb, CollMissid,
                        Family, Genus, Species, SpAuthor, SubTaxa, SubAuthor, CropNume, CropName, AcqDate, OrigDate,
                StateName, CountyName, LocalityName, Latitude, Longitude, Elevation, GeoRefName, CollDate, SampStatName,
                Ancest, CollSrcName, DonorInstCode, OtherNumb, TempNumb, AccessionUrl, AccessionConf, MlsStatName,
                AegisStatName, HistoricName, CultCateg;

                dDepositCodeField.setValue(id_depositField.getValue().getDeposit_code());
                dStorageField.setValue(id_depositField.getValue().getId_storage().getName());

                if (id_depositField.getValue().getYearstorage() == null) {
                    VdYearStorage = "";
                } else VdYearStorage = id_depositField.getValue().getYearstorage();
                dYearstorageField.setValue(VdYearStorage);

                if (id_depositField.getValue().getYearmulti() == null) {
                    YearMulti = "";
                } else YearMulti = id_depositField.getValue().getYearmulti().toString();
                dYearmultiField.setValue(YearMulti);

                if (id_depositField.getValue().getMultiply() == null) {
                    Multiply = "";
                } else Multiply = id_depositField.getValue().getMultiply().toString();
                dMultiplyField.setValue(Multiply);

                if (id_depositField.getValue().getYeargerm() == null) {
                    YearGerm = "";
                } else YearGerm = id_depositField.getValue().getYeargerm().toString();
                dYeargermField.setValue(YearGerm);

                if (id_depositField.getValue().getPercentage() == null) {
                    Percentage = "";
                } else Percentage = id_depositField.getValue().getPercentage().toString();
                dPercentageField.setValue(Percentage);

                dStockField.setValue(id_depositField.getValue().getStock().toString());

                if (id_depositField.getValue().getHumidity() == null) {
                    Humidity = "";
                } else Humidity = id_depositField.getValue().getHumidity().toString();
                dHumidityField.setValue(Humidity);

                if (id_depositField.getValue().getMmb() == null) {
                    Mmb = "";
                } else Mmb = id_depositField.getValue().getMmb().toString();
                dMmbField.setValue(Mmb);

                dOriginalField.setValue(id_depositField.getValue().getOriginal());
                pAccenumbField.setValue(id_depositField.getValue().getId_accenumb().getAccenumb());

                if (id_depositField.getValue().getId_accenumb().getDoi() == null) {
                    Doi = "";
                } else Doi = id_depositField.getValue().getId_accenumb().getDoi();
                pDoiField.setValue(Doi);

                if (id_depositField.getValue().getId_accenumb().getCollnumb() == null) {
                    CollNumb = "";
                } else CollNumb = id_depositField.getValue().getId_accenumb().getCollnumb();
                pCollnumbField.setValue(CollNumb);

                if (id_depositField.getValue().getId_accenumb().getCollmissid() == null) {
                    CollMissid = "";
                } else CollMissid = id_depositField.getValue().getId_accenumb().getCollmissid();
                pCollmissidField.setValue(CollMissid);

                if (id_depositField.getValue().getId_accenumb().getId_taxonomy().getFamily() == null) {
                    Family = "";
                } else Family = id_depositField.getValue().getId_accenumb().getId_taxonomy().getFamily();
                tFammilyField.setValue(Family);

                if (id_depositField.getValue().getId_accenumb().getId_taxonomy().getGenus() == null) {
                    Genus = "";
                } else Genus = id_depositField.getValue().getId_accenumb().getId_taxonomy().getGenus();
                tGenusField.setValue(Genus);

                if (id_depositField.getValue().getId_accenumb().getId_taxonomy().getSpecies() == null) {
                    Species = "";
                } else Species = id_depositField.getValue().getId_accenumb().getId_taxonomy().getSpecies();
                tSpeciesField.setValue(Species);

                if (id_depositField.getValue().getId_accenumb().getId_taxonomy().getSpauthor() == null) {
                    SpAuthor = "";
                } else SpAuthor = id_depositField.getValue().getId_accenumb().getId_taxonomy().getSpauthor();
                tSpauthorField.setValue(SpAuthor);

                if (id_depositField.getValue().getId_accenumb().getId_taxonomy().getSubtaxa() == null) {
                    SubTaxa = "";
                } else SubTaxa = id_depositField.getValue().getId_accenumb().getId_taxonomy().getSubtaxa();
                tSubtaxaField.setValue(SubTaxa);

                if (id_depositField.getValue().getId_accenumb().getId_taxonomy().getSubauthor() == null) {
                    SubAuthor = "";
                } else SubAuthor = id_depositField.getValue().getId_accenumb().getId_taxonomy().getSubauthor();
                tSubauthorField.setValue(SubAuthor);

                if (id_depositField.getValue().getId_accenumb().getId_taxonomy().getCropnume() == null) {
                    CropNume = "";
                } else CropNume = id_depositField.getValue().getId_accenumb().getId_taxonomy().getCropnume();
                tCropnumeField.setValue(CropNume);

                if (id_depositField.getValue().getId_accenumb().getId_taxonomy().getCropname() == null) {
                    CropName = "";
                } else CropName = id_depositField.getValue().getId_accenumb().getId_taxonomy().getCropname();
                tCropnameField.setValue(CropName);

                pAccenameField.setValue(id_depositField.getValue().getId_accenumb().getAccname());

                if (id_depositField.getValue().getId_accenumb().getAcqdate() == null) {
                    AcqDate = "";
                } else AcqDate = id_depositField.getValue().getId_accenumb().getAcqdate();
                pAcqdateField.setValue(AcqDate);

                if (id_depositField.getValue().getId_accenumb().getOrigdate() == null) {
                    OrigDate = "";
                } else OrigDate = id_depositField.getValue().getId_accenumb().getOrigdate();
                pOrigdateField.setValue(OrigDate);

                if (id_depositField.getValue().getId_accenumb().getId_country() == null) {
                    StateName = "";
                } else StateName = id_depositField.getValue().getId_accenumb().getId_country().getName();
                stateNameField.setValue(StateName);

                if (id_depositField.getValue().getId_accenumb().getId_countysiruta() == null) {
                    CountyName = "";
                } else CountyName = id_depositField.getValue().getId_accenumb().getId_countysiruta().getName();
                countyNameField.setValue(CountyName);

                if (id_depositField.getValue().getId_accenumb().getId_localitysiruta() == null) {
                    LocalityName = "";
                } else LocalityName = id_depositField.getValue().getId_accenumb().getId_localitysiruta().getName();
                localityNameField.setValue(LocalityName);

                if (id_depositField.getValue().getId_accenumb().getLatitude() == null) {
                    Latitude = "";
                } else Latitude = id_depositField.getValue().getId_accenumb().getLatitude().toString();
                pLatitudeField.setValue(Latitude);

                if (id_depositField.getValue().getId_accenumb().getLongitude() == null) {
                    Longitude = "";
                } else Longitude = id_depositField.getValue().getId_accenumb().getLongitude().toString();
                pLongitudeField.setValue(Longitude);

                if (id_depositField.getValue().getId_accenumb().getElevation() == null) {
                    Elevation = "";
                } else Elevation = id_depositField.getValue().getId_accenumb().getElevation().toString();
                pElevationField.setValue(Elevation);

                if (id_depositField.getValue().getId_accenumb().getId_georefmeth() == null) {
                    GeoRefName = "";
                } else GeoRefName = id_depositField.getValue().getId_accenumb().getId_georefmeth().getName();
                gNameField.setValue(GeoRefName);

                if (id_depositField.getValue().getId_accenumb().getColldate() == null) {
                    CollDate = "";
                } else CollDate = id_depositField.getValue().getId_accenumb().getColldate();
                pColldateField.setValue(CollDate);

                if (id_depositField.getValue().getId_accenumb().getId_sampstat() == null) {
                    SampStatName = "";
                } else SampStatName = id_depositField.getValue().getId_accenumb().getId_sampstat().getName();
                sNameField.setValue(SampStatName);

                if (id_depositField.getValue().getId_accenumb().getAncest() == null) {
                    Ancest = "";
                } else Ancest = id_depositField.getValue().getId_accenumb().getAncest();
                pAncestField.setValue(Ancest);

                if (id_depositField.getValue().getId_accenumb().getId_collsrc() == null) {
                    CollSrcName = "";
                } else CollSrcName = id_depositField.getValue().getId_accenumb().getId_collsrc().getName();
                cNameField.setValue(CollSrcName);

                if (id_depositField.getValue().getId_accenumb().getId_donorcode() == null) {
                    DonorInstCode = "";
                } else DonorInstCode = id_depositField.getValue().getId_accenumb().getId_donorcode().getInstcode();
                donorInstcodeField.setValue(DonorInstCode);

                if (id_depositField.getValue().getId_accenumb().getOthernumb() == null) {
                    OtherNumb = "";
                } else OtherNumb = id_depositField.getValue().getId_accenumb().getOthernumb();
                pOthernumbField.setValue(OtherNumb);

                if (id_depositField.getValue().getId_accenumb().getTempnumb() == null) {
                    TempNumb = "";
                } else TempNumb = id_depositField.getValue().getId_accenumb().getTempnumb();
                pTempnumbField.setValue(TempNumb);

                if (id_depositField.getValue().getId_accenumb().getAcceurl() == null) {
                    AccessionUrl = "";
                } else AccessionUrl = id_depositField.getValue().getId_accenumb().getAcceurl();
                pAcceurlField.setValue(AccessionUrl);

                if (id_depositField.getValue().getId_accenumb().getId_acceconf() == null) {
                    AccessionConf = "";
                } else AccessionConf = id_depositField.getValue().getId_accenumb().getId_acceconf().getName();
                pAcceconfField.setValue(AccessionConf);

                if (id_depositField.getValue().getId_accenumb().getId_mlsstat() == null) {
                    MlsStatName = "";
                } else MlsStatName = id_depositField.getValue().getId_accenumb().getId_mlsstat().getName();
                mNameField.setValue(MlsStatName);

                if (id_depositField.getValue().getId_accenumb().getId_aegisstat() == null) {
                    AegisStatName = "";
                } else AegisStatName = id_depositField.getValue().getId_accenumb().getId_aegisstat().getName();
                aNameField.setValue(AegisStatName);

                if (id_depositField.getValue().getId_accenumb().getId_historic() == null) {
                    HistoricName = "";
                } else HistoricName = id_depositField.getValue().getId_accenumb().getId_historic().getName();
                hNameField.setValue(HistoricName);

                if (id_depositField.getValue().getId_accenumb().getId_taxonomy().getId_culturecateg().isEmpty()) {
                    CultCateg = "";
                } else CultCateg = id_depositField.getValue().getId_accenumb().getId_taxonomy().getId_culturecateg().iterator().next().getCode();
                tCultCategField.setValue(CultCateg);


            }
        });
    }
    
    
}