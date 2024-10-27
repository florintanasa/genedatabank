package com.genebank.genedatabank.view.viaboldseeds;

import com.genebank.genedatabank.entity.*;
import com.genebank.genedatabank.view.main.MainView;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.shared.Tooltip;
import com.vaadin.flow.router.Route;
import io.jmix.chartsflowui.component.Chart;
import io.jmix.chartsflowui.data.ContainerChartItems;
import io.jmix.chartsflowui.data.item.EntityDataItem;
import io.jmix.chartsflowui.kit.component.model.Aria;
import io.jmix.chartsflowui.kit.component.model.DataSet;
import io.jmix.chartsflowui.kit.component.model.Title;
import io.jmix.chartsflowui.kit.component.model.axis.AxisLabel;
import io.jmix.chartsflowui.kit.component.model.axis.XAxis;
import io.jmix.chartsflowui.kit.component.model.axis.YAxis;
import io.jmix.chartsflowui.kit.component.model.legend.Legend;
import io.jmix.chartsflowui.kit.component.model.series.AbstractSeries;
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
import io.jmix.flowui.component.textfield.JmixIntegerField;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.*;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;


/**
 * @author : Florin Tanasă
 * @since : 21.10.2024
 **/
@Route(value = "viabOldSeedses/:id", layout = MainView.class)
@ViewController("ViabOldSeeds.detail")
@ViewDescriptor("viab-old-seeds-detail-view.xml")
@EditedEntityContainer("viabOldSeedsDc")
public class ViabOldSeedsDetailView extends StandardDetailView<ViabOldSeeds> {
    @Autowired
    private TimeSource timeSource;
    @ViewComponent
    private InstanceContainer<ViabOldSeeds> viabOldSeedsDc;
    @ViewComponent
    private CollectionPropertyContainer<ViabOldSeedsLine> viabOldSeedsLinesDc;
    @ViewComponent
    private EntityComboBox<Deposit> depositsComboBox;
    @ViewComponent
    private JmixIntegerField viabPercentField;
    @ViewComponent
    private JmixIntegerField lastViabTestField;
    @ViewComponent
    private JmixIntegerField lastYearTestField;
    @ViewComponent
    private JmixIntegerField stockField;
    @Autowired
    private NumberFormatter numberFormatter;
    @ViewComponent
    private DataGrid<ViabOldSeedsLine> viabOldSeedsLineDataGrid;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private Sequences sequences;
    @ViewComponent
    private MessageBundle messageBundle;
    @Autowired
    private Notifications notifications;
    @ViewComponent
    private TypedTextField<String> idVOSField;
    @ViewComponent
    private JmixIntegerField yearTestField;
    @ViewComponent
    private JmixSelect<Object> statusField;
    @ViewComponent
    private CollectionLoader<ViabOldSeeds> viabOldHistoryDl;
    @ViewComponent
    private CollectionContainer<ViabOldSeeds> viabOldHistoryDc;
    @ViewComponent
    private Chart chartOldSeedHistoric;

    @Subscribe
    public void onInit(final InitEvent event) {
        // call some methods
        initManualTooltip();
        checkUserConnected();
    }

    // event only for new opening item
    @Subscribe
    public void onInitEntity(final InitEntityEvent<ViabOldSeeds> event) {
        // set default status to In progress
        event.getEntity().setStatus(ViabilityStatus.IN_PROGRESS);
        //set the Start Germination Date with current date
        event.getEntity().setYearTest(timeSource.now().getYear());
        // set read only field status
        statusField.setReadOnly(true);
        // set read only Viability percentage field
        viabPercentField.setReadOnly(true);
    }

    // event in the view opening process
    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        // load new data for deposit code saved
        loadViabOldHistoryDl();
        // load chart with data for deposit code saved
        loadChartByCodeDeposit();
        // when I add new item run event
        Objects.requireNonNull(viabOldSeedsLineDataGrid.getItems()).addItemSetChangeListener(itemSetChangeEvent -> {
            if (viabOldSeedsLineDataGrid.getItems().getItems().isEmpty()) {  // check if not exist items in data grid
                getEditedEntity().setViabPercent(null); // if not exist set null viability percent because 0 is a value possible
            } else {
                calMedViabPercent();  // if exist calculate medium viability percent and display the value
                calcGermTestNumber(); // if exist calculate the number for test
            }
        });
        // when I change item run event
        viabOldSeedsLineDataGrid.getItems().addStateChangeListener(stateChangeEvent -> {
            if (viabOldSeedsLineDataGrid.getItems().getItems().isEmpty()) { // check if not exist items in data grid
                getEditedEntity().setViabPercent(null);// if not exist set null viability percent because 0 is a value possible
            } else {
                calMedViabPercent(); // if exist calculate medium viability percent and display the value
                calcGermTestNumber(); // if exist calculate the number for test
            }
        });

        //check if is chosen a new deposit code
        depositsComboBox.addValueChangeListener(valueChangeEvent -> {
            // load new data for deposit code chosen
            loadViabOldHistoryDl();
            // load chart with data for deposit code chosen
            loadChartByCodeDeposit();
            if (depositsComboBox.getValue() != null) { // check if user choose a deposit code
                // add values in Viability Old Seeds
                lastViabTestField.setValue(depositsComboBox.getValue().getPercentage());
                stockField.setValue(depositsComboBox.getValue().getStock());
                lastYearTestField.setValue(depositsComboBox.getValue().getYeargerm());
            }
        });
    }

    // method to calculate medium percent viability and display
    private void calMedViabPercent() {
        double total = 0;
        for (ViabOldSeedsLine line : viabOldSeedsLinesDc.getItems()) {
            if (line.getGermFaculty() != null) { // check what is not null
                // calculate the sum of viability for all items
                total += (double) line.getGermFaculty();
            }
        }
        // calculate the medium percent - attention is used all items null or not null
        int viabPercent = (int) (total / viabOldSeedsLinesDc.getItems().size());
        // display the value for medium percent calculated
        getEditedEntity().setViabPercent(viabPercent);
        //getEditedEntity().setViabPercent((int) Math.round(total / viabOldSeedsLinesDc.getItems().size()));
        //viabPercentField.setValue(Objects.requireNonNull(numberFormatter.apply((int) Math.round(total / viabOldSeedsLinesDc.getItems().size()))));
    }

    // before to save we run sequences to create the next serial number for idVOS
    @Subscribe(target = Target.DATA_CONTEXT)
    public void onPreSave(final DataContext.PreSaveEvent event) {
        final User user = (User) currentAuthentication.getUser();
        if (getEditedEntity().getIdVOS() == null) {
            if (getEditedEntity().getId_deposit_code().getId_accenumb().getId_instcode().getInstcode()
                    .equals(user.getId_institute().getInstcode())) {
                long idVosNumber = sequences.createNextValue(Sequence.withName(getEditedEntity().getId_deposit_code()
                        .getId_accenumb().getId_instcode().getSerialVOS()));
                String serialVOS = getEditedEntity().getId_deposit_code()
                        .getId_accenumb().getId_instcode().getSerialVOS()+"-"+idVosNumber;
                getEditedEntity().setIdVOS(serialVOS);
            } else {
                String error_message = messageBundle.getMessage("error_message_serialVOS");
                notifications.create("HOPA",error_message).withDuration(5000).show();
            }
        }
    }

    // Create Tool Tips for input fields
    private void initManualTooltip()   {
        // create button for tooltip help
        JmixButton hlpBtnDepositsComboBox = createHlpBtn();
        JmixButton hlpBtnIdVOSField = createHlpBtn();
        JmixButton hlpBtnStockField = createHlpBtn();
        JmixButton hlpBtnLastYearTestField = createHlpBtn();
        JmixButton hlpBtnLastViabTestField = createHlpBtn();
        JmixButton hlpBtnYearTestField = createHlpBtn();
        JmixButton hlpBtnViabPercentField = createHlpBtn();
        JmixButton hlpBtnStatusField = createHlpBtn();

        // get tool tips for objects
        Tooltip tooltipDepositsComboBox = depositsComboBox.getTooltip();
        Tooltip tooltipIdVOSField = idVOSField.getTooltip();
        Tooltip tooltipStockField = stockField.getTooltip();
        Tooltip tooltipLastYearTestField = lastYearTestField.getTooltip();
        Tooltip tooltipLastViabTestField = lastViabTestField.getTooltip();
        Tooltip tooltipYearTestField = yearTestField.getTooltip();
        Tooltip tooltipViabPercentField = viabPercentField.getTooltip();
        Tooltip tooltipStatusField = statusField.getTooltip();

        // create event if click the tool tip button
        hlpBtnDepositsComboBox.addClickListener(buttonClickEvent ->
                tooltipDepositsComboBox.setOpened(!tooltipDepositsComboBox.isOpened()));
        hlpBtnIdVOSField.addClickListener(buttonClickEvent ->
                tooltipIdVOSField.setOpened(!tooltipIdVOSField.isOpened()));
        hlpBtnStockField.addClickListener(buttonClickEvent ->
                tooltipStockField.setOpened(!tooltipStockField.isOpened()));
        hlpBtnLastYearTestField.addClickListener(buttonClickEvent ->
                tooltipLastYearTestField.setOpened(!tooltipLastYearTestField.isOpened()));
        hlpBtnLastViabTestField.addClickListener(buttonClickEvent ->
                tooltipLastViabTestField.setOpened(!tooltipLastViabTestField.isOpened()));
        hlpBtnYearTestField.addClickListener(buttonClickEvent ->
                tooltipYearTestField.setOpened(!tooltipYearTestField.isOpened()));
        hlpBtnViabPercentField.addClickListener(buttonClickEvent ->
                tooltipViabPercentField.setOpened(!tooltipViabPercentField.isOpened()));
        hlpBtnStatusField.addClickListener(buttonClickEvent ->
                tooltipStatusField.setOpened(!tooltipStatusField.isOpened()));

        // set position for tool tip button in field
        depositsComboBox.setPrefixComponent(hlpBtnDepositsComboBox);
        idVOSField.setSuffixComponent(hlpBtnIdVOSField);
        stockField.setSuffixComponent(hlpBtnStockField);
        lastYearTestField.setSuffixComponent(hlpBtnLastYearTestField);
        lastViabTestField.setSuffixComponent(hlpBtnLastViabTestField);
        yearTestField.setSuffixComponent(hlpBtnYearTestField);
        viabPercentField.setSuffixComponent(hlpBtnViabPercentField);
        statusField.setPrefixComponent(hlpBtnStatusField);
    }
    
    // method for create a button for tool tips
    protected JmixButton createHlpBtn() {
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
            idVOSField.setReadOnly(false);
            stockField.setReadOnly(false);
            lastYearTestField.setReadOnly(false);
            lastViabTestField.setReadOnly(false);
            yearTestField.setReadOnly(false);
            viabPercentField.setReadOnly(false);
            statusField.setReadOnly(false);
        }
    }

    // method cu calculate test number
    private void calcGermTestNumber() {
        for (ViabOldSeedsLine line : viabOldSeedsLinesDc.getItems()) {
            if ( !viabOldSeedsLinesDc.getItems().isEmpty() && line.getGermTestNum() == null) {
                int number = viabOldSeedsLinesDc.getItems().indexOf(line)+1;
                line.setGermTestNum(number);
            }
        }
    }

    @Subscribe(id = "viabOldSeedsDc", target = Target.DATA_CONTAINER)
    public void onViabOldSeedsDcItemChange(final InstanceContainer.ItemChangeEvent<ViabOldSeeds> event) {
        loadViabOldHistoryDl();
    }

    // method to load old values for viability and year to populate data grid with historic
    private void loadViabOldHistoryDl() {
        if (depositsComboBox.getValue() != null) {
            viabOldHistoryDl.setParameter("deposit_code", depositsComboBox.getValue().getId());
            viabOldHistoryDl.load();
        }
    }

    // method to load chart for code deposit
    private void loadChartByCodeDeposit(){
        if (depositsComboBox.getValue() != null) {
            // create title for chart
            chartOldSeedHistoric.setTitle(new Title().withText(depositsComboBox.getValue().getDeposit_code()));
            // create X axis
            chartOldSeedHistoric.withXAxis(new XAxis());
            // create Y axis
            chartOldSeedHistoric.withYAxis(new YAxis().withAxisLabel(new AxisLabel().withFormatter("{value} %")));
            // create chart source data
            chartOldSeedHistoric.setDataSet(new DataSet().withSource(
                    new DataSet.Source<EntityDataItem>()
                            .withDataProvider(new ContainerChartItems<>(viabOldHistoryDc))
                            .withCategoryField("yearTest")
                            .withValueFields("viabPercent")
            ));
        }
    }
}
