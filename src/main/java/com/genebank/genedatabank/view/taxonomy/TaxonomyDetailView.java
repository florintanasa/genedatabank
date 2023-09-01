package com.genebank.genedatabank.view.taxonomy;

import com.genebank.genedatabank.entity.Taxonomy;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 01.09.2023
**/
@Route(value = "taxonomies/:id", layout = MainView.class)
@ViewController("Taxonomy.detail")
@ViewDescriptor("taxonomy-detail-view.xml")
@EditedEntityContainer("taxonomyDc")
public class TaxonomyDetailView extends StandardDetailView<Taxonomy> {
}