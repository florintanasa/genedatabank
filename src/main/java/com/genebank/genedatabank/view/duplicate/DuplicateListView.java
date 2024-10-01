package com.genebank.genedatabank.view.duplicate;

import com.genebank.genedatabank.entity.Duplicate;
import com.genebank.genedatabank.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "duplicates", layout = MainView.class)
@ViewController("Duplicate.list")
@ViewDescriptor("duplicate-list-view.xml")
@LookupComponent("duplicatesDataGrid")
@DialogMode(width = "64em")
public class DuplicateListView extends StandardListView<Duplicate> {
}