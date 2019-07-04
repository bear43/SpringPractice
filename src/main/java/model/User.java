package model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usr")
@Data
public class User
{
    @Id
    @GeneratedValue
    private Long id;

    private String login;

    private String password;

    private boolean admin;

    private boolean active;

    private int authority;

    public User()
    {}

    public User(String login, String password, boolean admin, boolean active, int authority)
    {
        this.login = login;
        this.password = password;
        this.admin = admin;
        this.active = active;
        this.authority = authority;
    }

    public User(String login, String password, boolean admin)
    {
        this(login, password, admin, true, 0);
    }

    public User(String login, String password)
    {
        this(login, password, false);
    }

    @Override
    public String toString()
    {
        return String.format("login: %s | %s", login, admin ? "admin" : "not admin");
    }
}
