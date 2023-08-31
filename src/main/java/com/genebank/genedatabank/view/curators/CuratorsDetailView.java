package com.genebank.genedatabank.view.curators;

import com.genebank.genedatabank.entity.Curators;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 31.08.2023
**/
@Route(value = "curatorses/:id", layout = MainView.class)
@ViewController("Curators.detail")
@ViewDescriptor("curators-detail-view.xml")
@EditedEntityContainer("curatorsDc")
public class CuratorsDetailView extends StandardDetailView<Curators> {
}