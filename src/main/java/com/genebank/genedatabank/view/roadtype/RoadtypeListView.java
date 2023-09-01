package com.genebank.genedatabank.view.roadtype;

import com.genebank.genedatabank.entity.Roadtype;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 01.09.2023
**/
@Route(value = "roadtypes", layout = MainView.class)
@ViewController("Roadtype.list")
@ViewDescriptor("roadtype-list-view.xml")
@LookupComponent("roadtypesDataGrid")
@DialogMode(width = "64em")
public class RoadtypeListView extends StandardListView<Roadtype> {
}