package com.genebank.genedatabank.view.pasaport;

import com.genebank.genedatabank.entity.Pasaport;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.data.Sequence;
import io.jmix.data.Sequences;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author : Florin Tanasă
 * @since : 04.09.2023
**/
@Route(value = "pasaports/:id", layout = MainView.class)
@ViewController("Pasaport.detail")
@ViewDescriptor("pasaport-detail-view.xml")
@EditedEntityContainer("pasaportDc")
public class PasaportDetailView extends StandardDetailView<Pasaport> {
    @Autowired
    private Sequences sequences;
    @Autowired
    private Notifications notifications;
    @Autowired
    private MessageBundle messageBundle;

    @Subscribe(target = Target.DATA_CONTEXT)
    public void onPreSave(DataContext.PreSaveEvent event) {
        if (getEditedEntity().getId_instcode().getInstcode().equals("ROM007")) {
            Long accNumberSVGB = sequences.createNextValue(Sequence.withName("NrAccenumbSVGB"));
            getEditedEntity().setAccenumb("SVGB-" + accNumberSVGB);
        } else if (getEditedEntity().getId_instcode().getInstcode().equals("ROM028")) {
            Long accNumberSVSCA = sequences.createNextValue(Sequence.withName("NrAccenumbSVCA"));
            getEditedEntity().setAccenumb("SVSCA-" + accNumberSVSCA);
        }
        else {
            String error_message_accenumber = messageBundle.getMessage("error_message_accenumber");
            notifications.create("HOPA", error_message_accenumber)
                    .withDuration(5000).show();
        }
    }
}