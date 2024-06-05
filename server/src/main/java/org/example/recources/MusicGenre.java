package org.example.recources; /**
 * The MusicGenre enum represents different genres of music.
 */

import java.io.Serializable;

public enum MusicGenre implements Serializable {

    PROGRESSIVE_ROCK, // Progressive rock genre
    JAZZ, // Jazz genre
    BLUES; // Blues genre

    /**
     * Constructs a MusicGenre object.
     */
    MusicGenre() {

    }
}
