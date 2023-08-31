package com.genebank.genedatabank.view.institute;

import com.genebank.genedatabank.entity.Institute;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 31.08.2023
**/
@Route(value = "institutes/:id", layout = MainView.class)
@ViewController("Institute.detail")
@ViewDescriptor("institute-detail-view.xml")
@EditedEntityContainer("instituteDc")
public class InstituteDetailView extends StandardDetailView<Institute> {
}