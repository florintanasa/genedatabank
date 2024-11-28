package com.genebank.genedatabank.view.pasaport;

import com.genebank.genedatabank.entity.Pasaport;

import com.genebank.genedatabank.entity.User;
import com.genebank.genedatabank.view.main.MainView;

import com.google.common.base.Strings;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.NoResultException;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.Dialogs;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.app.inputdialog.DialogActions;
import io.jmix.flowui.app.inputdialog.DialogOutcome;
import io.jmix.flowui.app.inputdialog.InputParameter;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.validation.ValidationErrors;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;


/**
 * @author : Florin Tanasă
 * @since : 04.09.2023
**/
@Route(value = "pasaports", layout = MainView.class)
@ViewController("Pasaport.list")
@ViewDescriptor("pasaport-list-view.xml")
@LookupComponent("pasaportsDataGrid")
@DialogMode(width = "64em")
public class PasaportListView extends StandardListView<Pasaport> {
    @ViewComponent
    private DataGrid<Pasaport> pasaportsDataGrid;
    @Autowired
    private ViewNavigators viewNavigators;
    @Autowired
    private Dialogs dialogs;
    @Autowired
    private Notifications notifications;
    @Autowired
    private DataManager dataManager;
    @ViewComponent
    private MessageBundle messageBundle;
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @ViewComponent
    private JmixButton copyeditBtn;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        // assign copy and edit function only to users admin and cezar
        final User user = (User) currentAuthentication.getUser();
        if (Objects.equals(user.getUsername(), "cezar") || Objects.equals(user.getUsername(), "admin")) {
            copyeditBtn.setEnabled(true);
        }
    }
    
    @Subscribe("pasaportsDataGrid.copyEdit")
    public void onPasaportsDataGridCopyEdit(final ActionPerformedEvent event) {
        Pasaport pasaport = pasaportsDataGrid.getSingleSelectedItem();

        if (pasaport != null) {
            navigateToCopyAndEditEntity(pasaport);
        }
    }
    //method for copy and edit selected item
    public void navigateToCopyAndEditEntity(Pasaport pasaport) {
        viewNavigators.detailView(pasaportsDataGrid)
                .withViewClass(PasaportDetailView.class)
                .newEntity()
                .withAfterNavigationHandler(afterNavigationEvent -> {
                    afterNavigationEvent.getView().getEditedEntity()
                            .setAccname(StringUtils.abbreviate("Copie a "
                            + pasaport.getAccname(),45));
                    afterNavigationEvent.getView().getEditedEntity()
                            .setAcceurl(pasaport.getAcceurl());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setAcqdate(pasaport.getAcqdate());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setAncest(pasaport.getAncest());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setColldate(pasaport.getColldate());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setCollmissid(pasaport.getCollmissid());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setCollnumb(pasaport.getCollnumb());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setComments(pasaport.getComments());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setDoi(pasaport.getDoi());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setDonornumb(pasaport.getDonornumb());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setElevation(pasaport.getElevation());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_acceconf(pasaport.getId_acceconf());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_aegisstat(pasaport.getId_aegisstat());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_bredcode(pasaport.getId_bredcode());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_collsrc(pasaport.getId_collsrc());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_country(pasaport.getId_country());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_collcode(pasaport.getId_collcode());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_countysiruta(pasaport.getId_countysiruta());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_donorcode(pasaport.getId_donorcode());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_duplsite(pasaport.getId_duplsite());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_georefmeth(pasaport.getId_georefmeth());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_historic(pasaport.getId_historic());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_instcode(pasaport.getId_instcode());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_localitysiruta(pasaport.getId_localitysiruta());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_mlsstat(pasaport.getId_mlsstat());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_sampstat(pasaport.getId_sampstat());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_taxonomy(pasaport.getId_taxonomy());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setLatitude(pasaport.getLatitude());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setLongitude(pasaport.getLongitude());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setOrigdate(pasaport.getOrigdate());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setOthernumb(pasaport.getOthernumb());
                    // afterNavigationEvent.getView().getEditedEntity()
                    // .setProbeImages(pasaport.getProbeImages());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setRemarks(pasaport.getRemarks());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setTempnumb(pasaport.getTempnumb());
                })
                .navigate();
    }

    //method for copy and modify with selected item other entity
    public void navigateToCopyAndModifyEntity(Pasaport copyPasaport, Pasaport modifyPasaport) {
        viewNavigators.detailView(pasaportsDataGrid)
                .withViewClass(PasaportDetailView.class)
                .editEntity(modifyPasaport)
                .withAfterNavigationHandler(afterNavigationEvent -> {
                    afterNavigationEvent.getView().getEditedEntity()
                            .setAccname(StringUtils.abbreviate("Copie a "
                                    + copyPasaport.getAccname(),45));
                    afterNavigationEvent.getView().getEditedEntity()
                            .setAcceurl(copyPasaport.getAcceurl());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setAcqdate(copyPasaport.getAcqdate());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setAncest(copyPasaport.getAncest());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setColldate(copyPasaport.getColldate());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setCollmissid(copyPasaport.getCollmissid());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setCollnumb(copyPasaport.getCollnumb());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setComments(copyPasaport.getComments());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setDoi(copyPasaport.getDoi());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setDonornumb(copyPasaport.getDonornumb());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setElevation(copyPasaport.getElevation());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_acceconf(copyPasaport.getId_acceconf());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_aegisstat(copyPasaport.getId_aegisstat());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_bredcode(copyPasaport.getId_bredcode());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_collsrc(copyPasaport.getId_collsrc());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_country(copyPasaport.getId_country());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_collcode(copyPasaport.getId_collcode());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_countysiruta(copyPasaport.getId_countysiruta());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_donorcode(copyPasaport.getId_donorcode());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_duplsite(copyPasaport.getId_duplsite());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_georefmeth(copyPasaport.getId_georefmeth());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_historic(copyPasaport.getId_historic());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_instcode(copyPasaport.getId_instcode());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_localitysiruta(copyPasaport.getId_localitysiruta());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_mlsstat(copyPasaport.getId_mlsstat());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_sampstat(copyPasaport.getId_sampstat());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setId_taxonomy(copyPasaport.getId_taxonomy());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setLatitude(copyPasaport.getLatitude());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setLongitude(copyPasaport.getLongitude());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setOrigdate(copyPasaport.getOrigdate());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setOthernumb(copyPasaport.getOthernumb());
                    // afterNavigationEvent.getView().getEditedEntity()
                    // .setProbeImages(copyPasaport.getProbeImages());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setRemarks(copyPasaport.getRemarks());
                    afterNavigationEvent.getView().getEditedEntity()
                            .setTempnumb(copyPasaport.getTempnumb());
                })
                .navigate();
    }

    @Subscribe(id = "copymodifyBtn", subject = "clickListener")
    public void onCopymodifyBtnClick(final ClickEvent<JmixButton> event) {
        String msgHeader = messageBundle.getMessage("msgHeaderInputDialogPasaport");
        String msgLabel = messageBundle.getMessage("msgLabelInputDialogPasaport");
        String msgValidation = messageBundle.getMessage("msgValidationInputDialogPasaport");
        String msgNoResult = messageBundle.getMessage("msgNoResultInputDialogPasaport");
        // create a Dialog with parameter input to search Accession number
        dialogs.createInputDialog(this)
                .withHeader(msgHeader)
                .withParameters(InputParameter.stringParameter("accenumber")
                        .withLabel(msgLabel)
                )
                .withValidator(validationContext -> {
                    String accenumber = validationContext.getValue("accenumber");
                    if (Strings.isNullOrEmpty(accenumber)) {
                        return ValidationErrors.of(msgValidation);
                    }
                    return ValidationErrors.none();
                })
                .withActions(DialogActions.OK_CANCEL)
                .withCloseListener(inputDialogCloseEvent -> {
                    if (inputDialogCloseEvent.closedWith(DialogOutcome.OK)) {
                        // create parameter used in query
                        String accenumber = inputDialogCloseEvent.getValue("accenumber");
                        try {
                            // create pasaport object with entities selected from data grid
                            Pasaport copyPasaport = pasaportsDataGrid.getSingleSelectedItem();
                            // create pasaport object with entities query by parameter input - Accesion number
                            Pasaport modifyPasaport = dataManager.load(Pasaport.class)
                                    .query("select p from Pasaport p " +
                                            "where p.accenumb = :accenum and p.accname is null and p.acceurl is null " +
                                            "and p.acqdate is null and p.ancest is null and p.colldate is null " +
                                            "and p.collmissid is null and p.collnumb is null and p.comments is null " +
                                            "and p.doi is null and p.donornumb is null and p.elevation is null " +
                                            "and p.id_acceconf is null and p.id_aegisstat is null " +
                                            "and p.id_collsrc is null and p.id_country is null " +
                                            "and p.id_countysiruta is null and p.id_donorcode is null " +
                                            "and p.id_georefmeth is null and p.id_historic is null " +
                                            "and p.latitude is null and p.longitude is null and p.remarks is null " +
                                            "and p.tempnumb is null")
                                    .parameter("accenum", accenumber)
                                    .one();
                            // if the result for query is not null
                            if (copyPasaport != null) {
                                // call the method
                                navigateToCopyAndModifyEntity(copyPasaport, modifyPasaport);
                            }
                        } catch (NoResultException e) {
                            if (e.getMessage() == null) { // if result of query return null
                                // notify user because query return null
                                notifications.create(msgNoResult).withDuration(6000).show();
                            }
                        }
                    }
                })
                .open();
    }
}