package com.genebank.genedatabank.view.duplicate;

import com.genebank.genedatabank.entity.Duplicate;
import com.genebank.genedatabank.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "duplicates/:id", layout = MainView.class)
@ViewController("Duplicate.detail")
@ViewDescriptor("duplicate-detail-view.xml")
@EditedEntityContainer("duplicateDc")
public class DuplicateDetailView extends StandardDetailView<Duplicate> {
}