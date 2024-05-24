package com.szalay.thinktools.ui;

import com.szalay.thinktools.io.ProjectIO;
import com.szalay.thinktools.model.Project;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ApplicationDocument {
    private Project project;
    private boolean projectModified;
    private final ApplicationSettings settings;

    private final Stage stage;
    private final Label statusLabel;

    private File file;

    private final List<ApplicationDocumentListener> listeners = Collections.synchronizedList(new ArrayList<>());

    public ApplicationDocument(final Stage stage, final Label statusLabel) throws Exception {
        settings = new ApplicationSettings();
        this.statusLabel = statusLabel;

        if (settings.getLastProjectFile() != null) {
            open(settings.getLastProjectFile());
        }

        this.stage = stage;
    }

    public void setStatus(final String msg) {
        statusLabel.setText(msg);
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public boolean hasUnsavedChanges() {
        return projectModified;
    }

    public void setProjectModified(boolean m) {
        this.projectModified = m;

        if (this.projectModified) {
            triggerListeners();
        }
    }

    public ApplicationSettings getApplicationSettings() {
        return settings;
    }

    public void doNew(final String name) {
        save();

        this.project = new Project(name);
        this.projectModified = true;
        this.file = null;

        triggerListeners();
    }

    public void open(final File file) {
        save();

        try {
            this.project = ProjectIO.loadProject(file);
            this.projectModified = false;
            this.file = file;
            this.settings.setLastProjectFile(file);

            triggerListeners();

            setStatus("Open " + this.project.getName());
        } catch (Exception e) {
            e.printStackTrace();
            UIUtil.showError(TextUtil.getText("errorTitle"), e.toString());
        }
    }

    public void save() {
        if (project == null) {
            return;
        }

        if (file != null) {
            try {
                ProjectIO.saveProject(project, file);
                this.settings.setLastProjectFile(file);
                setStatus("Saved " + project.getName());
            }
            catch (Exception e) {
                e.printStackTrace();
                UIUtil.showError(TextUtil.getText("errorTitle"), e.toString());
            }
        }
        else {
            final FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(TextUtil.getText("selectProjectFileTitle"));
            file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                try {
                    ProjectIO.saveProject(project, file);
                } catch (Exception e) {
                    e.printStackTrace();
                    UIUtil.showError(TextUtil.getText("errorTitle"), e.toString());
                }
            }

            projectModified = false;
        }
    }

    public void addListener(final ApplicationDocumentListener listener) {
        this.listeners.add(listener);
    }

    public void triggerListeners() {
        for (final ApplicationDocumentListener l : listeners) {
            l.changed(this);
        }
    }

    public void exit() {
        if (file != null) {
            settings.setLastProjectFile(file);

            try {
                settings.save();
            }
            catch (Exception e) {
                e.printStackTrace();
                UIUtil.showError(TextUtil.getText("errorTitle"), e.toString());
            }
        }
    }

    public void removeListener(ApplicationDocumentListener oldListener) {
        listeners.remove(oldListener);
    }
}
