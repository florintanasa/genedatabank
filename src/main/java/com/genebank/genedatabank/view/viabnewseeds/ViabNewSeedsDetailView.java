package com.genebank.genedatabank.view.viabnewseeds;

import com.genebank.genedatabank.entity.ViabNewSeeds;
import com.genebank.genedatabank.entity.ViabilityStatus;
import com.genebank.genedatabank.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.TimeSource;
import io.jmix.flowui.component.select.JmixSelect;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author : Florin Tanasă
 * @since : 15.10.2024
 **/
@Route(value = "viabNewSeedses/:id", layout = MainView.class)
@ViewController("ViabNewSeeds.detail")
@ViewDescriptor("viab-new-seeds-detail-view.xml")
@EditedEntityContainer("viabNewSeedsDc")
public class ViabNewSeedsDetailView extends StandardDetailView<ViabNewSeeds> {
    @Autowired
    private TimeSource timeSource;
    @ViewComponent
    private JmixSelect<Object> statusField;
    @ViewComponent
    private TypedTextField<Integer> viabPercentField;
    @ViewComponent
    private TypedTextField<Integer> yearTestField;

    @Subscribe
    public void onInitEntity(final InitEntityEvent<ViabNewSeeds> event) {
        // set default status to In progress
        event.getEntity().setStatus(ViabilityStatus.IN_PROGRESS);
        // set default Year test field to actual year
        event.getEntity().setYearTest(timeSource.now().getYear());
        // set read only field status
        statusField.setReadOnly(true);
        // set read only Viability percentage field
        viabPercentField.setReadOnly(true);
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        // set read only field status
        statusField.setReadOnly(true);
        // set read only Viability percentage field
        viabPercentField.setReadOnly(true);
        // set read only Year test field
        yearTestField.setReadOnly(true);
    }
    
    
}