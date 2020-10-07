package learn.data;

import learn.models.Host;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HostFileRepositoryDouble implements HostRepository{

    private ArrayList<Host> allHosts = new ArrayList<>();
    private final Host HOST = new Host("AAA-BBB-CCC-DDD",
            "Dorsett","ndfakie@email.com",
            "(111) 1234567","fake address",
            "Milwaukee","WI",53212, new BigDecimal(477), new BigDecimal(596.25));

    public HostFileRepositoryDouble() {
        allHosts.add(HOST);
        allHosts.add(new Host("123-456-789-0",
                "Danger","fakedanger@email.net",
                "(123) 4567890","fake address two",
                "Dallas","TX",15568, new BigDecimal(477), new BigDecimal(596.25)));
    }

    @Override
    public List<Host> findAll() {
        return allHosts;
    }

    @Override
    public Host findByEmail(String email) {
        List<Host> all = findAll();
        for (Host h : all) {
            if (h.getEmail().equals(email)) {
                return h;
            }
        }
        return null;
    }

    @Override
    public Host findById(String id) {
        List<Host> all = findAll();
        for(Host h :all) {
            if (h.getId().equals(id)) {
                return h;
            }
        }
        return null;
    }

    @Override
    public Host add(Host host) throws DataException {
        return null;
    }

    @Override
    public boolean update(Host host) throws DataException {
        return false;
    }

    @Override
    public boolean delete(Host host) throws DataException {
        return false;
    }
}
