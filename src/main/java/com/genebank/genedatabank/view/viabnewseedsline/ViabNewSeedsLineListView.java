package com.genebank.genedatabank.view.viabnewseedsline;

import com.genebank.genedatabank.entity.User;
import com.genebank.genedatabank.entity.ViabNewSeedsLine;
import com.genebank.genedatabank.view.main.MainView;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.action.list.CreateAction;
import io.jmix.flowui.action.list.EditAction;
import io.jmix.flowui.action.list.ReadAction;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;


/**
 * @author : Florin Tanasă
 * @since : 17.10.2024
 **/
@Route(value = "viabNewSeedsLines", layout = MainView.class)
@ViewController("ViabNewSeedsLine.list")
@ViewDescriptor("viab-new-seeds-line-list-view.xml")
@LookupComponent("viabNewSeedsLinesDataGrid")
@DialogMode(width = "64em")
public class ViabNewSeedsLineListView extends StandardListView<ViabNewSeedsLine> {
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @ViewComponent("viabNewSeedsLinesDataGrid.edit")
    private EditAction<ViabNewSeedsLine> viabNewSeedsLinesDataGridEdit;
    @ViewComponent("viabNewSeedsLinesDataGrid.create")
    private CreateAction<ViabNewSeedsLine> viabNewSeedsLinesDataGridCreate;
    @ViewComponent("viabNewSeedsLinesDataGrid.read")
    private ReadAction<ViabNewSeedsLine> viabNewSeedsLinesDataGridRead;
    @ViewComponent
    private JmixButton editBtn;
    @ViewComponent
    private DataGrid<ViabNewSeedsLine> viabNewSeedsLinesDataGrid;

    @Subscribe
    public void onInit(final InitEvent event) {
        final User user = (User) currentAuthentication.getUser();
        // only user admin can edit an item all others only read
        if (!Objects.equals(user.getUsername(), "admin")) {
            viabNewSeedsLinesDataGridEdit.setEnabled(false);
        }
        // disable for all users to create new items
        viabNewSeedsLinesDataGridCreate.setEnabled(false);
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        
    }

    @Subscribe("viabNewSeedsLinesDataGrid")
    public void onViabNewSeedsLinesDataGridItemClick(final ItemClickEvent<ViabNewSeedsLine> event) {
        final User user = (User) currentAuthentication.getUser();
        // only user admin can edit selected item all others can read only
        if (!Objects.equals(user.getUsername(), "admin")
                && viabNewSeedsLinesDataGrid.getSingleSelectedItem() != null) {
            editBtn.setAction(viabNewSeedsLinesDataGridRead);
        }
    }

}