package com.genebank.genedatabank.view.viabnewseedsline;

import com.genebank.genedatabank.entity.ViabNewSeedsLine;
import com.genebank.genedatabank.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 17.10.2024
 **/
@Route(value = "viabNewSeedsLines", layout = MainView.class)
@ViewController("ViabNewSeedsLine.list")
@ViewDescriptor("viab-new-seeds-line-list-view.xml")
@LookupComponent("viabNewSeedsLinesDataGrid")
@DialogMode(width = "64em")
public class ViabNewSeedsLineListView extends StandardListView<ViabNewSeedsLine> {
}