package com.genebank.genedatabank.view.viaboldseedsline;

import com.genebank.genedatabank.entity.User;
import com.genebank.genedatabank.entity.ViabOldSeedsLine;
import com.genebank.genedatabank.view.main.MainView;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.action.list.CreateAction;
import io.jmix.flowui.action.list.ReadAction;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;


/**
 * @author : florin
 * @since : 22.10.2024
 **/
@Route(value = "viabOldSeedsLines", layout = MainView.class)
@ViewController("ViabOldSeedsLine.list")
@ViewDescriptor("viab-old-seeds-line-list-view.xml")
@LookupComponent("viabOldSeedsLinesDataGrid")
@DialogMode(width = "64em")
public class ViabOldSeedsLineListView extends StandardListView<ViabOldSeedsLine> {
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @ViewComponent
    private JmixButton editBtn;
    @ViewComponent("viabOldSeedsLinesDataGrid.create")
    private CreateAction<ViabOldSeedsLine> viabOldSeedsLinesDataGridCreate;
    @ViewComponent
    private DataGrid<ViabOldSeedsLine> viabOldSeedsLinesDataGrid;
    @ViewComponent("viabOldSeedsLinesDataGrid.read")
    private ReadAction<ViabOldSeedsLine> viabOldSeedsLinesDataGridRead;

    @Subscribe
    public void onInit(final InitEvent event) {
        final User user = (User) currentAuthentication.getUser();
        // only user admin can edit an item all others only read
        if (!Objects.equals(user.getUsername(), "admin")) {
            // change action for edit button to read
            editBtn.setAction(viabOldSeedsLinesDataGridRead);
        }
        // disable for all users to create new items
        viabOldSeedsLinesDataGridCreate.setEnabled(false);
    }

    @Subscribe("viabOldSeedsLinesDataGrid")
    public void onViabOldSeedsLinesDataGridItemClick(final ItemClickEvent<ViabOldSeedsLine> event) {
        final User user = (User) currentAuthentication.getUser();
        // only user admin can edit selected item all others can read only
        if (!Objects.equals(user.getUsername(), "admin")
                && viabOldSeedsLinesDataGrid.getSingleSelectedItem() != null) {
            editBtn.setAction(viabOldSeedsLinesDataGridRead);
        }
    }

}