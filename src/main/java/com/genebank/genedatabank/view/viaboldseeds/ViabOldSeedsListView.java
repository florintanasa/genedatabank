package com.genebank.genedatabank.view.viaboldseeds;

import com.genebank.genedatabank.entity.User;
import com.genebank.genedatabank.entity.ViabOldSeeds;
import com.genebank.genedatabank.entity.ViabilityStatus;
import com.genebank.genedatabank.view.main.MainView;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
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

import java.util.Objects;


/**
 * @author : Florin Tanasă
 * @since : 21.10.2024
 **/
@Route(value = "viabOldSeedses", layout = MainView.class)
@ViewController("ViabOldSeeds.list")
@ViewDescriptor("viab-old-seeds-list-view.xml")
@LookupComponent("viabOldSeedsesDataGrid")
@DialogMode(width = "64em")
public class ViabOldSeedsListView extends StandardListView<ViabOldSeeds> {
    @ViewComponent
    private DataGrid<ViabOldSeeds> viabOldSeedsesDataGrid;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private Notifications notifications;
    @ViewComponent
    private CollectionLoader<ViabOldSeeds> viabOldSeedsesDl;
    @ViewComponent
    private MessageBundle messageBundle;
    @Autowired
    private Dialogs dialogs;
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @ViewComponent("viabOldSeedsesDataGrid.markAsDone")
    private ItemTrackingAction<ViabOldSeeds> viabOldSeedsesDataGridMarkAsDone;
    @ViewComponent
    private JmixButton editBtn;
    @ViewComponent("viabOldSeedsesDataGrid.edit")
    private EditAction<ViabOldSeeds> viabOldSeedsesDataGridEdit;
    @ViewComponent("viabOldSeedsesDataGrid.read")
    private ReadAction<ViabOldSeeds> viabOldSeedsesDataGridRead;

    @Subscribe("viabOldSeedsesDataGrid.markAsDone")
    public void onViabOldSeedsesDataGridMarkAsDone(final ActionPerformedEvent event) {
        // create dialog with header, content and an action when the user press Ok button
        dialogs.createOptionDialog()
                .withHeader(messageBundle.getMessage("viabOldSeedsesDataGrid.markAsDoneHeader"))
                .withContent(new Html(messageBundle.getMessage("viabOldSeedsesDataGrid.markAsDoneContent")))
                .withActions(
                        new DialogAction(DialogAction.Type.OK).withHandler(actionPerformedEvent ->
                                markCurrentViabOldSeedsTestAsDone()), // if user press Ok button run the method
                        new DialogAction(DialogAction.Type.CANCEL)
                )
                .open();
    }
    // method to change the status field to Finished
    private void markCurrentViabOldSeedsTestAsDone() {
        // create entity object with data from selected item
        ViabOldSeeds viaOldSeedsTestToMarkAsFinished = viabOldSeedsesDataGrid.getSingleSelectedItem();
        if (viaOldSeedsTestToMarkAsFinished != null) { // check if user was selected something
            viaOldSeedsTestToMarkAsFinished.setStatus(ViabilityStatus.FINISHED); // then set the status
            dataManager.save(viaOldSeedsTestToMarkAsFinished); // and then save modification in database
            notifications.create(messageBundle.getMessage("viaOldSeedsTestToMarkAsFinished.confirmation")); // alert user
            viabOldSeedsesDl.load(); // finally we load the changes
        }
    }
    // method to validate/invalidate actions. change action for edit button to read only by state and user
    private void actionsviabOldSeedsesDataGrid(){
        final User user = (User) currentAuthentication.getUser();
        if (!Objects.equals(user.getUsername(), "admin")) {
            if (viabOldSeedsesDataGrid.getSingleSelectedItem() == null) {
                viabOldSeedsesDataGridMarkAsDone.setEnabled(false);
                editBtn.setEnabled(false);
            } else if (viabOldSeedsesDataGrid.getSingleSelectedItem().getStatus() == ViabilityStatus.IN_PROGRESS) {
                viabOldSeedsesDataGridMarkAsDone.setEnabled(true);
                editBtn.setEnabled(true);
                editBtn.setAction(viabOldSeedsesDataGridEdit);
            } else if (viabOldSeedsesDataGrid.getSingleSelectedItem().getStatus().equals(ViabilityStatus.FINISHED)) {
                viabOldSeedsesDataGridMarkAsDone.setEnabled(false);
                editBtn.setAction(viabOldSeedsesDataGridRead);
            }
            if (viabOldSeedsesDataGrid.getSingleSelectedItem() != null
                    && !Objects.equals(viabOldSeedsesDataGrid.getSingleSelectedItem().getCreatedBy(), user.getUsername())) {
                viabOldSeedsesDataGridMarkAsDone.setEnabled(false);
                editBtn.setAction(viabOldSeedsesDataGridRead);
            }
            if (viabOldSeedsesDataGrid.getSingleSelectedItem() != null) {
                // calculate for selected item how many items from ViabNewSeedsLine have germFaculty null
                long countNullGermFaculty = dataManager
                        .loadValue("select count(v.viabOldSeeds) from ViabOldSeedsLine v " +
                                "where v.viabOldSeeds.id=:id_viabOldSeeds and v.germFaculty is null",Long.class)
                        .parameter("id_viabOldSeeds", viabOldSeedsesDataGrid.getSingleSelectedItem().getId())
                        .one();

            // Work also next but is better for the list data
             /*
                List<KeyValueEntity>  countNullGermFaculty = dataManager
                    .loadValues("select count(v.viabOldSeeds) from ViabOldSeedsLine v where v.viabOldSeeds.id=:id_viabOldSeeds and v.germFaculty is null")
                    .parameter("id_viabOldSeeds", viabOldSeedsesDataGrid.getSingleSelectedItem().getId())
                    .store("main")
                    .properties("count")
                    .maxResults(1)
                    .list();

                    long count = countNullGermFaculty.get(0).getValue("count");
              */

                // if exist minim one I disable button to mark Finished the determination
                if (countNullGermFaculty > 0) {
                    viabOldSeedsesDataGridMarkAsDone.setEnabled(false);
                }
            }
            // check if analysis have some test if not invalidate button to mark Finished
            if (viabOldSeedsesDataGrid.getSingleSelectedItem() != null
            && viabOldSeedsesDataGrid.getSingleSelectedItem().getViabPercent() == null) {
                viabOldSeedsesDataGridMarkAsDone.setEnabled(false);
            }
        }
    }

    // run event when an item in container is changed
    @Subscribe(id = "viabOldSeedsesDc", target = Target.DATA_CONTAINER)
    public void onViabOldSeedsesDcItemChange(final InstanceContainer.ItemChangeEvent<ViabOldSeeds> event) {
        actionsviabOldSeedsesDataGrid(); //call the method
    }

    // run event on click on item
    @Subscribe("viabOldSeedsesDataGrid")
    public void onViabOldSeedsesDataGridItemClick(final ItemClickEvent<ViabOldSeeds> event) {
        actionsviabOldSeedsesDataGrid(); // call the method
    }

}