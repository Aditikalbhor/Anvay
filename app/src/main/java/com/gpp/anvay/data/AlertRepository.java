package com.gpp.anvay.data;

import com.gpp.anvay.model.AlertItem;

import java.util.ArrayList;
import java.util.List;

public class AlertRepository {
    private static AlertRepository instance;
    private final List<AlertItem> alerts;

    private AlertRepository() {
        alerts = new ArrayList<>(MockDataProvider.getInitialAlerts());
    }

    public static synchronized AlertRepository getInstance() {
        if (instance == null) {
            instance = new AlertRepository();
        }
        return instance;
    }

    public List<AlertItem> getAllAlerts() {
        return new ArrayList<>(alerts);
    }

    public List<AlertItem> getActiveAlerts() {
        List<AlertItem> list = new ArrayList<>();
        for (AlertItem alert : alerts) {
            if (alert.isActive()) {
                list.add(alert);
            }
        }
        return list;
    }

    public List<AlertItem> getAlertsByCategory(String category) {
        if (category == null || category.equalsIgnoreCase("All")) {
            return getAllAlerts();
        }
        List<AlertItem> list = new ArrayList<>();
        for (AlertItem alert : alerts) {
            if (category.equalsIgnoreCase(alert.getCategory()) ||
                category.equalsIgnoreCase(alert.getPriority())) {
                list.add(alert);
            }
        }
        return list;
    }

    public void addAlert(AlertItem alert) {
        if (alert != null) {
            alerts.add(0, alert);
        }
    }

    public boolean toggleAlertStatus(String id) {
        for (AlertItem alert : alerts) {
            if (alert.getId().equals(id)) {
                alert.setActive(!alert.isActive());
                return true;
            }
        }
        return false;
    }

    public boolean deleteAlert(String id) {
        for (int i = 0; i < alerts.size(); i++) {
            if (alerts.get(i).getId().equals(id)) {
                alerts.remove(i);
                return true;
            }
        }
        return false;
    }
}
