package com.genebank.genedatabank.view.viabnewseedsline;

import com.genebank.genedatabank.entity.ViabNewSeedsLine;
import com.genebank.genedatabank.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.TimeSource;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.datepicker.TypedDatePicker;
import io.jmix.flowui.component.formatter.NumberFormatter;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;


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
    private TypedTextField<Integer> seedsNumField;
    @ViewComponent
    private TypedTextField<Integer> viableSeedsField;
    @ViewComponent
    private TypedTextField<Integer> germFacultyField;
    @Autowired
    private NumberFormatter numberFormatter;
    @Autowired
    private TimeSource timeSource;
    @ViewComponent
    private MessageBundle messageBundle;
    @Autowired
    private Notifications notifications;
    @ViewComponent
    private TypedDatePicker<LocalDate> germStartDateField;
    @ViewComponent
    private TypedTextField<Integer> germTimeField;
    @ViewComponent
    private TypedDatePicker<LocalDate> germEvalDateField;

    @Subscribe
    public void onInitEntity(final InitEntityEvent<ViabNewSeedsLine> event) {
        //set the date with current date
        event.getEntity().setGermStartDate(timeSource.now().toLocalDate());
        event.getEntity().setSeedsNum(0);
        event.getEntity().setViableSeeds(0);
    }

    // check some conditions to save
    @Subscribe
    public void onValidation(final ValidationEvent event) {
        // check if germination faculty is between 0-100
        if (getEditedEntity().getGermFaculty() > 100 || getEditedEntity().getGermFaculty() < 0) {
            String canNotSaveGermFaculty = messageBundle.getMessage("can_not_save_germ_faculty");
            event.getErrors().add(messageBundle.getMessage(canNotSaveGermFaculty));
            notifications.create("HOPA", canNotSaveGermFaculty).withDuration(5000).show();
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
            if (!seedsNumField.getValue().isEmpty()) {
                // declared some variables and convert to double to be used for calculate the viability
                Double valueSeedsNum = Double.valueOf(seedsNumField.getValue());
                Double valueViableSeedsNum = Double.valueOf(viableSeedsField.getValue());
                // calculate viability and round to integer
                double valueGermFaculty = Math.round((valueViableSeedsNum/valueSeedsNum)*100);
                // display viability
                germFacultyField.setValue(Objects.requireNonNull(numberFormatter.apply((int) valueGermFaculty)));
            }
        });

        //check if value in viable seeds field was changed and then calculate viability percentage
        viableSeedsField.addValueChangeListener(valueChangeEvent -> {
            if (!viableSeedsField.getValue().isEmpty()) {
                // declared some variables and convert to double to be used for calculate the viability
                Double valueSeedsNum = Double.valueOf(seedsNumField.getValue());
                Double valueViableSeedsNum = Double.valueOf(viableSeedsField.getValue());
                // calculate viability and round to integer
                double valueGermFaculty = Math.round((valueViableSeedsNum/valueSeedsNum)*100);
                // display viability
                germFacultyField.setValue(Objects.requireNonNull(numberFormatter.apply((int) valueGermFaculty)));
            }
        });

        // check if values in Germination Start Date field is changed
        germStartDateField.addValueChangeListener(valueChangeEvent -> {
           if (!germStartDateField.isEmpty()) { // if is not empty
               // I calculate the days as differences
               long germTime = ChronoUnit.DAYS.between(germStartDateField.getValue(),germEvalDateField.getTypedValue());
               // display the value for Germination Time
               germTimeField.setValue((Objects.requireNonNull(numberFormatter.apply(germTime))));
           } else {
               germTimeField.setValue("");
           }
        });

        // check if values in Germination Evaluation Date field is changed
        germEvalDateField.addValueChangeListener(valueChangeEvent -> {
            if (!germEvalDateField.isEmpty()) { // if is not empty
                // I calculate the days as differences
                long germTime = ChronoUnit.DAYS.between(germStartDateField.getValue(),germEvalDateField.getTypedValue());
                // display the value for Germination Time
                germTimeField.setValue((Objects.requireNonNull(numberFormatter.apply(germTime))));
            } else {
                germTimeField.setValue("");
            }
        });
    }

}