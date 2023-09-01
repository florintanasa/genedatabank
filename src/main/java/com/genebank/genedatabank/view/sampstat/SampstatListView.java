package com.genebank.genedatabank.view.sampstat;

import com.genebank.genedatabank.entity.Sampstat;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 01.09.2023
**/
@Route(value = "sampstats", layout = MainView.class)
@ViewController("Sampstat.list")
@ViewDescriptor("sampstat-list-view.xml")
@LookupComponent("sampstatsDataGrid")
@DialogMode(width = "64em")
public class SampstatListView extends StandardListView<Sampstat> {
}