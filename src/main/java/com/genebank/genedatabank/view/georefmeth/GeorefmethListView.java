package com.genebank.genedatabank.view.georefmeth;

import com.genebank.genedatabank.entity.Georefmeth;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 31.08.2023
**/
@Route(value = "georefmeths", layout = MainView.class)
@ViewController("Georefmeth.list")
@ViewDescriptor("georefmeth-list-view.xml")
@LookupComponent("georefmethsDataGrid")
@DialogMode(width = "64em")
public class GeorefmethListView extends StandardListView<Georefmeth> {
}