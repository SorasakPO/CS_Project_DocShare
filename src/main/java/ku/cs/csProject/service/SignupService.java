package ku.cs.csProject.service;

import ku.cs.csProject.entity.User;
import ku.cs.csProject.model.SignupRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ku.cs.csProject.repository.UserRepository;

@Service
public class SignupService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    public boolean isEmailAvailable(String email) {
        if (!email.equals("") && !email.equals("null") && !email.trim().isEmpty() && !email.contains(" ")) {
            return repository.findByEmail(email) == null;
        }else {
            return repository.findByEmail(email) != null;
        }
    }

    public void createUser(SignupRequest user) {
        User record = modelMapper.map(user, User.class);

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        record.setPassword(hashedPassword);

        record.setUserRole("ROLE_USER");
        record.setUserStatus("WUT"); //มีทำไมนะ

        repository.save(record);
    }

}