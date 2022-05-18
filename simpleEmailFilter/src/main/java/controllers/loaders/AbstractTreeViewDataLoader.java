package controllers.loaders;

import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeView;
import repositories.AbstractRepository;

public abstract class AbstractTreeViewDataLoader<T> {
    private final AbstractRepository<T> dataRepository;
    private final TreeView<T> context;

    public AbstractTreeViewDataLoader(TreeView<T> context, Class<T> dataType) {
        this.dataRepository = new AbstractRepository<T>(dataType);
        this.context = context;
    }

    public AbstractRepository<T> getDataRepository() {
        return dataRepository;
    }

    public TreeView<T> getContext() {
        return context;
    }
}
