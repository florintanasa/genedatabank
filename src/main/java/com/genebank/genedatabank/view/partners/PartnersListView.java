package com.genebank.genedatabank.view.partners;

import com.genebank.genedatabank.entity.Partners;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 01.09.2023
**/
@Route(value = "partnerses", layout = MainView.class)
@ViewController("Partners.list")
@ViewDescriptor("partners-list-view.xml")
@LookupComponent("partnersesDataGrid")
@DialogMode(width = "64em")
public class PartnersListView extends StandardListView<Partners> {
}