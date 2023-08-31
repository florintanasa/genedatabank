package com.genebank.genedatabank.view.institute;

import com.genebank.genedatabank.entity.Institute;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 31.08.2023
**/
@Route(value = "institutes", layout = MainView.class)
@ViewController("Institute.list")
@ViewDescriptor("institute-list-view.xml")
@LookupComponent("institutesDataGrid")
@DialogMode(width = "64em")
public class InstituteListView extends StandardListView<Institute> {
}