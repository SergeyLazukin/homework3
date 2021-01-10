package ru.digitalhabbits.homework3.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.digitalhabbits.homework3.domain.Person;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PersonDaoImpl
        implements PersonDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Person create(@Nonnull Person entity) {
        entityManager.persist(entity);
        entityManager.flush();
        return entity;
    }

    @Override
    public Person findById(@Nonnull Integer id) {
        // TODO: NotImplemented
        return entityManager.find(Person.class, id);
    }

    @Override
    public List<Person> findAll() {
        // TODO: NotImplemented
        return entityManager.createQuery("FROM Person", Person.class).getResultList();
    }

    @Override
    public Person update(Person entity) {
        // TODO: NotImplemented
        return entityManager.merge(entity);
    }

    @Override
    public Person delete(Integer integer) {
        // TODO: NotImplemented
        Person person = entityManager.find(Person.class, integer);
        entityManager.remove(person);
        return person;
    }
}
