# Gökbörü Tarayıcı

Gökbörü görsel kimliğinden esinlenen Android WebView tarayıcı başlangıç projesi. Koyu lacivert, altın sarısı ve turkuaz vurgular kullanır. Birinci görsel uygulama simgesi, ikinci görsel tema referansı olarak projeye eklenmiştir.

## GitHub Actions ile APK derleme

1. Bu ZIP'i çıkarın ve içindeki dosyaları yeni bir GitHub deposuna yükleyin.
2. Depoda **Actions** sekmesini açın.
3. `Build Gokboru APK` iş akışını seçip **Run workflow** düğmesine basın (veya `main` dalına push yapın).
4. İşlem bitince çalışma sayfasındaki **Artifacts** bölümünden `gokboru-tarayici-debug-apk` dosyasını indirin.
5. ZIP içinden `app-debug.apk` dosyasını çıkarıp Android cihazınıza kurun. Android, bilinmeyen kaynaklardan uygulama kurma izni isteyebilir.

## Yerel derleme

Android Studio'da projeyi açın ve Gradle senkronizasyonundan sonra `app > Tasks > build > assembleDebug` çalıştırın. Java 17 ve Android SDK 35 gerekir.

## Mevcut durum / önemli notlar

Bu bir **başlangıç sürümüdür**; temel URL/adres arama, WebView içinde gezinme, geri/ileri ve ana sayfa vardır. Sekme yönetimi, kalıcı favoriler/geçmiş, gerçek reklam filtre listeleri, gizli gezinmenin güvenli biçimde izole edilmesi ve gelişmiş masaüstü kullanıcı aracısı henüz tam uygulanmamıştır. Menüde görünen bazı seçenekler görsel prototip/yer tutucudur. Gerçek reklam engelleme için ayrıca filtre motoru ve güncel filtre listeleri gerekir.

WebView güvenlik sınırları nedeniyle bazı siteler çalışmayabilir. Bu proje ilk prototip olarak sunulmaktadır; yayınlamadan önce güvenlik, gizlilik, erişilebilirlik ve hedef SDK uyumluluğunu test edin.
