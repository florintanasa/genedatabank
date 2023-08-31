package com.genebank.genedatabank.view.aegisstat;

import com.genebank.genedatabank.entity.Aegisstat;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 31.08.2023
**/
@Route(value = "aegisstats/:id", layout = MainView.class)
@ViewController("Aegisstat.detail")
@ViewDescriptor("aegisstat-detail-view.xml")
@EditedEntityContainer("aegisstatDc")
public class AegisstatDetailView extends StandardDetailView<Aegisstat> {
}