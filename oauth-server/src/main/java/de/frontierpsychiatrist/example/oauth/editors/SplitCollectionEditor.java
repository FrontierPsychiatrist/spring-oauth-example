package de.frontierpsychiatrist.example.oauth.editors;

import org.springframework.beans.propertyeditors.CustomCollectionEditor;

import java.util.Collection;

/**
 * Creates collections from a string.
 * If the string is empty or null, return an empty collection. Otherwise split by the given splitRegex and use the array.
 *
 * @author Moritz Schulze
 */
public class SplitCollectionEditor extends CustomCollectionEditor {

    private final Class<? extends Collection> collectionType;
    private final String splitRegex;

    public SplitCollectionEditor(Class<? extends Collection> collectionType, String splitRegex) {
        super(collectionType, true);
        this.collectionType = collectionType;
        this.splitRegex = splitRegex;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null || text.isEmpty()) {
            super.setValue(super.createCollection(this.collectionType, 0));
        } else {
            super.setValue(text.split(splitRegex));
        }
    }
}
