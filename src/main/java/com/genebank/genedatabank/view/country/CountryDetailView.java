package com.genebank.genedatabank.view.country;

import com.genebank.genedatabank.entity.Country;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "countries/:id", layout = MainView.class)
@ViewController("Country.detail")
@ViewDescriptor("country-detail-view.xml")
@EditedEntityContainer("countryDc")
public class CountryDetailView extends StandardDetailView<Country> {
}