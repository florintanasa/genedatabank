package com.genebank.genedatabank.view.deposit;

import com.genebank.genedatabank.entity.Deposit;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import io.jmix.core.FileRef;
import io.jmix.core.FileStorage;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author : Florin Tanasă
 * @since : 05.09.2023
**/
@Route(value = "deposits", layout = MainView.class)
@ViewController("Deposit.list")
@ViewDescriptor("deposit-list-view.xml")
@LookupComponent("depositsDataGrid")
@DialogMode(width = "64em")
public class DepositListView extends StandardListView<Deposit> {
    @ViewComponent
    private DataGrid<Deposit> depositsDataGrid;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private FileStorage fileStorage;

    @Subscribe
    public void onInit(final InitEvent event) {
        // 	The addComponentColumn() method accepts a lambda which creates a UI component to be rendered in the column.
        // 	The lambda receives an entity instance for the current row.
        Grid.Column<Deposit> qrcodeColumn = depositsDataGrid.addComponentColumn(deposit -> {
            FileRef fileRef = deposit.getQrcode();
            if (fileRef != null) {
                //The Image component instance is created using the UiComponents factory.
                Image image = uiComponents.create(Image.class);
                image.setWidth("75px");
                image.setHeight("75px");
                StreamResource streamResource = new StreamResource(fileRef.getFileName(),
                        () -> fileStorage.openStream(fileRef));
                //The image component gets its content from the file storage by the reference stored in the
                // qrcode attribute of the Deposit entity.
                image.setSrc(streamResource);
                image.setClassName("qrcode-image");

                //The lambda returns the visual component to be shown in the column cells.
                return image;
            } else {
                return new Span();
            }
        });
        //Setting the width of the created column.
        qrcodeColumn.setFlexGrow(0);
        qrcodeColumn.setWidth("85px");
        //Setting the header of the created column
        qrcodeColumn.setHeader("QR");
        //Setting the position of the created column.
        depositsDataGrid.setColumnPosition(qrcodeColumn, 12);
    }
}