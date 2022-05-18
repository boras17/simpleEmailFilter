package controllers.loaders;

import javafx.scene.control.TableView;
import repositories.AbstractRepository;

public abstract class AbstractTableViewDataLoader<T> {
    private final AbstractRepository<T> dataRepository;
    private final TableView<T> context;

    public AbstractTableViewDataLoader(TableView<T> context, Class<T> dataType) {
        this.dataRepository = new AbstractRepository<T>(dataType);
        this.context = context;
    }

    public AbstractRepository<T> getDataRepository() {
        return dataRepository;
    }

    public TableView<T> getContext() {
        return context;
    }
}
