package com.genebank.genedatabank.view.viaboldseeds;

import com.genebank.genedatabank.entity.ViabOldSeeds;
import com.genebank.genedatabank.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 21.10.2024
 **/
@Route(value = "viabOldSeedses", layout = MainView.class)
@ViewController("ViabOldSeeds.list")
@ViewDescriptor("viab-old-seeds-list-view.xml")
@LookupComponent("viabOldSeedsesDataGrid")
@DialogMode(width = "64em")
public class ViabOldSeedsListView extends StandardListView<ViabOldSeeds> {
}