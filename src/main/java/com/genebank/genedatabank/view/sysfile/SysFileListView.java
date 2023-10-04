package com.genebank.genedatabank.view.sysfile;

import com.genebank.genedatabank.entity.SysFile;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 04.10.2023
**/
@Route(value = "sysFiles", layout = MainView.class)
@ViewController("SysFile.list")
@ViewDescriptor("sys-file-list-view.xml")
@LookupComponent("sysFilesDataGrid")
@DialogMode(width = "64em")
public class SysFileListView extends StandardListView<SysFile> {
}