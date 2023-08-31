package com.genebank.genedatabank.view.collsrc;

import com.genebank.genedatabank.entity.Collsrc;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 31.08.2023
**/
@Route(value = "collsrcs/:id", layout = MainView.class)
@ViewController("Collsrc.detail")
@ViewDescriptor("collsrc-detail-view.xml")
@EditedEntityContainer("collsrcDc")
public class CollsrcDetailView extends StandardDetailView<Collsrc> {
}