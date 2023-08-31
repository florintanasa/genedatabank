package com.genebank.genedatabank.view.culturecateg;

import com.genebank.genedatabank.entity.Culturecateg;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 31.08.2023
**/
@Route(value = "culturecategs", layout = MainView.class)
@ViewController("Culturecateg.list")
@ViewDescriptor("culturecateg-list-view.xml")
@LookupComponent("culturecategsDataGrid")
@DialogMode(width = "64em")
public class CulturecategListView extends StandardListView<Culturecateg> {
}