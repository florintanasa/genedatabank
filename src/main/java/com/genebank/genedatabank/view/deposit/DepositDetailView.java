package com.genebank.genedatabank.view.deposit;

import com.genebank.genedatabank.entity.Deposit;

import com.genebank.genedatabank.entity.Pasaport;
import com.genebank.genedatabank.entity.Storage;
import com.genebank.genedatabank.security.OnlyViewRole;
import com.genebank.genedatabank.view.main.MainView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.shared.Tooltip;
import com.vaadin.flow.component.upload.Receiver;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.FileRef;
import io.jmix.core.entity.KeyValueEntity;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.Dialogs;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.component.details.JmixDetails;
import io.jmix.flowui.component.image.JmixImage;
import io.jmix.flowui.component.textarea.JmixTextArea;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.component.upload.FileStorageUploadField;
import io.jmix.flowui.component.upload.FileUploadField;
import io.jmix.flowui.component.upload.receiver.FileTemporaryStorageBuffer;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.download.DownloadFormat;
import io.jmix.flowui.download.Downloader;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.kit.component.upload.event.FileUploadSucceededEvent;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.upload.TemporaryStorage;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author : Florin Tanasă
 * @since : 05.09.2023
**/
@Route(value = "deposits/:id", layout = MainView.class)
@ViewController("Deposit.detail")
@ViewDescriptor("deposit-detail-view.xml")
@EditedEntityContainer("depositDc")
public class DepositDetailView extends StandardDetailView<Deposit> {

    @ViewComponent
    private MessageBundle messageBundle;
    @Autowired
    private Notifications notifications;
    @ViewComponent
    private FileStorageUploadField imageqrcodeUpload;
    @Autowired
    private TemporaryStorage temporaryStorage;
    @ViewComponent
    private JmixImage imageqrcode;
    @Autowired
    private Downloader downloader;
    @ViewComponent
    private JmixButton clearImageqrcodeBtn;
    @ViewComponent
    private JmixButton downloadImageqrcodeBtn;
    @ViewComponent
    private JmixButton generateqrcodeBtn;
    @Autowired
    private DataManager dataManager;
    @ViewComponent
    private EntityComboBox<Storage> id_storageField;
    @ViewComponent
    private TypedTextField<String> deposit_codeField;
    @Autowired
    private UiComponents uiComponents;
    @ViewComponent
    private EntityComboBox<Pasaport> pasaportsComboBox;
    @ViewComponent
    private TypedTextField<String> yearstorageField;
    @ViewComponent
    private TypedTextField<Integer> yearmultiField;
    @ViewComponent
    private TypedTextField<Integer> multiplyField;
    @ViewComponent
    private TypedTextField<Integer> yeargermField;
    @ViewComponent
    private TypedTextField<Integer> percentageField;
    @ViewComponent
    private TypedTextField<Integer> stockField;
    @ViewComponent
    private TypedTextField<Double> humidityField;
    @ViewComponent
    private TypedTextField<Double> mmbField;
    @ViewComponent
    private JmixTextArea commentsField;
    @ViewComponent
    private JmixDetails detailsQrCode;
    @ViewComponent
    private TypedTextField<String> id_taxonomy_genusField;
    @ViewComponent
    private TypedTextField<String> id_taxonomy_speciesField;
    @ViewComponent
    private TypedTextField<String> scopeField;
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private Dialogs dialogs;

    @Subscribe
    public void onInit(final InitEvent event) {
        initManualTooltip();
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        // create object for got authentication roles
        Authentication authentication = currentAuthentication.getAuthentication();
        // display AR code image
        displayImage();
        // update buttons state
        updateImageButtons(getEditedEntity().getQrcode() != null);
        // check if the user is part from "only-view" role code
        // if yes disable buttons for clear and download the QR code image
        if (getRolesNames(authentication).contains(OnlyViewRole.CODE)) {
            clearImageqrcodeBtn.setEnabled(false);
            downloadImageqrcodeBtn.setEnabled(false);
        }
    }

    // create method to got roles code names for current authenticate user
    private String getRolesNames(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining());
    }

    // method when we change the value in storage type ComboBox
    @Subscribe("id_storageField")
    public void onId_storageFieldComponentValueChange(final AbstractField.ComponentValueChangeEvent<EntityPicker<Storage>, Storage> event) {
        // Define some variables
        String LastDepositCode;
        String NewDepositCode;
        // clear the field for Code Deposit
        deposit_codeField.clear();
        // If Deposit code is null and is for long or medium time load the last code inserted for specific Storage
        if (getEditedEntity().getDeposit_code() == null
                && (getEditedEntity().getId_storage().getCodespe() == 12
                || getEditedEntity().getId_storage().getCodespe() == 13)) {
            /* Next lines work for scalar only
               String lastCode = dataManager
                       .loadValue("select d.deposit_code from Deposit d where d.id_storage=:id_storageFields order by d.createdDate desc",
                               String.class).parameter("id_storageFields",getEditedEntity().getId_storage())
                       .one();
             */
            List<KeyValueEntity> lastCode = dataManager
                    .loadValues("select d.deposit_code from Deposit d where d.id_storage=:id_storage_id order by d.createdDate desc")
                    .parameter("id_storage_id", getEditedEntity().getId_storage())
                    .store("main")
                    .properties("deposit_code")
                    .maxResults(1)
                    .list();

            if (!lastCode.isEmpty()) {
                //getEditedEntity().setDeposit_code(lastCode.get(0).getValue("deposit_code"));
                // CR1A01a1
                // CR1 - is the refrigerator chamber number 1
                // A - is the cabinet from CR1 (can be A,B,C,D)
                // 01 - is the row number or the rack from the cabinet A
                // a - is the column number from the cabinet A (can be a,b,c,d,f,g,h,i,j)
                // So finally CR1A01a is the label for the box from CR1
                // 1 - represent the number for envelope from the box CR1A01a
                // Get the last code deposit for storage selected, example CR1A01a1
                LastDepositCode = lastCode.get(0).getValue("deposit_code");
                // Calculate the length for last code deposit, example for CR1A01a1 is 8
                int lengthDepositCode = LastDepositCode.length();
                // Got the recipient number from the box, example for CR1A01a1 is 1
                int lastRecipient = Integer.parseInt(LastDepositCode.substring(7, lengthDepositCode));
                // Add 1 to the last recipient number, example for CR1A01a1 the result is 2
                int newRecipient = lastRecipient + 1;
                // Concatenate the new deposit code from the box CR1A01a and the new recipient number what is 2
                NewDepositCode = LastDepositCode.substring(0, 7) + newRecipient;
                // Complete the field Deposit code with the new deposit code, example CR1A01a2
                getEditedEntity().setDeposit_code(NewDepositCode);
            }
            else getEditedEntity().setDeposit_code("NA");
        }
    }

    // maps (association) keys to values
    Map<String, String> getQrCodeImageFileMap() {
        //I got the current date and time
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime currentDate = LocalDate.now().atStartOfDay();
        String currentDateToday = dateFormat.format(currentDate);

        String currentUserHomeDir = System.getProperty("user.home");
        String qrCodeImageFolder = currentUserHomeDir + File.separator + "qrCodeImage";

        //I set the file name for qrCodeImage
        String fileName = getEditedEntity().getId_accenumb().getAccenumb() + "-"
                + getEditedEntity().getDeposit_code() + "-"
                + getEditedEntity().getId_accenumb().getId_taxonomy().getGenus() + "-"
                + getEditedEntity().getId_accenumb().getId_taxonomy().getSpecies() + "-"
                + getEditedEntity().getYearstorage();

        String qrCodeImageFile = qrCodeImageFolder + File.separator + fileName + "-" + currentDateToday + ".jpg";

        Map<String, String> qrCodeImageFiles = new HashMap<>();

        qrCodeImageFiles.put("folder", qrCodeImageFolder);
        qrCodeImageFiles.put("fileName", fileName);
        qrCodeImageFiles.put("fileWithPath", qrCodeImageFile);

        return qrCodeImageFiles;

    }

    // method for click button to generate the QR code image
    @Subscribe("generateqrcodeBtn")
    public void onGenerateQrCodeBtnClick(ClickEvent<Button> event) throws IOException, WriterException {
        // call the method
        generateQrCode();
    }

    // method to generate the QR code image
    public void generateQrCode() throws WriterException, IOException {
        // the date necessary to be added in QR code
        // attention if next fields is null or have only free space we cant generate the QR code image
        if (getEditedEntity().getId_accenumb() == null
                || getEditedEntity().getId_accenumb().getId_taxonomy() == null
                || getEditedEntity().getId_accenumb().getId_taxonomy().getGenus() == null
                || getEditedEntity().getId_accenumb().getId_taxonomy().getSpecies() == null
                || getEditedEntity().getId_accenumb().getId_taxonomy().getSubtaxa() == null
                || getEditedEntity().getId_accenumb().getAccname() == null
                || getEditedEntity().getId_accenumb().getId_sampstat() == null) {
            Html htmlMessage = new Html(messageBundle.getMessage("htmlMessageNullDataQrCode"));
            dialogs.createMessageDialog()
                    .withHeader("Info")
                    .withContent(htmlMessage)
                    .open();
        } else if (getEditedEntity().getId_accenumb().getId_taxonomy().getGenus().isBlank()
                || getEditedEntity().getId_accenumb().getId_taxonomy().getSpecies().isBlank()
                || getEditedEntity().getId_accenumb().getId_taxonomy().getSubtaxa().isBlank()
                || getEditedEntity().getId_accenumb().getAccname().trim().isBlank()) {
            Html htmlMessage = new Html(messageBundle.getMessage("htmlMessageBlankDataQrCode"));
            dialogs.createMessageDialog()
                    .withHeader("Info")
                    .withContent(htmlMessage)
                    .open();
        }
        else {
            // some variables because exist possibility to some fields to be null
            String Country;
            String County;
            String Locality;

            // for next fields we accept to be null or blank
            // but in this case we complete with NA (Not Allocated)
            if (getEditedEntity().getId_accenumb().getId_country() == null) {
                Country = "NA";
            } else Country = getEditedEntity().getId_accenumb().getId_country().getName();
            if (getEditedEntity().getId_accenumb().getId_countysiruta() == null ) {
                County = "NA";
            } else  County = getEditedEntity().getId_accenumb().getId_countysiruta().getName();
            if (getEditedEntity().getId_accenumb().getId_localitysiruta() == null ) {
                Locality = "NA";
            } else Locality = getEditedEntity().getId_accenumb().getId_localitysiruta().getName();

            // I got the current date and time
            DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String dateTimeNow = dateTimeFormat.format(now);

            // construct data necessary to be encode in QR code
            String data = getEditedEntity().getId_accenumb().getAccenumb() + "|"
                + getEditedEntity().getDeposit_code() + "|"
                + getEditedEntity().getId_accenumb().getId_taxonomy().getGenus() + "|"
                + getEditedEntity().getId_accenumb().getId_taxonomy().getSpecies() + "|"
                + getEditedEntity().getId_accenumb().getId_taxonomy().getSubtaxa() + "|"
                + getEditedEntity().getId_accenumb().getAccname() + "|"
                + Country + "|"
                + County + "|"
                + Locality + "|"
                + getEditedEntity().getId_accenumb().getId_sampstat().getCodespe() + "|"
                + dateTimeNow;

            // create directory if not exist
            File theDir = new File(getQrCodeImageFileMap().get("folder"));
            if (!theDir.exists()) {
                theDir.mkdirs();
            }

            // for QR code using UTF-8
            Hashtable<EncodeHintType, String> hash = new Hashtable<>();
            hash.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            //encode the data in format QR_CODE with width 500 and height 500
            BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 500, 500, hash);

            //Write the matrix in the path
            MatrixToImageWriter.writeToPath(matrix, "jpg", Paths.get(getQrCodeImageFileMap().get("fileWithPath")));

            //Notification for user with success messages
            String okMessage = messageBundle.getMessage("okMessageQRcode");
            notifications.create("OK", okMessage).show();
        }
    }

    // method to upload the QR image to database when the button is clicked
    @Subscribe("imageqrcodeUpload")
    public void onImageqrcodeUploadFileUploadSucceed(final FileUploadSucceededEvent<FileStorageUploadField> event){
        Receiver receiver = event.getReceiver();
        if (receiver instanceof FileTemporaryStorageBuffer) {
            UUID fileID = ((FileTemporaryStorageBuffer) receiver).getFileData().getFileInfo().getId();
            File file = temporaryStorage.getFile(fileID);

            if (file != null) {
                //notifications.create("Fișierul este salvat temporar la " + file.getAbsolutePath()).show();
            }
            FileRef fileRef = temporaryStorage.putFileIntoStorage(fileID, event.getFileName());
            imageqrcodeUpload.setValue(fileRef);
            displayImage();
            notifications.create("Încarcă fișierul: "
                    + ((FileTemporaryStorageBuffer) receiver).getFileData().getFileName()).show();

        }
    }

    // method for click button to download the QR code image from database
    @Subscribe("downloadImageqrcodeBtn")
    public void onDownloadImageQrCodeBtnClick(ClickEvent<Button> event) {
        FileRef fileRef = getEditedEntity().getQrcode();
        downloader.download(fileRef);
        //downloadFromFileStorage();
    }

    // method to download the QR code image from database - used first
    private void downloadFromFileStorage(byte[] content) {
        //FileRef fileRef = getEditedEntity().getQrcode();
        //downloader.download(fileRef);
        //downloader.download(content, getEditedEntity().getQrcode().getFileName(), DownloadFormat.JPG);
    }

    // method for click button to delete/clear the QR code image from database
    @Subscribe("clearImageqrcodeBtn")
    public void onClearImageQrCodeBtnClick(ClickEvent<Button> event) {
        getEditedEntity().setQrcode(null);
        displayImage();
    }

    // method to display the QR code image
    public void displayImage() {
        imageqrcode.setVisible(getEditedEntity().getQrcode() != null);
    }

    // method for update the state of buttons
    public void updateImageButtons(boolean enable) {
        downloadImageqrcodeBtn.setVisible(enable);
        clearImageqrcodeBtn.setVisible(enable);
        generateqrcodeBtn.setVisible(!enable);
        imageqrcodeUpload.setVisible(!enable);
    }

    // Delete file created with Qr code after was saved
    @Subscribe(target = Target.DATA_CONTEXT)
    public void onPostSave(final DataContext.PostSaveEvent event) {
        File file = new File(getQrCodeImageFileMap().get("fileWithPath"));
        if (file.exists()) {
            file.delete();
        }
    }

    // Create Tool Tips for input fields
    private void initManualTooltip()   {
      JmixButton hlpBtnPasaportsComboBox = createHelperButton();
      JmixButton hlpBtnId_taxonomy_genusField = createHelperButton();
      JmixButton hlpBtnId_taxonomy_speciesField = createHelperButton();
      JmixButton hlpBtnId_storageField = createHelperButton();
      JmixButton hlpBtnDeposit_codeField = createHelperButton();
      JmixButton hlpBtnYearstoargeField = createHelperButton();
      JmixButton hlpBtnYearmultiField = createHelperButton();
      JmixButton hlpBtnMultiplyField = createHelperButton();
      JmixButton hlpBtnYeargermField = createHelperButton();
      JmixButton hlpBtnPercentageField = createHelperButton();
      JmixButton hlpBtnStockField = createHelperButton();
      JmixButton hlpBtnHumidityField = createHelperButton();
      JmixButton hlpBtnMmbField = createHelperButton();
      JmixButton hlpBtnCommentsField = createHelperButton();
      JmixButton hlpBtnDetailsQrCode = createHelperButton();
      JmixButton hlpBtnScopeField = createHelperButton();

      Tooltip tooltipPasaportsComboBox = pasaportsComboBox.getTooltip();
      Tooltip tooltipId_taxonomy_genusField = id_taxonomy_genusField.getTooltip();
      Tooltip tooltipId_taxonomy_speciesField = id_taxonomy_speciesField.getTooltip();
      Tooltip tooltipId_storageField = id_storageField.getTooltip();
      Tooltip tooltipDeposit_codeField = deposit_codeField.getTooltip();
      Tooltip tooltipYearstoargeField = yearstorageField.getTooltip();
      Tooltip tooltipYearmultiField = yearmultiField.getTooltip();
      Tooltip tooltipMultiplyField = multiplyField.getTooltip();
      Tooltip tooltipYeargermField = yeargermField.getTooltip();
      Tooltip tooltipPercentageField = percentageField.getTooltip();
      Tooltip tooltipStockField = stockField.getTooltip();
      Tooltip tooltipHumidityField = humidityField.getTooltip();
      Tooltip tooltipMmbField = mmbField.getTooltip();
      Tooltip tooltipCommentsField = commentsField.getTooltip();
      Tooltip tooltipDetailsQrCode = detailsQrCode.getTooltip();
      Tooltip tooltipScopeField = scopeField.getTooltip();

      hlpBtnPasaportsComboBox.addClickListener(event -> tooltipPasaportsComboBox.setOpened(!tooltipPasaportsComboBox.isOpened()));
      hlpBtnId_taxonomy_genusField.addClickListener(event -> tooltipId_taxonomy_genusField.setOpened(!tooltipId_taxonomy_genusField.isOpened()));
      hlpBtnId_taxonomy_speciesField.addClickListener(event -> tooltipId_taxonomy_speciesField.setOpened(!tooltipId_taxonomy_speciesField.isOpened()));
      hlpBtnId_storageField.addClickListener(event -> tooltipId_storageField.setOpened(!tooltipId_storageField.isOpened()));
      hlpBtnDeposit_codeField.addClickListener(event -> tooltipDeposit_codeField.setOpened(!tooltipDeposit_codeField.isOpened()));
      hlpBtnYearstoargeField.addClickListener(event -> tooltipYearstoargeField.setOpened(!tooltipYearstoargeField.isOpened()));
      hlpBtnYearmultiField.addClickListener(event -> tooltipYearmultiField.setOpened(!tooltipYearmultiField.isOpened()));
      hlpBtnMultiplyField.addClickListener(event -> tooltipMultiplyField.setOpened(!tooltipMultiplyField.isOpened()));
      hlpBtnYeargermField.addClickListener(event -> tooltipYeargermField.setOpened(!tooltipYeargermField.isOpened()));
      hlpBtnPercentageField.addClickListener(event -> tooltipPercentageField.setOpened(!tooltipPercentageField.isOpened()));
      hlpBtnStockField.addClickListener(event -> tooltipStockField.setOpened(!tooltipStockField.isOpened()));
      hlpBtnHumidityField.addClickListener(event -> tooltipHumidityField.setOpened(!tooltipHumidityField.isOpened()));
      hlpBtnMmbField.addClickListener(event -> tooltipMmbField.setOpened(!tooltipMmbField.isOpened()));
      hlpBtnCommentsField.addClickListener(event -> tooltipCommentsField.setOpened(!tooltipCommentsField.isOpened()));
      hlpBtnDetailsQrCode.addClickListener(event -> tooltipDetailsQrCode.setOpened(!tooltipDetailsQrCode.isOpened()));
      hlpBtnScopeField.addClickListener(event -> tooltipScopeField.setOpened(!tooltipScopeField.isOpened()));

      pasaportsComboBox.setPrefixComponent(hlpBtnPasaportsComboBox);
      id_taxonomy_genusField.setSuffixComponent(hlpBtnId_taxonomy_genusField);
      id_taxonomy_speciesField.setSuffixComponent(hlpBtnId_taxonomy_speciesField);
      id_storageField.setPrefixComponent(hlpBtnId_storageField);
      deposit_codeField.setSuffixComponent(hlpBtnDeposit_codeField);
      yearstorageField.setSuffixComponent(hlpBtnYearstoargeField);
      yearmultiField.setSuffixComponent(hlpBtnYearmultiField);
      multiplyField.setSuffixComponent(hlpBtnMultiplyField);
      yeargermField.setSuffixComponent(hlpBtnYeargermField);
      percentageField.setSuffixComponent(hlpBtnPercentageField);
      stockField.setSuffixComponent(hlpBtnStockField);
      humidityField.setSuffixComponent(hlpBtnHumidityField);
      mmbField.setSuffixComponent(hlpBtnMmbField);
      commentsField.setSuffixComponent(hlpBtnCommentsField);
      detailsQrCode.addComponentAsFirst(hlpBtnDetailsQrCode);
      scopeField.setSuffixComponent(hlpBtnScopeField);
    }

    // method for create a button for tips
    private JmixButton createHelperButton() {
        // create object
        JmixButton helperButton = uiComponents.create(JmixButton.class);
        // set the icon for button
        helperButton.setIcon(VaadinIcon.QUESTION_CIRCLE.create());
        // set the theme for button
        helperButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_TERTIARY_INLINE);

        // return object
        return helperButton;
    }
}