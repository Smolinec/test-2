package cz.jirka.test.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DeviceProfile.
 */
@Entity
@Table(name = "device_profile")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DeviceProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "device_profile_device",
               joinColumns = @JoinColumn(name = "device_profile_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "device_id", referencedColumnName = "id"))
    private Set<Device> devices = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public DeviceProfile name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Device> getDevices() {
        return devices;
    }

    public DeviceProfile devices(Set<Device> devices) {
        this.devices = devices;
        return this;
    }

    public DeviceProfile addDevice(Device device) {
        this.devices.add(device);
        device.getDeviceProfiles().add(this);
        return this;
    }

    public DeviceProfile removeDevice(Device device) {
        this.devices.remove(device);
        device.getDeviceProfiles().remove(this);
        return this;
    }

    public void setDevices(Set<Device> devices) {
        this.devices = devices;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeviceProfile)) {
            return false;
        }
        return id != null && id.equals(((DeviceProfile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeviceProfile{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
