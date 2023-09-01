package com.genebank.genedatabank.view.partners;

import com.genebank.genedatabank.entity.Partners;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 01.09.2023
**/
@Route(value = "partnerses/:id", layout = MainView.class)
@ViewController("Partners.detail")
@ViewDescriptor("partners-detail-view.xml")
@EditedEntityContainer("partnersDc")
public class PartnersDetailView extends StandardDetailView<Partners> {
}