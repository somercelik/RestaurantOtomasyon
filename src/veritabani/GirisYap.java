package veritabani;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author byporti
 */
public class GirisYap {
    
    // Bu metod parametre olarak aldığı değerleri mysql tablosu içerisindeki
    // verilerle kontrol edip eğer değerler doğruysa true , yanlışsa false
    // değeri döndürerek veritabanı içerisinde yönetim hakkına sahip olup
    // olamayacağımızı kontrol eder.
    public boolean girisYap(String alinanAd, String alinanSifre) 
                                                          throws SQLException {
        
        //Sorguları işlemek ve gerekli ayarlamaları yapmak için
        PreparedStatement ps;
        // Sonuçları alıp kullanabilmek için
        ResultSet rs;
        
        //Veritabanına bağlantı sağlayalım.
        VeritabaniBaglantisi vb = new VeritabaniBaglantisi();
        try {
            vb.baglan();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Veritabanı Bağlantı Hatası!!"
                    + e);
        }
        // admin tablosundan sifre bilgisini kAdi bilgisi girilerek almaya 
        // çalışalım.
        String sql = "SELECT sifre FROM admin WHERE kadi=?";
        // sql i preparedStatement içerisine aktaralım.
        ps = vb.con.prepareStatement(sql);
        // kadi bilgisini aktaralım.kadi bilgisine parametre olarak aldığımız
        // alinanAd ı yolladık.Bu adda bir kayıt varsa bunun sifresini bize
        // sorgu sonucu vericektir.Bu sifreyi sorgu sonucunda alıp parametre 
        // olarak alınanSifre ile doğrumu diye kontrol edip eğer doğruysa
        // veritabanı içerisinde yönetim iznimiz olucaktır diğer türlü
        // veritabanı içerisinde yönetim iznimiz olmuyacak ve hata alıcağız.
        ps.setString(1, alinanAd);
        // sql i çalıştırıp resultSet e sonucu aktardım.
        rs = ps.executeQuery();
        // resultSet içerisinde bir sonuc dönüp dönmediğini kontrol edelim.
        while(rs.next()) {
            //resultSet ile gelen şifreyi alalım.
            String gercekSifre = rs.getString("sifre");
            // parametre olarak gelen sifre ile karşılaştırma yapalım.
            return gercekSifre.equals(alinanSifre);
        }

        // Desteklenmeyen işlem hatası için.
        throw new UnsupportedOperationException();
    }
}
