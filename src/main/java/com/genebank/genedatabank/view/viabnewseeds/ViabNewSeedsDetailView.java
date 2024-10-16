package com.genebank.genedatabank.view.viabnewseeds;

import com.genebank.genedatabank.entity.ViabNewSeeds;
import com.genebank.genedatabank.entity.ViabNewSeedsLine;
import com.genebank.genedatabank.entity.ViabilityStatus;
import com.genebank.genedatabank.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.TimeSource;
import io.jmix.flowui.component.formatter.NumberFormatter;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.select.JmixSelect;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.model.CollectionPropertyContainer;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;


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
    @ViewComponent
    private DataGrid<ViabNewSeedsLine> viabNewsSeedsLineDataGrid;
    @ViewComponent
    private CollectionPropertyContainer<ViabNewSeedsLine> viabNewSeedsLinesDc;
    @Autowired
    private NumberFormatter numberFormatter;

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
    public void onInit(final InitEvent event) {
        viabNewsSeedsLineDataGrid.getItems().addItemSetChangeListener(itemSetChangeEvent -> {
            calMedViabPercent();
        });
        viabNewsSeedsLineDataGrid.getItems().addStateChangeListener(stateChangeEvent -> {
            calMedViabPercent();
        });
            // I see work when the detail screen is loaded
            // the method calMedViabPercent() work
            // an the values is put on field and is saved
            viabNewsSeedsLineDataGrid.addAttachListener(attachEvent -> {
                calMedViabPercent();
            });
            // when some value of one item is changed call method to calculate medium percent for viability
            // the method calMedViabPercent() work
            // an the values is put on field and is NOT saved ?
            viabNewsSeedsLineDataGrid.getItems().addValueChangeListener(valueChangeEvent -> {
                    calMedViabPercent();
            });
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

    private void calMedViabPercent() {
        double total = 0;
        for (ViabNewSeedsLine line : viabNewSeedsLinesDc.getItems()){
         total += (double) line.getGermFaculty() /viabNewSeedsLinesDc.getItems().size();
        }
        //getEditedEntity().setViabPercent((int)(Math.round(total)));
        viabPercentField.setValue(Objects.requireNonNull(numberFormatter.apply((int) (Math.round(total)))));
    }

}