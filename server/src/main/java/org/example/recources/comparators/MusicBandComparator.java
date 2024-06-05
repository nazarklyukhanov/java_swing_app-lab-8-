/**
 * The MusicBandComparator class compares MusicBand objects based on their label sales.
 */
package org.example.recources.comparators;

import org.example.recources.MusicBand;

import java.util.Comparator;

public class MusicBandComparator implements Comparator<MusicBand> {
    /**
     * Compares two MusicBand objects based on their label sales.
     *
     * @param o1 the first MusicBand object to compare
     * @param o2 the second MusicBand object to compare
     * @return a negative integer, zero, or a positive integer as the first argument
     *         is less than, equal to, or greater than the second
     */
    @Override
    public int compare(MusicBand o1, MusicBand o2) {
        return o1.getLabel().getSales().compareTo(o2.getLabel().getSales());
    }
}
