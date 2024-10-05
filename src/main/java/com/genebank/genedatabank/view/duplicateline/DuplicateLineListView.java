package com.genebank.genedatabank.view.duplicateline;

import com.genebank.genedatabank.entity.DuplicateLine;
import com.genebank.genedatabank.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "duplicateLines", layout = MainView.class)
@ViewController("DuplicateLine.list")
@ViewDescriptor("duplicate-line-list-view.xml")
@LookupComponent("duplicateLinesDataGrid")
@DialogMode(width = "64em")
public class DuplicateLineListView extends StandardListView<DuplicateLine> {
}