package com.edushare.edushare_backend.entities;

import java.util.ArrayList;
import java.util.List;

import com.edushare.edushare_backend.enums.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Teacher extends User{

   @OneToMany(mappedBy = "teacher",cascade = CascadeType.ALL)
   private List<Course> createdCourses=new ArrayList<>();

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
