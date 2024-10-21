package com.genebank.genedatabank.view.viaboldseeds;

import com.genebank.genedatabank.entity.ViabOldSeeds;
import com.genebank.genedatabank.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;


/**
 * @author : Florin Tanasă
 * @since : 21.10.2024
 **/
@Route(value = "viabOldSeedses/:id", layout = MainView.class)
@ViewController("ViabOldSeeds.detail")
@ViewDescriptor("viab-old-seeds-detail-view.xml")
@EditedEntityContainer("viabOldSeedsDc")
public class ViabOldSeedsDetailView extends StandardDetailView<ViabOldSeeds> {
}