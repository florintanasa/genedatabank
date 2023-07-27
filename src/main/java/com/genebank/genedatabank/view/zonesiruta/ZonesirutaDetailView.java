package com.genebank.genedatabank.view.zonesiruta;

import com.genebank.genedatabank.entity.Zonesiruta;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "zonesirutas/:id", layout = MainView.class)
@ViewController("Zonesiruta.detail")
@ViewDescriptor("zonesiruta-detail-view.xml")
@EditedEntityContainer("zonesirutaDc")
public class ZonesirutaDetailView extends StandardDetailView<Zonesiruta> {
}