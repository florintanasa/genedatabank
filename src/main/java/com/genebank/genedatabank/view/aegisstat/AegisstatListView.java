package com.genebank.genedatabank.view.aegisstat;

import com.genebank.genedatabank.entity.Aegisstat;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 31.08.2023
**/
@Route(value = "aegisstats", layout = MainView.class)
@ViewController("Aegisstat.list")
@ViewDescriptor("aegisstat-list-view.xml")
@LookupComponent("aegisstatsDataGrid")
@DialogMode(width = "64em")
public class AegisstatListView extends StandardListView<Aegisstat> {
}