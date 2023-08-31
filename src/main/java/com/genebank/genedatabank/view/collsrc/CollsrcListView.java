package com.genebank.genedatabank.view.collsrc;

import com.genebank.genedatabank.entity.Collsrc;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 31.08.2023
**/
@Route(value = "collsrcs", layout = MainView.class)
@ViewController("Collsrc.list")
@ViewDescriptor("collsrc-list-view.xml")
@LookupComponent("collsrcsDataGrid")
@DialogMode(width = "64em")
public class CollsrcListView extends StandardListView<Collsrc> {
}