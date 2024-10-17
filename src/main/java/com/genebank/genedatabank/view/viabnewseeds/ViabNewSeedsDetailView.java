package com.genebank.genedatabank.view.viabnewseeds;

import com.genebank.genedatabank.entity.User;
import com.genebank.genedatabank.entity.ViabNewSeeds;
import com.genebank.genedatabank.entity.ViabNewSeedsLine;
import com.genebank.genedatabank.entity.ViabilityStatus;
import com.genebank.genedatabank.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.TimeSource;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.data.Sequence;
import io.jmix.data.Sequences;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.formatter.NumberFormatter;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.select.JmixSelect;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.model.CollectionPropertyContainer;
import io.jmix.flowui.model.DataContext;
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
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private Sequences sequences;
    @Autowired
    private MessageBundle messageBundle;
    @Autowired
    private Notifications notifications;

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
}

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        // when I add new item run event
        Objects.requireNonNull(viabNewsSeedsLineDataGrid.getItems()).addItemSetChangeListener(itemSetChangeEvent -> {
            if (viabNewSeedsLinesDc.getItems().isEmpty()) { // check if not exist items in data grid
                getEditedEntity().setViabPercent(null); // if not exist set null viability percent because 0 is a value possible
            } else {
                calMedViabPercent(); // if exist calculate medium viability percent
            }
        });
        // when I change item run event
        viabNewsSeedsLineDataGrid.getItems().addStateChangeListener(stateChangeEvent -> {
            if (viabNewSeedsLinesDc.getItems().isEmpty()) { // check if not exist items in data grid
                getEditedEntity().setViabPercent(null); // if not exist set null viability percent because 0 is a value possible
            } else {
                calMedViabPercent(); // if exist calculate medium viability percent
            }
        });
        // set read only field Status
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

    // before to save we run sequences to create the next serial number for idVNS
    @Subscribe(target = Target.DATA_CONTEXT)
    public void onPreSave(final DataContext.PreSaveEvent event) {
        final User user = (User) currentAuthentication.getUser();
        if (getEditedEntity().getIdVNS() == null) {
            if (getEditedEntity().getId_accenumb().getId_instcode().getInstcode().equals(user.getId_institute().getInstcode())) {
                long idVnsNumber = sequences.createNextValue(Sequence.withName("Nr" + getEditedEntity().getId_accenumb().getId_instcode().getSerialVNS()));
                String serialVNS = getEditedEntity().getId_accenumb().getId_instcode().getSerialVNS()+ "-" + idVnsNumber;
                getEditedEntity().setIdVNS(serialVNS);
            } else {
                String error_message = messageBundle.getMessage("error_message_serialVNS");
                notifications.create("HOPA",error_message).withDuration(5000).show();
            }
        }
    }
    
    

}