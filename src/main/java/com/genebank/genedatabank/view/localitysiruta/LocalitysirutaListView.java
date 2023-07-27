package com.genebank.genedatabank.view.localitysiruta;

import com.genebank.genedatabank.entity.Localitysiruta;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "localitysirutas", layout = MainView.class)
@ViewController("Localitysiruta.list")
@ViewDescriptor("localitysiruta-list-view.xml")
@LookupComponent("localitysirutasDataGrid")
@DialogMode(width = "64em")
public class LocalitysirutaListView extends StandardListView<Localitysiruta> {
}