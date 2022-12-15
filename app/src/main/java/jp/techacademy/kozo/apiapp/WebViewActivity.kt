package jp.techacademy.kozo.apiapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_web_view.*
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog

class WebViewActivity : AppCompatActivity() {

    var onClickAddFavorite: ((Shop) -> Unit)? = null
    var onClickDeleteFavorite: ((Shop) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        var shop = intent.getSerializableExtra(KEY_ID)
        if (shop is Shop) {
            webView.loadUrl(if(shop.couponUrls.sp.isNotEmpty()) shop.couponUrls.sp else shop.couponUrls.pc)

            var isFavorite = FavoriteShop.findBy(shop.id) != null
            favoriteImageView.apply {
                setImageResource(if (isFavorite) R.drawable.ic_star else R.drawable.ic_star_border)
                setOnClickListener {
                    if (isFavorite) {
                        showConfirmDeleteFavoriteDialog(shop.id)
                    } else {
                        addFavoriteData(shop)
                    }
                }
            }
        }
    }

    /**
     *  Add favorite data
     */
    private fun addFavoriteData(shop: Shop){
        FavoriteShop.insert(FavoriteShop().apply {
            id = shop.id
            name = shop.name
            imageUrl = shop.logoImage
            url = if (shop.couponUrls.sp.isNotEmpty()) shop.couponUrls.sp else shop.couponUrls.pc
        })
    }

    /**
     *  Delete favorite data
     */
    private fun showConfirmDeleteFavoriteDialog(id: String) {
        AlertDialog.Builder(this)
            .setTitle(R.string.delete_favorite_dialog_title)
            .setMessage(R.string.delete_favorite_dialog_message)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                deleteFavoriteData(id)
            }
            .setNegativeButton(android.R.string.cancel) { _, _ ->}
            .create()
            .show()
    }
    private fun deleteFavoriteData(id: String){
        FavoriteShop.delete(id)
    }

    companion object {
        private const val KEY_ID = "key_id"
        fun start(activity: Activity, shop: Shop) {
            activity.startActivity(Intent(activity, WebViewActivity::class.java).putExtra(KEY_ID, shop))
        }
    }
}