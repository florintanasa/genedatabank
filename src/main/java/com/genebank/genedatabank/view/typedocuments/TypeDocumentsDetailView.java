package com.genebank.genedatabank.view.typedocuments;

import com.genebank.genedatabank.entity.TypeDocuments;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 01.09.2023
**/
@Route(value = "typeDocumentses/:id", layout = MainView.class)
@ViewController("TypeDocuments.detail")
@ViewDescriptor("type-documents-detail-view.xml")
@EditedEntityContainer("typeDocumentsDc")
public class TypeDocumentsDetailView extends StandardDetailView<TypeDocuments> {
}