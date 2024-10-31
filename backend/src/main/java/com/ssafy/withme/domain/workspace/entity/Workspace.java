package com.ssafy.withme.domain.workspace.entity;

import com.ssafy.withme.global.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE workspace SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class Workspace extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 2083)
    private String thumbnail;

    @Column(length = 2083, name = "repo_url")
    private String repoUrl;

    @Column(nullable = false, name = "is_created", columnDefinition = "boolean default false")
    private Boolean isCreated;

    @Column(columnDefinition = "TEXT", name = "readme_content")
    private String readmeContent;

    @Column(nullable = false, name = "is_private", columnDefinition = "boolean default false")
    private Boolean isPrivate;
}
