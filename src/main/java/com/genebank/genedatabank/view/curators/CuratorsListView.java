package com.genebank.genedatabank.view.curators;

import com.genebank.genedatabank.entity.Curators;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 31.08.2023
**/
@Route(value = "curatorses", layout = MainView.class)
@ViewController("Curators.list")
@ViewDescriptor("curators-list-view.xml")
@LookupComponent("curatorsesDataGrid")
@DialogMode(width = "64em")
public class CuratorsListView extends StandardListView<Curators> {
}