# Manajemen Karyawan

Aplikasi untuk mengelola data karyawan, termasuk menambahkan, menghapus, memperbarui, dan melihat detail karyawan beserta foto mereka. Aplikasi ini dibangun menggunakan Java dengan antarmuka grafis berbasis `Swing`.


## Penggunaan
- **Menambahkan karyawan** :isi nama, posisi, gaji, dan pilih foto karyawan, lalu klik tombol **Tambah Karyawan**
- **Menghapus karyawan** : Pilih karyawan dari tabel dan **klik Hapus Karyawan**.
- **Memperbarui karyawan** : Pilih karyawan yang ingin diperbarui, isi informasi baru dan klik **Update Karyawan**.
- **Melihat detail Karyawan** : Pilih karyawan dari tabel dan klik **Detail Karyawan** untuk melihat nama, posisi, gaji, dan foto.

## Struktur Program
- **EmployeeManager** : Kelas utama yang menjalankan aplikasi dan menangani tampilan antarmuka pengguna (GUI).
- **EmployeeDetails** : Kelas yang memnyimpan informasi rinci karyawan, termasuk nama, posisi, gaji, dan foto.
- **GUI** : Antarmuka pengguna dibangun menggunakan JFrame, JTable, dan berbagai elemen Swing lainnya untuk interaksi.

## Contoh Antarmuka Pengguna
- **Tabel Karyawan** : Antarmuka utama menunjukkan tabel daftar karyawan yang menampilkan nama, posisi, dan gaji karyawan. pengguna dapat memilih baris untuk menghapus, memperbarui atau melihat detail.
- **Form Input** : Form input bagian bawah memungkinkan pengguna untuk memasukkan nama, posisi, dan gaji karyawan. Tombol-tombol seperti tambah karyawan,hapus karyawan,update karyawan, dan detail karyawan memungkinkan pengguna untuk melakukan operasi yang sesuai.

## Menangani Kesalahan
- **Kesalahan input**: jika ada kolom yang kosong atau input yang tidak valid (misalnya, "gaji bukan angka!"), program akan menampilkan pesan kesalahan menggunakan JOptionPane.
- **Foto Tidak Ditemukan** : jika file foto tidak ditemukan atau tidak dapat dibaca, aplikasi akan menampilkan pesan kesalahan yang sesuai.

## Penulis

proyek ini dibuat oleh Fakhri Ziddan Akbar dan Abdul Ghofir Samjani. 