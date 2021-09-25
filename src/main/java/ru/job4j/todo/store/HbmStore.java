package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.todo.model.Item;

import java.util.ArrayList;
import java.util.List;

public class HbmStore {
    private static final Logger LOG = LoggerFactory.getLogger(HbmStore.class.getName());
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
            LOG.error("Exception: ", e);
        }
        return item;
    }

    public List<Item> findAll() {
        List<Item> result = new ArrayList<>();
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            result = session.createQuery("from Item").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Exception: ", e);
        }
        return result;
    }

    public Item findById(int id) {
        Item item = null;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            item = session.get(Item.class, id);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            LOG.error("Exception: ", e);
        }
        return item;
    }

    public boolean update(int id) {
        boolean result = false;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.createQuery("update Item set done = true where id = :id")
                    .setParameter("id", id).executeUpdate();
            session.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            LOG.error("Exception: ", e);
        }
        return result;
    }
}
