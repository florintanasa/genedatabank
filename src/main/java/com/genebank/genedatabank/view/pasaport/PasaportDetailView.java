package com.genebank.genedatabank.view.pasaport;

import com.genebank.genedatabank.entity.Pasaport;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 04.09.2023
**/
@Route(value = "pasaports/:id", layout = MainView.class)
@ViewController("Pasaport.detail")
@ViewDescriptor("pasaport-detail-view.xml")
@EditedEntityContainer("pasaportDc")
public class PasaportDetailView extends StandardDetailView<Pasaport> {
}