# library-Management-System
Sistem Perpustakaan dengan Java - Implementasi OOP untuk manajemen data buku, anggota, dan transaksi peminjaman. Studi kasus pembelajaran pemrograman berorientasi objek.

## 🗄️ Desain Database

Aplikasi ini menggunakan database bernama `library_db` dengan struktur tabel utama:

- "books": Menyimpan data buku (id, isbn, title, author, publisher, year, category, stock).
- "members": Menyimpan data anggota (id, code, name, email, status).
- "users": Menyimpan akun login (username, password, role).
- "transactions": Menyimpan riwayat peminjaman dan pengembalian.

## 🚀 Cara Menjalankan Aplikasi

Ikuti langkah-langkah berikut untuk menjalankan aplikasi di komputer Anda:

### 1. Prasyarat (Prerequisites)
Pastikan komputer Anda sudah terinstall:
- Java Development Kit (JDK) 8 atau yang lebih baru.
- MariaDB atau MySQL Server.
- Apache NetBeans IDE.

### 2. Setup Database
1.  Buka database client (HeidiSQL atau phpMyAdmin).
2.  Buat database baru bernama: `library_db`.
3.  Jalankan script SQL (query) yang terdapat di folder `docs/` atau file `schema.sql`.
4.  Pastikan tabel `books`, `members`, `users`, dan `transactions` terbentuk.

### 3. Import Project ke NetBeans
1.  Download/Clone repository ini.
2.  Buka NetBeans -> `File` -> `Open Project`.
3.  Pilih folder `LibraryManagementSystem`.
4.  Klik "Open Project".

### 4. Tambahkan MySQL JDBC Driver
"Agar aplikasi bisa terhubung ke database, library driver harus ditambahkan."
1.  Di panel "Projects" NetBeans, klik kanan pada folder "Libraries".
2.  Pilih "Add Library...".
3.  Pilih "MySQL JDBC Driver".
4.  Klik "Add Library".

### 5. Konfigurasi Koneksi (PENTING)
1.  Buka file: `src/database/DatabaseConnection.java`.
2.  Sesuaikan username dan password database Anda pada bagian berikut:
    ```java
    private static final String USER = "root";
    private static final String PASS = "123456"; 
    ```
3.  Save file.

### 6. Menjalankan Aplikasi
1.  Klik kanan pada file `src/gui/BookForm.java`.
2.  Pilih Run File.
3.  Aplikasi akan muncul dan siap digunakan.

## 📸 Dokumentasi & Bukti

Dokumentasi lengkap, diagram UML, ERD, dan screenshot hasil implementasi dapat dilihat pada folder berikut:
- Diagrams: Folder `docs/` berisi gambar `UML_ClassDiagram.png` dan `ERD_Perpustakaan.png`.
- Screenshots: Gambar screenshot aplikasi berjalan dan database tersedia di root folder.

Laporan lengkap (Dokumen A5) tersedia terpisah sebagai tugas tertulis.

## 👥 Penyusun / Tim

Project ini disusun oleh:
- Nama: [Imam Syahid Al-Mulki]
- NIM: [24131310053]
- Universitas: [Universitas Tangerang Raya]

## 📝 Link E-book
-https://ebook.webiot.id/ebooks/buku-panduan-sistem-perpustakaan



© 2026 - Dibuat untuk Tugas UAS Pemrograman Berorientasi Objek.
