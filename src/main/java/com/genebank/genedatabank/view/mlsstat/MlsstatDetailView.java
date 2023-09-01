package com.genebank.genedatabank.view.mlsstat;

import com.genebank.genedatabank.entity.Mlsstat;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 01.09.2023
**/
@Route(value = "mlsstats/:id", layout = MainView.class)
@ViewController("Mlsstat.detail")
@ViewDescriptor("mlsstat-detail-view.xml")
@EditedEntityContainer("mlsstatDc")
public class MlsstatDetailView extends StandardDetailView<Mlsstat> {
}