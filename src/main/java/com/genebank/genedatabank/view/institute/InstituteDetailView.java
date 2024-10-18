package com.genebank.genedatabank.view.institute;

import com.genebank.genedatabank.entity.Institute;

import com.genebank.genedatabank.entity.User;
import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.component.textfield.JmixPasswordField;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;


/**
 * @author : Florin Tanasă
 * @since : 31.08.2023
**/
@Route(value = "institutes/:id", layout = MainView.class)
@ViewController("Institute.detail")
@ViewDescriptor("institute-detail-view.xml")
@EditedEntityContainer("instituteDc")
public class InstituteDetailView extends StandardDetailView<Institute> {
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @ViewComponent
    private TypedTextField<String> serialAccenumbField;
    @ViewComponent
    private TypedTextField<String> serialAccenumbTempField;
    @ViewComponent
    private TypedTextField<String> serialVOSField;
    @ViewComponent
    private TypedTextField<String> serialVNSField;
    @ViewComponent
    private JmixPasswordField apiKeyGoogleMapsField;

    @Subscribe
    public void onInit(final InitEvent event) {
        final User user = (User) currentAuthentication.getUser();
        // check if user logged is admin if yes set editable some buttons
        if (Objects.equals(user.getUsername(), "admin")) {
            apiKeyGoogleMapsField.setReadOnly(false);
            apiKeyGoogleMapsField.setVisible(true);

            serialAccenumbField.setReadOnly(false);
            serialAccenumbField.setVisible(true);

            serialAccenumbTempField.setReadOnly(false);
            serialAccenumbTempField.setVisible(true);

            serialVOSField.setReadOnly(false);
            serialVOSField.setVisible(true);

            serialVNSField.setReadOnly(false);
            serialVNSField.setVisible(true);
        }
    }
}