package com.genebank.genedatabank.view.duplicate;

import com.genebank.genedatabank.entity.Duplicate;
import com.genebank.genedatabank.entity.DuplicateStatus;
import com.genebank.genedatabank.view.main.MainView;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.router.Route;
import io.jmix.core.TimeSource;
import io.jmix.flowui.Dialogs;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.action.DialogAction;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "duplicates", layout = MainView.class)
@ViewController("Duplicate.list")
@ViewDescriptor("duplicate-list-view.xml")
@LookupComponent("duplicatesDataGrid")
@DialogMode(width = "64em")
public class DuplicateListView extends StandardListView<Duplicate> {

    @ViewComponent
    private DataGrid<Duplicate> duplicatesDataGrid;
    @ViewComponent
    private DataContext dataContext;
    @Autowired
    private Notifications notifications;
    @Autowired
    private MessageBundle messageBundle;
    @ViewComponent
    private CollectionLoader<Duplicate> duplicatesDl;
    @Autowired
    private Dialogs dialogs;
    @Autowired
    private TimeSource timeSource;

    @Subscribe("duplicatesDataGrid.markAsConfirmed")
    public void onDuplicatesDataGridMarkAsConfirmed(final ActionPerformedEvent event) {
        dialogs.createOptionDialog()
                .withHeader(messageBundle.getMessage("duplicatesDataGrid.markAsConfirmed"))
                .withContent(new Html(messageBundle.getMessage("duplicatesDataGrid.markAsConfirmedMessage")))
                .withActions(
                        new DialogAction(DialogAction.Type.OK).withHandler(actionPerformedEvent ->
                                markCurrentDuplicateAsConfirmed()),
                        new DialogAction(DialogAction.Type.CANCEL)
                )
                .open();
    }

    @Subscribe("duplicatesDataGrid.markAsInDelivery")
    public void onDuplicatesDataGridMarkAsInDelivery(final ActionPerformedEvent event) {
        dialogs.createOptionDialog()
                .withHeader(messageBundle.getMessage("duplicatesDataGrid.markAsInDelivery"))
                .withContent(new Html(messageBundle.getMessage("duplicatesDataGrid.markAsInDeliveryMessage")))
                .withActions(
                        new DialogAction(DialogAction.Type.OK).withHandler(actionPerformedEvent ->
                                markCurrentDuplicateAsInDelivery()),
                        new DialogAction(DialogAction.Type.CANCEL)
                )
                .open();
    }

    @Subscribe("duplicatesDataGrid.markAsDelivered")
    public void onDuplicatesDataGridMarkAsDelivered(final ActionPerformedEvent event) {
        dialogs.createOptionDialog()
                .withHeader(messageBundle.getMessage("duplicatesDataGrid.markAsDelivered"))
                .withContent(new Html(messageBundle.getMessage("duplicatesDataGrid.markAsDeliveredMessage")))
                .withActions(
                        new DialogAction(DialogAction.Type.OK).withHandler(actionPerformedEvent ->
                                markCurrentDuplicateAsConfirmedDelivery()),
                        new DialogAction(DialogAction.Type.CANCEL)
                )
                .open();
    }

    private void markCurrentDuplicateAsConfirmed() {
        Duplicate duplicateToMarkAsConfirmed = duplicatesDataGrid.getSingleSelectedItem();
        if (duplicateToMarkAsConfirmed != null) {
            duplicateToMarkAsConfirmed.setStatus(DuplicateStatus.CONFIRMED);
            dataContext.save();
            notifications.create(messageBundle.getMessage("duplicate.confirmed")).show();
            duplicatesDl.load();
        }
    }

    private void markCurrentDuplicateAsInDelivery() {
        Duplicate duplicateToMarkAsInDelivery = duplicatesDataGrid.getSingleSelectedItem();
        if (duplicateToMarkAsInDelivery != null) {
            duplicateToMarkAsInDelivery.setStatus(DuplicateStatus.IN_DELIVERY);
            duplicateToMarkAsInDelivery.setSendDate(timeSource.now().toLocalDate());
            dataContext.save();
            notifications.create(messageBundle.getMessage("duplicate.inDelivery")).show();
            duplicatesDl.load();
        }
    }

    private void markCurrentDuplicateAsConfirmedDelivery() {
        Duplicate duplicateToMarkAsConfirmedDelivery = duplicatesDataGrid.getSingleSelectedItem();
        if (duplicateToMarkAsConfirmedDelivery != null) {
            duplicateToMarkAsConfirmedDelivery.setStatus(DuplicateStatus.CONFIRMED);
            dataContext.save();
            notifications.create(messageBundle.getMessage("duplicate.delivery")).show();
            duplicatesDl.load();
        }
    }
}