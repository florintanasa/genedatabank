package com.genebank.genedatabank.view.historic;

import com.genebank.genedatabank.entity.Historic;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 31.08.2023
**/
@Route(value = "historics", layout = MainView.class)
@ViewController("Historic.list")
@ViewDescriptor("historic-list-view.xml")
@LookupComponent("historicsDataGrid")
@DialogMode(width = "64em")
public class HistoricListView extends StandardListView<Historic> {
}