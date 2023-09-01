package com.genebank.genedatabank.view.storage;

import com.genebank.genedatabank.entity.Storage;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 01.09.2023
**/
@Route(value = "storages/:id", layout = MainView.class)
@ViewController("Storage.detail")
@ViewDescriptor("storage-detail-view.xml")
@EditedEntityContainer("storageDc")
public class StorageDetailView extends StandardDetailView<Storage> {
}