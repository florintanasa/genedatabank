/**
 * @author : Florin Tanasă
 * @since : 05.10.2024
 **/

package com.genebank.genedatabank.view.duplicateline;

import com.genebank.genedatabank.entity.DuplicateLine;
import com.genebank.genedatabank.entity.User;
import com.genebank.genedatabank.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.action.list.CreateAction;
import io.jmix.flowui.action.list.EditAction;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;


@Route(value = "duplicateLines", layout = MainView.class)
@ViewController("DuplicateLine.list")
@ViewDescriptor("duplicate-line-list-view.xml")
@LookupComponent("duplicateLinesDataGrid")
@DialogMode(width = "64em")
public class DuplicateLineListView extends StandardListView<DuplicateLine> {
    @ViewComponent("duplicateLinesDataGrid.create")
    private CreateAction<DuplicateLine> duplicateLinesDataGridCreate;
    @ViewComponent("duplicateLinesDataGrid.edit")
    private EditAction<DuplicateLine> duplicateLinesDataGridEdit;
    @Autowired
    private CurrentAuthentication currentAuthentication;

    @Subscribe
    public void onInit(final InitEvent event) {
        final User user = (User) currentAuthentication.getUser();
        if (!Objects.equals(user.getUsername(), "admin")) {
            duplicateLinesDataGridEdit.setEnabled(false);
        }
        duplicateLinesDataGridCreate.setEnabled(false);

    }
    
}