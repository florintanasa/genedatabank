package com.genebank.genedatabank.view.roadtype;

import com.genebank.genedatabank.entity.Roadtype;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 01.09.2023
**/
@Route(value = "roadtypes/:id", layout = MainView.class)
@ViewController("Roadtype.detail")
@ViewDescriptor("roadtype-detail-view.xml")
@EditedEntityContainer("roadtypeDc")
public class RoadtypeDetailView extends StandardDetailView<Roadtype> {
}