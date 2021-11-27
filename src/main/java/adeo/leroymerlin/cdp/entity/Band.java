package adeo.leroymerlin.cdp.entity;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class Band {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(fetch=FetchType.EAGER)
    private Set<Member> members;

    public Set<Member> getMembers() {
        return members;
    }

    public void setMembers(Set<Member> members) {
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Band band = (Band) o;
        return Objects.equals(id, band.id) &&
                Objects.equals(name, band.name) &&
                Objects.equals(members, band.members);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, members);
    }

    @Override
    public String toString() {
        return "Band{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", members=" + members +
                '}';
    }
}
