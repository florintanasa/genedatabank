package com.genebank.genedatabank.view.institute;

import com.genebank.genedatabank.entity.Institute;

import com.genebank.genedatabank.entity.User;
import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.grid.DataGridColumn;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;


/**
 * @author : Florin Tanasă
 * @since : 31.08.2023
**/
@Route(value = "institutes", layout = MainView.class)
@ViewController("Institute.list")
@ViewDescriptor("institute-list-view.xml")
@LookupComponent("institutesDataGrid")
@DialogMode(width = "100%")
public class InstituteListView extends StandardListView<Institute> {
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @ViewComponent
    private DataGrid<Institute> institutesDataGrid;

    @Subscribe
    public void onInit(final InitEvent event) {
        final User user = (User) currentAuthentication.getUser();
        // made next fields visible only for user admin
        if (Objects.equals(user.getUsername(), "admin")) {
            DataGridColumn<Institute> apiKeyGoogleMaps = institutesDataGrid.getColumnByKey("apiKeyGoogleMaps");
            if (apiKeyGoogleMaps != null) {
                apiKeyGoogleMaps.setVisible(true);
            }
            DataGridColumn<Institute> serialAccenumb = institutesDataGrid.getColumnByKey("serialAccenumb");
            if (serialAccenumb != null) {
                serialAccenumb.setVisible(true);
            }
            DataGridColumn<Institute> serialAccenumbTemp = institutesDataGrid.getColumnByKey("serialAccenumbTemp");
            if (serialAccenumbTemp != null) {
                serialAccenumbTemp.setVisible(true);
            }
            DataGridColumn<Institute> serialVNS = institutesDataGrid.getColumnByKey("serialVNS");
            if (serialVNS != null) {
                serialVNS.setVisible(true);
            }
            DataGridColumn<Institute> serialVOS = institutesDataGrid.getColumnByKey("serialVOS");
            if (serialVOS != null) {
                serialVOS.setVisible(true);
            }
        }
        }
    
}