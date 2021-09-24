package ru.job4j.todo.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    private Date created;
    private boolean done;

    public Item itemOf(int id, String description, Date created, boolean done) {
        Item item = new Item();
        item.id = id;
        item.description = description;
        item.created = created;
        item.done = done;
        return item;
    }

    public int getId() {
        return id;
    }

    public String getDesc() {
        return description;
    }

    public Date getCreated() {
        return created;
    }

    public boolean isDone() {
        return done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return id == item.id
                && done == item.done
                && Objects.equals(description, item.description)
                && Objects.equals(created, item.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, created, done);
    }

    @Override
    public String toString() {
        return "Item{"
                + "id=" + id
                + ", description='" + description + '\''
                + ", created=" + created
                + ", done=" + done
                + '}';
    }
}
