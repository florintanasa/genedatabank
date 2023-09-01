package com.genebank.genedatabank.view.street;

import com.genebank.genedatabank.entity.Street;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 01.09.2023
**/
@Route(value = "streets", layout = MainView.class)
@ViewController("Street.list")
@ViewDescriptor("street-list-view.xml")
@LookupComponent("streetsDataGrid")
@DialogMode(width = "64em")
public class StreetListView extends StandardListView<Street> {
}