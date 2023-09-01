package com.genebank.genedatabank.view.scope;

import com.genebank.genedatabank.entity.Scope;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 01.09.2023
**/
@Route(value = "scopes/:id", layout = MainView.class)
@ViewController("Scope_.detail")
@ViewDescriptor("scope-detail-view.xml")
@EditedEntityContainer("scopeDc")
public class ScopeDetailView extends StandardDetailView<Scope> {
}