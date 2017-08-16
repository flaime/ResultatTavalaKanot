package hjälpprogram;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class HållaKollPåMattTask {

}

//
//public class HållaKollPåMattTask extends Task<ObservableList<Rectangle>> {
//    private ReadOnlyObjectWrapper<ObservableList<Rectangle>> partialResults =
//            new ReadOnlyObjectWrapper<>(
//                    this, 
//                    "partialResults",
//                    FXCollections.observableArrayList(
//                            new ArrayList<>()
//                    )
//            );
//
//    public final ObservableList<Rectangle> getPartialResults() {
//        return partialResults.get();
//    }
//
//    public final ReadOnlyObjectProperty<ObservableList<Rectangle>> partialResultsProperty() {
//        return partialResults.getReadOnlyProperty();
//    }
//
//    @Override
//    protected ObservableList<Rectangle> call() throws Exception {
//        updateMessage("Creating Rectangles...");
//        for (int i = 0; i < 100; i++) {
//            if (isCancelled()) break;
//            final Rectangle r = new Rectangle(10, 10);
//            r.setX(10 * i);
//            Platform.runLater(() -> partialResults.get().add(r));
//            updateProgress(i, 100);
//        }
//        return partialResults.get();
//    }
//}