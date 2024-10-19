package com.genebank.genedatabank.view.viabnewseedsline;

import com.genebank.genedatabank.entity.ViabNewSeedsLine;
import com.genebank.genedatabank.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.TimeSource;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.datepicker.TypedDatePicker;
import io.jmix.flowui.component.textfield.JmixIntegerField;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


/**
 * @author : Florin Tanasă
 * @since : 15.10.2024
 **/
@Route(value = "viabNewSeedsLines/:id", layout = MainView.class)
@ViewController("ViabNewSeedsLine.detail")
@ViewDescriptor("viab-new-seeds-line-detail-view.xml")
@EditedEntityContainer("viabNewSeedsLineDc")
public class ViabNewSeedsLineDetailView extends StandardDetailView<ViabNewSeedsLine> {
    @ViewComponent
    private JmixIntegerField seedsNumField;
    @ViewComponent
    private JmixIntegerField viableSeedsField;
    @ViewComponent
    private JmixIntegerField germFacultyField;
    @Autowired
    private TimeSource timeSource;
    @ViewComponent
    private MessageBundle messageBundle;
    @Autowired
    private Notifications notifications;
    @ViewComponent
    private TypedDatePicker<LocalDate> germStartDateField;
    @ViewComponent
    private JmixIntegerField germTimeField;
    @ViewComponent
    private TypedDatePicker<LocalDate> germEvalDateField;

    @Subscribe
    public void onInitEntity(final InitEntityEvent<ViabNewSeedsLine> event) {
        //set the Start Germination Date with current date
        event.getEntity().setGermStartDate(timeSource.now().toLocalDate());
    }

    @Subscribe
    public void onInit(final InitEvent event) {
        checkFields();
    }

    // method to determine what field is necessary to be set required
    private void checkFields() {
    // check if numbers of seeds is > 0 so is necessary a Start Germination Date
    if (!seedsNumField.isEmpty()) {
        if (seedsNumField.getValue() > 0) {
            germStartDateField.setRequired(true);
        }
    }
    // check if numbers of seeds is null so is not necessary an Start Germination Date
    if (seedsNumField.isEmpty()) {
        germStartDateField.setRequired(false);
    }
    // check if numbers of viable seeds is > 0 so is necessary a Evaluation Germination Date to have a date
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
    if (!germEvalDateField.isEmpty()) {
        viableSeedsField.setRequired(true);
    } else viableSeedsField.setRequired(false);
}

    // check some conditions to save
    @Subscribe
    public void onValidation(final ValidationEvent event) {
        // check if germination faculty is between 0-100
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

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        //check if value in seeds number field was changed and then calculate viability percentage
        seedsNumField.addValueChangeListener(valueChangeEvent -> {
            if (seedsNumField.getValue() != null && viableSeedsField.getValue() != null) {
                // declared some variables and convert to double to be used for calculate the viability
                Double valueSeedsNum = Double.valueOf(seedsNumField.getValue());
                Double valueViableSeedsNum = Double.valueOf(viableSeedsField.getValue());
                // calculate viability and round to integer
                double valueGermFaculty = Math.round((valueViableSeedsNum/valueSeedsNum)*100);
                // display viability
                germFacultyField.setValue((int) valueGermFaculty);
            } else germFacultyField.setValue(null);
            // then check what field is empty and what is written
            checkFields();
        });
        //check if value in viable seeds field was changed and then calculate viability percentage
        viableSeedsField.addValueChangeListener(valueChangeEvent -> {
            if (viableSeedsField.getValue() != null && seedsNumField.getValue() != null) {
                // declared some variables and convert to double to be used for calculate the viability
                Double valueSeedsNum = Double.valueOf(seedsNumField.getValue());
                Double valueViableSeedsNum = Double.valueOf(viableSeedsField.getValue());
                // calculate viability and round to integer
                double valueGermFaculty = Math.round((valueViableSeedsNum/valueSeedsNum)*100);
                // display viability
                germFacultyField.setValue((int) valueGermFaculty);
            } else germFacultyField.setValue(null);
            // then check what field is empty and what is written
            checkFields();
        });
        // check if values in Germination Start Date field is changed
        germStartDateField.addValueChangeListener(valueChangeEvent -> {
           if (!germStartDateField.isEmpty() && !germEvalDateField.isEmpty()) { // if is not empty
               // I calculate the days as differences
               long germTime = ChronoUnit.DAYS.between(germStartDateField.getValue(),germEvalDateField.getTypedValue());
               // display the value for Germination Time
               germTimeField.setValue((int) germTime);
           } else {
               germTimeField.setValue(null);
           }
            // then check what field is empty and what is written
           checkFields();
        });
        // check if values in Germination Evaluation Date field is changed
        germEvalDateField.addValueChangeListener(valueChangeEvent -> {
            if (!germEvalDateField.isEmpty() && !germStartDateField.isEmpty()) { // if is not empty
                // I calculate the days as differences
                long germTime = ChronoUnit.DAYS.between(germStartDateField.getValue(),germEvalDateField.getTypedValue());
                // display the value for Germination Time
                germTimeField.setValue((int) germTime);
            } else {
                germTimeField.setValue(null);
            }
            // then check what field is empty and what is written
            checkFields();
        });
    }
}