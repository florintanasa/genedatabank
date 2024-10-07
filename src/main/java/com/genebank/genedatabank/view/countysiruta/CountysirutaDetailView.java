/**
 * @author : Florin Tanasă
 * @since : 27.07.2023
 **/

package com.genebank.genedatabank.view.countysiruta;

import com.genebank.genedatabank.entity.Countysiruta;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "countysirutas/:id", layout = MainView.class)
@ViewController("Countysiruta.detail")
@ViewDescriptor("countysiruta-detail-view.xml")
@EditedEntityContainer("countysirutaDc")
public class CountysirutaDetailView extends StandardDetailView<Countysiruta> {
}