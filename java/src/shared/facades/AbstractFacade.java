package shared.facades;

import com.sun.istack.internal.NotNull;
import shared.models.game.ClientModel;

abstract class AbstractFacade {
    private FacadeManager manager;

    /**
     * Constructor. Requires a valid game model to work.
     *
     * @param manager the facade manager to use, not null
     * @throws NullPointerException if {@code model} is null
     * @pre {@code model} is a valid {@link ClientModel}.
     * @post This provides valid operations on {@code model}.
     */
    AbstractFacade(@NotNull FacadeManager manager) {
        setManager(manager);
    }

    /**
     * Gets the model of this facade used for performing operations.
     *
     * @return the current model
     */
    protected ClientModel getModel() {
        return this.manager.getClientModel();
    }

    /**
     * Sets the current game model.
     *
     * @param manager the facade manager to use, not null
     * @throws NullPointerException if {@code model is null}
     * @pre {@code model} is a valid {@link ClientModel}.
     * @post This provides valid operations on {@code model}.
     */
    public void setManager(@NotNull FacadeManager manager) {
        this.manager = manager;
    }
}
