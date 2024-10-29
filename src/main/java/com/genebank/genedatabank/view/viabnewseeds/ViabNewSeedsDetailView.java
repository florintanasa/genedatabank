package com.genebank.genedatabank.view.viabnewseeds;

import com.genebank.genedatabank.entity.*;
import com.genebank.genedatabank.view.main.MainView;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.shared.Tooltip;
import com.vaadin.flow.router.Route;
import io.jmix.core.TimeSource;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.data.Sequence;
import io.jmix.data.Sequences;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.component.formatter.NumberFormatter;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.select.JmixSelect;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionPropertyContainer;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;


/**
 * @author : Florin Tanasă
 * @since : 15.10.2024
 **/
@Route(value = "viabNewSeedses/:id", layout = MainView.class)
@ViewController("ViabNewSeeds.detail")
@ViewDescriptor("viab-new-seeds-detail-view.xml")
@EditedEntityContainer("viabNewSeedsDc")
public class ViabNewSeedsDetailView extends StandardDetailView<ViabNewSeeds> {
    @Autowired
    private TimeSource timeSource;
    @ViewComponent
    private JmixSelect<Object> statusField;
    @ViewComponent
    private TypedTextField<Integer> viabPercentField;
    @ViewComponent
    private TypedTextField<Integer> yearTestField;
    @ViewComponent
    private DataGrid<ViabNewSeedsLine> viabNewsSeedsLineDataGrid;
    @ViewComponent
    private CollectionPropertyContainer<ViabNewSeedsLine> viabNewSeedsLinesDc;
    @Autowired
    private NumberFormatter numberFormatter;
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private Sequences sequences;
    @ViewComponent
    private MessageBundle messageBundle;
    @Autowired
    private Notifications notifications;
    @Autowired
    private UiComponents uiComponents;
    @ViewComponent
    private TypedTextField<String> idVnsField;
    @ViewComponent
    private EntityComboBox<Pasaport> id_accenumbField;
    @ViewComponent
    private TypedTextField<String> pAccnameField;
    @ViewComponent
    private TypedTextField<String> pGenusField;
    @ViewComponent
    private TypedTextField<String> pSpeciesField;


    @Subscribe
    public void onInit(final InitEvent event) {
        initManualTooltip();
        checkUserConnected();
    }

    // event only for new opening item
    @Subscribe
    public void onInitEntity(final InitEntityEvent<ViabNewSeeds> event) {
        // set default status to In progress
        event.getEntity().setStatus(ViabilityStatus.IN_PROGRESS);
        // set default Year test field to actual year
        event.getEntity().setYearTest(timeSource.now().getYear());
    }

    // event in the view opening process
    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        // when I add new item run event
        Objects.requireNonNull(viabNewsSeedsLineDataGrid.getItems()).addItemSetChangeListener(itemSetChangeEvent -> {
            if (viabNewSeedsLinesDc.getItems().isEmpty()) { // check if not exist items in data grid
                getEditedEntity().setViabPercent(null); // if not exist set null viability percent because 0 is a value possible
            } else {
                calMedViabPercent(); // if exist calculate medium viability percent
                calcGermTestNumber(); // if exist calculate the number for test
            }
        });
        // when I change item run event
        viabNewsSeedsLineDataGrid.getItems().addStateChangeListener(stateChangeEvent -> {
            if (viabNewSeedsLinesDc.getItems().isEmpty()) { // check if not exist items in data grid
                getEditedEntity().setViabPercent(null); // if not exist set null viability percent because 0 is a value possible
            } else {
                calMedViabPercent(); // if exist calculate medium viability percent
                calcGermTestNumber(); // if exist calculate the number for test
            }
        });
        //check if is chosen a new probe code
        id_accenumbField.addValueChangeListener(valueChangeEvent -> {
            if (id_accenumbField.getValue() != null) { // check if user choose a probe code
                pAccnameField.setValue(id_accenumbField.getValue().getAccname());
                pGenusField.setValue(id_accenumbField.getValue().getId_taxonomy().getGenus());
                pSpeciesField.setValue(id_accenumbField.getValue().getId_taxonomy().getSpecies());
            }
        });
    }

    // method to calculate medium percent viability
    private void calMedViabPercent() {
        double total = 0;
        for (ViabNewSeedsLine line : viabNewSeedsLinesDc.getItems()) {
            if (line.getGermFaculty() != null) {
                total += (double) line.getGermFaculty() / viabNewSeedsLinesDc.getItems().size();
            }
        }
        //getEditedEntity().setViabPercent((int)(Math.round(total)));
        viabPercentField.setValue(Objects.requireNonNull(numberFormatter.apply((int) (Math.round(total)))));
    }

    // before to save we run sequences to create the next serial number for idVNS
    @Subscribe(target = Target.DATA_CONTEXT)
    public void onPreSave(final DataContext.PreSaveEvent event) {
        final User user = (User) currentAuthentication.getUser();
        if (getEditedEntity().getIdVNS() == null) {
            if (getEditedEntity().getId_accenumb().getId_instcode().getInstcode().equals(user.getId_institute().getInstcode())) {
                long idVnsNumber = sequences.createNextValue(Sequence.withName(getEditedEntity().getId_accenumb().getId_instcode().getSerialVNS()));
                String serialVNS = getEditedEntity().getId_accenumb().getId_instcode().getSerialVNS()+ "-" + idVnsNumber;
                getEditedEntity().setIdVNS(serialVNS);
            } else {
                String error_message = messageBundle.getMessage("error_message_serialVNS");
                notifications.create("HOPA",error_message).withDuration(5000).show();
            }
        }
    }

    // Create Tool Tips for input fields
    private void initManualTooltip()   {
        // create button for tooltip help
        JmixButton hlpBtnId_accenumField = createHelperButton();
        JmixButton hlpBtnIdVnsField = createHelperButton();
        JmixButton hlpBtnStatusField = createHelperButton();
        JmixButton hlpBtnYearTestField = createHelperButton();
        JmixButton hlpBtnViabPercentField = createHelperButton();

        // get tooltips for objects
        Tooltip tooltipId_accenumbField = id_accenumbField.getTooltip();
        Tooltip tooltipIdVnsField = idVnsField.getTooltip();
        Tooltip tooltipStatusField = statusField.getTooltip();
        Tooltip tooltipYearTestField = yearTestField.getTooltip();
        Tooltip tooltipViabPercentField = viabPercentField.getTooltip();

        // create event if click the tooltip button
        hlpBtnId_accenumField.addClickListener(buttonClickEvent -> tooltipId_accenumbField.setOpened(!tooltipId_accenumbField.isOpened()));
        hlpBtnIdVnsField.addClickListener(buttonClickEvent -> tooltipIdVnsField.setOpened(!tooltipIdVnsField.isOpened()));
        hlpBtnStatusField.addClickListener(buttonClickEvent -> tooltipStatusField.setOpened(!tooltipStatusField.isOpened()));
        hlpBtnYearTestField.addClickListener(buttonClickEvent -> tooltipYearTestField.setOpened(!tooltipYearTestField.isOpened()));
        hlpBtnViabPercentField.addClickListener(buttonClickEvent -> tooltipViabPercentField.setOpened(!tooltipViabPercentField.isOpened()));

        // set position for tooltip button
        id_accenumbField.setPrefixComponent(hlpBtnId_accenumField);
        idVnsField.setSuffixComponent(hlpBtnIdVnsField);
        statusField.setPrefixComponent(hlpBtnStatusField);
        yearTestField.setSuffixComponent(hlpBtnYearTestField);
        viabPercentField.setSuffixComponent(hlpBtnViabPercentField);
    }

    // method for create a button for tips
    protected JmixButton createHelperButton() {
        // create object
        JmixButton helperButton = uiComponents.create(JmixButton.class);
        // set the icon for button
        helperButton.setIcon(VaadinIcon.QUESTION_CIRCLE.create());
        // set the theme for button
        helperButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_TERTIARY_INLINE);

        // return object
        return helperButton;
    }

    // check the user connected and change fields from read only to be edited
    private void checkUserConnected() {
        final User user = (User) currentAuthentication.getUser();
        if (Objects.equals(user.getUsername(), "admin")) {
            // set read only to false to can be edited
            idVnsField.setReadOnly(false);
            // set read only to false to can be edited
            statusField.setReadOnly(false);
            // set read only to false to can be edited
            viabPercentField.setReadOnly(false);
            // set read only to false to can be edited
            yearTestField.setReadOnly(false);
            // set read only to false to can be edited
            pAccnameField.setReadOnly(false);
            // set read only to false to can be edited
            pGenusField.setReadOnly(false);
            // set read only to false to can be edited
            pSpeciesField.setReadOnly(false);
        }
    }

    // method cu calculate test number
    private void calcGermTestNumber() {
        for (ViabNewSeedsLine line : viabNewSeedsLinesDc.getItems()) {
            if ( !viabNewSeedsLinesDc.getItems().isEmpty() && line.getGermTestNum() == null) {
                int number = viabNewSeedsLinesDc.getItems().indexOf(line);
                line.setGermTestNum(number+1);
            }
        }
    }
}