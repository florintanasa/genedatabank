package com.genebank.genedatabank.view.localitysiruta;

import com.genebank.genedatabank.entity.Localitysiruta;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "localitysirutas/:id", layout = MainView.class)
@ViewController("Localitysiruta.detail")
@ViewDescriptor("localitysiruta-detail-view.xml")
@EditedEntityContainer("localitysirutaDc")
public class LocalitysirutaDetailView extends StandardDetailView<Localitysiruta> {
}