package com.genebank.genedatabank.view.viaboldseedsline;

import com.genebank.genedatabank.entity.ViabOldSeedsLine;
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
@Route(value = "viabOldSeedsLines/:id", layout = MainView.class)
@ViewController("ViabOldSeedsLine.detail")
@ViewDescriptor("viab-old-seeds-line-detail-view.xml")
@EditedEntityContainer("viabOldSeedsLineDc")
public class ViabOldSeedsLineDetailView extends StandardDetailView<ViabOldSeedsLine> {
}