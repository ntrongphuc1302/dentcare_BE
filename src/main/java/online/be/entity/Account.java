package online.be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import online.be.enums.Role;
import online.be.enums.Status;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Account implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String fullName;

    @Column(unique = true)
    String email;

    @Column(unique = true)
    String phone;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;

    @Enumerated(EnumType.STRING)
    Role role;

//    String qualification; +1 việc
//
//    String specialization;
    //trạng thái hoạt động của tài khoản
    @Enumerated(EnumType.STRING)
    Status status;

    @ManyToOne
    @JoinColumn(name = "dental_clinic_id")
    private DentalClinic dentalClinic;

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<Patient> patients;

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    List<DentistServices> dentistServices;

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    List<CheckIn> checkIns;

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    List<Slot> slots;

//    @JsonIgnore
//    @OneToMany(mappedBy = "account")
//    List<WorkingHours> workingHours;

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    List<Qualification> qualifications;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.toString()));
    }


    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
