package com.genebank.genedatabank.view.taxonomy;

import com.genebank.genedatabank.entity.Taxonomy;

import com.genebank.genedatabank.view.main.MainView;

import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.shared.Tooltip;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.details.JmixDetails;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author : Florin Tanasă
 * @since : 01.09.2023
**/
@Route(value = "taxonomies/:id", layout = MainView.class)
@ViewController("Taxonomy.detail")
@ViewDescriptor("taxonomy-detail-view.xml")
@EditedEntityContainer("taxonomyDc")
public class TaxonomyDetailView extends StandardDetailView<Taxonomy> {
    @Autowired
    private UiComponents uiComponents;
    @ViewComponent
    private TypedTextField<String> familyField;
    @ViewComponent
    private TypedTextField<String> genusField;
    @ViewComponent
    private TypedTextField<String> speciesField;
    @ViewComponent
    private TypedTextField<String> spauthorField;
    @ViewComponent
    private TypedTextField<String> subtaxaField;
    @ViewComponent
    private TypedTextField<String> subauthorField;
    @ViewComponent
    private TypedTextField<String> syn_taxonoField;
    @ViewComponent
    private TypedTextField<String> cropnumeField;
    @ViewComponent
    private TypedTextField<String> cropnameField;
    @ViewComponent
    private TypedTextField<String> refferenceField;
    @ViewComponent
    private JmixDetails id_culturecategDetails;

    @Subscribe
    public void onInit(final InitEvent event) {
        initManualTooltip();
    }

    // Create Tool Tips for input fields
    protected void initManualTooltip() {
        JmixButton helperButtonFamilyField = createHelperButton();
        JmixButton helperButtonGenusField = createHelperButton();
        JmixButton helperButtonSpeciesField = createHelperButton();
        JmixButton helperButtonSpauthorField = createHelperButton();
        JmixButton helperButtonSubtaxaField = createHelperButton();
        JmixButton helperButtonSubauthorField = createHelperButton();
        JmixButton helperButtonSyn_TaxonField = createHelperButton();
        JmixButton helperButtonCropnumeField = createHelperButton();
        JmixButton helperButtonCropnameField = createHelperButton();
        JmixButton helperButtonRefferenceField = createHelperButton();
        JmixButton helperButtonId_culturecategDetails = createHelperButton();

        Tooltip tooltipFamilyField = familyField.getTooltip();
        Tooltip tooltipGenusField = genusField.getTooltip();
        Tooltip tooltipSpeciesField = speciesField.getTooltip();
        Tooltip tooltipSpauthorField = spauthorField.getTooltip();
        Tooltip tooltipSubtaxaField = subtaxaField.getTooltip();
        Tooltip tooltipSubauthorField = subauthorField.getTooltip();
        Tooltip tooltipSyn_TaxonField = syn_taxonoField.getTooltip();
        Tooltip tooltipCropnumeField = cropnumeField.getTooltip();
        Tooltip tooltipCropnameField = cropnameField.getTooltip();
        Tooltip tooltipRefferenceField = refferenceField.getTooltip();
        Tooltip tooltipId_culturecategDetails = id_culturecategDetails.getTooltip();
        
        helperButtonFamilyField.addClickListener(event -> tooltipFamilyField.setOpened(!tooltipFamilyField.isOpened()));
        helperButtonGenusField.addClickListener(event -> tooltipGenusField.setOpened(!tooltipGenusField.isOpened()));
        helperButtonSpeciesField.addClickListener(event -> tooltipSpeciesField.setOpened(!tooltipSpeciesField.isOpened()));
        helperButtonSpauthorField.addClickListener(event -> tooltipSpauthorField.setOpened(!tooltipSpauthorField.isOpened()));
        helperButtonSubtaxaField.addClickListener(event -> tooltipSubtaxaField.setOpened(!tooltipSubtaxaField.isOpened()));
        helperButtonSubauthorField.addClickListener(event -> tooltipSubauthorField.setOpened(!tooltipSubauthorField.isOpened()));
        helperButtonSyn_TaxonField.addClickListener(event -> tooltipSyn_TaxonField.setOpened(!tooltipSyn_TaxonField.isOpened()));
        helperButtonCropnumeField.addClickListener(event -> tooltipCropnumeField.setOpened(!tooltipCropnumeField.isOpened()));
        helperButtonCropnameField.addClickListener(event -> tooltipCropnameField.setOpened(!tooltipCropnameField.isOpened()));
        helperButtonRefferenceField.addClickListener(event -> tooltipRefferenceField.setOpened(!tooltipRefferenceField.isOpened()));
        helperButtonId_culturecategDetails.addClickListener(event -> tooltipId_culturecategDetails.setOpened(!tooltipId_culturecategDetails.isOpened()));

        familyField.setSuffixComponent(helperButtonFamilyField);
        genusField.setSuffixComponent(helperButtonGenusField);
        speciesField.setSuffixComponent(helperButtonSpeciesField);
        spauthorField.setSuffixComponent(helperButtonSpauthorField);
        subtaxaField.setSuffixComponent(helperButtonSubtaxaField);
        subauthorField.setSuffixComponent(helperButtonSubauthorField);
        syn_taxonoField.setSuffixComponent(helperButtonSyn_TaxonField);
        cropnumeField.setSuffixComponent(helperButtonCropnumeField);
        cropnameField.setSuffixComponent(helperButtonCropnameField);
        refferenceField.setSuffixComponent(helperButtonRefferenceField);
        id_culturecategDetails.addComponentAsFirst(helperButtonId_culturecategDetails);
    }

    // createHelperButton is a component - JmixButton
    protected JmixButton createHelperButton() {
        JmixButton helperButton = uiComponents.create(JmixButton.class);
        helperButton.setIcon(VaadinIcon.QUESTION_CIRCLE.create());
        helperButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_TERTIARY_INLINE);

        return helperButton;
    }
}