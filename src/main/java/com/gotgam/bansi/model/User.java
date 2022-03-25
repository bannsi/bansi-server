package com.gotgam.bansi.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @Column(name = "kakao_id", nullable = false)
    private String kakaoId;
    @Column(name = "nickname", nullable = false)
    private String nickname;
    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;
    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column(columnDefinition = "LONGTEXT")
    private String image;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<ArchiveFolder> folders;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Comment> comments; 
    
    public User withKakaoId(String kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }

    public User withNickname(String nickname){
        this.nickname = nickname;
        return this;
    }
}
