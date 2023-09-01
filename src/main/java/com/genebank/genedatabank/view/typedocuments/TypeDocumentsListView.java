package com.genebank.genedatabank.view.typedocuments;

import com.genebank.genedatabank.entity.TypeDocuments;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 01.09.2023
**/
@Route(value = "typeDocumentses", layout = MainView.class)
@ViewController("TypeDocuments.list")
@ViewDescriptor("type-documents-list-view.xml")
@LookupComponent("typeDocumentsesDataGrid")
@DialogMode(width = "64em")
public class TypeDocumentsListView extends StandardListView<TypeDocuments> {
}