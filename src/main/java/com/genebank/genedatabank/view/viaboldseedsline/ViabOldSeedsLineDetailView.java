package com.genebank.genedatabank.view.viaboldseedsline;

import com.genebank.genedatabank.entity.ViabOldSeedsLine;
import com.genebank.genedatabank.view.UtilGeneDataBank;
import com.genebank.genedatabank.view.main.MainView;
import com.vaadin.flow.component.shared.Tooltip;
import com.vaadin.flow.router.Route;
import io.jmix.core.TimeSource;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.datepicker.TypedDatePicker;
import io.jmix.flowui.component.textarea.JmixTextArea;
import io.jmix.flowui.component.textfield.JmixIntegerField;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Locale;


/**
 * @author : Florin Tanasă
 * @since : 21.10.2024
 **/
@Route(value = "viabOldSeedsLines/:id", layout = MainView.class)
@ViewController("ViabOldSeedsLine.detail")
@ViewDescriptor("viab-old-seeds-line-detail-view.xml")
@EditedEntityContainer("viabOldSeedsLineDc")
public class ViabOldSeedsLineDetailView extends StandardDetailView<ViabOldSeedsLine> {

    @Autowired
    private TimeSource timeSource;
    @ViewComponent
    private JmixIntegerField seedsNumField;
    @ViewComponent
    private JmixIntegerField viableSeedsField;
    @ViewComponent
    private JmixIntegerField germFacultyField;
    @ViewComponent
    private TypedDatePicker<LocalDate> germStartDateField;
    @ViewComponent
    private TypedDatePicker<LocalDate> germEvalDateField;
    @ViewComponent
    private JmixIntegerField germTimeField;
    @ViewComponent
    private MessageBundle messageBundle;
    @Autowired
    private Notifications notifications;
    @ViewComponent
    private JmixIntegerField treatTimeField;
    @ViewComponent
    private JmixTextArea commentsField;
    @Autowired
    private CurrentAuthentication currentAuthentication;

    // first event in the view opening process
    @Subscribe
    public void onInit(final InitEvent event) {
        final Locale locale = currentAuthentication.getLocale();
        // check if user choose Romanian for display language
        if (locale.getDisplayLanguage().equals("română")) {
            // set the date piker in romanian
            germStartDateField.setI18n(UtilGeneDataBank.romanianI18nDatePicker());
            germEvalDateField.setI18n(UtilGeneDataBank.romanianI18nDatePicker());
        }
        // call some methods
        checkFields();
        initManualTooltip();
    }
    
    // event only for new opening item
    @Subscribe
    public void onInitEntity(final InitEntityEvent<ViabOldSeedsLine> event) {
        //set the Start Germination Date with current date
        event.getEntity().setGermStartDate(timeSource.now().toLocalDate());
    }
    
    // event in the view opening process
    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        //check if value in seeds number field was changed and then calculate viability percentage
        seedsNumField.addValueChangeListener(valueChangeEvent -> {
            if ((seedsNumField.getValue() != null && seedsNumField.getValue() > 0) && viableSeedsField.getValue() != null) {
                calcGermFaculty(); // call method for calculus and display the value
            } else germFacultyField.setValue(null);
            // then check what field is empty and what is written
            checkFields();
        });
        //check if value in viable seeds field was changed and then calculate viability percentage
        viableSeedsField.addValueChangeListener(valueChangeEvent -> {
            if (viableSeedsField.getValue() != null && seedsNumField.getValue() != null) {
                calcGermFaculty(); // call method to calculate viability (germination) in percent and display the value
            } else germFacultyField.setValue(null);
            // then check what field is empty and what is written
            checkFields();
        });
        // check if values in Germination Start Date field is changed
        germStartDateField.addValueChangeListener(valueChangeEvent -> {
           if (!germStartDateField.isEmpty() && !germEvalDateField.isEmpty()) { // if date fields is not empty
               calcGermTime(); // call method to calculate the time interval in day(s) and display the value
           } else germTimeField.setValue(null);
           // then check what field is empty and what is written
           checkFields();
        });
        // check if values in Germination Evaluation Date field is changed
        germEvalDateField.addValueChangeListener(valueChangeEvent -> {
            if (!germEvalDateField.isEmpty() && !germStartDateField.isEmpty()) { // if date fields is not empty
                calcGermTime(); // call method to calculate the time interval in day(s) and display the value
            } else germTimeField.setValue(null);
            // then check what field is empty and what is written
            checkFields();
        });
    }

    // event sent when the view is validated on saving the view data context
    @Subscribe
    public void onValidation(final ValidationEvent event) {
        // check if germination faculty is outside 0-100 values
        if (getEditedEntity().getGermFaculty() != null) {
            if (getEditedEntity().getGermFaculty() > 100 || getEditedEntity().getGermFaculty() < 0) {
                String canNotSaveGermFaculty = messageBundle.getMessage("can_not_save_germ_faculty");
                event.getErrors().add(messageBundle.getMessage(canNotSaveGermFaculty));
                notifications.create("HOPA", canNotSaveGermFaculty).withDuration(5000).show();
            }
        }
        // check if the date for evaluation is not before the date for germination start
        if (getEditedEntity().getGermEvalDate() != null && getEditedEntity().getGermEvalDate().isBefore(getEditedEntity().getGermStartDate()))  {
            String canNotSaveGermEvalDate = messageBundle.getMessage("can_not_save_germ_eval_date");
            event.getErrors().add(messageBundle.getMessage(canNotSaveGermEvalDate));
            notifications.create("HOPA", canNotSaveGermEvalDate).withDuration(5000).show();
        }
        // check if the date for germination start is in future
        if (getEditedEntity().getGermStartDate() != null && getEditedEntity().getGermStartDate().isAfter(timeSource.now().toLocalDate()))  {
            String canNotSaveGermStartDate = messageBundle.getMessage("can_not_save_germ_start_date");
            event.getErrors().add(messageBundle.getMessage(canNotSaveGermStartDate));
            notifications.create("HOPA", canNotSaveGermStartDate).withDuration(5000).show();
        }
        // check if the date for evaluation germination is in future
        if (getEditedEntity().getGermEvalDate() != null && getEditedEntity().getGermEvalDate().isAfter(timeSource.now().toLocalDate()))  {
            String canNotSaveGermEvalDateFuture = messageBundle.getMessage("can_not_save_germ_eval_date_future");
            event.getErrors().add(messageBundle.getMessage(canNotSaveGermEvalDateFuture));
            notifications.create("HOPA", canNotSaveGermEvalDateFuture).withDuration(5000).show();
        }
    }



    // method to calculate germination faculty
    private void calcGermFaculty() {
        // declared some variables and convert to double integer values to be used for calculate the germination
        Double valueSeedsNum = Double.valueOf(seedsNumField.getValue());
        Double valueViableSeedsNum = Double.valueOf(viableSeedsField.getValue());
        // calculate viability and round to integer
        double valueGermFaculty = Math.round((valueViableSeedsNum/valueSeedsNum)*100);
        // display viability
        germFacultyField.setValue((int) valueGermFaculty);
    }

    // method to calculate the days interval between Germination Start Date and Germination Evaluation Date
    private void calcGermTime() {
        // I calculate the days as differences and I put the value on long variable germTime
        long germTime = ChronoUnit.DAYS.between(germStartDateField.getValue(),germEvalDateField.getTypedValue());
        // display the value for Germination Time
        germTimeField.setValue((int) germTime);
    }

    // method to determine what field is necessary to be set required
    private void checkFields() {
        // check if numbers of seeds is > 0 so is necessary a Start Germination Date
        if (!seedsNumField.isEmpty()) {
            if (seedsNumField.getValue() > 0) {
                germStartDateField.setRequired(true);
            }
        }
        // check if numbers of seeds is null so is not necessary a Start Germination Date
        if (seedsNumField.isEmpty()) {
            germStartDateField.setRequired(false);
        }
        // check if numbers of viable seeds is >= 0 so is necessary an Evaluation Germination Date to have a date
        if (!viableSeedsField.isEmpty()) {
            if (viableSeedsField.getValue() >= 0) {
                germEvalDateField.setRequired(true);
            }
        }
        // check if numbers of viable seeds is null so no necessary an Evaluation Germination Date
        if (viableSeedsField.isEmpty()) {
            germEvalDateField.setRequired(false);
        }
        // check if the field for Evaluation Germination Date is not null so is necessary to have a value in Viable seeds
        viableSeedsField.setRequired(!germEvalDateField.isEmpty());
    }

    // Create Tool Tips for input fields
    private void initManualTooltip()   {
        // create button for tooltip help
        JmixButton hlpBtnSeedsNumField = UtilGeneDataBank.createHelperButton();
        JmixButton hlpBtnGermStartDateField = UtilGeneDataBank.createHelperButton();
        JmixButton hlpBtnViableSeedsNumField = UtilGeneDataBank.createHelperButton();
        JmixButton hlpBtnGermEvalDateField = UtilGeneDataBank.createHelperButton();
        JmixButton hlpBtnGermFacultyField = UtilGeneDataBank.createHelperButton();
        JmixButton hlpBtnGermTimeField = UtilGeneDataBank.createHelperButton();
        JmixButton hlpBtnTreatTimeField = UtilGeneDataBank.createHelperButton();
        JmixButton hlpBtnCommentsField = UtilGeneDataBank.createHelperButton();

        // get tooltips for objects
        Tooltip tooltipSeedsNumField = seedsNumField.getTooltip();
        Tooltip tooltipGermStartDateField = germStartDateField.getTooltip();
        Tooltip tooltipViableSeedsNumField = viableSeedsField.getTooltip();
        Tooltip tooltipGermEvalDateField = germEvalDateField.getTooltip();
        Tooltip tooltipGermFacultyField = germFacultyField.getTooltip();
        Tooltip tooltipGermTimeField = germTimeField.getTooltip();
        Tooltip tooltipTreatTimeField = treatTimeField.getTooltip();
        Tooltip tooltipCommentsField = commentsField.getTooltip();

        // create event if click the tooltip button
        hlpBtnSeedsNumField.addClickListener(buttonClickEvent ->
                tooltipSeedsNumField.setOpened(!tooltipSeedsNumField.isOpened()));
        hlpBtnGermStartDateField.addClickListener(buttonClickEvent ->
                tooltipGermStartDateField.setOpened(!tooltipGermStartDateField.isOpened()));
        hlpBtnViableSeedsNumField.addClickListener(buttonClickEvent ->
                tooltipViableSeedsNumField.setOpened(!tooltipViableSeedsNumField.isOpened()));
        hlpBtnGermEvalDateField.addClickListener(buttonClickEvent ->
                tooltipGermEvalDateField.setOpened(!tooltipGermEvalDateField.isOpened()));
        hlpBtnGermFacultyField.addClickListener(buttonClickEvent ->
                tooltipGermFacultyField.setOpened(!tooltipGermFacultyField.isOpened()));
        hlpBtnGermTimeField.addClickListener(buttonClickEvent ->
                tooltipGermTimeField.setOpened(!tooltipGermTimeField.isOpened()));
        hlpBtnTreatTimeField.addClickListener(buttonClickEvent ->
                tooltipTreatTimeField.setOpened(!tooltipTreatTimeField.isOpened()));
        hlpBtnCommentsField.addClickListener(buttonClickEvent ->
                tooltipCommentsField.setOpened(!tooltipCommentsField.isOpened()));

        // set position for tooltip button
        seedsNumField.setSuffixComponent(hlpBtnSeedsNumField);
        germStartDateField.setPrefixComponent(hlpBtnGermStartDateField);
        viableSeedsField.setSuffixComponent(hlpBtnViableSeedsNumField);
        germEvalDateField.setPrefixComponent(hlpBtnGermEvalDateField);
        germFacultyField.setSuffixComponent(hlpBtnGermFacultyField);
        germTimeField.setSuffixComponent(hlpBtnGermTimeField);
        treatTimeField.setSuffixComponent(hlpBtnTreatTimeField);
        commentsField.setSuffixComponent(hlpBtnCommentsField);
    }
}