/**
 * @author : Florin Tanasă
 * @since : 29.09.2029
 **/

package com.genebank.genedatabank.view.duplicate;

import com.genebank.genedatabank.entity.Duplicate;
import com.genebank.genedatabank.entity.DuplicateStatus;
import com.genebank.genedatabank.entity.User;
import com.genebank.genedatabank.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.TimeSource;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.Dialogs;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.action.DialogAction;
import io.jmix.flowui.action.list.EditAction;
import io.jmix.flowui.action.list.ItemTrackingAction;
import io.jmix.flowui.action.list.ReadAction;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.download.Downloader;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;
import io.jmix.reports.entity.ReportOutputType;
import io.jmix.reports.runner.ReportRunner;
import io.jmix.reports.yarg.reporting.ReportOutputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;


@Route(value = "duplicates", layout = MainView.class)
@ViewController("Duplicate.list")
@ViewDescriptor("duplicate-list-view.xml")
@LookupComponent("duplicatesDataGrid")
@DialogMode(width = "64em")
public class DuplicateListView extends StandardListView<Duplicate> {

    @ViewComponent
    private DataGrid<Duplicate> duplicatesDataGrid;
    @Autowired
    private Notifications notifications;
    @ViewComponent
    private MessageBundle messageBundle;
    @ViewComponent
    private CollectionLoader<Duplicate> duplicatesDl;
    @ViewComponent
    private JmixButton excelExportSvalBtn;
    @Autowired
    private Dialogs dialogs;
    @Autowired
    private TimeSource timeSource;
    @Autowired
    private DataManager dataManager;
    @ViewComponent
    private JmixButton editBtn;
    @ViewComponent("duplicatesDataGrid.read")
    private ReadAction<Duplicate> duplicatesDataGridRead;
    @ViewComponent("duplicatesDataGrid.edit")
    private EditAction<Duplicate> duplicatesDataGridEdit;
    @ViewComponent("duplicatesDataGrid.markAsConfirmed")
    private ItemTrackingAction<Duplicate> duplicatesDataGridMarkAsConfirmed;
    @ViewComponent("duplicatesDataGrid.markAsInDelivery")
    private ItemTrackingAction<Duplicate> duplicatesDataGridMarkAsInDelivery;
    @ViewComponent("duplicatesDataGrid.markAsDelivered")
    private ItemTrackingAction<Duplicate> duplicatesDataGridMarkAsDelivered;
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private ReportRunner reportRunner;
    @Autowired
    private Downloader downloader;

    @Subscribe
    public void onInit(final InitEvent event) {
        //invalidate button for Svalbard
        excelExportSvalBtn.setEnabled(false);
    }
    
    


    // run event when a item is changed
    @Subscribe(id = "duplicatesDc", target = Target.DATA_CONTAINER)
    public void onDuplicatesDcItemChange(final InstanceContainer.ItemChangeEvent<Duplicate> event) {
        actionsDuplicatesDataGrid(); // call the method
    }

    // run event when click on a item
    @Subscribe("duplicatesDataGrid")
    public void onDuplicatesDataGridItemClick(final ItemClickEvent<Duplicate> event) {
        actionsDuplicatesDataGrid(); // call the method
        if (duplicatesDataGrid.getSingleSelectedItem() == null) {
            excelExportSvalBtn.setEnabled(false);
        } else if (!duplicatesDataGrid.getSingleSelectedItem().getId_duplicate_institute().getInstcode().equals("NOR051")) {
            excelExportSvalBtn.setEnabled(false);// disable button for Svalbard report if selected item is not for Svalbard
        } else if (duplicatesDataGrid.getSingleSelectedItem().getId_duplicate_institute().getInstcode().equals("NOR051")) {
            excelExportSvalBtn.setEnabled(true);// enable button for Svalbard report if selected item is for Svalbard
        }
    }

    // method to validate/invalidate actions. change action for edit button to read only by state and user
    private void actionsDuplicatesDataGrid() {
        final User user = (User) currentAuthentication.getUser();
        if (!Objects.equals(user.getUsername(), "admin") ) {
            if (duplicatesDataGrid.getSingleSelectedItem() == null) {
                duplicatesDataGridMarkAsConfirmed.setEnabled(false);
                duplicatesDataGridMarkAsInDelivery.setEnabled(false);
                duplicatesDataGridMarkAsDelivered.setEnabled(false);
                editBtn.setEnabled(false);
                excelExportSvalBtn.setEnabled(false);
            } else if (duplicatesDataGrid.getSingleSelectedItem().getStatus().equals(DuplicateStatus.PREPARED)) {
                editBtn.setEnabled(true);
                editBtn.setAction(duplicatesDataGridEdit);// is necessary because the button remain for Read action when I am back
                duplicatesDataGridMarkAsConfirmed.setEnabled(true);
                duplicatesDataGridMarkAsInDelivery.setEnabled(false);
                duplicatesDataGridMarkAsDelivered.setEnabled(false);
            } else if (duplicatesDataGrid.getSingleSelectedItem().getStatus().equals(DuplicateStatus.CONFIRMED)) {
                editBtn.setAction(duplicatesDataGridRead);
                duplicatesDataGridEdit.setEnabled(false);
                duplicatesDataGridMarkAsConfirmed.setEnabled(false);
                duplicatesDataGridMarkAsInDelivery.setEnabled(true);
                duplicatesDataGridMarkAsDelivered.setEnabled(false);
            } else if (duplicatesDataGrid.getSingleSelectedItem().getStatus().equals(DuplicateStatus.IN_DELIVERY)) {
                editBtn.setAction(duplicatesDataGridRead);
                duplicatesDataGridEdit.setEnabled(false);
                duplicatesDataGridMarkAsConfirmed.setEnabled(false);
                duplicatesDataGridMarkAsInDelivery.setEnabled(false);
                duplicatesDataGridMarkAsDelivered.setEnabled(true);
            } else if (duplicatesDataGrid.getSingleSelectedItem().getStatus().equals(DuplicateStatus.DELIVERED)) {
                editBtn.setAction(duplicatesDataGridRead);
                duplicatesDataGridEdit.setEnabled(false);
                duplicatesDataGridMarkAsConfirmed.setEnabled(false);
                duplicatesDataGridMarkAsInDelivery.setEnabled(false);
                duplicatesDataGridMarkAsDelivered.setEnabled(false);
            }
        }
    }

    // run event when is clicked button markAsConfirmed
    @Subscribe("duplicatesDataGrid.markAsConfirmed")
    public void onDuplicatesDataGridMarkAsConfirmed(final ActionPerformedEvent event) {
        // create dialog with header, content and an action when the user press Confirmed button
        dialogs.createOptionDialog()
                .withHeader(messageBundle.getMessage("duplicatesDataGrid.markAsConfirmed"))
                .withContent(new Html(messageBundle.getMessage("duplicatesDataGrid.markAsConfirmedMessage")))
                .withActions(
                        new DialogAction(DialogAction.Type.OK).withHandler(actionPerformedEvent ->
                                markCurrentDuplicateAsConfirmed()), // if user press ok run the method
                        new DialogAction(DialogAction.Type.CANCEL)
                )
                .open();
    }

    // run event when is clicked button markAsInDelivery
    @Subscribe("duplicatesDataGrid.markAsInDelivery")
    public void onDuplicatesDataGridMarkAsInDelivery(final ActionPerformedEvent event) {
        // create dialog with header, content and an action when the user press Ok button
        dialogs.createOptionDialog()
                .withHeader(messageBundle.getMessage("duplicatesDataGrid.markAsInDelivery"))
                .withContent(new Html(messageBundle.getMessage("duplicatesDataGrid.markAsInDeliveryMessage")))
                .withActions(
                        new DialogAction(DialogAction.Type.OK).withHandler(actionPerformedEvent ->
                                markCurrentDuplicateAsInDelivery()), // if user press ok run the method
                        new DialogAction(DialogAction.Type.CANCEL)
                )
                .open();
    }

    // run event when is clicked button markAsDelivered
    @Subscribe("duplicatesDataGrid.markAsDelivered")
    public void onDuplicatesDataGridMarkAsDelivered(final ActionPerformedEvent event) {
        // create dialog with header, content and an action when the user press Ok button
        dialogs.createOptionDialog()
                .withHeader(messageBundle.getMessage("duplicatesDataGrid.markAsDelivered"))
                .withContent(new Html(messageBundle.getMessage("duplicatesDataGrid.markAsDeliveredMessage")))
                .withActions(
                        new DialogAction(DialogAction.Type.OK).withHandler(actionPerformedEvent ->
                                markCurrentDuplicateAsConfirmedDelivery()), // if user press ok run the method
                        new DialogAction(DialogAction.Type.CANCEL)
                )
                .open();
    }

    // method to change the status field to CONFIRMED
    private void markCurrentDuplicateAsConfirmed() {
        // create entity object with data from selected item
        Duplicate duplicateToMarkAsConfirmed = duplicatesDataGrid.getSingleSelectedItem();
        if (duplicateToMarkAsConfirmed != null) { // check if user was selected something
            duplicateToMarkAsConfirmed.setStatus(DuplicateStatus.CONFIRMED); // then set the status
            dataManager.save(duplicateToMarkAsConfirmed); // and then save in database the modification
            notifications.create(messageBundle.getMessage("duplicate.confirmed")).show(); // alert user
            duplicatesDl.load(); // finally we load the changes
        }
    }

    // method to change the status field to IN_DELIVERY
    private void markCurrentDuplicateAsInDelivery() {
        // create entity object with data from selected item
        Duplicate duplicateToMarkAsInDelivery = duplicatesDataGrid.getSingleSelectedItem();
        if (duplicateToMarkAsInDelivery != null) { // check if user was selected something
            duplicateToMarkAsInDelivery.setStatus(DuplicateStatus.IN_DELIVERY); // then set the status
            duplicateToMarkAsInDelivery.setSendDate(timeSource.now().toLocalDate()); // also set the date when delivery
            dataManager.save(duplicateToMarkAsInDelivery); // and then save in database the modification
            notifications.create(messageBundle.getMessage("duplicate.inDelivery")).show(); // alert user
            duplicatesDl.load(); // finally we load the changes
        }
    }

    // method to change the status field to DELIVERED
    private void markCurrentDuplicateAsConfirmedDelivery() {
        // create entity object with data from selected item
        Duplicate duplicateToMarkAsConfirmedDelivery = duplicatesDataGrid.getSingleSelectedItem();
        if (duplicateToMarkAsConfirmedDelivery != null) { // check if user was selected something
            duplicateToMarkAsConfirmedDelivery.setStatus(DuplicateStatus.DELIVERED); // then set the status
            dataManager.save(duplicateToMarkAsConfirmedDelivery); // and then save in database the modification
            notifications.create(messageBundle.getMessage("duplicate.delivery")).show(); // alert user
            duplicatesDl.load(); // finally we load the changes
        }
    }

    // method to get report for Svalbard is run when button excelExportSvalBtn is clicked
    @Subscribe(id = "excelExportSvalBtn", subject = "clickListener")
    public void onExcelExportSvalBtnClick(final ClickEvent<JmixButton> event) {
        // prepare the report in Excel output format by Report code and Template code
        ReportOutputDocument svalbard = reportRunner.byReportCode("RepSval")
                .addParam("duplicate", duplicatesDataGrid.getSelectedItems().iterator().next())
                .withTemplateCode("Sval_V1")
                .withOutputType(ReportOutputType.XLSX)
                .run();
        // download the report in Excel format
        downloader.download(svalbard.getContent(), svalbard.getDocumentName());
    }
}