package com.genebank.genedatabank.view.storage;

import com.genebank.genedatabank.entity.Storage;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 01.09.2023
**/
@Route(value = "storages", layout = MainView.class)
@ViewController("Storage.list")
@ViewDescriptor("storage-list-view.xml")
@LookupComponent("storagesDataGrid")
@DialogMode(width = "64em")
public class StorageListView extends StandardListView<Storage> {
}