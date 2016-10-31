package client.base;

/**
 * Base interface for overlay views
 */
public interface IOverlayView extends IView {

    /**
     * Displays the modal overlay view.
     */
    void showModal();

    /**
     * Closes the modal overlay view.
     */
    void closeModal();

    /**
     * Indicates whether or not the overlay is currently showing.
     *
     * @return True if the overlay is showing, false otherwise
     */
    boolean isModalShowing();

    /**
     * If this modal is not currently showing, show it.
     */
    default void showOneModal() {
        if (!isModalShowing()) {
            showModal();
        }
    }

    /**
     * If this model is currently showing, close it.
     */
    default void closeOneModal() {
        if (isModalShowing()) {
            closeModal();
        }
    }
}

