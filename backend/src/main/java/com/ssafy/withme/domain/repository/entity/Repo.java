package com.ssafy.withme.domain.repository.entity;

import com.ssafy.withme.domain.member.entity.Member;
import com.ssafy.withme.domain.workspace.entity.Workspace;
import com.ssafy.withme.global.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLDelete(sql = "UPDATE repository SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class Repo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "workspace_id", nullable = false)
    private Workspace workspace;

    @Column(nullable = false)
    private Boolean isVisible;

    @Column(name = "is_deleted", columnDefinition = "tinyint(1) not null default 0")
    private Boolean isDeleted;
}
