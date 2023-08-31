package com.genebank.genedatabank.view.georefmeth;

import com.genebank.genedatabank.entity.Georefmeth;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 31.08.2023
**/
@Route(value = "georefmeths/:id", layout = MainView.class)
@ViewController("Georefmeth.detail")
@ViewDescriptor("georefmeth-detail-view.xml")
@EditedEntityContainer("georefmethDc")
public class GeorefmethDetailView extends StandardDetailView<Georefmeth> {
}