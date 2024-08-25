package com.hosp.med.voll.util;

import com.hosp.med.voll.domain.model.AppointmentEntity;
import com.hosp.med.voll.domain.model.MedicEntity;
import com.hosp.med.voll.domain.model.PatientEntity;
import com.hosp.med.voll.domain.model.dto.appointment.UpdateAppointmentRequestDTO;
import com.hosp.med.voll.domain.model.dto.medic.UpdateMedicRequestDTO;
import com.hosp.med.voll.domain.model.dto.patient.UpdatePatientRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;

@Slf4j
public class LogUtils {

    public static String buildAppointmentUpdatedDataLog(UpdateAppointmentRequestDTO updateAppointmentDTO, AppointmentEntity appointmentRecord) {

        String updatedData = "Appointment data updated: ";
        HashMap<String, String> updatedAppointmentValues = new HashMap<>();

        if (!StringUtils.isBlank(updateAppointmentDTO.getDate()) && !updateAppointmentDTO.getDate().equals(appointmentRecord.getDate())) {
            updatedAppointmentValues.put("date", updateAppointmentDTO.getDate());
        }

        if (!StringUtils.isBlank(updateAppointmentDTO.getTime()) && !updateAppointmentDTO.getTime().equals(appointmentRecord.getTime())) {
            updatedAppointmentValues.put("time", updateAppointmentDTO.getTime());
        }

        updatedData += updatedData + updatedAppointmentValues.toString();
        return formatUpdatedDataLog(updatedData);
    }
    public static String buildMedicUpdatedDataLog(UpdateMedicRequestDTO updateMedicDTO, MedicEntity medicRecord) {

        String updatedData = "Medic data updated: ";
        HashMap<String, String> updatedMedicValues = new HashMap<>();

        if (!StringUtils.isBlank(updateMedicDTO.getName()) && !updateMedicDTO.getName().equals(medicRecord.getName())) {
            updatedMedicValues.put("name", updateMedicDTO.getName());
        }

        if (!StringUtils.isBlank(updateMedicDTO.getPhone()) && !updateMedicDTO.getPhone().equals(medicRecord.getPhone())) {
            updatedMedicValues.put("phone", updateMedicDTO.getPhone());
        }

        if (updateMedicDTO.getAddress() != null) {
            updatedMedicValues.put("address", "****");
        }

        updatedData += updatedData + updatedMedicValues.toString();
        return formatUpdatedDataLog(updatedData);
    }
    public static String buildPatientUpdatedDataLog(UpdatePatientRequestDTO updatePatientDTO, PatientEntity patientRecord) {

        String updatedData = "Patient data updated: ";
        HashMap<String, String> updatedPatientValues = new HashMap<>();

        if (!StringUtils.isBlank(updatePatientDTO.getName()) && !updatePatientDTO.getName().equals(patientRecord.getName())) {
            updatedPatientValues.put("name", updatePatientDTO.getName());
        }

        if (!StringUtils.isBlank(updatePatientDTO.getPhone()) && !updatePatientDTO.getPhone().equals(patientRecord.getPhone())) {
            updatedPatientValues.put("phone", updatePatientDTO.getPhone());
        }

        if (updatePatientDTO.getAddress() != null) {
            updatedPatientValues.put("address", "****");
        }

        updatedData += updatedData + updatedPatientValues.toString();
        return formatUpdatedDataLog(updatedData);
    }

    private static String formatUpdatedDataLog(String updatedDataToBeFormatted) {

        return updatedDataToBeFormatted.replace("=", ": ");
    }


    public static String buildBeanValidationErrorLog(List<FieldError> errors) {

        String invalidFieldsToBeFormatted = errors.stream().map(FieldError::getField).toList().toString();

        String invalidFieldsLog = formatBeanValidationErrorLog(invalidFieldsToBeFormatted);

        return invalidFieldsLog;
    }

    private static String formatBeanValidationErrorLog(String invalidFieldsToBeFormatted) {

        String formattedInvalidFieldsLog = invalidFieldsToBeFormatted
                .replace("[", "{")
                .replace("]", "}");

        return formattedInvalidFieldsLog;
    }

}
