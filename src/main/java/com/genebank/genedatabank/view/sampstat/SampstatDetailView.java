package com.genebank.genedatabank.view.sampstat;

import com.genebank.genedatabank.entity.Sampstat;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 01.09.2023
**/
@Route(value = "sampstats/:id", layout = MainView.class)
@ViewController("Sampstat.detail")
@ViewDescriptor("sampstat-detail-view.xml")
@EditedEntityContainer("sampstatDc")
public class SampstatDetailView extends StandardDetailView<Sampstat> {
}