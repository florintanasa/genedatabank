/**
 * @author : Florin Tanasă
 * @since : 27.07.2023
 **/

package com.genebank.genedatabank.view.country;

import com.genebank.genedatabank.entity.Country;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "countries", layout = MainView.class)
@ViewController("Country.list")
@ViewDescriptor("country-list-view.xml")
@LookupComponent("countriesDataGrid")
@DialogMode(width = "64em")
public class CountryListView extends StandardListView<Country> {
}