<div align="center">

# 🛡️ Cybersecurise

### แอปป้องกันภัยไซเบอร์สำหรับคนไทย ขับเคลื่อนด้วย AI/NLP

*สแกน SMS มิจฉาชีพ ตรวจสอบ Blacklist และฝึกภูมิคุ้มกันไซเบอร์ — ทำงานบนเครื่องแบบ Offline*

[![NSC 2024 Finalist](https://img.shields.io/badge/🏆%20NSC%202024-National%20Finalist-FFBD2E?style=for-the-badge)](https://www.nectec.or.th)

[![Kotlin](https://img.shields.io/badge/Kotlin-A97BFF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Python](https://img.shields.io/badge/Python-3776AB?style=for-the-badge&logo=python&logoColor=white)](https://python.org)
[![FastAPI](https://img.shields.io/badge/FastAPI-009688?style=for-the-badge&logo=fastapi&logoColor=white)](https://fastapi.tiangolo.com)
[![Firebase](https://img.shields.io/badge/Firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=black)](https://firebase.google.com)
[![Jupyter](https://img.shields.io/badge/Jupyter-F37626?style=for-the-badge&logo=jupyter&logoColor=white)](https://jupyter.org)

[![Accuracy](https://img.shields.io/badge/AI%20Accuracy-95%25-brightgreen?style=flat-square)](https://github.com/gong-sn-ix-ii/Cybersecurise)
[![Privacy](https://img.shields.io/badge/Processing-On--Device%20Offline-blueviolet?style=flat-square)](https://github.com/gong-sn-ix-ii/Cybersecurise)
[![Developer](https://img.shields.io/badge/developer-Kitsada%20Khamnuan-22D3EE?style=flat-square)](https://gong-ix-ii-dev.com)

</div>

---

## 🎯 ที่มาของโปรเจกต์

ทุกวันนี้คนไทยตกเป็นเหยื่ออาชญากรรมไซเบอร์เพิ่มขึ้นทุกปี — โดนหลอกผ่าน SMS แอบอ้างเป็นธนาคาร, ลิงก์ปลอม, หรือเบอร์ Call Center มิจฉาชีพ ความเสียหายระดับชาติแตะ **หลักหมื่นล้านบาทต่อปี**

ผมตั้งคำถามว่า — *ถ้ามือถือของเราสามารถ "รู้ทันมิจฉาชีพ" ได้ตั้งแต่ข้อความเด้งเข้ามา จะช่วยลดเหยื่อได้แค่ไหน?*

**Cybersecurise** คือคำตอบของคำถามนั้น — แอปที่ฝัง AI/NLP ลงในเครื่องโดยตรง วิเคราะห์ข้อความภาษาไทยแบบ **Real-time** บอกได้ว่าข้อความไหน *Scam, Spam, OTP, หรือปลอดภัย* — ทั้งหมดประมวลผลบนเครื่องของคุณเอง **ไม่ส่งข้อมูลออกไปไหน**

ไม่ใช่แค่ตรวจ SMS — แอปนี้ยังตรวจการตั้งค่ามือถือที่เปิดช่องโหว่ให้มิจฉาชีพ, เช็คเลขบัญชี/เบอร์โทรกับ Blacklist สากล, และมีแบบทดสอบฝึกความรู้ด้านไซเบอร์ให้ผู้ใช้ด้วย

> 🏆 **โปรเจกต์นี้ได้รับเลือกเข้ารอบชิงชนะเลิศระดับประเทศ NSC 2024** — โครงการการแข่งขันพัฒนาโปรแกรมคอมพิวเตอร์แห่งประเทศไทย ครั้งที่ 26 จัดโดย NECTEC & NSTDA

---

## ⚡ ฟีเจอร์หลัก 5 ระบบ

### 1️⃣ AI SMS Threat Detection — ตรวจ SMS มิจฉาชีพแบบ Real-time

หัวใจของแอปนี้ ผมเทรน NLP Model ภาษาไทยจนได้ความแม่นยำ **95%** จากนั้นฝังลงในแอปให้ทำงาน **Offline บนเครื่อง** ทันทีที่ SMS เข้ามา แอปจะคัดกรองและจำแนกเป็น 4 ประเภท: *Scam / Spam / OTP / Normal* — ผู้ใช้เห็นป้ายสีและไอคอนแยกชัดเจน ข้อความอันตรายจะถูก Quarantine ทันที (ผู้ใช้จะไม่เห็นเนื้อความเพื่อป้องกันการเผลอกดลิงก์)

### 2️⃣ Vulnerability Scanner — ตรวจการตั้งค่าที่เป็นช่องโหว่

มิจฉาชีพหลายเคสยึดเครื่องเหยื่อได้เพราะ Setting บางตัวเปิดทิ้งไว้ (เช่น Install from Unknown Sources, Accessibility Service) แอปจะสแกนการตั้งค่าทั้งหมด, ให้คะแนนความเสี่ยง, และ **นำทางไปปิด setting นั้นโดยตรง** พร้อมอธิบายว่าเปิดไว้แล้วอันตรายอย่างไร

### 3️⃣ Application Risk Assessment — ตรวจสิทธิ์การเข้าถึงของแต่ละแอป

ประเมินความเสี่ยงของทุกแอปที่ติดตั้งในเครื่อง โดยวิเคราะห์ **Permission ที่ขอ** — แอปที่ขอเข้าถึง SMS, Contacts, และ Accessibility พร้อมกัน หรือขอสิทธิ์ที่ไม่สมเหตุสมผลกับฟังก์ชันของแอป จะถูกคำนวณ **App Risk Score** ออกมาเป็นเปอร์เซ็นต์ความอันตราย พร้อมแสดงเหตุผลให้ผู้ใช้ตัดสินใจว่าควรลบแอปนั้นทิ้งหรือไม่

### 4️⃣ Blacklist Lookup — เช็คเลขบัญชี / เบอร์โทร / ชื่อ

เชื่อมต่อกับ API ของ **CheckGon** และ **ChaladOhn** (ฐานข้อมูล Blacklist ของไทย) ผู้ใช้กรอกเลขบัญชีก่อนโอนเงิน หรือเบอร์โทรก่อนรับสาย — ระบบจะเช็คกลับมาภายในไม่กี่วินาทีว่าเคยถูกรายงานเป็นมิจฉาชีพหรือไม่ พร้อมแสดงรายละเอียดเคสที่เคยมีคนแจ้ง

### 5️⃣ Cybersecurity Quiz — ฝึกภูมิคุ้มกันไซเบอร์

เทคโนโลยีอย่างเดียวไม่พอ — ผู้ใช้ต้อง "รู้ทัน" กลลวงด้วย แอปจึงมีแบบทดสอบหลายหมวดให้ผู้ใช้ฝึกแยกแยะกลโกงรูปแบบต่าง ๆ ผ่านสถานการณ์จำลอง ช่วยสร้างภูมิคุ้มกันระยะยาว ไม่ใช่แค่พึ่งระบบเตือน

---

## 🏗️ สถาปัตยกรรม & เทคโนโลยี

```
┌─────────────────────────────────────────────┐
│         Android App (Kotlin)                │
│  ┌───────────────────────────────────────┐  │
│  │   On-device NLP Model (95% accuracy)  │  │
│  │   • Real-time SMS Classification      │  │
│  │   • Offline Processing                │  │
│  └───────────────────────────────────────┘  │
└──────────────────┬──────────────────────────┘
                   │
                   ├─── 🔥 Firebase (Auth + Real-time DB)
                   ├─── 🔍 CheckGon API (Blacklist)
                   └─── 🔍 ChaladOhn API (Blacklist)

         🧠 ML Pipeline (Development Time)
         Python + Jupyter Notebook
         ├─ Data Preprocessing
         ├─ NLP Model Training
         └─ FastAPI (Testing Endpoint)
```

| Stack | บทบาท |
|---|---|
| **Kotlin** | ภาษาหลักของแอป Android |
| **Python + Jupyter** | สร้างและเทรน NLP Model |
| **FastAPI** | API สำหรับทดสอบ Model ก่อนฝังลงเครื่อง |
| **Firebase** | Authentication + Real-time Database |
| **CheckGon / ChaladOhn API** | Blacklist Lookup ภายนอก |

---

## 📱 หน้าจอการใช้งาน

แอปมี **25 หน้าจอ** แบ่งตามฟีเจอร์ดังนี้ ⤵️

### 🎬 Onboarding & Authentication

<table>
<tr>
<td width="25%"><img src="docs/screenshots/0.webp" alt="Welcome"/><br/><b>Welcome</b><br/>หน้าแรกของแอป</td>
<td width="25%"><img src="docs/screenshots/1.webp" alt="Login"/><br/><b>เข้าสู่ระบบ</b><br/>User Authentication</td>
<td width="25%"><img src="docs/screenshots/2.webp" alt="Register"/><br/><b>สมัครสมาชิก</b><br/>สร้างบัญชีใหม่</td>
<td width="25%"><img src="docs/screenshots/3.webp" alt="Recovery"/><br/><b>ลืมรหัสผ่าน</b><br/>Password Recovery</td>
</tr>
</table>

### 🏠 Main Hub & Knowledge

<table>
<tr>
<td width="50%"><img src="docs/screenshots/4.webp" alt="Dashboard"/><br/><b>Main Dashboard</b> — หน้าหลักรวมทุกฟีเจอร์</td>
<td width="50%"><img src="docs/screenshots/4_0.webp" alt="Knowledge"/><br/><b>Knowledge Hub</b> — คำแนะนำการรับมือมิจฉาชีพรูปแบบต่าง ๆ</td>
</tr>
</table>

### 💳 Blacklist Lookup (เช็คก่อนโอน)

<table>
<tr>
<td width="25%"><img src="docs/screenshots/4_1.webp" alt="Verify"/><br/><b>Account Verification</b></td>
<td width="25%"><img src="docs/screenshots/6.webp" alt="Results"/><br/><b>Verification Results</b></td>
<td width="25%"><img src="docs/screenshots/8.webp" alt="Bank"/><br/><b>Bank Account Lookup</b></td>
<td width="25%"><img src="docs/screenshots/7.webp" alt="Phone"/><br/><b>Phone Number Lookup</b></td>
</tr>
</table>

### 🤖 AI SMS Threat Detection (หัวใจของแอป)

<table>
<tr>
<td width="33%"><img src="docs/screenshots/9.webp" alt="AI Detection"/><br/><b>AI SMS Detection</b><br/>คัดกรอง SMS ด้วย AI</td>
<td width="33%"><img src="docs/screenshots/10.webp" alt="Category"/><br/><b>Threat Categories</b><br/>หมวดหมู่และไอคอน</td>
<td width="33%"><img src="docs/screenshots/11.webp" alt="Analysis"/><br/><b>Message Analysis</b><br/>รายละเอียดผลวิเคราะห์</td>
</tr>
<tr>
<td width="33%"><img src="docs/screenshots/11_1.webp" alt="Block"/><br/><b>Block Confirmation</b><br/>ยืนยันบล็อกข้อความ</td>
<td width="33%"><img src="docs/screenshots/11_2.webp" alt="Unblock"/><br/><b>Unblock</b><br/>ปลดบล็อกข้อความ</td>
<td width="33%"><img src="docs/screenshots/12.webp" alt="Quarantine"/><br/><b>Quarantined Messages</b><br/>ข้อความอันตรายที่ถูกกักไว้</td>
</tr>
</table>

### 🛡️ Vulnerability Scanner & App Risk

<table>
<tr>
<td width="25%"><img src="docs/screenshots/13.webp" alt="Menu"/><br/><b>Navigation Menu</b></td>
<td width="25%"><img src="docs/screenshots/14.webp" alt="Vuln Scanner"/><br/><b>Vulnerability Scanner</b><br/>ตรวจ Setting อันตราย</td>
<td width="25%"><img src="docs/screenshots/15.webp" alt="Remediation"/><br/><b>Security Remediation</b><br/>นำทางไปแก้ไข</td>
<td width="25%"><img src="docs/screenshots/16.webp" alt="App Risk"/><br/><b>App Risk Assessment</b><br/>ประเมินความเสี่ยงแอป</td>
</tr>
<tr>
<td width="25%"><img src="docs/screenshots/17.webp" alt="Permissions"/><br/><b>Permission Audit</b><br/>ตรวจสิทธิ์การเข้าถึง</td>
<td width="25%"><img src="docs/screenshots/18.webp" alt="Quiz"/><br/><b>Quiz Module</b><br/>แบบทดสอบ</td>
<td width="25%"><img src="docs/screenshots/19.webp" alt="Quiz Cat"/><br/><b>Quiz Categories</b><br/>หมวดหมู่แบบทดสอบ</td>
<td width="25%"><img src="docs/screenshots/20.webp" alt="Help"/><br/><b>Help & Support</b><br/>ช่องทางขอความช่วยเหลือ</td>
</tr>
</table>

---

## 🏆 NSC 2024 — เส้นทางสู่รอบชิงชนะเลิศ

โปรเจกต์นี้ส่งเข้าประกวด **โครงการการแข่งขันพัฒนาโปรแกรมคอมพิวเตอร์แห่งประเทศไทย ครั้งที่ 26 (NSC 2024)** จัดโดย **NECTEC** ร่วมกับ **NSTDA**

| | รายละเอียด |
|---|---|
| 🏅 **สถานะ** | National Finalist (รอบชิงชนะเลิศระดับประเทศ) |
| 📅 **ปีการแข่งขัน** | NSC 2024 (ครั้งที่ 26) |
| 🏛️ **หน่วยงาน** | NECTEC & NSTDA |
| 📋 **หมวด** | Cybersecurity / Anti-Fraud |

> หลังจากผ่านการนำเสนอต่อคณะกรรมการในรอบชิงชนะเลิศ ผมได้รับ Feedback เชิงเทคนิคจากผู้เชี่ยวชาญทั้งด้าน AI/NLP และ Mobile Security — เป็นประสบการณ์ที่ผลักดันให้ผมพัฒนาตัวเองในสายนี้ต่อไปอย่างจริงจัง

---

## 💡 บทเรียนจากการพัฒนา

ระหว่างทำโปรเจกต์นี้ ผมเจอความท้าทายสำคัญ 3 เรื่อง ที่กลายเป็นบทเรียนที่ใช้ได้ในงานต่อ ๆ มาทุกตัว:

### 🚀 Technical Leap

ยกระดับทักษะ Kotlin จากระดับเบื้องต้นไประดับที่ฝัง NLP Model ลงในเครื่องและทำงาน Offline ได้จริง — ไม่ใช่แค่เรียกใช้ Library แต่เข้าใจ Memory Footprint, Threading Model, และวิธีโหลด Model แบบ Lazy เพื่อไม่ให้แอป Lag

### ⚡ Performance Optimization

ตอนแรก Model วิ่งช้ามาก ขยับนิ้วทีเครื่องร้อน ผมต้องลงไปวิเคราะห์ **Bottleneck** ใช้ Android Profiler, ลด Memory Allocation ใน Hot Path, และเปลี่ยนวิธี Inference จนได้ความเร็วระดับ Real-time จริง ๆ — สแกน SMS ได้ทันทีไร้ความหน่วง

### 🎤 Communication & Pitching

ตอนนำเสนอ NSC เป็นครั้งแรกที่ผมต้องอธิบาย **เรื่องเทคนิคซับซ้อนให้คณะกรรมการระดับชาติเข้าใจในเวลาจำกัด** ฝึกการตัดส่วนที่ไม่จำเป็น เน้น Impact ของผู้ใช้จริง และเรียนรู้การทำงานเป็นทีมในสถานการณ์กดดัน

---

## 🚀 วิธีติดตั้งและใช้งาน

> ⚠️ **หมายเหตุ:** แอปนี้พัฒนาเพื่อ Android เท่านั้น (Kotlin) เพราะต้องการสิทธิ์เข้าถึง SMS / System Settings ระดับลึก ซึ่ง iOS ไม่อนุญาต

### สิ่งที่ต้องเตรียม

- Android Studio Hedgehog หรือใหม่กว่า
- Kotlin `>= 1.9.0`
- Android SDK API 24+ (Android 7.0 ขึ้นไป)
- Firebase Project (Authentication + Firestore)
- API Key สำหรับ CheckGon และ ChaladOhn (สมัครได้ที่เว็บผู้ให้บริการ)

### ขั้นตอนการติดตั้ง

```bash
# 1. Clone repository
git clone https://github.com/gong-sn-ix-ii/Cybersecurise.git
cd Cybersecurise

# 2. เปิดโปรเจกต์ด้วย Android Studio
# 3. วาง google-services.json ไว้ที่ app/

# 4. สร้างไฟล์ local.properties เพิ่ม API Keys
echo "CHECKGON_API_KEY=your_key_here" >> local.properties
echo "CHALADOHN_API_KEY=your_key_here" >> local.properties

# 5. Build & Run
./gradlew assembleDebug
```

### Build APK สำหรับติดตั้ง

```bash
./gradlew assembleRelease
# APK จะอยู่ที่ app/build/outputs/apk/release/
```

---

## 📂 โครงสร้างโปรเจกต์

```
Cybersecurise/
├── app/
│   ├── src/main/
│   │   ├── kotlin/com/cybersecurise/
│   │   │   ├── ui/                  # Activity + Fragment + Compose
│   │   │   ├── ml/                  # NLP Model Wrapper
│   │   │   ├── scanner/             # Vulnerability Scanner
│   │   │   ├── blacklist/           # CheckGon + ChaladOhn API
│   │   │   ├── quiz/                # Cybersecurity Quiz Engine
│   │   │   └── data/                # Repository + Firebase
│   │   ├── assets/
│   │   │   └── model.tflite         # NLP Model (On-device)
│   │   └── res/
│   └── build.gradle.kts
├── ml-training/                     # Python + Jupyter
│   ├── notebooks/
│   ├── data/
│   └── fastapi_test/
└── docs/
    └── screenshots/
```

---

## 📚 เอกสารเพิ่มเติม

- 📄 **เอกสารนำเสนอโครงการ (Technical Spec)** — รายละเอียดสถาปัตยกรรมและเทคนิคทั้งหมด (2.4 MB)
- 📄 **แบบฟอร์มสรุปโครงการ NSC** — บทสรุปผู้บริหารและผลการทดสอบ (1.1 MB)

> เอกสารฉบับเต็มสามารถขอได้ผ่านช่องทางติดต่อด้านล่าง

---

## 👨‍💻 ผู้พัฒนา

<table>
<tr>
<td>

### Kitsada Khamnuan (กฤษฎา คำนวน)

*Junior Software Engineer · Cybersecurity Enthusiast · AI Application Developer*

📍 ชลบุรี / กรุงเทพฯ, ประเทศไทย

🌐 **Portfolio:** [gong-ix-ii-dev.com](https://gong-ix-ii-dev.com)
💼 **GitHub:** [@gong-sn-ix-ii](https://github.com/gong-sn-ix-ii)
💬 **LinkedIn:** [Kitsada Khamnuan](https://www.linkedin.com/in/kitsada-khamnuan-2a6729407/)

</td>
</tr>
</table>

---

## 📄 License

โปรเจกต์นี้พัฒนาเพื่อการศึกษาและการแข่งขัน NSC 2024 ส่วนของ NLP Model และ Source Code มีลิขสิทธิ์ของผู้พัฒนา — กรุณาติดต่อก่อนนำไปใช้ในเชิงพาณิชย์หรือทำซ้ำ

---

<div align="center">

### 🛡️ "เทคโนโลยีที่ดี ควรทำให้ผู้ใช้ปลอดภัยยิ่งขึ้น — ไม่ใช่เก็บข้อมูลของเขาเพิ่ม"

**⭐ ถ้าคุณชอบโปรเจกต์นี้ ฝากกด Star เป็นกำลังใจให้ผู้พัฒนาด้วยนะครับ ⭐**

Made with 💜 by [Kitsada Khamnuan](https://gong-ix-ii-dev.com) · NSC 2024 Finalist 🏆

</div>

---

> ### 📌 หมายเหตุการ Setup (ลบ section นี้ก่อน commit จริง)
>
> รูปภาพ 26 ไฟล์อยู่ที่ Portfolio repo: `src/assets/images/projects/Cybersecurise/ui/`
>
> ก่อน push ให้ก๊อปไปไว้ที่ `Cybersecurise/docs/screenshots/`:
>
> ```
> 0.webp, 1.webp, 2.webp, 3.webp, 4.webp,
> 4_0.webp, 4_1.webp, 6.webp, 7.webp, 8.webp,
> 9.webp, 10.webp, 11.webp, 11_1.webp, 11_2.webp,
> 12.webp, 13.webp, 14.webp, 15.webp, 16.webp,
> 17.webp, 18.webp, 19.webp, 20.webp, 21.webp
> ```
>
> **ทางเลือก:** ใช้ raw URL ของ Portfolio: `https://raw.githubusercontent.com/gong-sn-ix-ii/Portfolio/main/src/assets/images/projects/Cybersecurise/ui/N.webp`
