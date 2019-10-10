package name.karmanov;

import name.karmanov.presentation.ConsoleDialog;
import name.karmanov.storage.FileStorage;

import java.io.InputStream;
import java.io.PrintStream;

public class KOrganizer {
    //TODO apply log4j
    private static final String MSG_ERR_CONSTRUCTOR_INSTREAMNULL = "Error: inStream is null!";
    private static final String MSG_ERR_CONSTRUCTOR_OUTSTREAMNULL = "Error: outStream is null!";

    private InputStream inStream;
    private PrintStream outStream;

    public KOrganizer(InputStream inStream, PrintStream outStream) {
        this.inStream = inStream;
        this.outStream = outStream;

        if (inStream == null) {
            throw new NullPointerException(MSG_ERR_CONSTRUCTOR_INSTREAMNULL);
        }
        if (outStream == null) {
            throw new NullPointerException(MSG_ERR_CONSTRUCTOR_OUTSTREAMNULL);
        }
    }

    public void run() {
        //-- load data
        FileStorage.Container storageContainer = FileStorage.loadData();
        if (storageContainer.getMessage().length() > 0) {
            outStream.println(storageContainer.getMessage());
        }
        if (!storageContainer.isLoadResult()) {
            return;
        }
        //-- run console dialog
        new ConsoleDialog(System.in, System.out, storageContainer.getOrganizerData()).run();
    }
}