package ru.digitalhabbits.homework3.dao;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Repository;
import ru.digitalhabbits.homework3.domain.Department;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DepartmentDaoImpl
        implements DepartmentDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Department create(@Nonnull Department entity) {
        return null;
    }

    @Override
    public Department findById(@Nonnull Integer integer) {
        // TODO: NotImplemented
        throw new NotImplementedException();
    }

    @Override
    public List<Department> findAll() {
        // TODO: NotImplemented
        throw new NotImplementedException();
    }

    @Override
    public Department update(Department entity) {
        // TODO: NotImplemented
        throw new NotImplementedException();
    }

    @Override
    public Department delete(Integer integer) {
        // TODO: NotImplemented
        throw new NotImplementedException();
    }
}
