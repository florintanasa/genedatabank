package com.genebank.genedatabank.view.acceconf;

import com.genebank.genedatabank.entity.Acceconf;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 31.08.2023
**/
@Route(value = "acceconfs", layout = MainView.class)
@ViewController("Acceconf.list")
@ViewDescriptor("acceconf-list-view.xml")
@LookupComponent("acceconfsDataGrid")
@DialogMode(width = "64em")
public class AcceconfListView extends StandardListView<Acceconf> {
}