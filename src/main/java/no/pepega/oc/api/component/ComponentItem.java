package no.pepega.oc.api.component;

import java.util.Collections;
import java.util.List;

/**
 *
 */
public interface ComponentItem {
    ComponentType componentType();

    default List<ComponentType> provides() {
        return Collections.singletonList(componentType());
    }

    int tier();
}
