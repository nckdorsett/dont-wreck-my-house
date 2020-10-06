package learn.data;

import learn.models.Host;

import java.util.List;

public interface HostRepository {

    List<Host> findAll();

    Host findByEmail(String email);

    Host add(Host host) throws DataException;

    boolean update(Host host) throws DataException;

    boolean delete(Host host) throws DataException;
}
