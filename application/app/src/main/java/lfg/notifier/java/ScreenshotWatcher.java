package lfg.notifier.java;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchService;
import java.nio.file.WatchKey;

public class ScreenshotWatcher {
    private File screenshotDir;
    private WatchService watcher;

    public ScreenshotWatcher() {
    }

    public void setWatchDirectory(File screenshotDir) {
        this.screenshotDir = screenshotDir;

        if (!this.screenshotDir.exists() || !this.screenshotDir.isDirectory()) {
            throw new IllegalArgumentException("Supplied path is not a directory");
        }
        try {
            watcher = FileSystems.getDefault().newWatchService();
            this.screenshotDir.toPath().register(watcher, StandardWatchEventKinds.ENTRY_CREATE);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void watch(Runnable callback) {
        new Thread(() -> {
            try {
                while (true) {
                    // this waits for the event
                    System.out.println("Watching for the event...");
                    WatchKey key = watcher.take();
                    for (WatchEvent<?> event: key.pollEvents()) {
                        if (event.kind() == StandardWatchEventKinds.OVERFLOW) {
                            System.err.println("WARNING: Overflow event received");
                            continue;
                        }
                    }
                    // As we know, any registered event means that the dungeon is found, so we have to notify user
                    callback.run();

                    boolean valid = key.reset();
                    if (!valid) {
                        System.err.println("WARNING: screenshots directory is no longer longer valid. Stopping watch service");
                        break;
                    }
                }
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }).start();
        System.out.println("After thread");
    }
}
