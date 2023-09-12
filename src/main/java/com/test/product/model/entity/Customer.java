package com.test.product.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "customer", schema = "shop")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    private Long id;
    @Column(name = "customer_full_name")
    private String customerFullName;
    @Column(name = "customer_login")
    private String customerLogin;
    @Column(name = "customer_password")
    private String customerPassword;
    @Column(name = "customer_role")
    @Enumerated(EnumType.STRING)
    private Role customerRole;
}
