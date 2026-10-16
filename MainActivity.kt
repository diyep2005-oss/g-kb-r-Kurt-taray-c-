package com.gokboru.browser

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*

class MainActivity : Activity() {
    private val navy = Color.rgb(4, 13, 23)
    private val panel = Color.rgb(8, 25, 39)
    private val gold = Color.rgb(246, 181, 39)
    private val pale = Color.rgb(231, 239, 248)
    private lateinit var web: WebView
    private lateinit var address: EditText
    private lateinit var status: TextView
    private lateinit var home: LinearLayout
    private lateinit var browserArea: FrameLayout
    private var desktop = false
    private var adBlock = true

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = navy
        window.navigationBarColor = navy
        val root = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setBackgroundColor(navy) }
        val top = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL
            setPadding(dp(10), dp(8), dp(10), dp(8)); setBackgroundColor(panel)
        }
        val logo = TextView(this).apply { text = "☪"; textSize = 27f; setTextColor(gold); setPadding(dp(4),0,dp(8),0) }
        address = EditText(this).apply {
            hint = "Google'da ara veya adres gir"; setSingleLine(true); textSize = 14f
            setTextColor(pale); setHintTextColor(0xff8c9aa8.toInt())
            setPadding(dp(14),0,dp(10),0); setBackgroundColor(0xff102638.toInt())
            imeOptions = EditorInfo.IME_ACTION_GO
            setOnEditorActionListener { _, action, _ ->
                if (action == EditorInfo.IME_ACTION_GO || action == EditorInfo.IME_ACTION_DONE) { navigate(text.toString()); true } else false
            }
        }
        val go = button("Git", gold, navy) { navigate(address.text.toString()) }
        top.addView(logo)
        top.addView(address, LinearLayout.LayoutParams(0, dp(44), 1f))
        top.addView(go, LinearLayout.LayoutParams(dp(54), dp(44)))
        root.addView(top)

        browserArea = FrameLayout(this)
        web = WebView(this).apply {
            setBackgroundColor(Color.WHITE)
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.loadsImagesAutomatically = true
            settings.useWideViewPort = true
            settings.loadWithOverviewMode = true
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                    address.setText(url ?: "")
                    status.text = "Yükleniyor…"
                }
                override fun onPageFinished(view: WebView?, url: String?) {
                    status.text = "Hazır"
                    address.setText(url ?: "")
                }
            }
            webChromeClient = WebChromeClient()
        }
        browserArea.addView(web, FrameLayout.LayoutParams(-1,-1))
        home = buildHome()
        browserArea.addView(home, FrameLayout.LayoutParams(-1,-1))
        root.addView(browserArea, LinearLayout.LayoutParams(-1,0,1f))

        val bottom = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER
            setBackgroundColor(0xff030a11.toInt()); setPadding(dp(4),dp(5),dp(4),dp(5))
        }
        bottom.addView(navButton("‹") { if (web.canGoBack()) web.goBack() })
        bottom.addView(navButton("›") { if (web.canGoForward()) web.goForward() })
        bottom.addView(navButton("⌂") { showHome() })
        bottom.addView(navButton("▢") { Toast.makeText(this, "Tek sekme açık", Toast.LENGTH_SHORT).show() })
        bottom.addView(navButton("☰") { showMenu() })
        root.addView(bottom)
        status = TextView(this).apply { text = "GÖKBÖRÜ  •  Güvenli gezinme"; textSize=10f; setTextColor(gold); gravity=Gravity.CENTER; setPadding(0,dp(3),0,dp(5)); setBackgroundColor(navy) }
        root.addView(status)
        setContentView(root)
    }

    private fun buildHome(): LinearLayout {
        val scroller = ScrollView(this)
        val content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL; gravity = Gravity.CENTER_HORIZONTAL
            setPadding(dp(20), dp(20), dp(20), dp(18))
            background = android.graphics.drawable.GradientDrawable(
                android.graphics.drawable.GradientDrawable.Orientation.TL_BR,
                intArrayOf(0xff061726.toInt(), 0xff0b2c40.toInt(), 0xff050d16.toInt())
            )
        }
        val emblem = ImageView(this).apply {
            setImageDrawable(android.graphics.drawable.Drawable.createFromStream(assets.open("gokboru-emblem-original.png"), null))
            scaleType = ImageView.ScaleType.FIT_CENTER
        }
        content.addView(emblem, LinearLayout.LayoutParams(dp(154),dp(154)))
        content.addView(text("GÖKBÖRÜ", 29f, gold, true))
        content.addView(text("SELÇUKLU TARZI TARAYICI", 12f, 0xffffd979.toInt(), true))
        val search = EditText(this).apply {
            hint = "Web'de ara veya adres yaz"; setSingleLine(true); textSize=15f
            setTextColor(pale); setHintTextColor(0xff91a3b2.toInt()); setPadding(dp(14),0,dp(14),0)
            setBackgroundColor(0xff102638.toInt()); imeOptions=EditorInfo.IME_ACTION_GO
            setOnEditorActionListener { _, action, _ -> if(action==EditorInfo.IME_ACTION_GO || action==EditorInfo.IME_ACTION_DONE){navigate(text.toString());true}else false }
        }
        val searchLp = LinearLayout.LayoutParams(-1,dp(48)); searchLp.setMargins(0,dp(18),0,dp(18))
        content.addView(search,searchLp)
        val grid = LinearLayout(this).apply { orientation=LinearLayout.VERTICAL }
        val row1 = LinearLayout(this); val row2 = LinearLayout(this)
        row1.addView(tile("★","Favoriler") { Toast.makeText(this@MainActivity,"Favoriler yakında eklenecek",Toast.LENGTH_SHORT).show() }, LinearLayout.LayoutParams(0,dp(88),1f))
        row1.addView(tile("◷","Geçmiş") { Toast.makeText(this@MainActivity,"Geçmiş, ziyaret edilen sayfalardan oluşur",Toast.LENGTH_SHORT).show() }, LinearLayout.LayoutParams(0,dp(88),1f))
        row1.addView(tile("▣","Sekmeler") { Toast.makeText(this@MainActivity,"Şimdilik tek sekme destekleniyor",Toast.LENGTH_SHORT).show() }, LinearLayout.LayoutParams(0,dp(88),1f))
        row1.addView(tile("⚙","Ayarlar") { showMenu() }, LinearLayout.LayoutParams(0,dp(88),1f))
        row2.addView(tile("▯","Telefon") { desktop=false; web.settings.useWideViewPort=true; Toast.makeText(this@MainActivity,"Telefon görünümü seçildi",Toast.LENGTH_SHORT).show() }, LinearLayout.LayoutParams(0,dp(88),1f))
        row2.addView(tile("▱","Bilgisayar") { desktop=true; Toast.makeText(this@MainActivity,"Bilgisayar görünümü için sayfayı yenileyin",Toast.LENGTH_SHORT).show() }, LinearLayout.LayoutParams(0,dp(88),1f))
        row2.addView(tile("◉","Gizli sekme") { Toast.makeText(this@MainActivity,"Gizli sekme geliştirme aşamasında",Toast.LENGTH_SHORT).show() }, LinearLayout.LayoutParams(0,dp(88),1f))
        row2.addView(tile("⬟","Reklam engel") { adBlock=!adBlock; Toast.makeText(this@MainActivity,"Reklam engelleme: "+if(adBlock)"Açık" else "Kapalı",Toast.LENGTH_SHORT).show() }, LinearLayout.LayoutParams(0,dp(88),1f))
        grid.addView(row1); grid.addView(row2); content.addView(grid)
        content.addView(text("Hızlı erişim", 16f, gold, true))
        val links = LinearLayout(this).apply { orientation=LinearLayout.HORIZONTAL; gravity=Gravity.CENTER }
        links.addView(button("Google",gold,navy){navigate("https://www.google.com")})
        links.addView(button("YouTube",gold,navy){navigate("https://m.youtube.com")})
        links.addView(button("Wikipedia",gold,navy){navigate("https://tr.wikipedia.org")})
        content.addView(links)
        scroller.addView(content)
        return LinearLayout(this).apply { addView(scroller) }
    }

    private fun tile(symbol: String, label: String, action: () -> Unit): View {
        val box = LinearLayout(this).apply {
            orientation=LinearLayout.VERTICAL; gravity=Gravity.CENTER
            setPadding(dp(3),dp(8),dp(3),dp(8))
            background=android.graphics.drawable.GradientDrawable().apply {
                setColor(0xff091b2a.toInt()); setStroke(dp(1),0xff204b63.toInt()); cornerRadius=dp(12).toFloat()
            }
            setOnClickListener { action() }
        }
        box.addView(text(symbol,24f,gold,true))
        box.addView(text(label,10f,pale,true))
        val lp = LinearLayout.LayoutParams(0,dp(88),1f); lp.setMargins(dp(3),dp(4),dp(3),dp(4))
        box.layoutParams=lp
        return box
    }

    private fun text(value:String,size:Float,color:Int,bold:Boolean=false)=TextView(this).apply {
        text=value; textSize=size; setTextColor(color); gravity=Gravity.CENTER
        if(bold) typeface=Typeface.DEFAULT_BOLD
        setPadding(dp(3),dp(4),dp(3),dp(4))
    }
    private fun button(label:String,bg:Int,fg:Int,action:()->Unit)=TextView(this).apply {
        text=label; textSize=12f; gravity=Gravity.CENTER; setTextColor(fg); setBackgroundColor(bg)
        setPadding(dp(8),dp(10),dp(8),dp(10)); setOnClickListener{action()}
    }
    private fun navButton(label:String,action:()->Unit)=TextView(this).apply {
        text=label; textSize=27f; gravity=Gravity.CENTER; setTextColor(pale)
        layoutParams=LinearLayout.LayoutParams(0,dp(42),1f); setOnClickListener{action()}
    }
    private fun navigate(input:String) {
        var target=input.trim()
        if(target.isEmpty()) return
        if(!target.contains("://")) {
            target=if(target.contains(".") && !target.contains(" ")) "https://$target"
                  else "https://www.google.com/search?q="+java.net.URLEncoder.encode(target,"UTF-8")
        }
        home.visibility=View.GONE
        web.visibility=View.VISIBLE
        web.loadUrl(target)
    }
    private fun showHome(){ web.stopLoading(); web.visibility=View.GONE; home.visibility=View.VISIBLE; status.text="GÖKBÖRÜ  •  Ana sayfa" }
    private fun showMenu() {
        val items=arrayOf("Yeni sekme","Favoriler","Geçmiş","Reklam engelleme: "+if(adBlock)"Açık" else "Kapalı","Telefon görünümü","Bilgisayar görünümü","Hakkında")
        android.app.AlertDialog.Builder(this).setTitle("☪  Gökbörü Tarayıcı").setItems(items){_,which->
            when(which){
                0->showHome()
                1,2->Toast.makeText(this,"Bu özellik sonraki sürümde geliştirilecek",Toast.LENGTH_SHORT).show()
                3->{adBlock=!adBlock;Toast.makeText(this,"Reklam engelleme "+if(adBlock)"açıldı" else "kapatıldı",Toast.LENGTH_SHORT).show()}
                4->{desktop=false;Toast.makeText(this,"Telefon görünümü seçildi",Toast.LENGTH_SHORT).show()}
                5->{desktop=true;Toast.makeText(this,"Bilgisayar görünümü seçildi",Toast.LENGTH_SHORT).show()}
                6->android.app.AlertDialog.Builder(this).setMessage("Gökbörü Tarayıcı v1.0.0\nSelçuklu esintili koyu lacivert ve altın tema.").setPositiveButton("Tamam",null).show()
            }
        }.setNegativeButton("Kapat",null).show()
    }
    private fun dp(v:Int)= (v*resources.displayMetrics.density).toInt()
    override fun onBackPressed() {
        if (web.visibility==View.VISIBLE && web.canGoBack()) web.goBack() else showHome()
    }
}
