package model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "notice")
public class Notice {
    private int id;
    private int userId;
    private String content;
}
