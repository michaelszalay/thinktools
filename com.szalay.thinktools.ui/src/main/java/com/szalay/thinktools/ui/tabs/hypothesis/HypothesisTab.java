package com.szalay.thinktools.ui.tabs.hypothesis;

import com.szalay.thinktools.model.CausalHypothesis;
import com.szalay.thinktools.model.CausalModel;
import com.szalay.thinktools.model.ComplexCause;
import com.szalay.thinktools.model.Factor;
import com.szalay.thinktools.ui.ApplicationDocument;
import com.szalay.thinktools.ui.ApplicationDocumentListener;
import com.szalay.thinktools.ui.TextUtil;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;

public class HypothesisTab extends Tab implements ApplicationDocumentListener {
    public HypothesisTab() {
        super(TextUtil.getText("hypothesisTabTitle"));
    }

    @Override
    public void changed(ApplicationDocument document) {
        final VBox layout = new VBox();

        if (document.getProject() != null) {
            final TreeView<Object> tree = createTree(document);
            layout.getChildren().add(tree);
        }
        setContent(layout);
    }

     public static TreeView<Object> createTree(CausalModel model, ApplicationDocument document) {

         final TreeView<Object> tree = new TreeView<>();
         tree.setCellFactory(causalHypothesisTreeView -> new CausalHypothesisTreeCellFactory(document));

         final TreeItem root = new TreeItem();
         tree.setRoot(root);
         root.setValue(TextUtil.getText("modelRootNode"));

         for (final CausalHypothesis h : model) {
            final TreeItem hypNode = new TreeItem();
            hypNode.setExpanded(true);
            hypNode.setValue(h);

            for (final ComplexCause c : h.getComplexCauses()) {
                final TreeItem cNode = new TreeItem();
                cNode.setExpanded(true);
                cNode.setValue(c);

                for (final Factor f : c) {
                    final TreeItem fNode = new TreeItem();
                    fNode.setExpanded(true);
                    fNode.setValue(f);
                    cNode.getChildren().add(fNode);
                }

                hypNode.getChildren().add(cNode);
            }

            final Factor effect = h.getEffect();
            if (effect != null) {
                final TreeItem effectNode = new TreeItem();
                effectNode.setExpanded(true);
                effectNode.setValue(new Effect(effect));
                hypNode.getChildren().add(effectNode);
            }

            root.getChildren().add(hypNode);
        }

         tree.getRoot().setExpanded(true);
         return tree;
    }

    public static TreeView<Object> createTree(ApplicationDocument document) {
        return createTree(document.getProject().getCausalModel(), document);
    }
}
