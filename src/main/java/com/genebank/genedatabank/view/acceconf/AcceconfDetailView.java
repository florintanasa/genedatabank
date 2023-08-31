package com.genebank.genedatabank.view.acceconf;

import com.genebank.genedatabank.entity.Acceconf;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 31.08.2023
**/
@Route(value = "acceconfs/:id", layout = MainView.class)
@ViewController("Acceconf.detail")
@ViewDescriptor("acceconf-detail-view.xml")
@EditedEntityContainer("acceconfDc")
public class AcceconfDetailView extends StandardDetailView<Acceconf> {
}