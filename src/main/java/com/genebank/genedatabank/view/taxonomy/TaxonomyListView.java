package com.genebank.genedatabank.view.taxonomy;

import com.genebank.genedatabank.entity.Taxonomy;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 01.09.2023
**/
@Route(value = "taxonomies", layout = MainView.class)
@ViewController("Taxonomy.list")
@ViewDescriptor("taxonomy-list-view.xml")
@LookupComponent("taxonomiesDataGrid")
@DialogMode(width = "100%")
public class TaxonomyListView extends StandardListView<Taxonomy> {
}