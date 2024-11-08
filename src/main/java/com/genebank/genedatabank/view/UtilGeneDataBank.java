package com.genebank.genedatabank.view;

import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.VaadinIcon;
import io.jmix.flowui.kit.component.button.JmixButton;

import java.util.List;

/**
 * @author : florin
 * @since : 08.11.2024
 **/
public class UtilGeneDataBank {
    // method for create date picker in romanian language
    public static DatePicker.DatePickerI18n romanianI18nDatePicker() {
        DatePicker.DatePickerI18n romanianI18n = new DatePicker.DatePickerI18n();

        // set Monday as first day of the week
        romanianI18n.setFirstDayOfWeek(1);
        // set the list with the months
        romanianI18n.setMonthNames(
                List.of("Ianuarie", "Februarie", "Martie", "Aprilie", "Mai", "Iunie",
                        "Iulie", "August", "Septembrie", "Octombrie", "Noiembrie", "Decembrie")
        );
        // set the list with long name of week days
        romanianI18n.setWeekdays(
                List.of("Duminică", "Luni", "Marți", "Miercuri", "Joi", "Vineri", "Sâmbătă")
        );
        // set the list with short name of week days
        romanianI18n.setWeekdaysShort(
                List.of("dum.", "lun.", "mar.", "mie.", "joi", "vin.", "sâm.")
        );
        // translate Today
        romanianI18n.setToday("Astăzi");
        // translate Cancel
        romanianI18n.setCancel("Anulare");
        // set date format
        romanianI18n.setDateFormat("dd/MM/yyyy");

        // return the date picker for romanian language
        return romanianI18n;
    }
    // method for create a button for tips
    public static JmixButton createHelperButton() {
        // create object
        JmixButton helperButton;
        helperButton = new JmixButton();
        //JmixButton helperButton = uiComponents.create(JmixButton.class);
        // set the icon for button
        helperButton.setIcon(VaadinIcon.QUESTION_CIRCLE.create());
        // set the theme for button
        helperButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_TERTIARY_INLINE);

        // return object
        return helperButton;
    }
}
