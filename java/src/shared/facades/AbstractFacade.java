package shared.facades;

import com.sun.istack.internal.NotNull;
import shared.models.game.ClientModel;

import java.util.Objects;

abstract class AbstractFacade {
    private ClientModel model;

    /**
     * Constructor. Requires a valid game model to work.
     *
     * @param model the model to use, not null
     * @throws NullPointerException if {@code model} is null
     * @pre {@code model} is a valid {@link ClientModel}.
     * @post This provides valid operations on {@code model}.
     */
    AbstractFacade(@NotNull ClientModel model) {
        setModel(model);
    }

    /**
     * Gets the model of this facade used for performing operations.
     *
     * @return the current model
     */
    protected ClientModel getModel() {
        return this.model;
    }

    /**
     * Sets the current game model.
     *
     * @param model the model to use, not null
     * @throws NullPointerException if {@code model is null}
     * @pre {@code model} is a valid {@link ClientModel}.
     * @post This provides valid operations on {@code model}.
     */
    public void setModel(@NotNull ClientModel model) {
        this.model = model;
    }
}
