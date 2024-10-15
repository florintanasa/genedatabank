package com.genebank.genedatabank.view.viabnewseeds;

import com.genebank.genedatabank.entity.ViabNewSeeds;
import com.genebank.genedatabank.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 15.10.2024
 **/
@Route(value = "viabNewSeedses", layout = MainView.class)
@ViewController("ViabNewSeeds.list")
@ViewDescriptor("viab-new-seeds-list-view.xml")
@LookupComponent("viabNewSeedsesDataGrid")
@DialogMode(width = "64em")
public class ViabNewSeedsListView extends StandardListView<ViabNewSeeds> {
}