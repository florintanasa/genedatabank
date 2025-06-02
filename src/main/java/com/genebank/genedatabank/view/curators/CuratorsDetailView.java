package com.genebank.genedatabank.view.curators;

import com.genebank.genedatabank.entity.Curators;

import com.genebank.genedatabank.entity.User;
import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.view.*;


/**
 * @author : Florin Tanasă
 * @since : 31.08.2023
**/
@Route(value = "curatorses/:id", layout = MainView.class)
@ViewController("Curators.detail")
@ViewDescriptor("curators-detail-view.xml")
@EditedEntityContainer("curatorsDc")
public class CuratorsDetailView extends StandardDetailView<Curators> {
    @ViewComponent
    private EntityPicker<User> id_userField;
    @ViewComponent
    private TypedTextField<String> curator_nameField;
    @ViewComponent
    private JmixCheckbox activeField;

    @Subscribe
    public void onInit(final InitEvent event) {
        //for check box Active now? to can be leave unchecked for false, problems after 2.5.0 jmix
        activeField.setRequired(false);
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        id_userField.addValueChangeListener(e -> {
           if (e.getValue() != null) {
               //if the user is selected or is changed add First and Last Name to the curator name
               curator_nameField.setValue(e.getValue().getFirstName()+" "+e.getValue().getLastName());
           }
        });
    }
    
}