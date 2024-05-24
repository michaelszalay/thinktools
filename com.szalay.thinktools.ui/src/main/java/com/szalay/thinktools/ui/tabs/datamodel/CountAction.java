package com.szalay.thinktools.ui.tabs.datamodel;

import com.szalay.thinktools.ui.ApplicationDocument;
import com.szalay.thinktools.ui.TextUtil;
import com.szalay.thinktools.ui.UIUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class CountAction implements EventHandler<ActionEvent> {
    private final ApplicationDocument document;

    public CountAction(ApplicationDocument document) {
        this.document = document;
    }

    @Override
    public void handle(ActionEvent event) {
        int count = 0;
        if (document.getProject() != null) {
            count = document.getProject().getDataModel().size();
        }

        UIUtil.showInfo(TextUtil.getText("countDataModel"), String.valueOf(count));
    }
}
