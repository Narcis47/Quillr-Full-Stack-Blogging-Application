package com.narcis.quillr.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("profile")
public class Profile {
    @Id
    private Long id;
    private Long userId;
    private String bio;
    private String avatarUrl;
    private String website;
}
