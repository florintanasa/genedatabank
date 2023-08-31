package com.genebank.genedatabank.view.historic;

import com.genebank.genedatabank.entity.Historic;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 31.08.2023
**/
@Route(value = "historics/:id", layout = MainView.class)
@ViewController("Historic.detail")
@ViewDescriptor("historic-detail-view.xml")
@EditedEntityContainer("historicDc")
public class HistoricDetailView extends StandardDetailView<Historic> {
}