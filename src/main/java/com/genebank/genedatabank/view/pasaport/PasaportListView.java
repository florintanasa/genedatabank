package com.genebank.genedatabank.view.pasaport;

import com.genebank.genedatabank.entity.Pasaport;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.ViewNavigators;
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
    @ViewComponent
    private DataGrid<Pasaport> pasaportsDataGrid;
    @Autowired
    private ViewNavigators viewNavigators;


    @Subscribe("pasaportsDataGrid.copyEdit")
    public void onPasaportsDataGridCopyEdit(final ActionPerformedEvent event) {
        Pasaport pasaport = pasaportsDataGrid.getSingleSelectedItem();

        if (pasaport != null) {
            navigateToCopyAndEditEntity(pasaport);
        }
    }
    //method for copy and edit selected item
    public void navigateToCopyAndEditEntity(Pasaport pasaport) {
        viewNavigators.detailView(pasaportsDataGrid)
                .withViewClass(PasaportDetailView.class)
                .newEntity()
                .withAfterNavigationHandler(afterNavigationEvent -> {
                    afterNavigationEvent.getView().getEditedEntity()
                            .setAccname(StringUtils.abbreviate("Copie a "
                            + pasaport.getAccname(),45));
                    afterNavigationEvent.getView().getEditedEntity()
                            .setAcceurl(pasaport.getAcceurl());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setAcqdate(pasaport.getAcqdate());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setAncest(pasaport.getAncest());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setColldate(pasaport.getColldate());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setCollmissid(pasaport.getCollmissid());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setCollnumb(pasaport.getCollnumb());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setComments(pasaport.getComments());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setDoi(pasaport.getDoi());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setDonornumb(pasaport.getDonornumb());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setElevation(pasaport.getElevation());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_acceconf(pasaport.getId_acceconf());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_aegisstat(pasaport.getId_aegisstat());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_bredcode(pasaport.getId_bredcode());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_collsrc(pasaport.getId_collsrc());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_country(pasaport.getId_country());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_collcode(pasaport.getId_collcode());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_countysiruta(pasaport.getId_countysiruta());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_donorcode(pasaport.getId_donorcode());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_duplsite(pasaport.getId_duplsite());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_georefmeth(pasaport.getId_georefmeth());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_historic(pasaport.getId_historic());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_instcode(pasaport.getId_instcode());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_localitysiruta(pasaport.getId_localitysiruta());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_mlsstat(pasaport.getId_mlsstat());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_sampstat(pasaport.getId_sampstat());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_taxonomy(pasaport.getId_taxonomy());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setLatitude(pasaport.getLatitude());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setLongitude(pasaport.getLongitude());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setOrigdate(pasaport.getOrigdate());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setOthernumb(pasaport.getOthernumb());
                    // afterNavigationEvent.getView().getEditedEntity()
                    // .setProbeImages(pasaport.getProbeImages());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setRemarks(pasaport.getRemarks());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setTempnumb(pasaport.getTempnumb());
                })
                .navigate();
    }
}