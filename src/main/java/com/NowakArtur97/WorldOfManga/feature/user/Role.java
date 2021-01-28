package com.NowakArtur97.WorldOfManga.feature.user;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "role", schema = "world_of_manga")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    @Setter(value = AccessLevel.PRIVATE)
    private Long id;

    @Column(name = "name")
    private String name;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof Role)) return false;

        Role role = (Role) o;

        return Objects.equals(getId(), role.getId()) &&
                Objects.equals(getName(), role.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
