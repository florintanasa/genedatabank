package com.genebank.genedatabank.view.deposit;

import com.genebank.genedatabank.entity.Deposit;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


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
}