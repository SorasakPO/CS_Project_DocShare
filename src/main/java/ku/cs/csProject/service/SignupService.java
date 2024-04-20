package ku.cs.csProject.service;

import ku.cs.csProject.common.UserRole;
import ku.cs.csProject.common.UserStatus;
import ku.cs.csProject.entity.User;
import ku.cs.csProject.model.SignupRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ku.cs.csProject.repository.UserRepository;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public void createUser(SignupRequest user, MultipartFile identificationImage) {
        User record = modelMapper.map(user, User.class);

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmssSSS_");
        String fileName =  formatter.format(currentDateTime) + StringUtils.cleanPath(identificationImage.getOriginalFilename());
        try {
            String uploadDir = "src/main/resources/static/identification/";
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            try {
                byte[] bytes = identificationImage.getBytes();
                Path filePath = uploadPath.resolve(fileName);
                Files.write(filePath, bytes);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        record.setIdentificationImage(fileName);
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        record.setPassword(hashedPassword);
        record.setUserRole(UserRole.BORROWER);
        record.setUserStatus(UserStatus.VISIBLE);

        repository.save(record);
    }
}