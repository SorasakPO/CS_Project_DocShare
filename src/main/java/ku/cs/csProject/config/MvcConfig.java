package ku.cs.csProject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/uploads/**") // เรียกใช้งานที่อยู่ uploads ด้วย URL ที่ตรงกับ /uploads/**
                .addResourceLocations("file:src/main/resources/static/uploads/") // ระบุที่อยู่ของไฟล์ uploads ในโฟลเดอร์แห่งนี้
                .setCachePeriod(0); // ไม่ใช้แคช
        registry
                .addResourceHandler("/identification/**")
                .addResourceLocations("file:src/main/resources/static/identification/")
                .setCachePeriod(0);
    }
}
