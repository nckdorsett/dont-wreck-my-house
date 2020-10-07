package learn.domain;

import learn.data.HostRepository;
import learn.models.Host;

public class HostService {

    public final HostRepository repository;

    public HostService(HostRepository repository) {
        this.repository = repository;
    }

    public Host findByEmail(String email) {
        return repository.findByEmail(email);
    }


}
