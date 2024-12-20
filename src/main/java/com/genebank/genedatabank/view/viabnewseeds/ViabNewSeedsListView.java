package com.genebank.genedatabank.view.viabnewseeds;

import com.genebank.genedatabank.entity.User;
import com.genebank.genedatabank.entity.ViabNewSeeds;
import com.genebank.genedatabank.entity.ViabNewSeedsLine;
import com.genebank.genedatabank.entity.ViabilityStatus;
import com.genebank.genedatabank.view.main.MainView;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.entity.KeyValueEntity;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.Dialogs;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.action.DialogAction;
import io.jmix.flowui.action.list.EditAction;
import io.jmix.flowui.action.list.ItemTrackingAction;
import io.jmix.flowui.action.list.ReadAction;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Objects;
import java.util.UUID;


/**
 * @author : Florin Tanasă
 * @since : 15.10.2024
 **/
@Route(value = "viabNewSeedses", layout = MainView.class)
@ViewController("ViabNewSeeds.list")
@ViewDescriptor("viab-new-seeds-list-view.xml")
@LookupComponent("viabNewSeedsesDataGrid")
@DialogMode(width = "64em")
public class ViabNewSeedsListView extends StandardListView<ViabNewSeeds> {
    @Autowired
    private Dialogs dialogs;
    @ViewComponent
    private MessageBundle messageBundle;
    @ViewComponent
    private DataGrid<ViabNewSeeds> viabNewSeedsesDataGrid;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private Notifications notifications;
    @ViewComponent
    private CollectionLoader<ViabNewSeeds> viabNewSeedsesDl;
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @ViewComponent("viabNewSeedsesDataGrid.markAsDone")
    private ItemTrackingAction<ViabNewSeeds> viabNewSeedsesDataGridMarkAsDone;
    @ViewComponent
    private JmixButton editBtn;
    @ViewComponent("viabNewSeedsesDataGrid.edit")
    private EditAction<ViabNewSeeds> viabNewSeedsesDataGridEdit;
    @ViewComponent("viabNewSeedsesDataGrid.read")
    private ReadAction<ViabNewSeeds> viabNewSeedsesDataGridRead;

    @Subscribe("viabNewSeedsesDataGrid.markAsDone")
    public void onViabNewSeedsesDataGridMarkAsDone(final ActionPerformedEvent event) {
        // create dialog with header, content and an action when the user press Ok button
        dialogs.createOptionDialog()
                .withHeader(messageBundle.getMessage("viabNewSeedsesDataGrid.markAsDoneHeader"))
                .withContent(new Html(messageBundle.getMessage("viabNewSeedsesDataGrid.markAsDoneContent")))
                .withActions(
                        new DialogAction(DialogAction.Type.OK).withHandler(actionPerformedEvent ->
                                markCurrentViabNewSeedsTestAsDone()), // if user press Ok button run the method
                        new DialogAction(DialogAction.Type.CANCEL)
                )
                .open();
    }
    // method to change the status field to Finished
    private void markCurrentViabNewSeedsTestAsDone() {
        // create entity object with data from selected item
        ViabNewSeeds viabNewSeedsTestToMarkAsFinished = viabNewSeedsesDataGrid.getSingleSelectedItem();
        if (viabNewSeedsTestToMarkAsFinished != null) { // check if user was selected something
            viabNewSeedsTestToMarkAsFinished.setStatus(ViabilityStatus.FINISHED); // then set the status
            dataManager.save(viabNewSeedsTestToMarkAsFinished); // and then save modification in database
            notifications.create(messageBundle.getMessage("viabNewSeedsTestToMarkAsFinished.confirmation")); // alert user
            viabNewSeedsesDl.load(); // finally we load the changes
        }
    }
    // method to validate/invalidate actions. change action for edit button to read only by state and user
    private void actionsviabNewSeedsesDataGrid(){
        final User user = (User) currentAuthentication.getUser();
        if (!Objects.equals(user.getUsername(), "admin")) {
            if (viabNewSeedsesDataGrid.getSingleSelectedItem() == null) {
                viabNewSeedsesDataGridMarkAsDone.setEnabled(false);
                editBtn.setEnabled(false);
            } else if (viabNewSeedsesDataGrid.getSingleSelectedItem().getStatus() == ViabilityStatus.IN_PROGRESS) {
                viabNewSeedsesDataGridMarkAsDone.setEnabled(true);
                editBtn.setEnabled(true);
                editBtn.setAction(viabNewSeedsesDataGridEdit);
            } else if (viabNewSeedsesDataGrid.getSingleSelectedItem().getStatus().equals(ViabilityStatus.FINISHED)) {
                viabNewSeedsesDataGridMarkAsDone.setEnabled(false);
                editBtn.setAction(viabNewSeedsesDataGridRead);
            }
             if (viabNewSeedsesDataGrid.getSingleSelectedItem() != null
                     && !Objects.equals(viabNewSeedsesDataGrid.getSingleSelectedItem().getCreatedBy(), user.getUsername())) {
                viabNewSeedsesDataGridMarkAsDone.setEnabled(false);
                editBtn.setAction(viabNewSeedsesDataGridRead);
            }
             if (viabNewSeedsesDataGrid.getSingleSelectedItem() != null) {
                 // calculate for selected item how many items from ViabNewSeedsLine have germFaculty null
                 long countNullGermFaculty = dataManager
                         .loadValue("select count(v.viabNewSeeds) from ViabNewSeedsLine v " +
                                         "where v.viabNewSeeds.id=:id_viabNewSeeds and v.germFaculty is null",Long.class)
                         .parameter("id_viabNewSeeds", viabNewSeedsesDataGrid.getSingleSelectedItem().getId())
                         .one();
             /*
            // Work also next but is better for the list data
            List<KeyValueEntity>  countNullGermFaculty = dataManager
                    .loadValues("select count(v.viabNewSeeds) from ViabNewSeedsLine v where v.viabNewSeeds.id=:id_viabNewSeeds and v.germFaculty is null")
                    .parameter("id_viabNewSeeds", viabNewSeedsesDataGrid.getSingleSelectedItem().getId())
                    .store("main")
                    .properties("count")
                    .maxResults(1)
                    .list();

            long count = countNullGermFaculty.get(0).getValue("count");
              */

                 // if exist minim one I disable button to mark Done the determination
                 if (countNullGermFaculty > 0) {
                     viabNewSeedsesDataGridMarkAsDone.setEnabled(false);
                 }
             }
            // check if analysis have some tests, if not have invalidated button to mark Finished
            if (viabNewSeedsesDataGrid.getSingleSelectedItem() != null
                    && viabNewSeedsesDataGrid.getSingleSelectedItem().getViabPercent() == null) {
                viabNewSeedsesDataGridMarkAsDone.setEnabled(false);
            }
        }
    }

    // run event when item in container is changed
    @Subscribe(id = "viabNewSeedsesDc", target = Target.DATA_CONTAINER)
    public void onViabNewSeedsesDcItemChange(final InstanceContainer.ItemChangeEvent<ViabNewSeeds> event) {
        actionsviabNewSeedsesDataGrid(); //call the method
    }

    // run event on click on item
    @Subscribe("viabNewSeedsesDataGrid")
    public void onViabNewSeedsesDataGridItemClick(final ItemClickEvent<ViabNewSeeds> event) {
        actionsviabNewSeedsesDataGrid(); // call the method
    }
/*
    // method to determine if some test is not done
    private void checkItemGermFacIsNull() {
        for (ViabNewSeedsLine line : viabNewSeedsLinesDc.getItems()) {
            if (line.getGermFaculty() == null) {

            }
        }
    }
*/
}