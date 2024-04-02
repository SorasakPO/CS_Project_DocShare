package ku.cs.csProject.service;

import ku.cs.csProject.common.UserRole;
import ku.cs.csProject.entity.User;
import ku.cs.csProject.model.SignupRequest;
import ku.cs.csProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getUserRole().name()));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    public org.springframework.security.core.userdetails.User updateRoleUser(SignupRequest user, Principal principal) {
        User record = userRepository.findByEmail(principal.getName());
        record.setLineId(user.getLineId());
        record.setUserRole(UserRole.LENDER);
        userRepository.save(record);

        // รับข้อมูลผู้ใช้งานจาก Spring Security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // ตรวจสอบว่าผู้ใช้งานมีการลงชื่อเข้าใช้หรือไม่
        if (authentication != null) {
            // ตรวจสอบว่า Principal เป็น UserDetails หรือไม่
            if (authentication.getPrincipal() instanceof UserDetails) {
                // แปลง Principal เป็น UserDetails
//                UserDetails userDetails = (UserDetails) authentication.getPrincipal();

                // สร้าง Authorities ใหม่ (บทบาทใหม่)
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(UserRole.LENDER.name()));

                // สร้าง UserDetails ใหม่ที่มี Authorities ใหม่
                UserDetails modifiedUserDetails = new org.springframework.security.core.userdetails.User(record.getEmail(), record.getPassword(), authorities);

                // สร้าง Authentication ใหม่โดยใช้ UsernamePasswordAuthenticationToken
                Authentication newAuthentication = new UsernamePasswordAuthenticationToken(modifiedUserDetails, authentication.getCredentials(), authorities);

                // อัปเดต SecurityContextHolder ด้วย Authentication ใหม่
                SecurityContextHolder.getContext().setAuthentication(newAuthentication);

            }
        }
        return null;
    }


}
