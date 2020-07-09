package com.gb.modelObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.gb.Constants.*;

public class Link {

    private Integer musicId;
    private String link;

    private static final Logger logger = LoggerFactory.getLogger(Link.class);

    public Link() {
    }

    public Link(ResultSet rs) {
        try {
            setMusicId(rs.getInt(MUSICID));
            setLink(rs.getString(LINK));
        } catch(SQLException e) {
            logger.error("Error creating Group object: {}", e.getMessage());
        }
    }

    public Link(Integer musicId, String link) {
        this.musicId = musicId;
        this.link = link;
    }

    public Integer getMusicId() {
        return musicId;
    }

    public void setMusicId(Integer musicId) {
        if(musicId < 0) {
            throw new IllegalArgumentException("Link.musicId deve essere > 0.");
        }
        this.musicId = musicId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        if(link != null && link.length() > 200) {
            throw new IllegalArgumentException("Lunghezza link deve essere < 200.");
        }
        this.link = link;
    }

}
