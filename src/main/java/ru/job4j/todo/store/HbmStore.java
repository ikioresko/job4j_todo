package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.todo.model.Item;

import java.util.List;
import java.util.function.Function;

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

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        T rsl = null;
        try {
            rsl = command.apply(session);
            tx.commit();
        } catch (final Exception e) {
            session.getTransaction().rollback();
            LOG.error("Exception: ", e);
        } finally {
            session.close();
        }
        return rsl;
    }

    public Item add(Item item) {
        this.tx(session -> session.save(item));
        return item;
    }

    public List<Item> findAll() {
        return this.tx(session -> session.createQuery("from Item").list());
    }

    public Item findById(int id) {
        return this.tx(session -> session.get(Item.class, id));
    }

    public boolean update(int id) {
        return this.tx(session -> session.createQuery("update Item set done = true where id = :id")
                .setParameter("id", id).executeUpdate()) != null;
    }
}
