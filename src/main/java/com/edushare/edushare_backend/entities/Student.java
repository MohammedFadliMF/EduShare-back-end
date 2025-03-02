package com.edushare.edushare_backend.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.edushare.edushare_backend.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Student extends User{

    @ManyToMany
    private List<Course> enrolledCourses=new ArrayList<>();
    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public void setUsername(String username) {
        super.setUsername(username);
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Override
    public Role getRole() {
        return super.getRole();
    }

    @Override
    public void setRole(Role role) {
        super.setRole(role);
    }
}
