package com.genebank.genedatabank.view.street;

import com.genebank.genedatabank.entity.Street;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 01.09.2023
**/
@Route(value = "streets/:id", layout = MainView.class)
@ViewController("Street.detail")
@ViewDescriptor("street-detail-view.xml")
@EditedEntityContainer("streetDc")
public class StreetDetailView extends StandardDetailView<Street> {
}