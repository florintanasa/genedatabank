package com.genebank.genedatabank.view.culturecateg;

import com.genebank.genedatabank.entity.Culturecateg;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 31.08.2023
**/
@Route(value = "culturecategs/:id", layout = MainView.class)
@ViewController("Culturecateg.detail")
@ViewDescriptor("culturecateg-detail-view.xml")
@EditedEntityContainer("culturecategDc")
public class CulturecategDetailView extends StandardDetailView<Culturecateg> {
}