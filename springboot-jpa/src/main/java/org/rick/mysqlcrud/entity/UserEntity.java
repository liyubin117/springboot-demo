package org.rick.mysqlcrud.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="t_user")
public class UserEntity implements Serializable {

    @Column(name="t_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="t_name")
    private String name;

    @Column(name="t_age")
    private int age;

    @Column(name="t_address")
    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                '}';
    }

    public UserEntity(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserEntity() {}
}
