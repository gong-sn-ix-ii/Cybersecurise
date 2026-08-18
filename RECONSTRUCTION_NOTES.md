# Reconstruction 1.0.7 → 1.0.9

โปรเจกต์เดิมเป็น 1.0.7 กู้ให้ตรงพฤติกรรม 1.0.9 (ดั้งเดิม) โดยอ้างอิงจาก APK ที่ decompile
สำรองเวอร์ชันเดิมไว้ที่ `../CyberSecurise_backup_1.0.7/`

## สิ่งที่เปลี่ยน
- **build.gradle.kts**: versionCode 7→9, versionName 1.0.7→1.0.9
- **ไฟล์ใหม่**: `LoginAndRegis.kt` (หน้า landing เลือก Login/Register + drawer) — ฟีเจอร์ใหม่หลักของ 1.0.9
- **ลบ (ไม่มีใน 1.0.9)**: `ScanQR.kt`, `Testcode.kt`, `services/SpamDetector.kt`
- **AndroidManifest.xml**: เพิ่ม activity `.LoginAndRegis`
- **Nav drawer ออกแบบใหม่ทั้งแอป** (1.0.9): เมนูเปลี่ยนจาก nav_login/nav_regis/nav_callhistory
  เป็น nav_scan_account / nav_scan_phone / nav_scan_SMS / nav_home
  - `res/menu/nav_menu.xml` (เมนูใหม่)
  - `res/layout/nav_header.xml` (เพิ่ม avatar: profile_user_none/have, text_in_profile)
  - `res/layout/activity_login_and_regis.xml` (ใหม่)
  - `res/layout/activity_main.xml`: rename id `btn_account`→`btnAccount`, `imageView_logo`→`imageViewLogo`
  - drawable ใหม่ 5 ตัว: icon_scan_account, icon_scan_sms, icon_home, icon_account_block2, bg_circle_color_fix
  - อัปเดต nav handler (when(itemId)) ใน 12 activity: MainActivity, LoginAndRegis, Login,
    ActivityRegister, AppInstalledActivity, CyberExam1/2/3, DisplayEnableSettings,
    QuickCyberTest, ReadSMSActivity, ReportCenter
  - MainActivity: เพิ่มปุ่ม `btnAccount` → (ล็อกอินแล้ว? Signout_Account : LoginAndRegis)

## ข้อจำกัด / ยังไม่ได้ทำ (โปรดตรวจตอน build)
- **Build ที่เครื่องนี้ไม่ได้** (Gradle 8.6 ต้องการ JDK 17 แต่มีแค่ Java 8) — ต้องเปิด/บิลด์ใน Android Studio
- **avatar ในหน้าอื่น**: 10 activity (นอกจาก MainActivity/LoginAndRegis) menuButton ยังเซ็ตแค่
  user_email ไม่ได้เซ็ต avatar (profile_have/none) — ไม่ทำให้ compile พัง แต่ตัวอักษร avatar
  จะไม่ขึ้นในหน้าเหล่านั้น (1.0.9 อาจเซ็ต — ยังไม่ได้ port ส่วนนี้)
- ไม่ได้ line-diff ครบทุกเมธอดใน 46 ไฟล์ที่เหมือนกัน (คลาส/แพ็กเกจ/โมเดลตรงกับ 1.0.9 อยู่แล้ว
  การเปลี่ยนหลักคือ nav + LoginAndRegis) — ถ้าเจอพฤติกรรมต่างเล็กน้อยส่วนไหน แจ้งได้
