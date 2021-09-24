package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Item;

import java.util.List;

public class HbmStore {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private static final class Lazy {
        private static final HbmStore INST = new HbmStore();
    }

    public static HbmStore instOf() {
        return Lazy.INST;
    }

    public Item add(Item item) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
        } catch (Exception e) {
            item = null;
        }
        return item;
    }

    public List<Item> findAll() {
        List<Item> result;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            result = session.createQuery("from Item").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            result = null;
        }
        return result;
    }

    public Item findById(int id) {
        Item item;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            item = session.get(Item.class, id);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            item = null;
        }
        return item;
    }

    public boolean update(Item item) {
        boolean result = true;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.update(item.itemOf(item.getId(), item.getDesc(), item.getCreated(), true));
            session.getTransaction().commit();
        } catch (Exception e) {
            result = false;
        }
        return result;
    }
}
