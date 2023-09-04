package com.genebank.genedatabank.view.pasaport;

import com.genebank.genedatabank.entity.Pasaport;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 04.09.2023
**/
@Route(value = "pasaports", layout = MainView.class)
@ViewController("Pasaport.list")
@ViewDescriptor("pasaport-list-view.xml")
@LookupComponent("pasaportsDataGrid")
@DialogMode(width = "64em")
public class PasaportListView extends StandardListView<Pasaport> {
}