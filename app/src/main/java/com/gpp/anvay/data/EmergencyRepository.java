package com.gpp.anvay.data;

import com.gpp.anvay.model.EmergencyContact;
import com.gpp.anvay.model.SafetyInstruction;

import java.util.ArrayList;
import java.util.List;

public class EmergencyRepository {
    private static EmergencyRepository instance;
    private final List<EmergencyContact> contacts;
    private final List<SafetyInstruction> instructions;

    private EmergencyRepository() {
        contacts = new ArrayList<>(MockDataProvider.getInitialEmergencyContacts());
        instructions = new ArrayList<>(MockDataProvider.getInitialSafetyInstructions());
    }

    public static synchronized EmergencyRepository getInstance() {
        if (instance == null) {
            instance = new EmergencyRepository();
        }
        return instance;
    }

    public List<EmergencyContact> getEmergencyContacts() {
        return new ArrayList<>(contacts);
    }

    public List<SafetyInstruction> getSafetyInstructions() {
        return new ArrayList<>(instructions);
    }

    public void addEmergencyContact(EmergencyContact contact) {
        if (contact != null) {
            contacts.add(contact);
        }
    }

    public boolean updateEmergencyContact(EmergencyContact updated) {
        if (updated == null) return false;
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getId().equals(updated.getId())) {
                contacts.set(i, updated);
                return true;
            }
        }
        return false;
    }

    public boolean deleteEmergencyContact(String id) {
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getId().equals(id)) {
                contacts.remove(i);
                return true;
            }
        }
        return false;
    }
}
