package com.genebank.genedatabank.view.sysfile;

import com.genebank.genedatabank.entity.SysFile;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 04.10.2023
**/
@Route(value = "sysFiles/:id", layout = MainView.class)
@ViewController("SysFile.detail")
@ViewDescriptor("sys-file-detail-view.xml")
@EditedEntityContainer("sysFileDc")
public class SysFileDetailView extends StandardDetailView<SysFile> {
}