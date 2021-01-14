package com.neoblacko.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "file", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "user")
public class UserFile {

    @Id
    @Column(name = "file_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fileId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_size")
    private Integer size;

    @ManyToOne
    @JoinColumn(name = "file_user_id")
    private User user;

    @Column(name = "file_uuid", unique = true)
    private UUID fileUUID;

    @Column(name = "file_path")
    private String path;

}
