package com.genebank.genedatabank.view.viabnewseedsline;

import com.genebank.genedatabank.entity.ViabNewSeedsLine;
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
@Route(value = "viabNewSeedsLines/:id", layout = MainView.class)
@ViewController("ViabNewSeedsLine.detail")
@ViewDescriptor("viab-new-seeds-line-detail-view.xml")
@EditedEntityContainer("viabNewSeedsLineDc")
public class ViabNewSeedsLineDetailView extends StandardDetailView<ViabNewSeedsLine> {
}