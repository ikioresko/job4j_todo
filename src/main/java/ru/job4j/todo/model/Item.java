package ru.job4j.todo.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    private boolean done;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Category> catList = new HashSet<>();

    public void addCategory(Category category) {
        catList.add(category);
    }

    public static Item itemOf(int id, String description, User user) {
        Item item = new Item();
        item.id = id;
        item.description = description;
        item.created = new Date(System.currentTimeMillis());
        item.done = false;
        item.user = user;
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

    public Set<Category> getCatList() {
        return catList;
    }

    public void setCatList(Set<Category> catList) {
        this.catList = catList;
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
        return id == item.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Item{"
                + "id=" + id
                + ", description='" + description + '\''
                + ", created=" + created
                + ", done=" + done
                + ", user=" + user
                + '}';
    }
}
