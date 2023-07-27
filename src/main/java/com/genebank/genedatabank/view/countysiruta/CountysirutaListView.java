package com.genebank.genedatabank.view.countysiruta;

import com.genebank.genedatabank.entity.Countysiruta;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "countysirutas", layout = MainView.class)
@ViewController("Countysiruta.list")
@ViewDescriptor("countysiruta-list-view.xml")
@LookupComponent("countysirutasDataGrid")
@DialogMode(width = "64em")
public class CountysirutaListView extends StandardListView<Countysiruta> {
}