package process.watcher;

public interface WatchableProcess<T> {

    void addWatcher(T watcher);

    void updateWatchers();
}
