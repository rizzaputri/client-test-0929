package com.enigma.edunity.entity;

import com.enigma.edunity.constant.ShopCategory;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "m_merchant")
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String shop_name;

    private ShopCategory category;

    @Column(name = "instagram_username")
    private String instagramUsername;

    @OneToOne
    @JoinColumn(name = "user_account_id", unique = true)
    private UserAccount userAccount;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
}
