package com.gb.db;

import com.gb.DAO.*;
import com.gb.db.PostgreSQLImpl.PostgreSQLImpl;

import java.sql.Connection;

/**
 * "Simile" (fra virgolette) allo strategy pattern.
 * Per usare una nuova implementazione del database,
 * ad esempio uno che utilizza una sintassi SQL diversa,
 * basterà creare una nuova classe e cambiare il metodo
 * getDatabase di questa classe, in modo che restituisca
 * un' istanza della classe desiderata. Nel Main non dovrà
 * cambiare nulla, poiché questa classe (Database) fornisce
 * un livello di astrazione.
 */
public abstract class Database implements MusicDAO, AlbumDAO, ArtistDAO, GroupDAO, GenreDAO, LinkDAO {

    public static synchronized Database getDatabase() {
        return PostgreSQLImpl.getInstance();
    }

    public static Connection getConnection() {
        return PostgreSQLImpl.getConnection();
    }

}