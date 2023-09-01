package com.genebank.genedatabank.view.scope;

import com.genebank.genedatabank.entity.Scope;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 01.09.2023
**/
@Route(value = "scopes", layout = MainView.class)
@ViewController("Scope_.list")
@ViewDescriptor("scope-list-view.xml")
@LookupComponent("scopesDataGrid")
@DialogMode(width = "64em")
public class ScopeListView extends StandardListView<Scope> {
}