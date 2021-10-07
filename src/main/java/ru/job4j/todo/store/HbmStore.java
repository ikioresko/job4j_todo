package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;

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

    public Item add(Item item, String[] ids) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            for (String id : ids) {
                Category category = session.find(Category.class, Integer.parseInt(id));
                item.addCategory(category);
            }
            session.persist(item);
            session.getTransaction().commit();
        } catch (Exception e) {
            LOG.error("Exception: ", e);
        }
        return item;
    }

    public User add(User user) {
        this.tx(session -> session.save(user));
        return user;
    }

    public List<Item> findAll() {
        return this.tx(session -> session
                .createQuery(
                        "select distinct i from Item i join fetch i.catList order by i.id asc")
                .list());
    }

    public List<User> findAllUsers() {
        return this.tx(session -> session.createQuery("from User").list());
    }

    public List<Category> findAllCategories() {
        return this.tx(session -> session.createQuery("from Category").list());
    }

    public Item findById(int id) {
        return this.tx(session -> session.get(Item.class, id));
    }

    public boolean update(int id) {
        return this.tx(session -> session
                .createQuery("update Item set done = true where id = :id")
                .setParameter("id", id).executeUpdate()) != null;
    }

    public User findUserByEmail(String email) {
        return (User) this.tx(session -> session.createQuery(
                        "from User where email = :email")
                .setParameter("email", email)
                .list()
                .stream()
                .findFirst()
                .orElse(null));
    }

    public void close() throws Exception {
        sf.close();
    }
}
