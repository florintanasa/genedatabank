package com.genebank.genedatabank.view.pasaport;

import com.genebank.genedatabank.entity.Pasaport;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.core.MetadataTools;
import io.jmix.core.UuidProvider;
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
    @ViewComponent
    private DataGrid<Pasaport> pasaportsDataGrid;
    @Autowired
    private MetadataTools metadataTools;
    @Autowired
    private DialogWindows dialogWindows;

    @Subscribe("pasaportsDataGrid.copyEdit")
    public void onPasaportsDataGridCopyEdit(final ActionPerformedEvent event) {
        Pasaport pasaport = pasaportsDataGrid.getSingleSelectedItem();
        Pasaport pasaportCopy = createCopy(pasaport);
        dialogWindows.detail(pasaportsDataGrid)
                .withViewClass(PasaportDetailView.class)
                .newEntity(pasaportCopy)
                .open();
        
    }

    public Pasaport createCopy(Pasaport pasaport) {
        Pasaport pasaportCopy = metadataTools.copy(pasaport);
        pasaportCopy.setId(UuidProvider.createUuid());
        pasaportCopy.setAccenumb(null);
        pasaportCopy.setAccname(StringUtils.abbreviate("Copie a " + pasaport.getAccname(),45));
        return pasaportCopy;
    }
}