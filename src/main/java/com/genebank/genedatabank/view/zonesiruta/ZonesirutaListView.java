package com.genebank.genedatabank.view.zonesiruta;

import com.genebank.genedatabank.entity.Zonesiruta;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "zonesirutas", layout = MainView.class)
@ViewController("Zonesiruta.list")
@ViewDescriptor("zonesiruta-list-view.xml")
@LookupComponent("zonesirutasDataGrid")
@DialogMode(width = "64em")
public class ZonesirutaListView extends StandardListView<Zonesiruta> {
}