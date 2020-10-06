package com.NowakArtur97.WorldOfManga.feature.user;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "role", schema = "world_of_manga")
@Data
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
}
