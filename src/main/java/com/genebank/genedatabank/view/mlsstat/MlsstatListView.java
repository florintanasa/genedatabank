package com.genebank.genedatabank.view.mlsstat;

import com.genebank.genedatabank.entity.Mlsstat;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 01.09.2023
**/
@Route(value = "mlsstats", layout = MainView.class)
@ViewController("Mlsstat.list")
@ViewDescriptor("mlsstat-list-view.xml")
@LookupComponent("mlsstatsDataGrid")
@DialogMode(width = "64em")
public class MlsstatListView extends StandardListView<Mlsstat> {
}