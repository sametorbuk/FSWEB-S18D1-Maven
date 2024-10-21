package com.workintech.sqldmljoins.repository;

import com.workintech.sqldmljoins.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OgrenciRepository extends JpaRepository<Ogrenci, Long> {


    //Kitap alan öğrencilerin öğrenci bilgilerini listeleyin..
    String QUESTION_2 = "SELECT ö.ogrno, ö.ad, ö.soyad, ö.cinsiyet, ö.sinif, ö.puan, ö.dtarih " +
            "FROM islem i JOIN ogrenci ö ON ö.ogrno = i.ogrno";
    @Query(value = QUESTION_2, nativeQuery = true)
    List<Ogrenci> findStudentsWithBook();


    //Kitap almayan öğrencileri listeleyin.
    String QUESTION_3 = "SELECT * FROM ogrenci\n" +
            "WHERE ogrno IN (SELECT ogrno FROM ogrenci\n" +
            "WHERE ogrno NOT IN  (SELECT ö.ogrno from islem i\n" +
            "JOIN ogrenci ö\n" +
            "ON ö.ogrno = i.ogrno\n" +
            ")\n" +
            ")\n";
    @Query(value = QUESTION_3, nativeQuery = true)
    List<Ogrenci> findStudentsWithNoBook();

    //10A veya 10B sınıfındaki öğrencileri sınıf ve okuduğu kitap sayısını getirin.
    String QUESTION_4 = "SELECT ö.sinif , COUNT(ad) FROM islem i\n" +
            "JOIN ogrenci ö\n" +
            "ON i.ogrno = ö.ogrno\n" +
            "GROUP BY ö.sinif\n" +
            "ORDER BY ö.sinif\n" +
            "LIMIT 2";
    @Query(value = QUESTION_4, nativeQuery = true)
    List<KitapCount> findClassesWithBookCount();

    //Öğrenci tablosundaki öğrenci sayısını gösterin
    String QUESTION_5 = "SELECT COUNT(ogrno) FROM ogrenci";
    @Query(value = QUESTION_5, nativeQuery = true)
    Integer findStudentCount();

    //Öğrenci tablosunda kaç farklı isimde öğrenci olduğunu listeleyiniz.
    String QUESTION_6 = "SELECT COUNT(ad) FROM (SELECT DISTINCT ad FROM ogrenci)";
    @Query(value = QUESTION_6, nativeQuery = true)
    Integer findUniqueStudentNameCount();

    //--İsme göre öğrenci sayılarının adedini bulunuz.
    //--Ali: 2, Mehmet: 3
    String QUESTION_7 = "SELECT ad , COUNT(ad) FROM ogrenci\n" +
            "GROUP BY ad";
    @Query(value = QUESTION_7, nativeQuery = true)
    List<StudentNameCount> findStudentNameCount();


    String QUESTION_8 = "SELECT sinif , COUNT(ogrno) FROM ogrenci\n" +
            "GROUP BY sinif";
    @Query(value = QUESTION_8, nativeQuery = true)
    List<StudentClassCount> findStudentClassCount();

    String QUESTION_9 = "\n" +
            "SELECT ö.ad , ö.soyad , COUNT(ad) FROM ogrenci ö\n" +
            "            JOIN islem i \n" +
            "            ON ö.ogrno = i.ogrno\n" +
            "            GROUP BY (ö.ad , ö.soyad)";
    @Query(value = QUESTION_9, nativeQuery = true)
    List<StudentNameSurnameCount> findStudentNameSurnameCount();
}
