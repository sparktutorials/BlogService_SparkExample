package me.tomassetti.handlers;

import me.tomassetti.Validable;

/**
 * Created by federico on 24/07/15.
 */
public class EmptyPayload implements Validable {
    @Override
    public boolean isValid() {
        return true;
    }
}
