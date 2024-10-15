package com.genebank.genedatabank.view.viabnewseeds;

import com.genebank.genedatabank.entity.ViabNewSeeds;
import com.genebank.genedatabank.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;


/**
 * @author : Florin Tanasă
 * @since : 15.10.2024
 **/
@Route(value = "viabNewSeedses/:id", layout = MainView.class)
@ViewController("ViabNewSeeds.detail")
@ViewDescriptor("viab-new-seeds-detail-view.xml")
@EditedEntityContainer("viabNewSeedsDc")
public class ViabNewSeedsDetailView extends StandardDetailView<ViabNewSeeds> {
}