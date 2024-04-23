# Computer Science Project (01418499)
## โปรแกรมประยุกต์สำหรับการบริจาค และยืม-คืนเอกสารการเรียน
(Web Application for Giving-away and Borrowing-Returning Learning Paper Document)

### ผู้จัดทำ
* นาย กันตนพ สุพรรณกูล รหัสประจำตัวนิสิต 6210451063
* นาย สรศักดิ์ พงศ์สวัสดิ์  รหัสประจำตัวนิสิต 6210451560

### เครื่องมือและภาษาที่ใช้ในการพัฒนา
* IntelliJ IDEA 2023.1.6
* MySQL
* Java spring boot
* HTML
* CSS

### ขั้นตอนการติดตั้ง
1. clone repository นี้ไปวางในโฟล์เดอรืที่ต้องการ
2. เปิด repository ด้วย IntelliJ IDEA 2023.1.6
3. สร้างโฟล์เดอร์ "uploads" และ "identification" ในโฟล์เดอร์ "static" เพื่อเก็บรูป
4. สร้างฐานข้อมูลใน MySQL ชื่อ "cs_project" collection "	utf8mb4_general_ci"
5. รันโปรแกรม และค้นหา "http://localhost:8091/" ใน browser

### ข้อเสนอแนะเพิ่มเติม
เนื่องจากตัวโรแกรมได้ปิดการใช้งานในส่วนของ Email Server หากต้องการเปิดโปรดทำตามขั้นตอนดังนี้
1. เปิด class "EmailConfig.java" ใน packet config
2. แก้ไขบรรทัดที่ 16 และ 17 ให้แก้เป็น email และ password ที่จะใช้ส่งหาผู้ใช้งาน
3. เปิด application.properties ในโฟล์เดอร์ resources
4. แก้ไขบรรทัดที่ 21 และ 22 ให้แก้เป็น email และ password ที่จะใช้ส่งหาผู้ใช้งาน
5. เปิด class "BookController" ใน packet controller
6. นำ comment บรรที่ 54 และ 61 ออก
7. เสร็จสิ้น


