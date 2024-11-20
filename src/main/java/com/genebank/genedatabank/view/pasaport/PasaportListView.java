package com.genebank.genedatabank.view.pasaport;

import com.genebank.genedatabank.entity.Pasaport;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.view.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author : Florin Tanasă
 * @since : 04.09.2023
**/
@Route(value = "pasaports", layout = MainView.class)
@ViewController("Pasaport.list")
@ViewDescriptor("pasaport-list-view.xml")
@LookupComponent("pasaportsDataGrid")
@DialogMode(width = "64em")
public class PasaportListView extends StandardListView<Pasaport> {
    @Autowired
    protected DataManager dataManager;
    @ViewComponent
    private DataGrid<Pasaport> pasaportsDataGrid;
    @Autowired
    private DialogWindows dialogWindows;

    @Subscribe("pasaportsDataGrid.copyEdit")
    public void onPasaportsDataGridCopyEdit(final ActionPerformedEvent event) {
        Pasaport pasaport = pasaportsDataGrid.getSingleSelectedItem();

        if (pasaport != null) {
            Pasaport pasaportCopy = createCopy(pasaport);
            dialogWindows.detail(pasaportsDataGrid)
                    .withViewClass(PasaportDetailView.class)
                    .newEntity(pasaportCopy)
                    .open();
        }
    }

    public Pasaport createCopy(Pasaport pasaport) {
        /*
        Pasaport pasaportCopy = metadataTools.copy(pasaport);
        Pasaport pasaportCopy = dataManager.load(Pasaport.class).id(pasaport.getId()).one();
        pasaportCopy.setId(UuidProvider.createUuid());
        pasaportCopy.setAccenumb(null);
        pasaportCopy.setAccname(StringUtils.abbreviate("Copie a " + pasaport.getAccname(),45));
        return pasaportCopy;
         */
        Pasaport pasaportNew = dataManager.create(Pasaport.class);
        pasaportNew.setAccname(StringUtils.abbreviate("Copie a " + pasaport.getAccname(),45));
        pasaportNew.setAcceurl(pasaport.getAcceurl());
        pasaportNew.setAcqdate(pasaport.getAcqdate());
        pasaportNew.setAncest(pasaport.getAncest());
        pasaportNew.setColldate(pasaport.getColldate());
        pasaportNew.setCollmissid(pasaport.getCollmissid());
        pasaportNew.setCollnumb(pasaport.getCollnumb());
        pasaportNew.setComments(pasaport.getComments());
        pasaportNew.setDoi(pasaport.getDoi());
        pasaportNew.setDonornumb(pasaport.getDonornumb());
        pasaportNew.setElevation(pasaport.getElevation());
        pasaportNew.setId_acceconf(pasaport.getId_acceconf());
        pasaportNew.setId_aegisstat(pasaport.getId_aegisstat());
        pasaportNew.setId_bredcode(pasaport.getId_bredcode());
        pasaportNew.setId_collsrc(pasaport.getId_collsrc());
        pasaportNew.setId_country(pasaport.getId_country());
        pasaportNew.setId_collcode(pasaport.getId_collcode());
        pasaportNew.setId_countysiruta(pasaport.getId_countysiruta());
        pasaportNew.setId_donorcode(pasaport.getId_donorcode());
        pasaportNew.setId_duplsite(pasaport.getId_duplsite());
        pasaportNew.setId_georefmeth(pasaport.getId_georefmeth());
        pasaportNew.setId_historic(pasaport.getId_historic());
        pasaportNew.setId_instcode(pasaport.getId_instcode());
        pasaportNew.setId_localitysiruta(pasaport.getId_localitysiruta());
        pasaportNew.setId_mlsstat(pasaport.getId_mlsstat());
        pasaportNew.setId_sampstat(pasaport.getId_sampstat());
        pasaportNew.setId_taxonomy(pasaport.getId_taxonomy());
        pasaportNew.setLatitude(pasaport.getLatitude());
        pasaportNew.setLongitude(pasaport.getLongitude());
        pasaportNew.setOrigdate(pasaport.getOrigdate());
        pasaportNew.setOthernumb(pasaport.getOthernumb());
        //pasaportNew.setProbeImages(pasaport.getProbeImages());
        pasaportNew.setRemarks(pasaport.getRemarks());
        pasaportNew.setTempnumb(pasaport.getTempnumb());
        return pasaportNew;
    }
}